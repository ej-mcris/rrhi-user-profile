package com.crud.user.profile.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.crud.user.profile.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
