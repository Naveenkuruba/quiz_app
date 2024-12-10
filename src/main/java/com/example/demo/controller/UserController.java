package com.example.demo.controller;

import java.util.HashMap;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Questions;
import com.example.demo.entity.User;
import com.example.demo.entity.UserResponse;
import com.example.demo.service.UserService;

@RestController
@CrossOrigin(origins = "http://localhost:3001")
public class UserController {
	@Autowired
	private UserService userservice;
	@PostMapping("/user")
	public ResponseEntity<Map<String, Object>> submitUserDetails(@RequestBody User user) {
	   
	    userservice.saveUser(user);

	    Map<String, Object> response = new HashMap<>();
	    response.put("message", "User details saved successfully! Ready to take the quiz.");
	    response.put("userId", user.getId()); // Assuming user.getId() returns the saved user ID

	    return ResponseEntity.ok(response);
	}
	@GetMapping("/questions")
	public ResponseEntity<Map<String, Object>> getAllQuestions() {
	    Long attemptId = System.currentTimeMillis(); // Generate unique attemptId (or you can use UUID.randomUUID().toString() for uniqueness)
	    
	    List<Questions> questionsList = userservice.getAllquestions(); // Fetch all questions

	    // Prepare the response as a Map with both attemptId and questions
	    Map<String, Object> response = new HashMap<>();
	    response.put("attemptId", attemptId);
	    response.put("questions", questionsList);

	    return ResponseEntity.ok(response); // Return both the attemptId and questions
	}

    @PostMapping("/submit-response")
    public ResponseEntity<String> submitResponse(@RequestParam Long userId, @RequestParam Long questionId, @RequestParam List<Integer> selectedOption,Long attemptId) {
        userservice.saveUserResponse(userId, questionId, selectedOption,attemptId);
        return ResponseEntity.ok("Response saved successfully");
    }

    @GetMapping("/validate-quiz")
    public ResponseEntity<String> validateQuiz(@RequestParam Long userId) {
    	  if (userId == null) {
    	        return ResponseEntity.badRequest().body("User ID is missing");
    	    }
            userservice.validateAllQuestionsAnswered(userId);
            return ResponseEntity.ok("All questions answered");
        
    }

    
    @GetMapping("/result")
    public ResponseEntity<String> getQuizResult(@RequestParam Long userId ,@RequestParam Long attemptId) {
    	  if (userId == null) {
    	        return ResponseEntity.badRequest().body("User ID is missing");
    	    }
       int score= userservice.getQuizResult(userId,attemptId);
       
        return ResponseEntity.ok("Results calculated Your quiz score is: " + score);
    }
    @GetMapping("/quiz-review")
    public ResponseEntity<List<Map<String, Object>>> getQuizReview(@RequestParam Long userId,@RequestParam Long attemptId) {
        List<Map<String, Object>> reviewData = userservice.getUserQuizReview(userId,attemptId);
        return ResponseEntity.ok(reviewData);
    }
    
}
