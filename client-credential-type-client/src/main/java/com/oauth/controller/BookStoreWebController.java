package com.oauth.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.HttpClientErrorException;

import com.oauth.entity.BookPriceInfo;
import com.oauth.entity.UserRatingInfo;

@Controller
public class BookStoreWebController {
	
	@Autowired
	private OAuth2RestTemplate restTemplate;
	
	@GetMapping("/")
	public String home() {
		System.out.println("----home----");
		return "index";
	}
	

	
	@GetMapping("/mybookPriceInfo")
	public String getBookPriceInfo(HttpSession session) {
		System.out.println("----getBookPriceInfo----");

		String endpoint = "http://localhost:12345/myapi/bookPrice/105"; 
		try {
			System.out.println("Before BookPriceInfo");
			System.out.println(restTemplate.getAccessToken());
		
			BookPriceInfo bInfo = restTemplate.getForObject(endpoint, BookPriceInfo.class);
			
			System.out.println("After BookPriceInfo");
			System.out.println(restTemplate.getAccessToken());
	
			
			session.setAttribute("mybookPriceInfo", bInfo);
		}catch (HttpClientErrorException e) {
			throw new RuntimeException("Some problem in connecting BookPriceMS");
		}
		return "bookInfo";
	}
	
	@GetMapping("/mybookRatingInfo")
	public String getRatingInfp(HttpSession session) {
		System.out.println("BookStoreWebController -- getRatingInfp()");
		
		String endpoint = "http://localhost:12345/myapi/userRating/U-999"; 
		try {
			System.out.println("Before UserRatingInfo");
			System.out.println(restTemplate.getAccessToken());
			
			List<UserRatingInfo> myList = restTemplate.getForObject(endpoint, List.class);
			
			System.out.println("After UserRatingInfo");
			System.out.println(restTemplate.getAccessToken());
			
			session.setAttribute("myRatingList", myList);
		}catch (HttpClientErrorException e) {
			throw new RuntimeException("Some Problem in Connecting UserRatingMS");
		}
		return "ratingInfo";
	}
}
