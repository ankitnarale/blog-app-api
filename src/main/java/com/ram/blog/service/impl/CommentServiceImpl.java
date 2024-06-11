package com.ram.blog.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ram.blog.constants.ApiConstants;
import com.ram.blog.entities.Comment;
import com.ram.blog.entities.Post;
import com.ram.blog.entities.User;
import com.ram.blog.exceptions.ResourceNotFoundException;
import com.ram.blog.payloads.CommentDto;
import com.ram.blog.repositories.CommentRepo;
import com.ram.blog.repositories.PostRepo;
import com.ram.blog.repositories.UserRepo;
import com.ram.blog.service.CommentServiceInterface;

@Service
public class CommentServiceImpl implements CommentServiceInterface {

	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private UserRepo userRepo;

	@Autowired
	private CommentRepo commentRepo;

	@Autowired
	private ModelMapper mdlMapper;

	@Override
	public CommentDto createComment(CommentDto content, Integer postId) {
		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException(ApiConstants.POST_NOT_FOUND, postId));
		Comment comment = this.mdlMapper.map(content, Comment.class);
		comment.setPost(post);
		Comment comment2 = this.commentRepo.save(comment);

		return this.mdlMapper.map(comment2, CommentDto.class);
	}

	@Override
	public void deleteComment(Integer commentId) {
		Comment comment = this.commentRepo.findById(commentId)
				.orElseThrow(() -> new ResourceNotFoundException("Comment not found", commentId));
		this.commentRepo.delete(comment);

	}

	@Override
	public CommentDto createCommentWithUser(CommentDto content, Integer postId, Integer userId) {
		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException(ApiConstants.POST_NOT_FOUND, postId));
		User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException(ApiConstants.USER_NOT_FOUND,userId));
		Comment comment = this.mdlMapper.map(content, Comment.class);
		comment.setPost(post);
		comment.setUser(user);
		Comment comment2 = this.commentRepo.save(comment);
		return this.mdlMapper.map(comment2, CommentDto.class);
	}

}
