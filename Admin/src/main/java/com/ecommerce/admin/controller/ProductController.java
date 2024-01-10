package com.ecommerce.admin.controller;

import com.ecommerce.library.dto.ProductDto;
import com.ecommerce.library.model.Category;
import com.ecommerce.library.model.Product;
import com.ecommerce.library.service.CategoryService;
import com.ecommerce.library.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
public class ProductController {
    @Autowired
    ProductService productService;
    @Autowired
    CategoryService categoryService;

    @GetMapping("/products")
    public String products(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        List<ProductDto> products = productService.allProduct();
        model.addAttribute("products", products);
        model.addAttribute("size", products.size());
        model.addAttribute("title", "Products");
        return "products";
    }

    @GetMapping("/add-product")
    public String products(Model model, Principal principal, RedirectAttributes redirectAttributes) {
        if (principal == null) {
            return "redirect:/login";
        }
        List<Category> categories = categoryService.findAllByActivatedIs();
        model.addAttribute("title", "Add Product");
        model.addAttribute("categories", categories);
        model.addAttribute("product", new ProductDto());
        return "add-product";
    }

    @PostMapping("/save-product")
    public String saveProduct(@ModelAttribute(name = "product") ProductDto productDto,
                              @RequestParam("imageProduct") MultipartFile imageProduct,
                              RedirectAttributes redirectAttributes
    ) {
        try {
            productService.save(imageProduct, productDto);
            redirectAttributes.addFlashAttribute("success", "Add success product");
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Fail to add");
        }
        return "redirect:/products";
    }

    @GetMapping("/update-product/{id}")
    public String updateProduct(@PathVariable("id") Long id,
                                Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        model.addAttribute("title", "Update products");
        List<Category> categories = categoryService.findAllByActivatedIs();
        ProductDto productDto = productService.getById(id);
        model.addAttribute("productDto", productDto);
        model.addAttribute("categories", categories);
        return "update-products";
    }

    @PostMapping("/saveUpdate-product")
    public String processUpdate(@ModelAttribute(name = "productDto") ProductDto productDto,
                                @RequestParam("imageProduct") MultipartFile imageProduct,
                                RedirectAttributes redirectAttributes
    ) {
        try {
            productService.update(imageProduct, productDto);
            redirectAttributes.addFlashAttribute("success", "Update successfully");
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Failed Update");
        }
        return "redirect:/products";
    }

    @RequestMapping(value = "/enable-product/{id}", method = {RequestMethod.GET, RequestMethod.PUT})
    public String enableProduct(@PathVariable("id") Long id,
                                RedirectAttributes redirectAttributes) {
        try{
            productService.enableById(id);
            redirectAttributes.addFlashAttribute("success", "Enable Success");
        }catch(Exception e){
            redirectAttributes.addFlashAttribute("error","Failed Enable");
            e.printStackTrace();
        }


        return "redirect:/products";
    }
    @RequestMapping(value = "/delete-product/{id}", method = {RequestMethod.GET, RequestMethod.PUT})
    public String deleteProduct(@PathVariable("id") Long id,
                                RedirectAttributes redirectAttributes) {
        try{
            productService.deleteById(id);
            redirectAttributes.addFlashAttribute("success", "Delete Success");
        }catch(Exception e){
            redirectAttributes.addFlashAttribute("error","Failed Delete");
            e.printStackTrace();
        }


        return "redirect:/products";
    }
//    @GetMapping("/products/{pageNo}")
//    public String allProduct(@PathVariable("pageNo") int pageNo,Model model,Principal principal){
//        if(principal == null){
//            return "redirect:/login";
//        }
//        Page<ProductDto> products = productService.getAllProducts(pageNo);
//        model.addAttribute("title", "Manage Products");
//        model.addAttribute("size", products.getSize());
//        model.addAttribute("products", products);
//        model.addAttribute("currentPage", pageNo);
//        model.addAttribute("totalPages", products.getTotalPages());
//        return "products";
//
//    }
}
