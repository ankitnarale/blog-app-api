package com.ram.blog.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ram.blog.service.FileServiceInterface;

@Service
public class FileServiceImpl implements FileServiceInterface {

	@Override
	public String uploadImage(String path, MultipartFile file) throws IOException {
//		get file name
		String name = file.getOriginalFilename();

//		get random name

		String random = UUID.randomUUID().toString();
		String randomName = random.concat(name.substring(name.lastIndexOf(".")));

//		full path
		String fullPath = path + File.separator + randomName;

//		get/Create category
		File f = new File(path);
		if (!f.exists()) {
			f.mkdir();
		}

//		Copy file path
		Files.copy(file.getInputStream(), Paths.get(fullPath));

		return name;
	}

	@Override
	public InputStream getImage(String path, String fileName) throws IOException {
		String fullPath = path + File.separator + fileName;
		InputStream is = new FileInputStream(fullPath);
		return is;

	}

}
