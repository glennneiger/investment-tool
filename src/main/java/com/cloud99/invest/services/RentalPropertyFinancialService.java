package com.cloud99.invest.services;

import com.cloud99.invest.domain.financial.rental.RentalPropertyFinances;
import com.cloud99.invest.exceptions.EntityNotFoundException;
import com.cloud99.invest.repo.RentalPropertyFinancesRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RentalPropertyFinancialService {

	@Autowired
	private RentalPropertyFinancesRepo finacialRepo;

	@Autowired
	private PropertyService propertyService;

	public RentalPropertyFinances getPropertyFinancials(String propertyId) {

		Optional<RentalPropertyFinances> optional = finacialRepo.findByPropertyId(propertyId);
		if (!optional.isPresent()) {
			log.info("Rental property finances not found for property: " + propertyId);
			throw new EntityNotFoundException("Rental Property Finances", propertyId);
		}
		return optional.get();
	}

	public RentalPropertyFinances createPropertyFinances(RentalPropertyFinances propFinances) {

		// make sure the property is valid
		propertyService.getAndValidateProperty(propFinances.getPropertyId());
		return finacialRepo.save(propFinances);

	}

}
