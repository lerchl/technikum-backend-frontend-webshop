package at.technikumwien.webshop.service;

import java.util.List;
import java.util.Optional;

import at.technikumwien.webshop.model.Product;
import at.technikumwien.webshop.repository.ProductRepository;
import at.technikumwien.webshop.repository.TaxRateRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private ProductRepository productRepository;
    private TaxRateRepository taxRateRepository;

    // /////////////////////////////////////////////////////////////////////////
    // Init
    // /////////////////////////////////////////////////////////////////////////

    public ProductService(ProductRepository repository, TaxRateRepository taxRateRepository) {
        this.productRepository = repository;
        this.taxRateRepository = taxRateRepository;
    }

    // /////////////////////////////////////////////////////////////////////////
    // Methods
    // /////////////////////////////////////////////////////////////////////////

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    public List<Product> findByType(String type) {
        return productRepository.findByType(type);
    }

    public Product save(Product product) {
        return productRepository.save(product);
    }

    public Product save(Product product, Long taxRateId) {
        var taxRate = taxRateRepository.findById(taxRateId);

        if (taxRate.isEmpty()) {
            throw new EntityNotFoundException();
        }

        product.setTaxRate(taxRate.get());
        return save(product);
    }

    public Product setActive(Long id) {
        var product = productRepository.findById(id);

        if (product.isEmpty()) {
            throw new EntityNotFoundException();
        }

        Product p = product.get();
        p.setActive(true);
        return save(p);
    }
}
