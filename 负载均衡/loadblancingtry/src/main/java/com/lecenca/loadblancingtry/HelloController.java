package com.lecenca.loadblancingtry;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class HelloController {

    @RequestMapping("/")
    public String hello(HttpServletRequest request,Model model){
        model.addAttribute("ip",request.getLocalAddr());
        model.addAttribute("port",request.getLocalPort());
        return "hello";
    }

}
