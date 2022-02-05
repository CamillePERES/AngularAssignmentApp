package com.example.assignmentapp.security;

import com.example.assignmentapp.model.UserEntity;
import com.example.assignmentapp.model.UserRoleEntity;
import com.example.assignmentapp.repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Service
public class MyUserDetails implements UserDetailsService {

  @Autowired
  private IUserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {

    final UserEntity entity = userRepository.getUserByLogin(login);

    if (entity == null) {
      throw new UsernameNotFoundException("User '" + login + "' not found");
    }

    Set<SimpleGrantedAuthority> authorities = new HashSet<>();
    authorities.add(new SimpleGrantedAuthority("ROLE_" + entity.getRole()));

    return new org.springframework.security.core.userdetails.User(login, entity.getPassword(), authorities);
  }
}
