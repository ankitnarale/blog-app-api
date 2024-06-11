package com.ram.blog.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ram.blog.constants.ApiConstants;
import com.ram.blog.constants.UriConstants;
import com.ram.blog.payloads.ApiResponse;
import com.ram.blog.payloads.PostDto;
import com.ram.blog.payloads.PostResponse;
import com.ram.blog.service.FileServiceInterface;
import com.ram.blog.service.PostServiceInterface;

import jakarta.servlet.http.HttpServletResponse;


@RestController
@RequestMapping("/api/post")
public class PostController {

	@Autowired
	private PostServiceInterface postServ;

	@Autowired
	private FileServiceInterface fileServ;

	@Value("${project.image}")
	private String path;

	/**
	 * @author Ankit Narale
	 * @apiNote this api is used to create posts wrt user and category
	 * @param post
	 * @param userId
	 * @param categoryId
	 * @return
	 */
	@PostMapping("/user/{userId}/category/{categoryId}/posts")
	public ResponseEntity<PostDto> createPost(@RequestBody PostDto post, @PathVariable Integer userId,
			@PathVariable Integer categoryId) {

		PostDto post2 = this.postServ.createPost(post, userId, categoryId);
		return new ResponseEntity<PostDto>(post2, HttpStatus.CREATED);

	}

	/**
	 * @author Ankit Narale
	 * @apiNote this api is used to get posts of the category
	 * @param catId
	 * @return
	 */
	@GetMapping("/categoryID/{catId}/posts")
	public ResponseEntity<PostResponse> getPostsByCategory(@PathVariable Integer catId,
			@RequestParam(value = "pageNumber", defaultValue = ApiConstants.PAGE_NUMBER, required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", defaultValue = ApiConstants.PAGE_SIZE, required = false) Integer pageSize,
			@RequestParam(value = "sortBy", defaultValue = ApiConstants.SORT_BY, required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = ApiConstants.SORT_DIR, required = false) String sortDir) {
		PostResponse postList = this.postServ.getPostByCategory(catId, pageNumber, pageSize, sortBy, sortDir);
		return new ResponseEntity<PostResponse>(postList, HttpStatus.OK);
	}

	/**
	 * @author Ankit Narale
	 * @apiNote this api is used to get posts of the category
	 * @param userId
	 * @return
	 */
	@GetMapping("/user/{userId}/posts")
	public ResponseEntity<PostResponse> getPostsByUser(@PathVariable Integer userId,
			@RequestParam(value = "pageNumber", defaultValue = ApiConstants.PAGE_NUMBER, required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", defaultValue = ApiConstants.PAGE_SIZE, required = false) Integer pageSize,
			@RequestParam(value = "sortBy", defaultValue = ApiConstants.SORT_BY, required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = ApiConstants.SORT_DIR, required = false) String sortDir) {
		PostResponse byUser = this.postServ.getPostByUser(userId, pageNumber, pageSize, sortBy, sortDir);
		return new ResponseEntity<PostResponse>(byUser, HttpStatus.OK);
	}

	/**
	 * @author Ankit Narale
	 * @apiNote this api is used to get all posts
	 * @return
	 */
	@GetMapping(value = UriConstants.POST_)
	public ResponseEntity<PostResponse> getAllPosts(
			@RequestParam(value = "pageNumber", defaultValue = ApiConstants.PAGE_NUMBER, required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", defaultValue = ApiConstants.PAGE_SIZE, required = false) Integer pageSize,
			@RequestParam(value = "sortBy", defaultValue = ApiConstants.SORT_BY, required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = ApiConstants.SORT_DIR, required = false) String sortDir) {
		PostResponse allPosts = this.postServ.getAllPosts(pageNumber, pageSize, sortBy, sortDir);
		return new ResponseEntity<PostResponse>(allPosts, HttpStatus.OK);
	}

	/**
	 * @author Ankit
	 * @apiNote This api is used to get post by id
	 * @param postId
	 * @return
	 */
	@GetMapping(value = UriConstants.POST_ID)
	public ResponseEntity<PostDto> getPostById(@PathVariable Integer postId) {
		PostDto byId = this.postServ.getById(postId);
		return new ResponseEntity<PostDto>(byId, HttpStatus.FOUND);
	}

	/**
	 * @author Ankit
	 * @apiNote This api is used to delete posts
	 * @param postId
	 * @return
	 */
	@DeleteMapping(value = UriConstants.POST_ID)
	public ResponseEntity<ApiResponse> deletePost(@PathVariable Integer postId) {
		this.postServ.deletePost(postId);
		return new ResponseEntity<ApiResponse>(new ApiResponse(ApiConstants.DELETE_POST, true), HttpStatus.OK);
	}

	/**
	 * @author Shree
	 * @apiNote This api is used to update posts
	 * @param postDto
	 * @param postId
	 * @return
	 */
	@PutMapping(value = UriConstants.POST_ID)
	public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto, @PathVariable Integer postId) {
		PostDto updatePost = this.postServ.updatePost(postDto, postId);
		return new ResponseEntity<PostDto>(updatePost, HttpStatus.CREATED);
	}

//	Search Posts:-
	/**
	 * @apiNote This api searches post wrt keyword.
	 * @param keyword
	 * @return
	 */
	@GetMapping("/search/{keyword}")
	public ResponseEntity<List<PostDto>> searchPost(@PathVariable String keyword) {
		List<PostDto> searchPost = this.postServ.searchPost(keyword);
		return new ResponseEntity<List<PostDto>>(searchPost, HttpStatus.FOUND);
	}

//	Post Image:-
	/**
	 * @apiNote This api is used to upload the image wrt postId
	 * @param image
	 * @param postId
	 * @return
	 * @throws IOException
	 */
	@PostMapping("/upload/image/{postId}")
	public ResponseEntity<PostDto> uploadImage(@RequestParam MultipartFile image, @PathVariable Integer postId)
			throws IOException {
		PostDto postDto = this.postServ.getById(postId);
		String image2 = this.fileServ.uploadImage(path, image);

		postDto.setImageName(image2);
		PostDto updatePost = this.postServ.updatePost(postDto, postId);
		return new ResponseEntity<PostDto>(updatePost, HttpStatus.OK);
	}
	
	@GetMapping(value = "/dld/{name}",produces = MediaType.IMAGE_PNG_VALUE)
	public void dldImage(@PathVariable String name, HttpServletResponse response) throws IOException {
		InputStream image = this.fileServ.getImage(path, name);
		response.setContentType(MediaType.IMAGE_PNG_VALUE);
		StreamUtils.copy(image, response.getOutputStream());
	}

}
