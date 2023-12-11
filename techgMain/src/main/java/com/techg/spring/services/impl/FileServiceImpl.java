package com.techg.spring.services.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.techg.spring.services.FileService;


@Service
public class FileServiceImpl implements FileService {

	@Override
	public String uploadImage(String path, MultipartFile file) throws IOException {
		
		//file name
		String name = file.getOriginalFilename();
		
		//random generated name of file
		String randomID = UUID.randomUUID().toString();
		String fileName1 = randomID.concat(name.substring(name.lastIndexOf(".")));
		
		//fullfath
		String filePath = path + File.separator + fileName1 ;
		
		//create folder if not found 
		File f = new File(path);
		if(!f.exists()) {
			f.mkdir();                 
		}
		                                         
		//file copy
		Files.copy(file.getInputStream(), Paths.get(filePath));
		return fileName1 ;       
	}

	@Override
	public InputStream getResource(String path, String filename) throws FileNotFoundException {
		String fullPath = path + File.separator + filename ;
		InputStream is = new FileInputStream(fullPath);
		
		return is;
	}

}
