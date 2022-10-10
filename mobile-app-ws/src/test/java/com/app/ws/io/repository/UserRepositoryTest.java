package com.app.ws.io.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.app.ws.io.entity.AddressEntity;
import com.app.ws.io.entity.UserEntity;
import com.app.ws.io.repositories.UserRepository;

@SpringBootTest
class UserRepositoryTest {

	@Autowired
	UserRepository userRepository;

	static boolean recordsCreated = false;

	@BeforeEach
	void setUp() throws Exception {

		if (!recordsCreated)
			createRecords();
	}

	@Test
	void testGetVerifiedUsers() {
		Pageable pageableRequest = PageRequest.of(0, 2);
		Page<UserEntity> page = userRepository.findAllUsersWithConfirmedEmailAddress(pageableRequest);
		assertNotNull(page);

		List<UserEntity> userEntities = page.getContent();
		assertNotNull(userEntities);
		assertTrue(userEntities.size() == 2);

		Pageable pageableRequest2 = PageRequest.of(0, 1);
		Page<UserEntity> page2 = userRepository.findAllUsersWithConfirmedEmailAddress(pageableRequest2);
		assertNotNull(page2);

		List<UserEntity> userEntities2 = page2.getContent();
		assertNotNull(userEntities2);
		assertTrue(userEntities2.size() == 1);

		Pageable pageableRequest3 = PageRequest.of(1, 1);
		Page<UserEntity> page3 = userRepository.findAllUsersWithConfirmedEmailAddress(pageableRequest3);
		assertNotNull(page3);

		List<UserEntity> userEntities3 = page3.getContent();
		assertNotNull(userEntities3);
		assertTrue(userEntities3.size() == 1);
	}

	@Test
	void testFindUsersByFirstName() {
		String firstName = "Jatin";
		List<UserEntity> users = userRepository.findUsersByFirstName(firstName);
		assertNotNull(users);
		assertTrue(users.size() == 2);

		UserEntity user = users.get(0);
		assertEquals(firstName, user.getFirstName());
	}

	@Test
	void testFindUsersByLastName() {
		String lastName = "Papreja";
		List<UserEntity> users = userRepository.findUsersByLastName(lastName);
		assertNotNull(users);
		assertTrue(users.size() == 2);

		UserEntity user = users.get(0);
		assertEquals(lastName, user.getLastName());
	}
	
	@Test
	void testFindUsersByKeyword() {
		String keyword = "prej";
		List<UserEntity> users = userRepository.findUsersByKeyword(keyword);
		assertNotNull(users);
		assertTrue(users.size() == 2);

		UserEntity user = users.get(0);
		assertTrue(user.getFirstName().contains(keyword)||
				user.getLastName().contains(keyword));
	}
	
	@Test 
	void testfindUsersFirstNameAndLastNameByKeyword() {
		String keyword = "prej";
		List<Object[]> users = userRepository.findUsersFirstNameAndLastNameByKeyword(keyword);
		assertNotNull(users);
		assertTrue(users.size() == 2);

		Object[] user = users.get(0);
		assertTrue(user.length==2);
		
		String userFirstName = String.valueOf(user[0]);
		String userLastName = String.valueOf(user[1]);
		
		assertNotNull(userFirstName);
		assertNotNull(userLastName);

//		System.out.println(userFirstName);
//		System.out.println(userLastName);
	}
	
	@Test
	void testFindUserEntityByUserId() {
		String userId = "1a2b3c";
		UserEntity userEntity = userRepository.findUserEntityByUserId(userId);
		
		assertNotNull(userEntity);
		assertEquals(userId,userEntity.getUserId());
	}
	
	@Test
	void testFindUserEntityFullNameByUserId() {
		String userId = "1a2b3c";
		List<Object[]> records = userRepository.findUserEntityFullNameByUserId(userId);
		assertNotNull(records);
		assertTrue(records.size() == 1);

		Object[] user = records.get(0);
		assertTrue(user.length==2);
		
		String userFirstName = String.valueOf(user[0]);
		String userLastName = String.valueOf(user[1]);
		
		assertNotNull(userFirstName);
		assertNotNull(userLastName);
	}
	
	@Test
	void testUpdateUserEmailVerificationStatus() {
		boolean newEmailVerificationStatus = false;
		userRepository.updateUserEmailVerificationStatus(newEmailVerificationStatus, "1a2b3c");
		
		UserEntity storedUserDetails = userRepository.findByUserId("1a2b3c");
		
		boolean storedEmailVerificationStatus = storedUserDetails.getEmailVerificationStatus();
		
		assertEquals(newEmailVerificationStatus,storedEmailVerificationStatus);
	}
	
	@Test
	void testUpdateUserEntityEmailVerificationStatus() {
		boolean newEmailVerificationStatus = true;
		userRepository.updateUserEntityEmailVerificationStatus(newEmailVerificationStatus, "1a2b3c");
		
		UserEntity storedUserDetails = userRepository.findByUserId("1a2b3c");
		
		boolean storedEmailVerificationStatus = storedUserDetails.getEmailVerificationStatus();
		
		assertEquals(newEmailVerificationStatus,storedEmailVerificationStatus);
	}
	

	private void createRecords() {
		// Prepare User Entity
		UserEntity userEntity = new UserEntity();
		userEntity.setFirstName("Jatin");
		userEntity.setLastName("Papreja");
		userEntity.setEmail("test@test.com");
		userEntity.setUserId("1a2b3c");
		userEntity.setEncryptedPassword("xxx");
		userEntity.setEmailVerificationStatus(true);

		// Prepare User Addresses
		AddressEntity addressEntity = new AddressEntity();
		addressEntity.setType("shipping");
		addressEntity.setAddressId("adk2k32kh2");
		addressEntity.setCity("Delhi");
		addressEntity.setCountry("India");
		addressEntity.setStreetName("123 Street name");
		addressEntity.setPostalCode("ABC123");

		List<AddressEntity> addresses = new ArrayList<>();
		addresses.add(addressEntity);

		userEntity.setAddresses(addresses);

		userRepository.save(userEntity);

		// Prepare User Entity
		UserEntity userEntity2 = new UserEntity();
		userEntity2.setFirstName("Jatin");
		userEntity2.setLastName("Papreja");
		userEntity2.setEmail("test@test.com");
		userEntity2.setUserId("1a2b3cdd");
		userEntity2.setEncryptedPassword("xxx");
		userEntity2.setEmailVerificationStatus(true);

		// Prepare User Addresses
		AddressEntity addressEntity2 = new AddressEntity();
		addressEntity2.setType("shipping");
		addressEntity2.setAddressId("adk2k32kh2dd");
		addressEntity2.setCity("Delhi");
		addressEntity2.setCountry("India");
		addressEntity2.setStreetName("123 Street name");
		addressEntity2.setPostalCode("ABC123");

		List<AddressEntity> addresses2 = new ArrayList<>();
		addresses2.add(addressEntity2);

		userEntity2.setAddresses(addresses2);

		userRepository.save(userEntity2);

		recordsCreated = true;
	}

}
