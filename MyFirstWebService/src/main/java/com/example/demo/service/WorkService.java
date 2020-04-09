package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import com.example.demo.app.entity.Work;

public interface WorkService {
	
	
	void insert(Work work,MultipartFile[] file);
	
	void update(Work work,MultipartFile[] file);
	
	List<Work> getAll();
	
	Optional<Work> getWork(int id);
	
	void deleteById(int id);
}
