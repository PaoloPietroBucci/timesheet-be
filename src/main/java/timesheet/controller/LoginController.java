package timesheet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import timesheet.ControllerRoute;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import timesheet.ManageException;
import timesheet.libs.models.AuthenticationRequest;
import timesheet.libs.models.AuthenticationResponse;
import timesheet.orm.entity.Users;
import timesheet.orm.repository.UsersRepository;
import timesheet.service.MyUserDetailsService;
import timesheet.util.*;
import timesheet.util.annotations.HandleAuthentication;
import timesheet.util.annotations.HandleLog;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.Optional;

@RestController
public class LoginController {

    @Autowired
    protected AuthenticationManager authenticationManager;

    @Autowired
    protected JwtUtil jwtTokenUtil;

    @Autowired
    protected MyUserDetailsService userDetailsService;


    @Autowired
    protected UsersRepository usersRepository;


    @PersistenceContext
    protected EntityManager entityManager;

    @HandleLog
    @HandleAuthentication
    @GetMapping(ControllerRoute.CURRENT_USER)
    public ResponseEntity<?> user() {
        try {
            Users user = AuthUtil.getLoggedUser();
            Users response = usersRepository.findByUsername(user.getUsername()).get().clean();
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return ManageException.manageControllerException(e);
        } finally {
            entityManager.close();
        }
    }

    @HandleLog
    @PostMapping(value = ControllerRoute.AUTHENTICATE)
    public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequest authenticationRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername().toLowerCase(), authenticationRequest.getPassword())
            );

            final Users userDetails = (Users) userDetailsService.loadUserByUsername(authenticationRequest.getUsername().toLowerCase());
            if (!userDetails.getActive()) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }

            final String jwt = jwtTokenUtil.generateToken(userDetails);
            final String refreshJwt = jwtTokenUtil.generateRefreshToken(userDetails);
            final Date expiration = jwtTokenUtil.extractExpiration(jwt);
            return new ResponseEntity<>(new AuthenticationResponse(jwt, refreshJwt, expiration), HttpStatus.OK);
        } catch (Exception e) {
            return ManageException.manageControllerException(e);
        } finally {
            entityManager.close();
        }
    }
}