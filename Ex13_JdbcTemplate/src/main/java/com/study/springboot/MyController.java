package com.study.springboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.study.springboot.jdbc.MyUserDAO;
import com.study.springboot.jdbc.MyUserDTO;

@Controller
public class MyController {

    @Autowired
    private MyUserDAO userDao;

    @RequestMapping("/")
    public @ResponseBody String root() throws Exception{
    	
    	MyUserDTO user = new MyUserDTO(50,"영업","서울");
    	userDao.insert(user);
    	
    	System.out.println("총건수:"+userDao.countDept());
        return "JdbcTEmplate 사용하기";
    }

//    @GetMapping("/user")
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public String userlistPage(Model model) {
        model.addAttribute("users", userDao.list());
        return "userlist";
    }
}
