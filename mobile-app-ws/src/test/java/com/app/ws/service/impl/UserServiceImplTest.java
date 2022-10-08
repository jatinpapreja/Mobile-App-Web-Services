package com.app.ws.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.app.ws.exception.UserServiceException;
import com.app.ws.io.entity.AddressEntity;
import com.app.ws.io.entity.UserEntity;
import com.app.ws.io.repositories.UserRepository;
import com.app.ws.shared.Utils;
import com.app.ws.shared.dto.AddressDto;
import com.app.ws.shared.dto.UserDto;

class UserServiceImplTest {
	
	@InjectMocks
	UserServiceImpl userService;
	
	@Mock
	UserRepository userRepository;
	
	@Mock
	Utils utils;

	@Mock
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	String userId = "kjasdhkjadf";
	String encryptedPassword = "asdfkjhaksh";
	
	UserEntity userEntity;
	
	@BeforeEach
	void setUp() throws Exception{
		MockitoAnnotations.initMocks(this);
		
		userEntity = new UserEntity();
		userEntity.setId(1L);
		userEntity.setFirstName("Jatin");
		userEntity.setLastName("Papreja");
		userEntity.setUserId(userId);
		userEntity.setEncryptedPassword(encryptedPassword);
		userEntity.setAddresses(getAddressesEntity());
	}

	@Test
	final void testGetUser() {
		
		
		
		when(userRepository.findByEmail(anyString())).thenReturn(userEntity);
		
		UserDto userDto = userService.getUser("test@test.com");
		
		assertNotNull(userDto);
		assertEquals("Jatin",userDto.getFirstName());
	}
	
	@Test
	final void testGetUser_UsernameNotFoundException() {
		
		when(userRepository.findByEmail(anyString())).thenReturn(null);
		
		assertThrows(UsernameNotFoundException.class,
				()->{
					userService.getUser("test@test.com");
				});
	}
	
	@Test
	final void testCreateUser_CreateUserServiceException() {
		when(userRepository.findByEmail(anyString())).thenReturn(userEntity);
		UserDto userDto = new UserDto();
		userDto.setAddresses(getAddressesDto());
		userDto.setFirstName("Jatin");
		userDto.setLastName("Papreja");
		userDto.setEmail("test@test.com");
		userDto.setPassword("12345678");
		
		assertThrows(UserServiceException.class,
				()->{
					userService.createUser(userDto);
				});
	}
	
	@Test
	final void testCreateUser() {
		
		
		when(userRepository.findByEmail(anyString())).thenReturn(null);
		when(utils.generateAddressId(anyInt())).thenReturn("asdfkh3490asdf");
		when(utils.generateUserId(anyInt())).thenReturn(userId);
		when(bCryptPasswordEncoder.encode(anyString())).thenReturn(encryptedPassword);
		when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);
		
		UserDto userDto = new UserDto();
		userDto.setAddresses(getAddressesDto());
		userDto.setFirstName("Jatin");
		userDto.setLastName("Papreja");
		userDto.setEmail("test@test.com");
		userDto.setPassword("12345678");
		
		UserDto storedUserDetails = userService.createUser(userDto);
		
		assertNotNull(storedUserDetails);
		assertEquals(userEntity.getFirstName(),storedUserDetails.getFirstName());
		assertEquals(userEntity.getLastName(),storedUserDetails.getLastName());
		assertNotNull(storedUserDetails.getUserId());
		assertEquals(storedUserDetails.getAddresses().size(),userEntity.getAddresses().size());
		verify(utils,times(2)).generateAddressId(30);
		verify(bCryptPasswordEncoder,times(1)).encode("12345678");
		verify(userRepository,times(1)).save(any(UserEntity.class));

	}
	
	private List<AddressDto> getAddressesDto(){
		
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
	
	private List<AddressEntity> getAddressesEntity(){
		List<AddressDto> addresses = getAddressesDto();
		
		Type listType = new TypeToken<List<AddressEntity>>() {}.getType();
		
		return new ModelMapper().map(addresses,listType);
	}

}
