package at.technikumwien.webshop.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import at.technikumwien.webshop.model.Product;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {
    
    private static List<Product> products = new ArrayList<>();
    private static Map<Product, Integer> quantity = new HashMap<>();

    { // NOSONAR
        products.add(new Product(1, "Honig", "Feinster Waldhonig aus eigener Imkerei", 5.0f));
        products.add(new Product(2, "Hammer", "Ein Hammer mit dem man Nagel in die Wand schlagen kann", 22.5f));
        products.add(new Product(3, "Fahrrad", "Mit diesem Fahrrad kommen sie einfach von A nach B", 234.99f));

        quantity.put(products.get(0), 200);
        quantity.put(products.get(1), 15);
        quantity.put(products.get(2), 3);
    }

    // /////////////////////////////////////////////////////////////////////////
    // Methods
    // /////////////////////////////////////////////////////////////////////////

    @GetMapping("/products")
    public List<Product> getAllProducts() {
        return products;
    }

    @GetMapping("/products/quantity/{id}")
    public int getQuantity(@PathVariable int id) {
        Optional<Product> product = products.stream().filter(p -> p.getId() == id).findFirst();

        if (product.isPresent()) {
            return quantity.get(product.get());
        } else {
            return 0;
        }
    }
}
