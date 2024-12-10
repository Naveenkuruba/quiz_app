package com.example.demo.service;

import java.util.List;

import java.util.Map;

import com.example.demo.entity.Questions;
import com.example.demo.entity.User;
import com.example.demo.entity.UserResponse;


public interface UserService {

	

	public void saveUser(User user);

	public void saveUserResponse(Long userId, Long questionId, List<Integer> selectedOption,Long attemptId);

	public void validateAllQuestionsAnswered(Long userId);

	public int getQuizResult(Long userId,Long attemptId);

	public List<Questions> getAllquestions();

	public List<Map<String, Object>> getUserQuizReview(Long userId,Long attemptId);


	

	

	

}
