package com.example.assignmentapp.security;

import com.example.assignmentapp.model.UserEntity;
import com.example.assignmentapp.model.UserRoleEntity;
import com.example.assignmentapp.repositories.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyUserDetails implements UserDetailsService {

  @Autowired
  private final IUserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
    final UserEntity entity = userRepository.getUserByLogin(login);

    if (entity == null) {
      throw new UsernameNotFoundException("User '" + login + "' not found");
    }

    return org.springframework.security.core.userdetails.User//
        .withUsername(login)//
        .password(entity.getPassword())//
        .authorities(UserRoleEntity.valueOf(entity.getRole()))//
        .accountExpired(false)//
        .accountLocked(false)//
        .credentialsExpired(false)//
        .disabled(false)//
        .build();
  }
}
