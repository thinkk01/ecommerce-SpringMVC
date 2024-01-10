package com.ecommerce.library.service.impl;

import com.ecommerce.library.model.CartItem;
import com.ecommerce.library.model.Customer;
import com.ecommerce.library.model.Product;
import com.ecommerce.library.model.ShoppingCart;
import com.ecommerce.library.repository.CartItemRepository;
import com.ecommerce.library.repository.ShoppingCartRepository;
import com.ecommerce.library.service.CustomerService;
import com.ecommerce.library.service.ShoppingCartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final CartItemRepository itemRepository;
    private final ShoppingCartRepository cartRepository;

    @Override
    public ShoppingCart addItemCart(Product product, int quality, Customer customer) {
        ShoppingCart cart = customer.getCart();
        if (cart == null) {
            cart = new ShoppingCart();
        }
        Set<CartItem> cartItems = cart.getCartItems();
        CartItem cartItem = findCartItem(cartItems, product.getId());
        if (cartItems == null) {
            cartItems = new HashSet<>();
            if (cartItem == null) {
                cartItem = new CartItem();
                cartItem.setProduct(product);
                cartItem.setUnitPrice(quality * product.getCostPrice());
                cartItem.setQuantity(quality);
                cartItem.setCart(cart);
                cartItems.add(cartItem);
                itemRepository.save(cartItem);
            }
        } else {
            if (cartItem == null) {
                cartItems = new HashSet<>();
                if (cartItem == null) {
                    cartItem = new CartItem();
                    cartItem.setProduct(product);
                    cartItem.setUnitPrice(quality * product.getCostPrice());
                    cartItem.setQuantity(quality);
                    cartItem.setCart(cart);
                    cartItems.add(cartItem);
                    itemRepository.save(cartItem);
                } else {
                    cartItem.setQuantity(cartItem.getQuantity() + quality);
                    cartItem.setUnitPrice(cartItem.getUnitPrice() + (quality * product.getCostPrice()));
                    itemRepository.save(cartItem);
                }
            }

        }
        cart.setCartItems(cartItems);
        int totalItems = totalItems(cart.getCartItems());
        double totalPrice = totalPrice(cart.getCartItems());
        cart.setTotalPrice(totalPrice);
        cart.setTotalItems(totalItems);
        cart.setCustomer(customer);

        return cartRepository.save(cart);
    }

    private CartItem findCartItem(Set<CartItem> cartItems, Long productId) {
        if (cartItems == null) {
            return null;
        }
        CartItem cartItem = null;
        for (CartItem item : cartItems) {
            if (item.getProduct().getId() == productId) {
                cartItem = item;
            }
        }
        return cartItem;
    }
    private int totalItems(Set<CartItem> cartItems){
        int totalItems = 0;
        for(CartItem item : cartItems){
            totalItems += item.getQuantity();
        }
        return totalItems;
    }
    private double totalPrice(Set<CartItem> cartItems){
        double totalPrice = 0.0;

        for(CartItem item : cartItems){
            totalPrice += item.getUnitPrice();
        }

        return totalPrice;
    }
}
