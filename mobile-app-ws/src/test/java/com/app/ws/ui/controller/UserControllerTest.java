package com.app.ws.ui.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.app.ws.service.impl.UserServiceImpl;
import com.app.ws.shared.dto.AddressDto;
import com.app.ws.shared.dto.UserDto;
import com.app.ws.ui.model.response.UserRest;

class UserControllerTest {

	@InjectMocks
	UserController userController;

	@Mock
	UserServiceImpl userService;

	UserDto userDto;
	String userId = "asdkj98374laksdjf43j";

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);

		userDto = new UserDto();
		userDto.setFirstName("Jatin");
		userDto.setLastName("Papreja");
		userDto.setEmail("test@test.com");
		userDto.setEmailVerificationStatus(Boolean.FALSE);
		userDto.setEmailVerificationToken(null);
		userDto.setUserId(userId);
		userDto.setAddresses(getAddressesDto());
		userDto.setEncryptedPassword("sdfkjafkauyiersd");
	}

	@Test
	void testGetUser() {
		when(userService.getUserByUserId(anyString())).thenReturn(userDto);
		
		UserRest userRest = userController.getUser(userId);
		
		assertNotNull(userRest);
		assertEquals(userId,userRest.getUserId());
		assertEquals(userDto.getFirstName(),userRest.getFirstName());
		assertEquals(userDto.getLastName(),userRest.getLastName());
		assertTrue(userDto.getAddresses().size()==userRest.getAddresses().size());

	}

	private List<AddressDto> getAddressesDto() {

		AddressDto addressDto = new AddressDto();
		addressDto.setType("shipping");
		addressDto.setCity("Delhi");
		addressDto.setCountry("India");
		addressDto.setStreetName("123 Street name");
		addressDto.setPostalCode("ABC123");

		AddressDto billingAddressDto = new AddressDto();
		billingAddressDto.setType("billing");
		billingAddressDto.setCity("Delhi");
		billingAddressDto.setCountry("India");
		billingAddressDto.setStreetName("123 Street name");
		billingAddressDto.setPostalCode("ABC123");

		List<AddressDto> addresses = new ArrayList<>();
		addresses.add(addressDto);
		addresses.add(billingAddressDto);

		return addresses;

	}

}
