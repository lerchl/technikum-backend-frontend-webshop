package at.technikumwien.webshop.controller;

import java.net.URI;
import java.util.List;

import at.technikumwien.webshop.dto.ProductDTO;
import at.technikumwien.webshop.model.Product;
import at.technikumwien.webshop.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProductController {

    private ProductService service;

    // /////////////////////////////////////////////////////////////////////////
    // Init
    // /////////////////////////////////////////////////////////////////////////

    public ProductController(ProductService service) {
        this.service = service;
    }

    // /////////////////////////////////////////////////////////////////////////
    // Methods
    // /////////////////////////////////////////////////////////////////////////

    @GetMapping
    public List<Product> findAllProducts() {
        return service.findAll();
    }

    @GetMapping("/{type}")
    public List<Product> findAllProductsByType(@PathVariable String type) {
        return service.findByType(type);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Product> createProduct(@RequestBody @Valid ProductDTO productDTO) {
        Product product = service.save(fromDTO(productDTO), productDTO.getTaxRateId());
        return ResponseEntity.created(URI.create("http://localhost:8080/products")).body(product);
    }

    @PutMapping("/setActive/{id}")
    public Product setActive(@PathVariable Long id) {
        return service.setActive(id);
    }

    // /////////////////////////////////////////////////////////////////////////
    // Util
    // /////////////////////////////////////////////////////////////////////////

    private static Product fromDTO(ProductDTO productDTO) {
        return new Product(productDTO.getName(),
                           productDTO.getDescription(),
                           productDTO.getImageUrl(),
                           productDTO.getPrice(),
                           productDTO.getQuantity(),
                           productDTO.getType());
    }
}
