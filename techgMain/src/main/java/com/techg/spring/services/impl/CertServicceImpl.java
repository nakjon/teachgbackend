package com.techg.spring.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.techg.spring.exceptions.ResourceNotFoundException;
import com.techg.spring.model.Certificate;
import com.techg.spring.payload.CertificateDto;
import com.techg.spring.payload.PagnationResponse;
import com.techg.spring.repositories.CertificateRepo;
import com.techg.spring.services.CertServicce;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@Service
public class CertServicceImpl implements CertServicce {

	@Autowired
	private ModelMapper modelMapper ;
	
	@Autowired
	private CertificateRepo repo;
	
	@Override
	public CertificateDto createCertificate(CertificateDto certificateDto) {
		Certificate certificate = modelMapper.map(certificateDto, Certificate.class) ;
		repo.save(certificate);
		return null;
	}

	@Override
	public CertificateDto updateCertificate(CertificateDto certificateDto) {
		Certificate cer = repo.findById(certificateDto.getId())
				.orElseThrow(()-> new ResourceNotFoundException("certificate", "certificate id", certificateDto.getId()));
		cer.setCoursename(certificateDto.getCoursename());
		cer.setFromdate(certificateDto.getFromdate());
		cer.setName(certificateDto.getName());
		cer.setTodate(certificateDto.getTodate());
		cer.setEmail(certificateDto.getEmail());
		cer.setUniRollNo(certificateDto.getUniRollNo());
		cer.setMobile(certificateDto.getMobile());
		repo.save(cer);
		return modelMapper.map(cer, CertificateDto.class);
	}

	@Override
	public void deleteCertificate(Integer cid) {
		Certificate cer = repo.findById(cid)
				.orElseThrow(()-> new ResourceNotFoundException("certificate", "certificate id", cid));
		repo.delete(cer);
	}

	@Override
	public CertificateDto getCertificate(Integer cid) {
		Certificate cer = repo.findById(cid)
				.orElseThrow(()-> new ResourceNotFoundException("certificate", "certificate id", cid));
		return modelMapper.map(cer, CertificateDto.class);
	}

	@Override
	public PagnationResponse getAllCertificate(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
		Sort sort=null;
		sort = sortDir.equalsIgnoreCase("asc")?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
		Pageable p = PageRequest.of(pageNumber, pageSize) ;
//		System.out.println("4555555555555555555555555555555555555555555555555544444444444");
		Page<Certificate> pageCer = repo.findAll(p);
//		System.out.println("4444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444");
		List<Certificate> certificates = pageCer.getContent();
		List<CertificateDto> certificateDtos = certificates.stream().map((certificate)-> modelMapper.map(certificate, CertificateDto.class)).collect(Collectors.toList());
//		System.out.println("aaaaaaaaa5555");
		PagnationResponse paginationResponse = new PagnationResponse();
		paginationResponse.setContent(certificateDtos);
		paginationResponse.setPageNumber(pageCer.getNumber());
		paginationResponse.setPageSize(pageCer.getSize());
		paginationResponse.setTotalElements(pageCer.getTotalElements());
		paginationResponse.setTotalPages(pageCer.getTotalPages());
		paginationResponse.setLastPage(pageCer.isLast());
		return paginationResponse;
	}

	@Override
	public List<Certificate> getAllCertf() {
		List<Certificate> certificates= repo.findAll();
		return certificates;
	}

	@Override
	public CertificateDto getCerById(Integer cid) {
		Optional<Certificate> certificate = repo.findByCertificateno(cid);
		Certificate certificate2 =null;
		if(certificate.isPresent()) {
			certificate2=certificate.get();
		}else {
			throw new ResourceNotFoundException("Certificate" ,"certificate no",cid);
		}
		return modelMapper.map(certificate2, CertificateDto.class);
	}

	@Override
	public List<CertificateDto> getCertificateByEmail(String email) {
		 List<Certificate> certificateDtos = repo.findByEmail(email) ;
		 return certificateDtos.stream().map(cer->modelMapper.map(cer, CertificateDto.class)).collect(Collectors.toList());
	}

}
