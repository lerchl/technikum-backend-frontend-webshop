package at.technikumwien.webshop.service;

import java.util.List;

import at.technikumwien.webshop.model.Product;
import at.technikumwien.webshop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repo;

    // /////////////////////////////////////////////////////////////////////////
    // Methods
    // /////////////////////////////////////////////////////////////////////////

    public List<Product> findAll() {
        return repo.findAll();
    }

    public List<Product> findByType(String type) {
        return repo.findByType(type);
    }

    public Product save(Product product) throws ValidationException {
        if (product.getName() == null || product.getName().trim().isEmpty()) {
            throw new ValidationException("Name is required");
        }

        return repo.save(product);
    }
}
