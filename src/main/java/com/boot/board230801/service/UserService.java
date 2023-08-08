package com.boot.board230801.service;

import com.boot.board230801.model.Role;
import com.boot.board230801.model.User;
import com.boot.board230801.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private PasswordEncoder passwordEncoder;
  public User save(User user){
//    사용자 패스워드를 가져와서 암호화
    String encodedPassword = passwordEncoder.encode(user.getPassword());
//    암호화된 패스워드를 비밀번호로 저장
    user.setPassword(encodedPassword);
//    활성화된 사용자
    user.setEnabled(true);
    Role role = new Role();
    role.setId(1L);
    user.getRoles().add(role);

    return userRepository.save(user);
  }
}
