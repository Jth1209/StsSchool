package com.study.springboot;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MyController {

    @RequestMapping("/")
    public @ResponseBody String root() throws Exception{
        return "Model & View";
    }

    @RequestMapping("/test1")
    public String test1(Model model,@RequestParam("name") String name) {//@RequestParam은 request.getParameter와 같은 역할을 해준다.
    	// Model 객체를 이용해서, view로 Data 전달
    	// 데이터만 설정이 가능
        model.addAttribute("name", name);

        return "test1";       
    }
     
    @RequestMapping("/mv")
    public ModelAndView test2() {
    	// 데이터와 뷰를 동시에 설정이 가능
        ModelAndView mv = new ModelAndView();
         
        List<String> list = new ArrayList<>();
         
        list.add("test1");
        list.add("test2");
        list.add("test3");
         
        mv.addObject("lists", list);      			 // jstl로 호출
        mv.addObject("ObjectTest", "테스트입니다."); // jstl로 호출
        mv.addObject("name", "홍길동");				 // jstl로 호출
        mv.setViewName("view/myView");
        
        return mv;                                     
    }

}
