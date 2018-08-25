package com.cloud99.invest.controller;

import com.cloud99.invest.dto.requests.FlipAnalysisRequest;
import com.cloud99.invest.dto.responses.FlipAnalysisResults;
import com.cloud99.invest.services.AnalyzerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/v1")
public class AnalysisController implements Controller {

	@Autowired
	private AnalyzerService analyzerService;

	@PostMapping(path = "/analyzeFlip", produces = JSON, consumes = JSON)
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public FlipAnalysisResults analyzeFlip(FlipAnalysisRequest request) {
		return analyzerService.analyzeFlip(request);
	}

}
