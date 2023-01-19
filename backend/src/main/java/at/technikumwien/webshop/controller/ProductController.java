package at.technikumwien.webshop.controller;

import java.util.List;

import at.technikumwien.webshop.model.Product;
import at.technikumwien.webshop.repository.ListProductRepository;
import at.technikumwien.webshop.repository.ProductRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {

    private final ProductRepository productRepository = new ListProductRepository();

    // /////////////////////////////////////////////////////////////////////////
    // Methods
    // /////////////////////////////////////////////////////////////////////////

    @GetMapping("/products")
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @GetMapping("/products/quantity/{id}")
    public int getQuantity(@PathVariable long id) {
        return productRepository.findQuatityById(id);
    }
}
