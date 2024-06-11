package com.ram.blog.service.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.ram.blog.constants.ApiConstants;
import com.ram.blog.entities.Category;
import com.ram.blog.entities.Post;
import com.ram.blog.entities.User;
import com.ram.blog.exceptions.ResourceNotFoundException;
import com.ram.blog.payloads.PostDto;
import com.ram.blog.payloads.PostResponse;
import com.ram.blog.repositories.CategoryRepo;
import com.ram.blog.repositories.PostRepo;
import com.ram.blog.repositories.UserRepo;
import com.ram.blog.service.PostServiceInterface;

@Service
public class PostServiceImpl implements PostServiceInterface {

	@Autowired
	private PostRepo postRep;

	@Autowired
	private ModelMapper mdlMapper;

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private CategoryRepo catRepo;

//	Create Post:-
	@Override
	public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException(ApiConstants.USER_NOT_FOUND, userId));

		Category category = this.catRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException(ApiConstants.CATEGORY_NOT_FOUND, categoryId));

		Post post = this.mdlMapper.map(postDto, Post.class);
		post.setImageName("Default.png");
		post.setDateAdded(new Date());
		post.setUser(user);
		post.setCategory(category);

		Post save = this.postRep.save(post);

		return this.mdlMapper.map(save, PostDto.class);
	}

//	Update Post:-
	@Override
	public PostDto updatePost(PostDto postDto, Integer postDtoId) {
		Post post = this.postRep.findById(postDtoId)
				.orElseThrow(() -> new ResourceNotFoundException(ApiConstants.POST_NOT_FOUND, postDtoId));

		post.setPostTitle(postDto.getPostTitle());
		post.setContent(postDto.getContent());
		post.setImageName(postDto.getImageName());
		Post savedPost = this.postRep.save(post);
		return this.mdlMapper.map(savedPost, PostDto.class);
	}

//	Delete Post:-
	@Override
	public void deletePost(Integer postDtoId) {
		Post post = this.postRep.findById(postDtoId)
				.orElseThrow(() -> new ResourceNotFoundException(ApiConstants.POST_NOT_FOUND, postDtoId));
		this.postRep.delete(post);

	}

//	Get All:-
	@Override
	public PostResponse getAllPosts(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
		Sort s = (sortDir.equalsIgnoreCase("asc")) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

//		Sort s = null;
//		if (sortDir.equalsIgnoreCase("asc")) {
//			s = Sort.by(sortBy).ascending();
//		} else {
//			s=Sort.by(sortBy).descending();
//		}
		Pageable p = PageRequest.of(pageNumber, pageSize, s);
		Page<Post> all = this.postRep.findAll(p);
		List<Post> posts = all.getContent();
		List<PostDto> postDto = posts.stream().map((post) -> this.mdlMapper.map(post, PostDto.class))
				.collect(Collectors.toList());

		PostResponse postResp = new PostResponse();
		postResp.setPostContent(postDto);
		postResp.setPageNumber(all.getNumber());
		postResp.setPageSize(all.getSize());
		postResp.setTotalElements(all.getTotalElements());
		postResp.setTotalPages(all.getTotalPages());
		postResp.setLastPage(all.isLast());

		return postResp;
	}

//	Get by Id:-
	@Override
	public PostDto getById(Integer postDtoId) {
		Post post = this.postRep.findById(postDtoId)
				.orElseThrow(() -> new ResourceNotFoundException(ApiConstants.POST_NOT_FOUND, postDtoId));

		return this.mdlMapper.map(post, PostDto.class);
	}

//	Get Posts By Category:-
	@Override
	public PostResponse getPostByCategory(Integer postCategoryID, Integer pageNumber, Integer pageSize, String sortBy,
			String sortDir) {
		Sort s = (sortDir.equalsIgnoreCase("asc")) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

		Pageable pR = PageRequest.of(pageNumber, pageSize, s);
		Category cat = this.catRepo.findById(postCategoryID)
				.orElseThrow(() -> new ResourceNotFoundException(ApiConstants.CATEGORY_NOT_FOUND, postCategoryID));

		Page<Post> byCategory = this.postRep.findByCategory(cat, pR);
		List<PostDto> postList = byCategory.getContent().stream().map((p) -> this.mdlMapper.map(p, PostDto.class))
				.collect(Collectors.toList());

		PostResponse postResp = new PostResponse();
		postResp.setPostContent(postList);
		postResp.setPageNumber(byCategory.getNumber());
		postResp.setLastPage(byCategory.isLast());
		postResp.setPageSize(byCategory.getSize());
		postResp.setTotalElements(byCategory.getTotalElements());
		postResp.setTotalPages(byCategory.getTotalPages());

		return postResp;
	}

//	Get Post by User:-
	@Override
	public PostResponse getPostByUser(Integer postUserID, Integer pageNumber, Integer pageSize, String sortBy,
			String sortDir) {
		Sort s = (sortDir.equalsIgnoreCase("asc")) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
		Pageable pR = PageRequest.of(pageNumber, pageSize, s);
		User user = this.userRepo.findById(postUserID)
				.orElseThrow(() -> new ResourceNotFoundException(ApiConstants.USER_NOT_FOUND, postUserID));
		Page<Post> byUser = this.postRep.findByUser(user, pR);
		List<PostDto> postUser = byUser.getContent().stream().map((p) -> this.mdlMapper.map(p, PostDto.class))
				.collect(Collectors.toList());
		PostResponse postResp = new PostResponse();
		postResp.setPostContent(postUser);
		postResp.setPageNumber(byUser.getNumber());
		postResp.setLastPage(byUser.isLast());
		postResp.setPageSize(byUser.getSize());
		postResp.setTotalElements(byUser.getTotalElements());
		postResp.setTotalPages(byUser.getTotalPages());

		return postResp;
	}

	@Override
	public List<PostDto> searchPost(String keyword) {
		List<Post> postTitleContaining = this.postRep.findByPostTitleContaining(keyword);
		List<PostDto> postDtoList = postTitleContaining.stream().map((p)-> this.mdlMapper.map(p, PostDto.class)).collect(Collectors.toList());
		
		return  postDtoList;
	}

}
