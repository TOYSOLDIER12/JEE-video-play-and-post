package ma.xproce.video.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class ProjectSecurityConfig {
    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.csrf((csrf) -> csrf.disable())
                .authorizeHttpRequests((requests)->requests
                        .requestMatchers("/editProfile","/post","/deleteVideo","/logout","/my-videos",  "/index","/updateVideo").authenticated()
                        .requestMatchers("/**","/webjars/**","/sign", "/login", "/static/**", "/css/**","/javascript/**").permitAll())
                .formLogin((form) -> form
                        //.loginPage("/login")
                        .defaultSuccessUrl("/index", true)
                        .permitAll()
                )
                //.logout((logout) -> logout.logoutSuccessUrl("/login"));
        ;
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

}
