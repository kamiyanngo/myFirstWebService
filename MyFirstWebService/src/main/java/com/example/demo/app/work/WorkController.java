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
	
	//�ꗗ�\��
	@GetMapping
	public String index(WorkForm workForm,Model model) {
		
		//���[�N�̃��X�g���擾
		List<Work> list = workService.getAll();
		
		model.addAttribute("workList", list);
		model.addAttribute("title", "�ꗗ�\��");
		return "work/index";
		
	}
	
	@PostMapping
	public String indexPost(WorkForm workForm,Model model) {
		
		//���[�N�̃��X�g���擾
		List<Work> list = workService.getAll();
		
		model.addAttribute("workList", list);
		model.addAttribute("title", "�ꗗ�\��");
		return "work/index";
		
	}
	
	@GetMapping("/form")
	public String work(WorkForm workForm, Model model, 
			@ModelAttribute("complete") String complete) {
		model.addAttribute("title", "�o�^�y�[�W");
		return "work/form";
	}
	
	@PostMapping("/form")
	public String workGoBack(WorkForm workForm, Model model) {
		model.addAttribute("title", "�o�^�y�[�W");
		return "work/form";
	}
	//���͊m�F
	@PostMapping("/confirm")
	public String confirm(@Validated WorkForm workForm,
			BindingResult result, Model model) {
		if(result.hasErrors()) {
			model.addAttribute("title", "�o�^�y�[�W");
		return "work/form";
		}
		model.addAttribute("title","�m�F�y�[�W");
		return "work/confirm";
	}
	
	//�V�K�o�^
		@PostMapping("/insert")
			public String insert(@Validated WorkForm workForm,
					BindingResult result, Model model,
					RedirectAttributes redirectAttributes, @RequestParam("filename") MultipartFile[] filename
		            )throws Exception{
			Work work = makeWork(workForm,0);
			
			if(result.hasErrors()) {
				model.addAttribute("title", "�o�^�y�[�W" );
				System.out.println(result);
				return "work/form";
			}
			workService.insert(work,filename);
			redirectAttributes.addFlashAttribute("complete", "�o�^�������܂���");
			
			return "redirect:/work";
		}

		//�ꌏ�^�X�N�f�[�^���擾���A�t�H�[�����ɕ\��
	    @GetMapping("/{id}")
	    public String show(WorkForm workForm,
	        @PathVariable int id,Model model) {

	    	//Work���擾(Optional�Ń��b�v)
	      Optional<Work> workOpt = workService.getWork(id);
	        
	        //workForm�ւ̋l�ߒ���
	        Optional<WorkForm> workFormOpt = workOpt.map(t -> makeWorkForm(t));
	        
	        //workForm��null�łȂ���Β��g�����o��
	        if(workOpt.isPresent()) {
	        workForm = workFormOpt.get();
	        }
	        
	        model.addAttribute("workForm", workForm);
	        model.addAttribute("title", "�ڍ�");

	        return "work/show";
	    }
	    

	    //id���擾���A�ꌏ�̃f�[�^�X�V
	  @GetMapping("{id}/edit")
	    public String update(
	    	@Valid @ModelAttribute WorkForm workForm,
	    	BindingResult result,
	    	@PathVariable int id,
	    	Model model,RedirectAttributes redirectAttributes) {
		  
	    	//Work���擾(Optional�Ń��b�v)
	      Optional<Work> workOpt = workService.getWork(id);
	        
	      //workForm�ւ̋l�ߒ���
	        Optional<WorkForm> workFormOpt = workOpt.map(t -> makeWorkForm(t));
	        
	        //workForm��null�łȂ���Β��g�����o��
	        if(workOpt.isPresent()) {
	        workForm = workFormOpt.get();
	        }
	        model.addAttribute("title", "�ҏW");
		  model.addAttribute("workForm", workForm);
		  return "work/edit";
	  }
	  
		@PostMapping("/update")
		public String updateimp(@Validated  @ModelAttribute WorkForm workForm,
				BindingResult result, Model model,
				RedirectAttributes redirectAttributes,@RequestParam("id") int id,@RequestParam("filename") MultipartFile[] filename){
	    	//WokrForm�̃f�[�^��Work�Ɋi�[
	    	Work work = makeWork(workForm,id);
	    	
	        if (!result.hasErrors()) {
	        	//�X�V�����A�t���b�V���X�R�[�v�̎g�p�A���_�C���N�g
	        	workService.update(work,filename);
	        	redirectAttributes.addFlashAttribute("complete", "�ύX���������܂���");
	            return "redirect:/work/";
	        } else {
	            model.addAttribute("workForm", workForm);
	            model.addAttribute("title", "�^�X�N�ꗗ");
	            return "work/";
	        }
	    }
	    
	    
	  //id���擾���A�ꌏ�̃f�[�^�폜
	    @PostMapping("/delete/{id}")
	    public String delete(
	    	@PathVariable("id") String id,//pathvariable��id�擾
	    	Model model) {
	    	
	    	//�^�X�N���ꌏ�폜�����_�C���N�g
	        workService.deleteById(Integer.parseInt(id));
	        return "redirect:/work";
	    }
	    
	// �l�̎擾�v���C�x�[�g
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
	
   // Work�̃f�[�^��WorkForm�ɓ���ĕԂ�
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
