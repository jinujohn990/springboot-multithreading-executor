package com.jinu.executor.service;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.jinu.executor.model.Candidate;
import com.jinu.executor.repository.CandidateRepository;

@Service
public class CandidateService {
	
	@Autowired
	private CandidateRepository candidateRepository;
	
    @Async
  	public CompletableFuture<List<Candidate>> saveCandidates(byte[] file) throws Exception {
  		List<Candidate> candidates = parseCSVFile(file);
  		candidateRepository.saveAll(candidates);
  		return CompletableFuture.completedFuture(candidates);
  	}

	private List<Candidate> parseCSVFile(byte[] file) throws Exception {
		List<Candidate> candidatesList = new ArrayList<Candidate>();
		String line;
		try (final BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(file)))) {
			//skipping header
			String headerLine = br.readLine();
			while ((line = br.readLine()) != null) {
				String[] data = line.split(",");
				Candidate candidate = new Candidate();
				candidate.setFirstName(data[0]);
				candidate.setLastName(data[1]);
				candidate.setEmail(data[2]);
				candidate.setUsername(data[3]);
				candidatesList.add(candidate);
			}

		} catch (IOException e) {
			throw new Exception();
		}
		return candidatesList;
	}
	@Async
	public CompletableFuture<List<Candidate>> findAllCandidates(){
		List<Candidate> candidates = candidateRepository.findAll();
		return CompletableFuture.completedFuture(candidates);
	}
}
