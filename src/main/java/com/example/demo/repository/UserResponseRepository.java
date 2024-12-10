package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.UserResponse;

public interface UserResponseRepository extends JpaRepository<UserResponse,Long>{
	List<UserResponse> findByUserId(Long userId);

//	Optional<UserResponse> findByUserIdAndQuestionId(Long userId, Long questionId);

	Optional<UserResponse> findByUserIdAndQuestionIdAndAttemptId(Long userId, Long questionId, Long attemptId);

	List<UserResponse> findByUserIdAndAttemptId(Long userId, Long attemptId);
	
}
