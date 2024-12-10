package com.example.demo.service;

import java.util.Optional;

import com.example.demo.entity.Questions;

public interface AdminService {

	public String login(String username, String password);

	public void addQuestion(Questions question);

	public void deleteQuestion(Long id);

	public Questions editQuestion(Long questionId, Questions updatedQuestion);

	public Optional<Questions> findById(Long id);

	

}
