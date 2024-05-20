package ma.xproce.video.config;

import ma.xproce.video.service.CreatorManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private CreatorManager creatorManager;



    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();

        String password = authentication.getCredentials().toString();

        boolean authenticated = creatorManager.checkLogin(username, password);

        if (authenticated) {
            return new UsernamePasswordAuthenticationToken(
                    username, password, new ArrayList<>());
        } else {
            throw new BadCredentialsException("hhhh ktab bchwiya");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
