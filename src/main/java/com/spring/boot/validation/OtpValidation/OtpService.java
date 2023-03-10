package com.spring.boot.validation.OtpValidation;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

@Service
public class OtpService {
	
	private static final Integer EXPIRE_MINS = 2;
	private LoadingCache<String, Integer> otpCache;
		    
	public OtpService(){
		  super();
		      otpCache = CacheBuilder
		    		  .newBuilder()
		    		  .expireAfterWrite(EXPIRE_MINS, TimeUnit.MINUTES)
		    		  .build(new CacheLoader<String, Integer>() {
		    			  
		    public Integer load(String key) {
		    		return 0;
		    }
		   });
	}
	
	//To Generating the OTP 
	public int generateOTP(String key){
		Random random = new Random();
		    int otp = 100000 + random.nextInt(900000);
		        System.out.println("Generated OTP is : " +otp);
		        otpCache.put(key, otp);
	    return otp;
	}

	public int getOtp(String key){ 
		try{
			return otpCache.get(key); 
		}
		catch (Exception e){
			return 0; 
		}
	}

	public void clearOTP(String key){ 
		otpCache.invalidate(key);
	}


}
