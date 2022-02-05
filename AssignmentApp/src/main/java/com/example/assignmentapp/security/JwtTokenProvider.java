package com.example.assignmentapp.security;

import java.util.*;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import com.example.assignmentapp.dto.LoginResultDto;
import com.example.assignmentapp.enumeration.UserExceptionType;
import com.example.assignmentapp.exceptions.UserException;
import com.example.assignmentapp.model.UserRoleEntity;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {

  /**
   * THIS IS NOT A SECURE PRACTICE! For simplicity, we are storing a static key here. Ideally, in a
   * microservices environment, this key would be kept on a config-server.
   */

  private final String rolesKey = "roles";

  @Value("${security.jwt.token.secret-key:secret-key}")
  private String secretKey;

  @Value("${security.jwt.token.expire-length:3600000}")
  private long validityInMilliseconds = 36000000; // 1h

  @Autowired
  private MyUserDetails myUserDetails;

  @PostConstruct
  protected void init() {
    secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
  }

  public LoginResultDto createToken(String login, String appUserRoles) {

    Claims claims = Jwts.claims().setSubject(login);
    claims.put(rolesKey, UserRoleEntity.valueOf(appUserRoles).getAuthority());

    Date now = new Date();
    Date validity = new Date(now.getTime() + validityInMilliseconds);

    String token = Jwts.builder()//
        .setClaims(claims)//
        .setIssuedAt(now)//
        .setExpiration(validity)//
        .signWith(SignatureAlgorithm.HS256, secretKey)//
        .compact();

    LoginResultDto loginResult = new LoginResultDto();
    loginResult.setToken(token);
    loginResult.setExpireAt(validity.getTime());

    return loginResult;
  }

  public Authentication getAuthentication(String token) {
    UserDetails userDetails = myUserDetails.loadUserByUsername(getUsername(token));
    return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
  }

  public String getUsername(String token) {
    return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
  }

  public String resolveToken(HttpServletRequest req) {
    String bearerToken = req.getHeader("Authorization");
    if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
      return bearerToken.substring(7);
    }
    return null;
  }

  public boolean validateToken(String token) throws UserException {
    try {
      Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
      return true;
    } catch (JwtException | IllegalArgumentException e) {
      throw new UserException(UserExceptionType.TOKEN_NOT_VALID);
    }
  }

  UsernamePasswordAuthenticationToken getAuthenticationToken(final String token) {

    UserDetails userDetails = myUserDetails.loadUserByUsername(getUsername(token));

    final JwtParser jwtParser = Jwts.parser().setSigningKey(secretKey);

    final Jws<Claims> claimsJws = jwtParser.parseClaimsJws(token);

    final Claims claims = claimsJws.getBody();

    final Collection<? extends GrantedAuthority> authorities =
            Arrays.stream(claims.get(rolesKey).toString().split(","))
                    .map(e -> new SimpleGrantedAuthority("ROLE_"+e))
                    .collect(Collectors.toList());

    return new UsernamePasswordAuthenticationToken(userDetails, "", authorities);
  }

  private Claims getAllClaimsFromToken(String token) {
    return Jwts.parser()
            .setSigningKey(secretKey)
            .parseClaimsJws(token)
            .getBody();
  }

}
