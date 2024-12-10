package com.example.demo.service;

import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Admin;
import com.example.demo.entity.Questions;
import com.example.demo.exception.InvalidcredentialsException;
import com.example.demo.exception.QuestionNotFoundException;
import com.example.demo.repository.AdminRepository;
import com.example.demo.repository.QuestionRepository;

import lombok.extern.slf4j.Slf4j;



@Service
@Slf4j
public class AdminServiceImpl implements AdminService{
	@Autowired
	private AdminRepository adminRepository;
	
	@Autowired
    private QuestionRepository questionRepository;
	@Override
	public String login(String username, String password) {
		// TODO Auto-generated method stub
		log.info("Attempting to login with username: {}", username);
		Admin admin = adminRepository.findByusername(username).orElseThrow(()->
	 new InvalidcredentialsException("Invalid Credentials")
		);
		if(admin.getPassword().equals(password)) {
			 log.info("Admin {} logged in successfully.", username);
			
			return "Admin login successfully";
		}
		else {
			
			throw new InvalidcredentialsException("Invalid Credentials");
		}
	}
//	@Override
//	public void addQuestion(Questions question) {
//		// TODO Auto-generated method stub
//		log.info("Adding question with title: {}", question.getQuestionText());
//		Questions questions=new Questions();
//		questions.setQuestionText(question.getQuestionText());
//		questions.setOption1(question.getOption1());
//		questions.setOption2(question.getOption2());
//		questions.setOption3(question.getOption3());
//		questions.setOption4(question.getOption4());
//		questions.setQuestionType(question.getQuestionType());
//		if("multi-select".equals(question.getQuestionType())) {
//			questions.setCorrectOptions(question.getCorrectOptions());
//			questions.setCorrectOption(0);
//		}else {
//			questions.setCorrectOption(question.getCorrectOption());
//			questions.setCorrectOptions(null);
//		}
//		questionRepository.save(questions);
//		 log.info("Question added successfully");
//		
//		
//	}
	@Override
	public void addQuestion(Questions question) {
	    log.info("Adding question with title: {}", question.getQuestionText());
	    questionRepository.save(question);
	    log.info("Question added successfully");
	}

	@Override
	public void deleteQuestion(Long id) {
		// TODO Auto-generated method stub
		 log.info("Attempting to delete question with id: {}", id);
	Questions question=questionRepository.findById(id).orElseThrow(()-> 
	new QuestionNotFoundException("Question not found for given questionid:"+id));
	questionRepository.deleteById(question.getId());
	
	 log.info("Question with id: {} deleted successfully", id); 
	}
	@Override
	public Questions editQuestion(Long questionId, Questions updatedQuestion) {
	
		  Optional<Questions> existingQuestionOpt = questionRepository.findById(questionId);

	        if (existingQuestionOpt.isPresent()) {
	            Questions existingQuestion = existingQuestionOpt.get();
	            
	           
	            existingQuestion.setQuestionText(updatedQuestion.getQuestionText());
	            existingQuestion.setOption1(updatedQuestion.getOption1());
	            existingQuestion.setOption2(updatedQuestion.getOption2());
	            existingQuestion.setOption3(updatedQuestion.getOption3());
	            existingQuestion.setOption4(updatedQuestion.getOption4());
	            existingQuestion.setCorrectOption(updatedQuestion.getCorrectOption());
	            existingQuestion.setQuestionType(updatedQuestion.getQuestionType());
	            
	            
	       
	            return questionRepository.save(existingQuestion);
	        } else {
	            throw new QuestionNotFoundException("Question not found for given questionid:"+questionId);
	        }
	}
	@Override
	public Optional<Questions> findById(Long id) {
		// TODO Auto-generated method stub
		return questionRepository.findById(id);
	}
	

}
