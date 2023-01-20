package com.spring.boot.validation.OtpValidation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/validation")
public class OtpController {
	
	@Autowired
	public OtpService otpService;
	
	//To Get OTP
	@GetMapping("/generateOtp")
	public int generateOTP() {		
		  Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		  String username = auth.getName(); 
		  int otp = otpService.generateOTP(username);
		  System.out.println(otp); 
		  return otp;		
	}
	
	//To Validate the OTP
	@PostMapping("/validate/{otp}")
	public String validate(@PathVariable int otp) {
		final String SUCCESS = "Entered Otp is valid";
		final String FAIL = "Entered Otp is NOT valid. Please Retry!";
		Authentication auth = SecurityContextHolder.getContext().getAuthentication(); 
		String username = auth.getName();
		//Validate the Otp 
		if(otp >= 0){			
		  int serverOtp = otpService.getOtp(username);
		    if(serverOtp > 0){
		      if(otp == serverOtp){
		          otpService.clearOTP(username);
		
                  return (SUCCESS);
                } 
		        else {
		        	
                    return FAIL;
                    
                }
               }
		    else {
		    	
              return FAIL;
              
            }
             }
		else {
                
			return FAIL;
         }
	}
}
