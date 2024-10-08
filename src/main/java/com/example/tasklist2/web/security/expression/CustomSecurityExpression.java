package com.example.tasklist2.web.security.expression;

import com.example.tasklist2.domain.user.Role;
import com.example.tasklist2.service.UserService;
import com.example.tasklist2.web.security.JwtEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;


@Component("cse")
@RequiredArgsConstructor
public class CustomSecurityExpression {

    private final UserService userService;

    public boolean canAccessUser(
            final Long id
    ) {
        JwtEntity user = getPrincipal();
        Long userId = user.getId();

        return userId.equals(id) || hasAnyRole(Role.ROLE_ADMIN);
    }

    private boolean hasAnyRole(
            final Role... roles
    ) {
        Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();
        for (Role role : roles) {
            SimpleGrantedAuthority authority
                    = new SimpleGrantedAuthority(role.name());
            if (authentication.getAuthorities().contains(authority)) {
                return true;
            }
        }
        return false;
    }

    public boolean canAccessTask(
            final Long taskId
    ) {
        JwtEntity user = getPrincipal();
        Long id = user.getId();

        return userService.isTaskOwner(id, taskId);
    }

    private JwtEntity getPrincipal() {
        Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();
        return (JwtEntity) authentication.getPrincipal();
    }

}
