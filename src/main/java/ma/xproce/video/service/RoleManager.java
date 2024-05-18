package ma.xproce.video.service;

import ma.xproce.video.dao.entity.Role;

public interface RoleManager {
    public Role getRoleByRoleName(String roleName);
}
