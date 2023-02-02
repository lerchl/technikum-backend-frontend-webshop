package at.technikumwien.webshop.controller;

import java.util.List;

import at.technikumwien.webshop.model.TaxRate;
import at.technikumwien.webshop.repository.TaxRateRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/taxRates")
public class TaxRateController {

    private TaxRateRepository repository;

    // /////////////////////////////////////////////////////////////////////////
    // Init
    // /////////////////////////////////////////////////////////////////////////

    public TaxRateController(TaxRateRepository repository) {
        this.repository = repository;
    }

    // /////////////////////////////////////////////////////////////////////////
    // Methods
    // /////////////////////////////////////////////////////////////////////////

    @GetMapping
    public List<TaxRate> findAll() {
        return repository.findAll();
    }

    @PostMapping
    @ResponseStatus(code = CREATED)
    public TaxRate create(@RequestBody TaxRate taxRate) {
        return repository.save(taxRate);
    }
}
