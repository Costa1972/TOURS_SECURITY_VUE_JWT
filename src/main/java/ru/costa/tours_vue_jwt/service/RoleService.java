package ru.costa.tours_vue_jwt.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.costa.tours_vue_jwt.entity.Role;
import ru.costa.tours_vue_jwt.repository.RoleRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public Role getRole(String roleName) {
        return roleRepository.findByRoleName(roleName)
                .orElseThrow(() -> new RuntimeException("Role not found"));
    }
}
