package edu.hm.network.group12.persistence;

import edu.hm.network.group12.persistence.model.Taxi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaxiRepository extends JpaRepository<Taxi, Long> {
}
