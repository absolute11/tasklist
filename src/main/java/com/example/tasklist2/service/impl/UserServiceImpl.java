package com.example.tasklist2.service.impl;

import com.example.tasklist2.domain.exception.ResourceNotFoundException;
import com.example.tasklist2.domain.user.Role;
import com.example.tasklist2.domain.user.User;
import com.example.tasklist2.repository.UserRepository;
import com.example.tasklist2.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    @Cacheable(value = "UserService::getById", key = "#id")
    public User getById(final Long id) {
        return userRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("User not found"));
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "UserService::getByUsername", key = "#username")
    public User getByUsername(final String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(
                        () -> new ResourceNotFoundException("User not found"));
    }

    @Override
    @Transactional
    @Caching(put = {
            @CachePut(value = "UserService::getById",
                    key = "#user.id"),
            @CachePut(value = "UserService::getByUsername",
                    key = "#user.username")
    })
    public User update(final User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return user;
    }

    @Override
    @Transactional
    @Caching(cacheable = {
            @Cacheable(value = "UserService::getById",
                    key = "#user.id"),
            @Cacheable(value = "UserService::getByUsername",
                    key = "#user.username")
    })
    public User create(final User user) {
        if (userRepository.findByUsername(user.getUsername())
                .isPresent()) {
            throw new IllegalStateException("User already exists");
        }
        if (!user.getPassword().equals(user.getPasswordConfirmation())) {
            throw new IllegalStateException("Password and password confirmation"
                    + " do not match");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Set<Role> roles = Set.of(Role.ROLE_USER);
        user.setRoles(roles);
        userRepository.save(user);
        return user;
    }

    @Override
    @Transactional
    @Cacheable(value = "UserService::isTaskOwner",
            key = "#userId + '.' + #taskId")
    public boolean isTaskOwner(final Long userId, final Long taskId) {
        return userRepository.isTaskOwner(userId, taskId);
    }

    @Override
    @Transactional
    @CacheEvict(value = "UserService::getById", key = "#id")
    public void delete(final Long id) {
        userRepository.deleteById(id);

    }
}
