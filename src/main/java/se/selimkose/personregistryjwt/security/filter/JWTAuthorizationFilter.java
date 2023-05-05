package se.selimkose.personregistryjwt.security.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import se.selimkose.personregistryjwt.security.SecurityConstants;

import java.io.IOException;
import java.util.Arrays;


@AllArgsConstructor
public class JWTAuthorizationFilter extends OncePerRequestFilter {

    //Authorization: Bearer JWT
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String header = request.getHeader("Authorization"); //Gives the 'Bearer token'

        //Check if the there is a token and if it starts with 'Bearer'.
        if (header == null || !header.startsWith(SecurityConstants.BEARER)) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = header.replace(SecurityConstants.BEARER, ""); // We filter out 'Bearer' from the token

        //Get the username from the token
        String user = JWT.require(Algorithm.HMAC512(SecurityConstants.SECRET_KEY))
                .build()
                .verify(token)
                .getSubject();
        //Get the role ex [ADMIN], from the token and format as a string and remove brackets, ex ADMIN
        String authority = JWT.require(Algorithm.HMAC512(SecurityConstants.SECRET_KEY))
                .build()
                .verify(token)
                .getClaim("role").asString().replace("[", "").replace("]","");

        Authentication authentication = new UsernamePasswordAuthenticationToken
                (user, null, Arrays.asList(new SimpleGrantedAuthority(authority)));

        //Adds authentication to the SecurityContextHolder
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }
}
