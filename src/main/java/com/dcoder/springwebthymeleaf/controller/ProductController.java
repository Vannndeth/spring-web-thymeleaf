package com.dcoder.springwebthymeleaf.controller;

import com.dcoder.springwebthymeleaf.model.Product;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ProductController {

    private static List<Product> products = new ArrayList<>();

    static {
        products.add(new Product(1L, "Product 1", 10.0));
        products.add(new Product(2L, "Product 2", 20.0));
        products.add(new Product(3L, "Product 3", 30.0));
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("products", products);
        return "index";
    }

    @GetMapping("/product/new")
    public String newProduct(Model model) {
        model.addAttribute("product", new Product(null, "", 0.0));
        return "new";
    }

    @PostMapping("/product")
    public String createProduct(@ModelAttribute Product product) {
        product.setId((long) (products.size() + 1)); // Simulating auto-increment ID
        products.add(product);
        return "redirect:/";
    }

    @GetMapping("/product/edit/{id}")
    public String editProduct(@PathVariable Long id, Model model) {
        Product product = products.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElse(null);
        if (product == null) {
            return "redirect:/";
        }
        model.addAttribute("product", product);
        return "edit";
    }

    @PostMapping("/product/update")
    public String updateProduct(@ModelAttribute Product product) {
        Product existingProduct = products.stream()
                .filter(p -> p.getId().equals(product.getId()))
                .findFirst()
                .orElse(null);
        if (existingProduct != null) {
            existingProduct.setName(product.getName());
            existingProduct.setPrice(product.getPrice());
        }
        return "redirect:/";
    }

    @GetMapping("/product/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        products.removeIf(p -> p.getId().equals(id));
        return "redirect:/";
    }
}

