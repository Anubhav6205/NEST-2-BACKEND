package Server.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import Server.model.UserModel;

import java.security.Key;
import java.util.Date;

import org.springframework.stereotype.Service;

@Service
public class JwtService {

	//
    public static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    public  String generateToken(UserModel user) {
        return Jwts.builder()
                .setSubject(user.getId())
                .claim("role",user.getRole())
                .claim("id",user.getId())
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS256")
                .setExpiration(new Date(System.currentTimeMillis() + 36000000)) // 1 hour
                .signWith(SECRET_KEY, SignatureAlgorithm.HS512)
                .compact();
        //compact jwt is returned after token is generated
    }

    public static UserModel parseToken(String token) {
        @SuppressWarnings("deprecation")
		Jws<Claims> claimsJws = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
        Claims claims = claimsJws.getBody();
        String userId = claims.getSubject();

        UserModel user = new UserModel();
        user.setId(userId);

        return user;
    }

    @SuppressWarnings("deprecation")
	public  boolean verifyToken(String token) {
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    public boolean verifyTokenInHeader(String authorizationHeader)
    {
    	try {
    		if(authorizationHeader!=null && authorizationHeader.startsWith("Bearer"))
    		{
    			String token=authorizationHeader.substring(7);
    			if(verifyToken(token))
    			{
    				System.out.println("Token verified");
    				return true;
    			}
    			else {
    				System.out.println(token);
					
    				System.out.println("Token not verified");
    				return false;
				}
    		}
    		else {
			
    			System.out.println("No token found");
    			return false;
			}
    	}
    	catch (Exception e) {
		  return false;
		}
    }
}
