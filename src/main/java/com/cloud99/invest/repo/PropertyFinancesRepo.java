package com.cloud99.invest.repo;

import com.cloud99.invest.domain.financial.PropertyFinances;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PropertyFinancesRepo extends MongoRepository<PropertyFinances, String> {

}
