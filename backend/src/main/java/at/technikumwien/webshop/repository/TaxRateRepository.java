package at.technikumwien.webshop.repository;

import at.technikumwien.webshop.model.TaxRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaxRateRepository extends JpaRepository<TaxRate, Long> {
    // noop
}
