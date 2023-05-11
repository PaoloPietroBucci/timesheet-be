package timesheet.configuration;

import timesheet.util.constants.AppConstants;
import timesheet.util.constants.HeaderConstants;
import timesheet.util.constants.MDCConstants;
import timesheet.util.exceptions.ClientUnauthorizedException;
import timesheet.util.exceptions.TokenExpiredException;
import timesheet.orm.entity.Users;
import timesheet.ProjectProperties;
import timesheet.service.MyUserDetailsService;
import timesheet.util.AuthUtil;
import timesheet.util.GenericUtil;
import timesheet.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.UUID;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

  final protected Logger logger = LoggerFactory.getLogger(JwtRequestFilter.class);

  @Autowired
  protected ProjectProperties projectProperties;


  @Autowired
  protected MyUserDetailsService userDetailsService;

  @Autowired
  protected JwtUtil jwtUtil;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
    if (!HttpMethod.OPTIONS.name().equals(request.getMethod())
        && !request.getRequestURI().contains(AppConstants.SWAGGER_URI)
        && !request.getRequestURI().contains(AppConstants.API_DOCS_URI)
        && !request.getRequestURI().contains(AppConstants.BATCH)
        && !request.getRequestURI().contains(AppConstants.WEBHOOK)) {
      manageRequestToken(request);
      manageResponseToken(request, response);
      manageJwtToken(request);
      //manageBrowserInfo(request);
     // manageDeviceInfo(request);
    }

    chain.doFilter(request, response);
  }

  /**
   * @param request HttpServletRequest
   * @param header  String
   * @return boolean
   */
  private String extractHeader(HttpServletRequest request, String header) {
    String out = request.getHeader(header);
    if (GenericUtil.isNullOrEmpty(out)) {
      out = request.getHeader(header.toLowerCase());
    }
    if (GenericUtil.isNullOrEmpty(out)) {
      out = request.getHeader(header.toUpperCase());
    }
    return out;
  }

  /**
   * @param request HttpServletRequest
   */
  private void manageRequestToken(HttpServletRequest request) {
    if (!projectProperties.getSecurityKey().equals(extractRequestToken(request))) {
      throw new ClientUnauthorizedException();
    }
  }

  /**
   * @param request  HttpServletRequest
   * @param response HttpServletResponse
   */
  private void manageResponseToken(HttpServletRequest request, HttpServletResponse response) {
    final String token = extractResponseToken(request);
    final String clientIP = extractClientIP(request);
    MDC.put(MDCConstants.MDC_CLIENT_IP_KEY, clientIP);
    MDC.put(MDCConstants.MDC_UUID_TOKEN_KEY, token);
    response.addHeader(HeaderConstants.RESPONSE_TOKEN, token);
  }

  /**
   * @param request HttpServletRequest
   * @throws TokenExpiredException TokenExpiredException
   */
  private void manageJwtToken(HttpServletRequest request) throws TokenExpiredException {
    final String authorizationHeader = extractHeader(request, HeaderConstants.AUTHORIZATION);
    String username = null;
    String jwt = null;
    if (authorizationHeader != null && authorizationHeader.startsWith(HeaderConstants.BEARER)) {
      jwt = authorizationHeader.substring(HeaderConstants.BEARER.length());
      username = jwtUtil.extractUsername(jwt);
    }

    if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
      if (jwtUtil.isTokenExpired(jwt)) {
        throw new TokenExpiredException();
      }

      Users user = (Users) this.userDetailsService.loadUserByUsername(username);
      if (jwtUtil.validateToken(jwt, user)) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
            user,
            null,
            Collections.singletonList(new SimpleGrantedAuthority(user.getRole().name()))
        );
        usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
      }
    }
  }

  /**
   * @param request HttpServletRequest
   */
 /* private void manageBrowserInfo(HttpServletRequest request) {
    if (AuthUtil.getLoggedUser() != null) {
      String jsonString = extractHeader(request, HeaderConstants.BROWSER_INFO);
      if (GenericUtil.isNotNullOrEmpty(jsonString)) {
        browserInfoService.manage(jsonString);
      }
    }
  }

  private void manageDeviceInfo(HttpServletRequest request) {
    if (AuthUtil.getLoggedUser() != null) {
      String jsonString = extractHeader(request, HeaderConstants.DEVICE_INFO);
      if (GenericUtil.isNotNullOrEmpty(jsonString)) {
        deviceInfoService.manage(jsonString);
      }
    }
  }

  /**
   * @param request HttpServletRequest
   * @return String
   */
  private String extractRequestToken(HttpServletRequest request) {
    return extractHeader(request, HeaderConstants.REQUEST_TOKEN);
  }

  /**
   * @param request HttpServletRequest
   * @return String
   */
  private String extractResponseToken(HttpServletRequest request) {
    String token = extractHeader(request, HeaderConstants.RESPONSE_TOKEN);
    if (GenericUtil.isNullOrEmpty(token)) {
      token = UUID.randomUUID().toString().toUpperCase().replace("-", "");
    }
    return token;
  }

  /**
   * @param request HttpServletRequest
   * @return String
   */
  private String extractClientIP(HttpServletRequest request) {
    return request.getRemoteAddr();
  }

}
