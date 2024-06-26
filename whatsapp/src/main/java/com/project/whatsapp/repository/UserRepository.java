package com.project.whatsapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.project.whatsapp.modal.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	 User findByEmail(String email);

	 @Query("SELECT u FROM User u WHERE u.fullName LIKE %?1% OR u.email LIKE %?1%")
	 List<User> searchUser(@Param("query") String query);
}
