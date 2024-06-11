package com.ram.blog.service;

import com.ram.blog.payloads.CommentDto;

public interface CommentServiceInterface {

	CommentDto createComment(CommentDto content, Integer postId);
	CommentDto createCommentWithUser(CommentDto content, Integer postId, Integer userId);
	void deleteComment(Integer commentId);
	
}
