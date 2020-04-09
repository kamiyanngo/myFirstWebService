package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.app.entity.Work;
import com.example.demo.dao.WorkDao;

@Service
public class WorkServiceImpl implements WorkService {
	
	private final WorkDao dao;
	
	@Autowired WorkServiceImpl(WorkDao dao){
		this.dao = dao;
	}

	@Override
	public List<Work> getAll() {
		return dao.getAll();
	}

	@Override
	public void update(Work work,MultipartFile[] file) {
		if(dao.update(work,file) == 0) {
			throw new WorkNotFoundException("更新するワークがありません");
		}
		
	}

	@Override
	public Optional<Work> getWork(int id) {
		//Optional１件取得　１件もなければ例外発生
		try {
			return dao.findById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new WorkNotFoundException("指定されたワークがありません");
		}
		
	}

	@Override
	public void deleteById(int id) {
		if(dao.deleteById(id) == 0) {
			throw new WorkNotFoundException("削除するワークがありません");
		
	}

}

	@Override
	public void insert(Work work,MultipartFile[] file) {
		dao.insert(work,file);
	}

}
