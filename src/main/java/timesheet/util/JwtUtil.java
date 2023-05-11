package timesheet.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import timesheet.orm.entity.Users;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtUtil {

  private final long MS_TO_EXPIRE = 1000L * 60 * 60 * 24 * 365; // to manage permanent login, set token expire to 1year
  @Value("hdnjcnnanhBHBWVBLELEF")
  private String SECRET_KEY;

  /**
   * @param token String
   * @return String
   */
  public String extractUsername(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  /**
   * @param token String
   * @return Date
   */
  public Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }

  /**
   * @param <T>            T
   * @param token          String
   * @param claimsResolver Function<Claims, T>
   * @return T
   */
  public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }

  /**
   * @param token String
   * @return Claims
   */
  private Claims extractAllClaims(String token) {
    return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
  }

  /**
   * @param token String
   * @return Boolean
   */
  public Boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }

  /**
   * @param userDetails Users
   * @return String
   */
  public String generateToken(Users userDetails) {
    Map<String, Object> claims = new HashMap<>();
    return createToken(claims, userDetails.getUsername().toLowerCase(), false);
  }

  /**
   * @param userDetails Users
   * @return String
   */
  public String generateRefreshToken(Users userDetails) {
    Map<String, Object> claims = new HashMap<>();
    return createToken(claims, userDetails.getUsername().toLowerCase(), true);
  }

  /**
   * @param claims  Map<String, Object>
   * @param subject String
   * @param refresh boolean
   * @return String
   */
  private String createToken(Map<String, Object> claims, String subject, boolean refresh) {
    long MS_TO_EXPIRE_REFRESH = MS_TO_EXPIRE * 6;
    return Jwts.builder().setClaims(claims).setSubject(subject)
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + (refresh ? MS_TO_EXPIRE_REFRESH : MS_TO_EXPIRE)))
        .signWith(SignatureAlgorithm.HS512, SECRET_KEY).compact();
  }

  /**
   * @param token       String
   * @param userDetails Users
   * @return Boolean
   */
  public Boolean validateToken(String token, Users userDetails) {
    final String username = extractUsername(token);
    return (username.equals(userDetails.getUsername().toLowerCase()) && !isTokenExpired(token));
  }

}
