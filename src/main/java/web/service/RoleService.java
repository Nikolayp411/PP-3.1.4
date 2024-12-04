package web.service;

import web.model.Role;

import java.util.Collection;
import java.util.List;

public interface RoleService {
    List<Role> listRoles();
    Role getRoleById(Long id);
    Collection<Role> getRolesById(List<Long> roleIds);
}