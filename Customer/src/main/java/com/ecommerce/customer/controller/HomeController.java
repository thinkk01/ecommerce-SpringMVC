package com.ecommerce.customer.controller;

import com.ecommerce.library.dto.ProductDto;
import com.ecommerce.library.model.Category;
import com.ecommerce.library.service.CategoryService;
import com.ecommerce.library.service.ProductService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.w3c.dom.stylesheets.LinkStyle;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final CategoryService categoryService;
    private final ProductService productService;

    @RequestMapping(value={"/index","/"},method = RequestMethod.GET)
    public String index(Model model, Principal principal, HttpSession session){
        if(principal!=null){
            session.setAttribute("username",principal.getName());
        }else{
            session.setAttribute("username","");
        }
        return "home";
    }
    @GetMapping("/home")
    public String home(Model model){
        List<Category> categoryList = categoryService.findAll();
        List<ProductDto> productDtoList = productService.allProduct();
        model.addAttribute("categories",categoryList);
        model.addAttribute("products",productDtoList);
        return "index";
    }
}
