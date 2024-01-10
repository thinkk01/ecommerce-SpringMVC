package com.ecommerce.admin.controller;

import com.ecommerce.library.model.Category;
import com.ecommerce.library.service.CategoryService;
import com.ecommerce.library.service.impl.CategoryServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Controller
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryServiceImpl categoryService;
    private static final Logger logger = (Logger) LoggerFactory.getLogger(CategoryController.class);

    @GetMapping("/categories")
    public String categories(Model model,Principal principal
    ) {
        if (principal == null) {
            return "redirect:/login";
        }
        model.addAttribute("title", "Category");
        List<Category> listCategory = categoryService.findAll();
        model.addAttribute("categories", listCategory);
        model.addAttribute("size", listCategory.size());
        model.addAttribute("categoryNew", new Category());
        return "categories";
    }

    @GetMapping("/add-category")
    public String addCategory(@ModelAttribute(name = "categoryNew") Category category,
                              Model model,
                              RedirectAttributes redirectAttributes) {
        try {
            categoryService.save(category);
            model.addAttribute("categoryNew", category);
            redirectAttributes.addFlashAttribute("success", "Added Successfully");
        } catch (DataIntegrityViolationException e1) {
            e1.printStackTrace();
            redirectAttributes.addFlashAttribute("failed", "Failed");
            return "redirect:/categories";
        } catch (Exception e2) {
            e2.printStackTrace();
            model.addAttribute("categoryNew", category);
            redirectAttributes.addFlashAttribute("error",
                    "Error server");
            return "redirect:/categories";
        }
        return "redirect:/categories";
    }

    @RequestMapping(value = "/findById", method = {RequestMethod.GET, RequestMethod.PUT})
    @ResponseBody
    public Optional<Category> findById(Long id) {
        return categoryService.findById(id);
    }

    @GetMapping("/update-category")
    public String update(Category category,
                         RedirectAttributes redirectAttributes
    ) {
        try {
            categoryService.update(category);
            redirectAttributes.addFlashAttribute("success", "Updates SuccessFully");
        } catch (DataIntegrityViolationException e1) {
            e1.printStackTrace();
            redirectAttributes.addFlashAttribute("failed", "Failed to update category");
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Error the Server");
        }
        return "redirect:/categories";
    }

    @RequestMapping(value = "delete-category", method = {RequestMethod.PUT, RequestMethod.GET})
    public String delete(Long id,
                         RedirectAttributes redirectAttributes
    ){
        try{
            categoryService.deleteById(id);
            redirectAttributes.addFlashAttribute("success", "Deleted successfully!");
        }catch(DataIntegrityViolationException e1){
            e1.printStackTrace();
            redirectAttributes.addFlashAttribute("failed", "Failed to delete category");
        }catch(Exception e){
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Error the Server");
        }
        return "redirect:/categories";
    }
    @RequestMapping(value="enable-category",method={RequestMethod.PUT,RequestMethod.GET})
    public String enable(Long id,
                         RedirectAttributes redirectAttributes
                         ){
        try{
            categoryService.enableById(id);
            redirectAttributes.addFlashAttribute("success", "Enable successfully!");
        }catch(DataIntegrityViolationException e1){
            e1.printStackTrace();
            redirectAttributes.addFlashAttribute("failed", "Failed to enable category");
        }catch(Exception e){
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Error the Server");
        }
        return "redirect:/categories";
    }
    @RequestMapping(value="unable-category",method={RequestMethod.PUT,RequestMethod.GET})
    public String unable(Long id,
                         RedirectAttributes redirectAttributes
    ){
        try{
            categoryService.unableId(id);
            redirectAttributes.addFlashAttribute("success", "Unable successfully!");
        }catch(DataIntegrityViolationException e1){
            e1.printStackTrace();
            redirectAttributes.addFlashAttribute("failed", "Failed to unable category");
        }catch(Exception e){
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Error the Server");
        }
        return "redirect:/categories";
    }
}
