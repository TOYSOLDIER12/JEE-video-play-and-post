package ma.xproce.video.service;

import ma.xproce.video.dao.entity.Role;
import ma.xproce.video.dao.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleService implements RoleManager{
    @Autowired
    private RoleRepository roleRepository;
    @Override
    public Role getRoleByRoleName(String roleName) {
        Role role = roleRepository.findByName(roleName);
        return role;
    }
}
