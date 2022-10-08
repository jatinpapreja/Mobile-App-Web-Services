package com.app.ws.shared;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UtilsTest {
	
	@Autowired
	Utils utils;

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void testGenerateUserId() {
		String userId = utils.generateUserId(30);
		String userId2 = utils.generateUserId(30);

		
		assertNotNull(userId);
		assertNotNull(userId2);
		assertTrue(userId.length()==30);
		assertTrue(!userId.equalsIgnoreCase(userId2));
		
	}
	
	@Test
	void testTokenNotExpired() {
		
		String token = utils.generateEmailVerificationToken("34jkdfk3kh345k");
		assertNotNull(token);
		
		boolean hasTokenExpired = Utils.hasTokenExpired(token);
		
		assertFalse(hasTokenExpired);
	}
	
	@Test
//	@Disabled
	void testTokenExpired() {
		
		String expiredToken="eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0QDEyMy5jb20iLCJleHAiOjE2NjQ3NzI1OTh9.g5ln0A-f7oVyi58tmFbGSBsKb661W7CrjnwDKver5WxNaJqcGEjFuOOx3vyE067adnKoq05GeAwWClJ4uKnN_Q";
		boolean hasTokenExpired = Utils.hasTokenExpired(expiredToken);
		
		assertTrue(hasTokenExpired);


	}

}
