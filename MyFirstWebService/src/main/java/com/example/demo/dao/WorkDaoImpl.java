package com.example.demo.dao;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.app.entity.Work;


@Repository
public class WorkDaoImpl implements WorkDao {

	private final JdbcTemplate jdbcTemplate;
    
	@Autowired
	public WorkDaoImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
}
	@Override
	public List<Work> getAll() {
		String sql = "SELECT id, work_name, number, money, gd_name, sg_name, contents, created FROM work";
		List<Map<String, Object>> resultlist = jdbcTemplate.queryForList(sql);
		
		List<Work> list = new ArrayList<Work>();
		
		for(Map<String,Object>result : resultlist) {
			Work work = new Work();
			work.setId((int)result.get("id"));
			work.setWork_name((String)result.get("work_name"));
			work.setNumber((String)result.get("number"));
			work.setMoney((String)result.get("money"));
			work.setGd_name((String)result.get("gd_name"));
			work.setSg_name((String)result.get("sg_name"));
			work.setContents((String)result.get("contents"));
			work.setCreated(((Timestamp)result.get("created")).toLocalDateTime());
			list.add(work);
		}
		return list;
	}

	
	@Override
	public Optional<Work> findById(int id) {
		String sql = "SELECT id, work_name, number, money, gd_name, sg_name, contents, created, filename, filename2 FROM work WHERE id = ?";
		
		Map<String,Object> result = jdbcTemplate.queryForMap(sql, id);
		
		Work work = new Work();
		work.setId((int)result.get("id"));
		work.setWork_name((String)result.get("work_name"));
		work.setNumber((String)result.get("number"));
		work.setMoney((String)result.get("money"));
		work.setGd_name((String)result.get("gd_name"));
		work.setSg_name((String)result.get("sg_name"));
		work.setContents((String)result.get("contents"));
		work.setCreated(((Timestamp)result.get("created")).toLocalDateTime());
		work.setPhotoname((String)result.get("filename"));
		work.setPhotoname2((String)result.get("filename2"));
		Optional<Work> workOpt = Optional.ofNullable(work);
		
		return workOpt;
	}
	
	@Override
	public void insert(Work work,MultipartFile[] file) {
		if(file[0].isEmpty()) {
			jdbcTemplate.update("INSERT INTO work(work_name, number, money, gd_name, sg_name, contents, created) VALUES(?,?,?,?,?,?,?)",
					work.getWork_name(),work.getNumber(),work.getMoney(),work.getGd_name(),work.getSg_name(),work.getContents(),work.getCreated());
			//ファイルが一つの場合の処理
		}else if(file.length ==1 || file[1].isEmpty()) {
	    Date now = new Date();
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
	    String filename = sdf.format(now) + file[0].getOriginalFilename();
		File originalFile = new File("F:/programing/MyFirstWebService/MyFirstWebService/static/img/" + filename);
        try {
             file[0].transferTo(originalFile);
        } catch (IOException e) {
            e.printStackTrace();
            //returnを使うためにStringのほうが良い？
        }
		jdbcTemplate.update("INSERT INTO work(work_name, number, money, gd_name, sg_name, contents, created, filename) VALUES(?,?,?,?,?,?,?,?)",
			work.getWork_name(),work.getNumber(),work.getMoney(),work.getGd_name(),work.getSg_name(),work.getContents(),work.getCreated(),filename);
		//ファイルが２つの場合の処理
		}else if(file.length <=2 ||file.length ==2){
		    Date now = new Date();
		    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		    String filename = sdf.format(now) + file[0].getOriginalFilename();
		    String filename2 = sdf.format(now) + file[1].getOriginalFilename();
			File originalFile = new File("F:/programing/MyFirstWebService/MyFirstWebService/static/img/" + filename);
			File originalFile2 = new File("F:/programing/MyFirstWebService/MyFirstWebService/static/img/" + filename2);
	        try {
	             file[0].transferTo(originalFile);
	             file[1].transferTo(originalFile2);
	        } catch (IOException e) {
	            e.printStackTrace();
	            //returnを使うためにStringのほうが良い？
	        }
			
			jdbcTemplate.update("INSERT INTO work(work_name, number, money, gd_name, sg_name, contents, created, filename,filename2) VALUES(?,?,?,?,?,?,?,?,?)",
				work.getWork_name(),work.getNumber(),work.getMoney(),work.getGd_name(),work.getSg_name(),work.getContents(),work.getCreated(),filename,filename2);
		
		}else {
			throw new IllegalArgumentException("不正な値です。");
		}
	
	}
	
	@Override
	public int update(Work work,MultipartFile[] file) {
		if(file[0].isEmpty()) {
		return jdbcTemplate.update("UPDATE work SET work_name = ?, number = ?, money = ?, gd_name = ?, sg_name = ?,contents = ? WHERE id = ? ",
				work.getWork_name(),work.getNumber(),work.getMoney(),work.getGd_name(),work.getSg_name(),work.getContents(),work.getId());
		 //ファイルが一つの場合
		}else if(file.length ==1 || file[1].isEmpty()) {
			//編集前の画像データ削除
			String sql = "SELECT id, work_name, number, money, gd_name, sg_name, contents, created, filename,filename2 FROM work WHERE id = ?";
			Map<String,Object> result = jdbcTemplate.queryForMap(sql,work.getId());
			work.setPhotoname((String)result.get("filename"));
			work.setPhotoname2((String)result.get("filename2"));
	        File editoriginalFile = new File("F:/programing/MyFirstWebService/MyFirstWebService/static/img/"+work.getPhotoname());
	        editoriginalFile.delete();
		    File editoriginalFile2 = new File("F:/programing/MyFirstWebService/MyFirstWebService/static/img/"+work.getPhotoname2());
		    editoriginalFile2.delete();	
		    jdbcTemplate.update("UPDATE work SET filename2 = null WHERE id = ?", work.getId());
		    
	        //新しい画像保存
		    Date now = new Date();
		    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		    String filename = sdf.format(now) + file[0].getOriginalFilename();
			File originalFile = new File("F:/programing/MyFirstWebService/MyFirstWebService/static/img/" + filename);
	        try {
	             file[0].transferTo(originalFile);
	        } catch (IOException e) {
	            e.printStackTrace();
	            //returnを使うためにStringのほうが良い？
	        }
	        return jdbcTemplate.update("UPDATE work SET work_name = ?, number = ?, money = ?, gd_name = ?, sg_name = ?,contents = ?, filename = ? WHERE id = ? ",
					work.getWork_name(),work.getNumber(),work.getMoney(),work.getGd_name(),work.getSg_name(),work.getContents(),filename,work.getId());
			//画像が２つの場合
		}else if(file.length <=2 ||file.length ==2){
			//編集前の画像データ削除
		String sql = "SELECT id, work_name, number, money, gd_name, sg_name, contents, created, filename,filename2 FROM work WHERE id = ?";
		Map<String,Object> result = jdbcTemplate.queryForMap(sql,work.getId());
		work.setPhotoname((String)result.get("filename"));
		work.setPhotoname2((String)result.get("filename2"));
	    File editoriginalFile = new File("F:/programing/MyFirstWebService/MyFirstWebService/static/img/"+work.getPhotoname());
	    editoriginalFile.delete();	
	    File editoriginalFile2 = new File("F:/programing/MyFirstWebService/MyFirstWebService/static/img/"+work.getPhotoname2());
	    editoriginalFile2.delete();	
			
		
	    Date now = new Date();
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
	    String filename = sdf.format(now) + file[0].getOriginalFilename();
	    String filename2 = sdf.format(now) + file[1].getOriginalFilename();
		File originalFile = new File("F:/programing/MyFirstWebService/MyFirstWebService/static/img/" + filename);
		File originalFile2 = new File("F:/programing/MyFirstWebService/MyFirstWebService/static/img/" + filename2);
        try {
             file[0].transferTo(originalFile);
             file[1].transferTo(originalFile2);
        } catch (IOException e) {
            e.printStackTrace();
            //returnを使うためにStringのほうが良い？
        }
		return jdbcTemplate.update("UPDATE work SET work_name = ?, number = ?, money = ?, gd_name = ?, sg_name = ?,contents = ?, filename = ?,filename2 = ? WHERE id = ? ",
				work.getWork_name(),work.getNumber(),work.getMoney(),work.getGd_name(),work.getSg_name(),work.getContents(),filename,filename2,work.getId());			
	}else {
		return 1;
	}
}
	@Override
	public int deleteById(int id) {
		String sql = "SELECT id, work_name, number, money, gd_name, sg_name, contents, created, filename FROM work WHERE id = ?";
		Work work = new Work();
		Map<String,Object> result = jdbcTemplate.queryForMap(sql, id);
		work.setPhotoname((String)result.get("filename"));
		work.setPhotoname2((String)result.get("filename2"));
		
        File originalFile = new File("F:/programing/MyFirstWebService/MyFirstWebService/static/img/"+work.getPhotoname());
        originalFile.delete();
	    File editoriginalFile2 = new File("F:/programing/MyFirstWebService/MyFirstWebService/static/img/"+work.getPhotoname2());
	    editoriginalFile2.delete();
	    
		return jdbcTemplate.update("DELETE FROM work WHERE id = ?", id);
	}

}
