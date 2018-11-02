package com.cloud99.invest.controller;

import com.cloud99.invest.calculations.flip.FlipCalculationType;
import com.cloud99.invest.domain.financial.flip.FlipPropertyFinances;
import com.cloud99.invest.services.FlipAnalyzrService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(path = "/v1/account/{accountId}/flipAnalysis")
public class AnalyzrController implements Controller {

	@Autowired
	private FlipAnalyzrService analyzerService;

	@PostMapping(path = "/flipAnalyzr", produces = JSON_MEDIA_TYPE, consumes = JSON_MEDIA_TYPE)
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public Map<FlipCalculationType, Object> flipAnalyzr(@RequestBody @Validated FlipPropertyFinances request, @RequestParam String accountId) {
		return analyzerService.analyzeFlip(accountId, request);
	}

}
