package com.study.springboot;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.study.springboot.dao.ISimpleBbsDao;

@Controller
public class MyController {

    @Autowired
    ISimpleBbsDao dao;

    @RequestMapping("/")
    public String root() throws Exception{
        // JdbcTemplate : SimpleBBS
        return "redirect:list";
    }

    @RequestMapping("/list")
    public String userlistPage(Model model) {
        model.addAttribute("list", dao.listDao());
        return "list";
    }

    @RequestMapping("/view")
    public String view(HttpServletRequest request, Model model) {
        String sId = request.getParameter("id");
        model.addAttribute("dto", dao.viewDao(sId));
        return "view";
    }
    
    @RequestMapping("/writeForm")
    public String writeForm() {
        
        return "writeForm";
    }
    
    @RequestMapping("/updateForm")
    public String updateForm(HttpServletRequest request, Model model) {
    	String sId = request.getParameter("id");
        model.addAttribute("dto", dao.viewDao(sId));
        return "updateForm";
    }
    
    @RequestMapping("/write")
    public String write(Model model, HttpServletRequest request) {
        dao.writeDao(request.getParameter("writer"),
                     request.getParameter("title"),
                     request.getParameter("content"));
        return "redirect:list";
    }
    
    @RequestMapping("/update")
    public String update(Model model, HttpServletRequest request) {
        dao.updateDao(request.getParameter("writer"),
                     request.getParameter("title"),
                     request.getParameter("content"),
                     request.getParameter("id"));
        return "redirect:list";
    }
    
    @RequestMapping("/delete")
    public String delete(HttpServletRequest request, Model model) {
        dao.deleteDao(request.getParameter("id"));
        return "redirect:list";
    }

}
