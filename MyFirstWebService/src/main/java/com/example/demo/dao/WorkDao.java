package com.example.demo.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import com.example.demo.app.entity.Work;

public interface WorkDao {
	
	void insert(Work work,MultipartFile[] file);
	
	int update(Work work,MultipartFile[] file);
	
	List<Work> getAll();
	
	int deleteById(int id);
	
	Optional<Work> findById(int id);
	
}
