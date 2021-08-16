package com.jinu.executor.controller;

import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.jinu.executor.service.CandidateService;

@RestController
public class CandidateController {

	@Autowired
	CandidateService candidateService;

	@PostMapping(value = "/addCandidates", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE }, produces = "application/json")
	public ResponseEntity<String> saveCandidates(@RequestParam("files") MultipartFile[] files) throws Exception {
		for(MultipartFile file: files) {
			candidateService.saveCandidates(file.getBytes());
		}		
		return ResponseEntity.status(HttpStatus.CREATED).body(new String("Candidates list is submitted for insertion"));
	}
	@GetMapping(value = "/findAllCandidates", produces = "application/json")
	public CompletableFuture<ResponseEntity> findAllCandidates() throws Exception {
		return candidateService.findAllCandidates().thenApply(ResponseEntity::ok);
	}
}
