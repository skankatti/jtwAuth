package jwt.start.config;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jwt.start.account.AccountMODL;

@Component
public class JwtTokenHelper {

	public static final String JWT_TOKEN_TIME = "18000";
	
	private String JWT_SECRET_KEY = "jtwTokenKey";
	
	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	public Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	private Claims extractAllClaims(String token) {
		return Jwts.parser().setSigningKey(JWT_SECRET_KEY).parseClaimsJws(token).getBody();
	}

	public  Boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	public String generateToken(AccountMODL account) {
		
		Map<String, Object> claims = new HashMap<>();
		claims.put("accountId", account.getId());
		return createToken( claims, account.getEmail ());
	}

	private String createToken(Map<String, Object> claims, String subject) {
		int tokkenTime=Integer.parseInt(JWT_TOKEN_TIME);
		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + tokkenTime * 60 * 60 * 10)) // tokkenTime = 10000
				.signWith(SignatureAlgorithm.HS256, JWT_SECRET_KEY).compact();
	}

	public Boolean validateToken(String token, UserDetails userDetails) {
		System.out.println("checking validation for token "+token);
		final String username = extractUsername(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

}
