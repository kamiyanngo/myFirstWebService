package com.example.demo.app.work;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.app.entity.Work;
import com.example.demo.service.WorkService;

@Controller
@RequestMapping("/work")
public class WorkController {
	
	private final WorkService workService;
	@Autowired
	public WorkController(WorkService workService) {
		this.workService = workService;
	}
	
	//一覧表示
	@GetMapping
	public String index(WorkForm workForm,Model model) {
		
		//ワークのリストを取得
		List<Work> list = workService.getAll();
		
		model.addAttribute("workList", list);
		model.addAttribute("title", "一覧表示");
		return "work/index";
		
	}
	
	@PostMapping
	public String indexPost(WorkForm workForm,Model model) {
		
		//ワークのリストを取得
		List<Work> list = workService.getAll();
		
		model.addAttribute("workList", list);
		model.addAttribute("title", "一覧表示");
		return "work/index";
		
	}
	
	@GetMapping("/form")
	public String work(WorkForm workForm, Model model, 
			@ModelAttribute("complete") String complete) {
		model.addAttribute("title", "登録ページ");
		return "work/form";
	}
	
	@PostMapping("/form")
	public String workGoBack(WorkForm workForm, Model model) {
		model.addAttribute("title", "登録ページ");
		return "work/form";
	}
	//入力確認
	@PostMapping("/confirm")
	public String confirm(@Validated WorkForm workForm,
			BindingResult result, Model model) {
		if(result.hasErrors()) {
			model.addAttribute("title", "登録ページ");
		return "work/form";
		}
		model.addAttribute("title","確認ページ");
		return "work/confirm";
	}
	
	//新規登録
		@PostMapping("/insert")
			public String insert(@Validated WorkForm workForm,
					BindingResult result, Model model,
					RedirectAttributes redirectAttributes, @RequestParam("filename") MultipartFile[] filename
		            )throws Exception{
			Work work = makeWork(workForm,0);
			
			if(result.hasErrors()) {
				model.addAttribute("title", "登録ページ" );
				System.out.println(result);
				return "work/form";
			}
			workService.insert(work,filename);
			redirectAttributes.addFlashAttribute("complete", "登録完了しました");
			
			return "redirect:/work";
		}

		//一件タスクデータを取得し、フォーム内に表示
	    @GetMapping("/{id}")
	    public String show(WorkForm workForm,
	        @PathVariable int id,Model model) {

	    	//Workを取得(Optionalでラップ)
	      Optional<Work> workOpt = workService.getWork(id);
	        
	        //workFormへの詰め直し
	        Optional<WorkForm> workFormOpt = workOpt.map(t -> makeWorkForm(t));
	        
	        //workFormがnullでなければ中身を取り出し
	        if(workOpt.isPresent()) {
	        workForm = workFormOpt.get();
	        }
	        
	        model.addAttribute("workForm", workForm);
	        model.addAttribute("title", "詳細");

	        return "work/show";
	    }
	    

	    //idを取得し、一件のデータ更新
	  @GetMapping("{id}/edit")
	    public String update(
	    	@Valid @ModelAttribute WorkForm workForm,
	    	BindingResult result,
	    	@PathVariable int id,
	    	Model model,RedirectAttributes redirectAttributes) {
		  
	    	//Workを取得(Optionalでラップ)
	      Optional<Work> workOpt = workService.getWork(id);
	        
	      //workFormへの詰め直し
	        Optional<WorkForm> workFormOpt = workOpt.map(t -> makeWorkForm(t));
	        
	        //workFormがnullでなければ中身を取り出し
	        if(workOpt.isPresent()) {
	        workForm = workFormOpt.get();
	        }
	        model.addAttribute("title", "編集");
		  model.addAttribute("workForm", workForm);
		  return "work/edit";
	  }
	  
		@PostMapping("/update")
		public String updateimp(@Validated  @ModelAttribute WorkForm workForm,
				BindingResult result, Model model,
				RedirectAttributes redirectAttributes,@RequestParam("id") int id,@RequestParam("filename") MultipartFile[] filename){
	    	//WokrFormのデータをWorkに格納
	    	Work work = makeWork(workForm,id);
	    	
	        if (!result.hasErrors()) {
	        	//更新処理、フラッシュスコープの使用、リダイレクト
	        	workService.update(work,filename);
	        	redirectAttributes.addFlashAttribute("complete", "変更が完了しました");
	            return "redirect:/work/";
	        } else {
	            model.addAttribute("workForm", workForm);
	            model.addAttribute("title", "タスク一覧");
	            return "work/";
	        }
	    }
	    
	    
	  //idを取得し、一件のデータ削除
	    @PostMapping("/delete/{id}")
	    public String delete(
	    	@PathVariable("id") String id,//pathvariableでid取得
	    	Model model) {
	    	
	    	//タスクを一件削除しリダイレクト
	        workService.deleteById(Integer.parseInt(id));
	        return "redirect:/work";
	    }
	    
	// 値の取得プライベート
	private Work makeWork(WorkForm workForm,int id) {
		Work work = new Work();
        if(id != 0) {
        	work.setId(id);
        }
		work.setId(workForm.getId());
		work.setWork_name(workForm.getWork_name());
		work.setNumber(workForm.getNumber());
		work.setMoney(workForm.getMoney());
		work.setGd_name(workForm.getGd_name());
		work.setSg_name(workForm.getSg_name());
		work.setContents(workForm.getContents());
		work.setCreated(LocalDateTime.now());
		work.setPhotoname(workForm.getPhotoname());
		work.setPhotoname2(workForm.getPhotoname2());
		
		return work;
	}
	
   // WorkのデータをWorkFormに入れて返す
   private WorkForm makeWorkForm(Work work) {
   	
       WorkForm workForm = new WorkForm();

       workForm.setId(work.getId());
       workForm.setWork_name(work.getWork_name());
       workForm.setNumber(work.getNumber());
       workForm.setMoney(work.getMoney());
	   workForm.setGd_name(work.getGd_name());
	   workForm.setSg_name(work.getSg_name());
	   workForm.setContents(work.getContents());
	   workForm.setCreated(LocalDateTime.now());
	   workForm.setPhotoname(work.getPhotoname());
	   workForm.setPhotoname2(work.getPhotoname2());
       
       return workForm;
   }
   
}
