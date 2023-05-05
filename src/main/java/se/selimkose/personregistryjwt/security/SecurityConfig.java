package se.selimkose.personregistryjwt.security;


import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import se.selimkose.personregistryjwt.security.filter.AuthenticationFilter;
import se.selimkose.personregistryjwt.security.filter.ExceptionHandlerFilter;
import se.selimkose.personregistryjwt.security.filter.JWTAuthorizationFilter;
import se.selimkose.personregistryjwt.security.manager.CustomAuthenticationManager;
import se.selimkose.personregistryjwt.service.UserService;


@Configuration
@AllArgsConstructor
public class SecurityConfig {
    BCryptPasswordEncoder bCryptPasswordEncoder;
    CustomAuthenticationManager customAuthenticationManager;
    UserService userService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        AuthenticationFilter authenticationFilter = new AuthenticationFilter(customAuthenticationManager);
        authenticationFilter.setFilterProcessesUrl("/authenticate");

        http
                .csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers(HttpMethod.POST,"user/register").permitAll()
                .requestMatchers(HttpMethod.GET).hasAuthority("ADMIN")
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(new ExceptionHandlerFilter(),AuthenticationFilter.class)
                .addFilter(authenticationFilter)
                .addFilterAfter(new JWTAuthorizationFilter(),AuthenticationFilter.class)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        return http.build();
    }

//    @Bean
//    public UserDetailsService user() {
//        UserDetails admin = User.builder()
//                .username("admin")
//                .password(bCryptPasswordEncoder.encode("password"))
//                .roles("ADMIN")
//                .build();
//
//        UserDetails user = User.builder()
//                .username("user")
//                .password("password")
//                .roles("USER")
//                .build();
//
//        return new InMemoryUserDetailsManager(admin,user);
//    }

}