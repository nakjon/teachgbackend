package com.techg.spring.services;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.techg.spring.model.Certificate;
import com.techg.spring.payload.CertificateDto;
import com.techg.spring.payload.PagnationResponse;

@Service
@Component
public interface CertServicce {

	PagnationResponse getAllCertificate(Integer pageNumber,Integer pageSize,String sortBy ,String sortDir);
	
    CertificateDto createCertificate(CertificateDto certificateDto);
	
	CertificateDto updateCertificate(CertificateDto certificateDto );
	
	void deleteCertificate(Integer cid); 
	
	CertificateDto getCertificate(Integer categoryId);

	List<Certificate> getAllCertf();
	
	CertificateDto getCerById(Integer cid);
	
	List<CertificateDto>  getCertificateByEmail(String email) ;
}
