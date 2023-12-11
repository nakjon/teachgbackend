package com.techg.spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techg.spring.model.Certificate;
import java.util.List;
import java.util.Optional;


public interface CertificateRepo extends JpaRepository<Certificate, Integer> {

	Optional<Certificate> findByCertificateno(int certificateno);
	
	List<Certificate> findByEmail(String email);
	
}
