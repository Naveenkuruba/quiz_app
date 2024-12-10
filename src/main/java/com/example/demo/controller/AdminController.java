package com.example.demo.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Dto.AdminLoginRequest;
import com.example.demo.entity.Questions;
import com.example.demo.service.AdminService;


@RestController
@CrossOrigin(origins = "http://localhost:3001")
public class AdminController {
	@Autowired
	private AdminService adminService;
	@PostMapping("/admin/login")
	 public ResponseEntity<String> login(@RequestBody AdminLoginRequest loginRequest) {
		String message=adminService.login(loginRequest.getusername(), loginRequest.getpassword());
		return  ResponseEntity.ok(message);
    }
	@PostMapping("/questions/add")
	  public ResponseEntity<String> addQuestion(@RequestBody Questions question) {
	      adminService.addQuestion(question);
	      return ResponseEntity.ok("Question added successfully");
	  }

	   
	@DeleteMapping("/questions/delete/{id}")
	  public ResponseEntity<String> deleteQuestion(@PathVariable Long id) {
	      adminService.deleteQuestion(id);
	      return ResponseEntity.ok("Question deleted successfully");
	   }
	@GetMapping("/get/{id}")
	public ResponseEntity<Questions> getQuestionById(@PathVariable Long id) {
	    Optional<Questions> question = adminService.findById(id);
	    if (question.isPresent()) {
	        return ResponseEntity.ok(question.get());
	    } else {
	        return ResponseEntity.notFound().build();
	    }
	}
    @PutMapping("/edit/{id}")
    public ResponseEntity<Questions> editQuestion(
            @PathVariable("id") Long questionId,
            @RequestBody Questions updatedQuestion) {

        Questions editedQuestion = adminService.editQuestion(questionId, updatedQuestion);
        return ResponseEntity.ok(editedQuestion);
    }

}
