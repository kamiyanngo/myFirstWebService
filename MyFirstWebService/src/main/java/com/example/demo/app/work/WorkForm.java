package com.example.demo.app.work;


import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

public class WorkForm {
	
	private int id;
	
	@Size(min = 1, max = 50, message="H––¼‚Í1•¶šˆÈã100•¶šˆÈ‰º‚Å“ü—Í‚µ‚Ä‚­‚¾‚³‚¢B")
	@NotNull(message="H––¼‚ğ“ü—Í‚µ‚Ä‰º‚³‚¢B")
	private String work_name;
	
	@Size(min = 1, max = 30, message="H–”Ô†‚Í1•¶šˆÈã30•¶šˆÈ‰º‚Å“ü—Í‚µ‚Ä‚­‚¾‚³‚¢B")
	@NotNull(message="H–”Ô†‚ğ“ü—Í‚µ‚Ä‰º‚³‚¢B")
	private String number;
	
	@NotNull(message="Œ_–ñ‹àŠz‚ğ“ü—Í‚µ‚Ä‰º‚³‚¢B")
	private String money;
	
	@Size(min = 1, max = 20, message="‘ã—l–¼‚Í1•¶šˆÈã20•¶šˆÈ‰º‚Å“ü—Í‚µ‚Ä‚­‚¾‚³‚¢B")
	private String gd_name;
	
	@Size(min = 1, max = 20, message="å”C‹ZpÒ–¼‚Í1•¶šˆÈã20•¶šˆÈ‰º‚Å“ü—Í‚µ‚Ä‚­‚¾‚³‚¢B")
	private String sg_name;
	
	@Size(min = 1, max = 400, message="H–ŠT—v‚Í•¶š1ˆÈã400•¶šˆÈ‰º‚Å“ü—Í‚µ‚Ä‚­‚¾‚³‚¢B")
	@NotNull(message="H–“à—e‚ğ“ü—Í‚µ‚Ä‰º‚³‚¢B")
	private String contents;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDateTime created;
	
	private MultipartFile filename;
	
	private String photoname;
	
	private String photoname2;
	
	private String search;
	
	
	
		public String getSearch() {
		return search;
	}



	public void setSearch(String search) {
		this.search = search;
	}



		public WorkForm() {
	}
		
		
	
	public MultipartFile getFilename() {
			return filename;
		}



	public void setFilename(MultipartFile filename) {
			this.filename = filename;
		}



	public String getPhotoname2() {
		return photoname2;
	}



	public void setPhotoname2(String photoname2) {
		this.photoname2 = photoname2;
	}



	public String getPhotoname() {
		return photoname;
	}

	public void setPhotoname(String photoname) {
		this.photoname = photoname;
	}

	public LocalDateTime getCreated() {
		return created;
	}

	public void setCreated(LocalDateTime created) {
		this.created = created;
	}
	

	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getWork_name() {
		return work_name;
	}
	public void setWork_name(String work_name) {
		this.work_name = work_name;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getMoney() {
		return money;
	}
	public void setMoney(String money) {
		this.money = money;
	}
	public String getGd_name() {
		return gd_name;
	}
	public void setGd_name(String gd_name) {
		this.gd_name = gd_name;
	}
	public String getSg_name() {
		return sg_name;
	}
	public void setSg_name(String sg_name) {
		this.sg_name = sg_name;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
		
}
	

