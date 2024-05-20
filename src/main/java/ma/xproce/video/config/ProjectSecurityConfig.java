package ma.xproce.video.config;

import ma.xproce.video.dao.entity.MyUserPrincipal;
import ma.xproce.video.service.dtos.CreatorDTO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
public class ProjectSecurityConfig {
    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.csrf((csrf) -> csrf.disable())
                .authorizeHttpRequests((requests)->requests
                        .requestMatchers("/editProfile","/post","/deleteVideo","/logout","/my-videos",  "/index","/updateVideo", "/admin").authenticated()
                        .requestMatchers("/**","/webjars/**","/sign", "/login", "/static/**", "/css/**","/javascript/**").permitAll())
                .formLogin((form) -> form
                        .loginPage("/login")
                        .successHandler(authenticationSuccessHandler())
                        .permitAll()
                )
                .logout((logout) -> logout.logoutSuccessUrl("/login"));
        ;
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return (request, response, authentication) -> {

            String redirectUrl = "/index";
            if (authentication.getPrincipal() instanceof MyUserPrincipal myUserPrincipal) {
                CreatorDTO creator = myUserPrincipal.getCreatorDTO();
                if (creator.getRole().getName() == null) {
                    redirectUrl = "/error";
                } else if (creator.getRole().getName().equalsIgnoreCase("admin")) {
                    redirectUrl = "/admin";
                }
            }
            response.sendRedirect(redirectUrl);
        };
    }

}
