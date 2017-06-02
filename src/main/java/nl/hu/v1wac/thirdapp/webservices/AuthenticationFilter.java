package nl.hu.v1wac.thirdapp.webservices;

import java.io.IOException;

import javax.annotation.*;
import javax.ws.rs.*;
import javax.ws.rs.container.*;
import javax.ws.rs.core.*;
import javax.ws.rs.ext.*;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;

@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter {

	@Override
	public void filter(ContainerRequestContext requestCtx) throws IOException {
		// Users are treated as guests, unless a valid JWT is provided
		boolean isSecure = requestCtx.getSecurityContext().isSecure();
		MySecurityContext msc = new MySecurityContext("Unknown", "guest", isSecure);
		// Check if the HTTP Authorization header is present and formatted correctly
		String authHeader = requestCtx.getHeaderString(HttpHeaders.AUTHORIZATION);
		if (authHeader != null && authHeader.startsWith("Bearer ")) {
			// Extract the token from the HTTP Authorization header
			String token = authHeader.substring("Bearer".length()).trim();
			try {
				// Validate the token
				JwtParser parser = Jwts.parser().setSigningKey(AuthenticationResource.key);
				Claims claims = parser.parseClaimsJws(token).getBody();
				String user = claims.getSubject();
				String role = claims.get("role").toString();
				msc = new MySecurityContext(user, role, isSecure);
			} catch (JwtException | IllegalArgumentException e) {
				System.out.println("Invalid JWT, processing as guest!");
			}
		}
		requestCtx.setSecurityContext(msc);
	}
}