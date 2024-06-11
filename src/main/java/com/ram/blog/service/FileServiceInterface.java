package com.ram.blog.service;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

public interface FileServiceInterface {

	public String uploadImage(String path, MultipartFile file) throws IOException;
	
	public InputStream getImage(String path, String fileName) throws IOException;
}
