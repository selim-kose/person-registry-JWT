package se.selimkose.personregistryjwt.security.manager;


import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import se.selimkose.personregistryjwt.entity.User;
import se.selimkose.personregistryjwt.service.UserService;

import java.util.List;

@AllArgsConstructor
@Component
public class CustomAuthenticationManager  implements AuthenticationManager {

    UserService userService;
    BCryptPasswordEncoder bCryptPasswordEncoder;


    //Where we check if the given password matches with the password in DB

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        User user = userService.getUser(authentication.getName());
        if(!bCryptPasswordEncoder.matches(authentication.getCredentials().toString(),user.getPassword())) {
            throw new BadCredentialsException("You provided wrong password");
        }
        return new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword(), List.of(new SimpleGrantedAuthority(user.getRole())));
    }
}
