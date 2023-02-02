package at.technikumwien.webshop.service;

import java.util.HashSet;
import java.util.Set;

import at.technikumwien.webshop.model.Cart;
import at.technikumwien.webshop.model.Product;
import at.technikumwien.webshop.repository.CartRepository;
import at.technikumwien.webshop.repository.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class CartService {
    
    private CartRepository cartRepository;
    private ProductRepository productRepository;

    public CartService(CartRepository cartRepository, ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
    }

    // /////////////////////////////////////////////////////////////////////////
    // Methods
    // /////////////////////////////////////////////////////////////////////////

    public Cart save(Long[] productIds) {
        Set<Product> products = new HashSet<>();

        for (Long id : productIds) {
            var product = productRepository.findById(id);

            if (product.isEmpty()) {
                throw new EntityNotFoundException();
            }

            products.add(product.get());
        }

        return cartRepository.save(new Cart(products));
    }
}
