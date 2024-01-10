package com.ecommerce.customer.controller;

import com.ecommerce.library.dto.CategoryDto;
import com.ecommerce.library.dto.ProductDto;
import com.ecommerce.library.model.Category;
import com.ecommerce.library.model.Product;
import com.ecommerce.library.service.CategoryService;
import com.ecommerce.library.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class ProductController {
    private final CategoryService categoryService;
    private final ProductService productService;
    @GetMapping("/products")
    public String menu(Model model) {
        model.addAttribute("page", "Products");
        model.addAttribute("title", "Menu");
        List<Category> categories = categoryService.findAllByActivatedTrue();
        List<Product> products = productService.getAllProducts();
        List<Product> listViewProducts = productService.listViewProducts();
        List<CategoryDto> categoryDtoList = categoryService.listCategoryAndProduct();
        model.addAttribute("products", products);
        model.addAttribute("categories",categoryDtoList);
        model.addAttribute("viewProducts", listViewProducts);
        return "shop";
    }
//    @GetMapping("/products-in-category/{id}")
//    public String productDetail(@PathVariable("id") Long id){
//        return "product-detail";
//    }
    @GetMapping("/find-product/{id}")
    public String findProductById(@PathVariable("id") Long id,Model model){
        Product product = productService.getProductById(id);
        model.addAttribute("product",product);
        Long categoryId = product.getCategory().getId();
        List<Product>  products = productService.getRelatedProducts(categoryId);
        model.addAttribute("products", products);
        return "product-detail";
    }
    @GetMapping("/products-in-category/{id}")
    public String findAllProductById(@PathVariable("id") Long id, Model model){
        List<Product> getAllProductInCategory = productService.getAllProductInCategory(id);
        List<CategoryDto> categoryDtoList = categoryService.listCategoryAndProduct();
        Optional<Category> category = categoryService.findById(id);
        String categoryName = category.getClass().getName();
        List<CategoryDto> categories = categoryService.listCategoryAndProduct();
        model.addAttribute("viewProducts", categoryDtoList);
        model.addAttribute("categoryName",categoryName);
        model.addAttribute("categories",categories);
        model.addAttribute("products",getAllProductInCategory);
        return"products-in-category";
    }
    @GetMapping("/high-price")
    public String filterHighPrice(Model model){
        List<Product> products = productService.filterHighPrice();
        List<CategoryDto> categoryDtoList = categoryService.listCategoryAndProduct();
        List<Category> categories = categoryService.findAllByActivatedTrue();
        model.addAttribute("categories",categories);
        model.addAttribute("products",products);
        model.addAttribute("categoryDtoList",categoryDtoList);
        return "filter-high-price";
    }
    @GetMapping("/low-price")
    public String filterLowPrice(Model model){
        List<Product> products = productService.filterLowPrice();
        List<CategoryDto> categoryDtoList = categoryService.listCategoryAndProduct();
        List<Category> categories = categoryService.findAllByActivatedTrue();
        model.addAttribute("categories",categories);
        model.addAttribute("products",products);
        model.addAttribute("categoryDtoList",categoryDtoList);
        return "filter-low-price";
    }
    @GetMapping("/Nothing")
    public String nothing(Model model){
        return "redirect:/products";
    }
}
