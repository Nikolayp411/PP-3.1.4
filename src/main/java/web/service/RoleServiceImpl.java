package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.model.Role;
import web.repository.RoleRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public List<Role> listRoles() {
        return roleRepository.findAll();
    }

    @Override
    public Role getRoleById(Long id) {
        return roleRepository.findById(id).orElse(null);
    }

    @Override
    public Collection<Role> getRolesById(List<Long> roleIds) {
        Collection<Role> roles = new ArrayList<>();
        for (Long roleId : roleIds) {
            Role role = getRoleById(roleId);
            if (role != null) {
                roles.add(role);
            }
        }
        return roles;
    }
}
