package com.example.booktree.role.service;

import com.example.booktree.enums.RoleType;
import com.example.booktree.exception.BusinessLogicException;
import com.example.booktree.exception.ExceptionCode;
import com.example.booktree.role.entity.Role;
import com.example.booktree.role.repository.RoleRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    // 서버 시작 시 기본 Role 등록 (ADMIN, USER)
    @PostConstruct
    public void initRoles() {
        Arrays.asList("ADMIN", "USER").forEach(roleName -> {
            if (!roleRepository.existsByRole(roleName)) {
                Role role = Role.builder()
                        .role(RoleType.valueOf(roleName))
                        .build();
                roleRepository.save(role);
            }
        });
    }

    // 모든 Role 조회
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    // Role 이름으로 조회
    public Role findByRoleName(String roleName) {
        return roleRepository.findByRole(roleName)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.ROLE_NOT_FOUND);
    }
}
