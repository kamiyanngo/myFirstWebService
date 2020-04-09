package com.example.demo.app.work;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LoginController {
 
	@RequestMapping(value = "/work/", method = RequestMethod.POST)
    private String init(Model model) {
	// HttpSessionÇ…èÓïÒäiî[ÇµÇƒÇ¢ÇÈ
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();
        model.addAttribute("userName", userName);
        return "/work/index";
 
    }
}
