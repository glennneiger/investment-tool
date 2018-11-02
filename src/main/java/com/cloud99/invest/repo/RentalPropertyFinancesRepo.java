package com.cloud99.invest.repo;

import com.cloud99.invest.domain.financial.rental.RentalPropertyFinances;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RentalPropertyFinancesRepo extends MongoRepository<RentalPropertyFinances, String> {

	Optional<RentalPropertyFinances> findByPropertyId(String propertyId);

}
