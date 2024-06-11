package com.ram.blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ram.blog.payloads.ApiResponse;
import com.ram.blog.payloads.CommentDto;
import com.ram.blog.service.CommentServiceInterface;

@RestController
@RequestMapping(value = "/api/comment")
public class CommentController {

	@Autowired
	private CommentServiceInterface cmntServ;

	@PostMapping("/post/{postId}")
	public ResponseEntity<CommentDto> createComment(@PathVariable Integer postId, @RequestBody CommentDto contents) {

		CommentDto commentDto = this.cmntServ.createComment(contents, postId);

		return new ResponseEntity<CommentDto>(commentDto, HttpStatus.CREATED);

	}
	
	
	@PostMapping("/post/{postId}/user/{userId}")
	public ResponseEntity<CommentDto> createCommentFromUser(@PathVariable Integer postId,@PathVariable Integer userId, @RequestBody CommentDto contents) {

		CommentDto commentDto = this.cmntServ.createCommentWithUser(contents, postId, userId);

		return new ResponseEntity<CommentDto>(commentDto, HttpStatus.CREATED);

	}

	@DeleteMapping("/delete/{commentId}")
	public ResponseEntity<ApiResponse> deleteComment(@PathVariable Integer commentId) {
		this.cmntServ.deleteComment(commentId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Comment deleted successfully", true), HttpStatus.OK);
	}

}
