package timesheet.service;

import timesheet.orm.entity.Users;
import timesheet.orm.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {

  @Autowired
  protected UsersRepository usersRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Optional<Users> foundUser = usersRepository.findByUsername(username);
    if (!foundUser.isPresent()) {
      throw new UsernameNotFoundException("Username or password is wrong.");
    }
    return foundUser.get();
  }

}
