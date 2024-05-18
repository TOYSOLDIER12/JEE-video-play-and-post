package ma.xproce.video.config;

import ma.xproce.video.dao.entity.MyUserPrincipal;
import ma.xproce.video.service.CreatorManager;
import ma.xproce.video.service.dtos.CreatorDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    CreatorManager creatorManager;
    private final CustomAuthenticationProvider customAuthenticationProvider;

    public MyUserDetailsService(CustomAuthenticationProvider customAuthenticationProvider) {
        this.customAuthenticationProvider = customAuthenticationProvider;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        CreatorDTO creatorDTO = creatorManager.findByUsername(username);
        if (creatorDTO == null) {
            throw new UsernameNotFoundException("User not found: " + username);
        }
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(creatorDTO.getRole().getName()));
        return new MyUserPrincipal(creatorDTO, authorities);

    }


}
