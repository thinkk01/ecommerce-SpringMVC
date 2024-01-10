package com.ecommerce.customer.controller;

import com.ecommerce.library.model.Customer;
import com.ecommerce.library.model.Product;
import com.ecommerce.library.model.ShoppingCart;
import com.ecommerce.library.repository.ShoppingCartRepository;
import com.ecommerce.library.service.CustomerService;
import com.ecommerce.library.service.ProductService;
import com.ecommerce.library.service.ShoppingCartService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class CartController {
    private final ShoppingCartService shoppingCartService;
    private final CustomerService customerService;
    private final ProductService productService;

    @GetMapping("/cart")
    public String cart(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        String username = principal.getName();
        Customer customer = customerService.findByUsername(username);
        ShoppingCart shoppingCart = customer.getCart();
        if (shoppingCart == null) {
            model.addAttribute("check", "No item in your cart!");
        }
        model.addAttribute("shoppingCart", shoppingCart);

        return "cart";
    }

    @PostMapping("/add-to-cart")
    public String addToCart(
            @RequestParam("id") Long productId,
            @RequestParam(value = "quantity", required = false, defaultValue = "1") int quantity,
            Principal principal,
            Model model,
            HttpServletRequest request) {
        if(principal==null){
            return "redirect:/login";
        }
        Product product = productService.getProductById(productId);
        String username =principal.getName();
        Customer customer = customerService.findByUsername(username);
        ShoppingCart cart =shoppingCartService.addItemCart(product,quantity,customer);
        return "redirect:" + request.getHeader("Referer");
    }
}
