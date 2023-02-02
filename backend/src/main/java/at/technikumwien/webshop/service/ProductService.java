package at.technikumwien.webshop.service;

import java.util.List;

import at.technikumwien.webshop.model.Product;
import at.technikumwien.webshop.repository.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private ProductRepository repository;

    // /////////////////////////////////////////////////////////////////////////
    // Init
    // /////////////////////////////////////////////////////////////////////////

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    // /////////////////////////////////////////////////////////////////////////
    // Methods
    // /////////////////////////////////////////////////////////////////////////

    public List<Product> findAll() {
        return repository.findAll();
    }

    public List<Product> findByType(String type) {
        return repository.findByType(type);
    }

    public Product save(Product product) {
        return repository.save(product);
    } 

    public Product setActive(Long id) {
        var product = repository.findById(id);

        if (product.isEmpty()) {
            throw new EntityNotFoundException();
        }

        Product p = product.get();
        p.setActive(true);
        return save(p);
    }
}
