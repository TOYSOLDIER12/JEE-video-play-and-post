package ma.xproce.video.dao.entity;

import lombok.Getter;
import lombok.Setter;
import ma.xproce.video.service.dtos.CreatorDTO;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;


@Getter
@Setter
public class MyUserPrincipal implements UserDetails {
    private CreatorDTO creatorDTO;
    private List<GrantedAuthority> authorities;
    public MyUserPrincipal(CreatorDTO creatorDTO, List<GrantedAuthority> authorities) {
        this.creatorDTO = creatorDTO;
        this.authorities = authorities;
    }
    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return "";
    }

    @Override
    public String getUsername() {
        return creatorDTO.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
