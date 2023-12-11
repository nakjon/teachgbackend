package com.techg.spring.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.*;
import com.techg.spring.config.AppConstants;
import com.techg.spring.model.Certificate;
import com.techg.spring.payload.ApiResponse;
import com.techg.spring.payload.CertificateDto;
import com.techg.spring.payload.PagnationResponse;
import com.techg.spring.services.CertServicce;

@RequestMapping("techg/api")                       
@RestController
public class CertificateController {
       

	@Autowired
	private CertServicce certServicce;
	
	 @PostMapping("/admin/createCertificate")
     public ResponseEntity<ApiResponse> createPost(@RequestBody CertificateDto postDto ){
		certServicce.createCertificate(postDto);
		 return new ResponseEntity<ApiResponse>(new ApiResponse("certificate created succesfully",1) ,HttpStatus.CREATED);         
	 }
	@PostMapping("/admin/updateCertificate")
	public ResponseEntity<ApiResponse> updateCert(@RequestBody CertificateDto postDto ){
		certServicce.updateCertificate(postDto);
		return new ResponseEntity<ApiResponse>(new ApiResponse("certificate updated succesfully",1) ,HttpStatus.CREATED);
	}
	 
	 @GetMapping("/admin/getAllCer")
     public ResponseEntity<List<Certificate>> getAllCert(){
		 List<Certificate> postDto = certServicce.getAllCertf();
		 return new ResponseEntity<List<Certificate>>(postDto ,HttpStatus.OK);         
	 }
	 @GetMapping("/auth/getCerById")
     public ResponseEntity<CertificateDto> getCertificateById(@RequestParam(value = "cid") Integer cid){
		 CertificateDto postDto = certServicce.getCerById(cid);
		 return new ResponseEntity<CertificateDto>(postDto ,HttpStatus.OK);         
	 }
	 @PostMapping("/admin/deletecer")
     public ResponseEntity<ApiResponse> deleteCertificateById(@RequestBody Map<String, Object> inputData){
		 Integer cid = (Integer) inputData.get("id") ;
		 //System.out.println("aaaaa"+cid);
		 certServicce.deleteCertificate(cid);
		 return new ResponseEntity<ApiResponse>(new ApiResponse("certificate deleted successfully",1) ,HttpStatus.CREATED);         
	 }
	 
	 @PostMapping("/user/getCerByEmail")
     public ResponseEntity<List<CertificateDto>> getCertificateByEmail(@RequestBody Map<String, Object> inputData){
		 String email = (String) inputData.get("email") ;
		  List<CertificateDto> certificateDtos = certServicce.getCertificateByEmail(email) ;
		 return new ResponseEntity<List<CertificateDto>>(certificateDtos ,HttpStatus.OK);         
	 }
	
	@GetMapping("/admin/getAllCertificate")
	public ResponseEntity<PagnationResponse> getAllCertificates(@RequestParam(value = "pageNumber" ,defaultValue = AppConstants.DEFAULT_PAGE_NUMBER ,required = false) Integer pageNumber ,
			 @RequestParam(value = "pageSize" ,defaultValue = AppConstants.DEFAULT_PAGE_SIZE ,required = false) Integer pageSize ,
			 @RequestParam(value = "sortBy" ,defaultValue = AppConstants.DEFAULT_SORT_BY ,required = false) String sortBy ,
			 @RequestParam(value = "sortDir" ,defaultValue = AppConstants.DEFAULT_SORT_DIR ,required = false) String sortDir){
		
		PagnationResponse posts= certServicce.getAllCertificate(pageNumber,pageSize ,sortBy,sortDir);
		return new ResponseEntity<PagnationResponse>(posts,HttpStatus.OK);
	}
	
	@GetMapping("/auth/getCersByEmail")
    public ResponseEntity<List<CertificateDto>> getCertificatesByEmail(@RequestParam(value = "email") String email){
		 List<CertificateDto> cersDto = certServicce.getCertificateByEmail(email);
		 return new ResponseEntity<List<CertificateDto>>(cersDto ,HttpStatus.OK);         
	 }
}
