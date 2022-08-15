package id.co.nds.catalogue.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import id.co.nds.catalogue.entities.LoanEntity;

@Repository
public interface LoanRepo extends JpaRepository<LoanEntity, String> {
    
}
