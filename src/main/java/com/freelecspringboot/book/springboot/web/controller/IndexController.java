package com.freelecspringboot.book.springboot.web.controller;

import com.freelecspringboot.book.springboot.config.auth.LoginUser;
import com.freelecspringboot.book.springboot.config.auth.dto.SessionUser;
import com.freelecspringboot.book.springboot.service.posts.PostsService;
import com.freelecspringboot.book.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService postsService;
    private final HttpSession httpSession;
    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser user){
        model.addAttribute("posts", postsService.findAllDesc());
        if(user!=null)
            model.addAttribute("userName",user.getName());
        return "index";
    }

    @GetMapping("/posts/save")
    public String postsSave(Model model, @LoginUser SessionUser user){
        if(user!=null)
            model.addAttribute("userName",user.getName());
        return "posts-save";
    }

    @GetMapping("posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model){
        model.addAttribute("post",postsService.findById(id));
        return "posts-update";
    }
}
