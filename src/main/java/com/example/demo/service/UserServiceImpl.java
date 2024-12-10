package com.example.demo.service;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.example.demo.entity.Questions;

import com.example.demo.entity.User;
import com.example.demo.entity.UserResponse;
import com.example.demo.exception.InvalidOptionException;
import com.example.demo.exception.NotAllQuestionAnsweredException;
import com.example.demo.exception.QuestionNotFoundException;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.repository.QuestionRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.UserResponseRepository;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private QuestionRepository questionRepository;
	@Autowired
	private UserResponseRepository userResponseRepository;


    
	@Override
	public void saveUser(User user) {
		// TODO Auto-generated method stub
		userRepository.save(user);
	}


 
	@Override
	public void saveUserResponse(Long userId, Long questionId, List<Integer> selectedOption,Long attemptId) {
	   
	    Questions question = questionRepository.findById(questionId)
	            .orElseThrow(() -> new QuestionNotFoundException("Question not found for given question ID: " + questionId));

	        User user = userRepository.findById(userId)
	            .orElseThrow(() -> new UserNotFoundException("No User found for given ID: " + userId));

	        // Check if a response already exists for this question in this attempt
	        Optional<UserResponse> existingResponseOpt = userResponseRepository.findByUserIdAndQuestionIdAndAttemptId(userId, questionId, attemptId);

	        UserResponse response;
	        if (existingResponseOpt.isPresent()) {
	            // Update the existing response with new selected options
	            response = existingResponseOpt.get();
	            response.setSelectedOption(selectedOption);
	        } else {
	            // Create a new response for the current attempt
	            response = new UserResponse();
	            response.setUser(user);
	            response.setQuestion(question);
	            response.setAttemptId(attemptId);  // Set the attempt ID
	            response.setSelectedOption(selectedOption);
	        }

	        userResponseRepository.save(response);
	}

	@Override
	public void validateAllQuestionsAnswered(Long userId) {
		// TODO Auto-generated method stub
		List<UserResponse> responses = userResponseRepository.findByUserId(userId);

		if (responses.size() < getAllquestions().size()) {
			throw new NotAllQuestionAnsweredException("Not all questions are answered.");
		}

	}

	@Override
	public int getQuizResult(Long userId,Long attemptId) {
		// TODO Auto-generated method stub
		 List<UserResponse> responses = userResponseRepository.findByUserIdAndAttemptId(userId,attemptId);
		    int correctCount = 0;
		    
		    for (UserResponse response : responses) {
		        // Get the selected options and the correct options
		        List<Integer> selectedOptions = response.getSelectedOption();
		        List<Integer> correctOptions = response.getQuestion().getCorrectOption();

		        // Compare selected options with correct options
		        if (selectedOptions != null && correctOptions != null) {
		            // Check if the selected options match the correct options
		            boolean isCorrect = selectedOptions.size() == correctOptions.size() && 
		                selectedOptions.containsAll(correctOptions);
		                
		            // Increment the correct count if the answer is correct
		            if (isCorrect) {
		                correctCount++;
		            }
		        }
		    }
		    
		    // Return the count of correct answers
		    return correctCount;
	}

	@Override
	public List<Questions> getAllquestions() {
		// TODO Auto-generated method stub
		return questionRepository.findAll();
	}
	@Override
	 public List<Map<String, Object>> getUserQuizReview(Long userId,Long attemptId) {
		   List<UserResponse> responses = userResponseRepository.findByUserIdAndAttemptId(userId,attemptId);
		    List<Map<String, Object>> reviewData = new ArrayList<>();

		    // Get all questions
		    List<Questions> questions = getAllquestions();

		    for (Questions question : questions) {
		        Map<String, Object> questionReview = new HashMap<>();
		        questionReview.put("questionId", question.getId());
		        questionReview.put("questionText", question.getQuestionText());
		        questionReview.put("correctOption", List.of(question.getCorrectOption()));
		        questionReview.put("options", List.of(
		            question.getOption1(),
		            question.getOption2(),
		            question.getOption3(),
		            question.getOption4()
		        ));
		        
		        // Initialize selectedOption to null if no response exists
		        List<Integer> selectedOptions = new ArrayList<>();

		        // Find the response for the current question
		        for (UserResponse response : responses) {
		            if (question.getId().equals(response.getQuestion().getId())) {
		                selectedOptions = response.getSelectedOption(); // Assuming this returns a List<Integer>
		                break; // No need to check further once found
		            }
		        }
		        
		        // Add selected options to the review map
		        questionReview.put("selectedOption", selectedOptions.isEmpty() ? null : selectedOptions);
		        
		        // Add the question review to the review data list
		        reviewData.add(questionReview);
		    }


	        return reviewData;
	    }




}
