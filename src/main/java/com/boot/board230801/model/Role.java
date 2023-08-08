package com.boot.board230801.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Role {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;

//  User 의 private List<Role> roles;
  @ManyToMany(mappedBy = "roles")
//  Set<Student> likes;
  private List<User> users;
}
