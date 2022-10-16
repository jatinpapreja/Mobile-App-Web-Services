package com.app.ws.ui.controller;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.ws.exception.UserServiceException;
import com.app.ws.service.AddressService;
import com.app.ws.service.UserService;
import com.app.ws.shared.dto.AddressDto;
import com.app.ws.shared.dto.UserDto;
import com.app.ws.ui.model.request.UserDetailsRequestModel;
import com.app.ws.ui.model.response.AddressesRest;
import com.app.ws.ui.model.response.ErrorMessages;
import com.app.ws.ui.model.response.OperationStatusModel;
import com.app.ws.ui.model.response.RequestOperationName;
import com.app.ws.ui.model.response.RequestOperationStatus;
import com.app.ws.ui.model.response.UserRest;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;

@RestController
@RequestMapping("users") // http://localhost:8080/mobile-app-ws/users
@CrossOrigin(origins="*")
public class UserController {

	@Autowired
	UserService userService;
	
	@Autowired
	AddressService addressesService;

	@ApiImplicitParams({
		@ApiImplicitParam(name="authorization",value="${userController.authorizationHeader.description}",paramType="header")
	})
	@GetMapping(path = "/{id}")
	public UserRest getUser(@PathVariable String id) {
		UserRest returnValue = new UserRest();

		UserDto userDto = userService.getUserByUserId(id);
//		BeanUtils.copyProperties(userDto, returnValue);
		ModelMapper modelMapper = new ModelMapper();
		returnValue = modelMapper.map(userDto, UserRest.class);

		return returnValue;
	}

	@PostMapping
	public UserRest createUser(@RequestBody UserDetailsRequestModel userDetails) throws Exception {

		UserRest returnValue = new UserRest();

		if (userDetails.getFirstName().isEmpty())
			throw new UserServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());

//		UserDto userDto = new UserDto();
//		BeanUtils.copyProperties(userDetails, userDto);
		
		ModelMapper modelMapper = new ModelMapper();
		UserDto userDto = modelMapper.map(userDetails, UserDto.class);

		UserDto createdUser = userService.createUser(userDto);
//		BeanUtils.copyProperties(createdUser, returnValue);
		
		returnValue = modelMapper.map(createdUser, UserRest.class);

		return returnValue;
	}

	@ApiImplicitParams({
		@ApiImplicitParam(name="authorization",value="${userController.authorizationHeader.description}",paramType="header")
	})
	@PutMapping(path = "/{id}")
	public UserRest updateUser(@PathVariable String id, @RequestBody UserDetailsRequestModel userDetails) {

		UserRest returnValue = new UserRest();

		UserDto userDto = new UserDto();
		BeanUtils.copyProperties(userDetails, userDto);

		UserDto updatedUser = userService.updateUser(id, userDto);
		BeanUtils.copyProperties(updatedUser, returnValue);

		return returnValue;
	}

	@ApiImplicitParams({
		@ApiImplicitParam(name="authorization",value="${userController.authorizationHeader.description}",paramType="header")
	})
	@DeleteMapping(path = "/{id}")
	public OperationStatusModel deleteUser(@PathVariable String id) {
		
		OperationStatusModel returnValue = new OperationStatusModel();
		returnValue.setOperationName(RequestOperationName.DELETE.name());
		
		userService.deleteUser(id);
		
		returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());
		
		return returnValue;
	}
	
	@ApiImplicitParams({
		@ApiImplicitParam(name="authorization",value="${userController.authorizationHeader.description}",paramType="header")
	})
	@GetMapping
	public List<UserRest> getUsers(@RequestParam(value="page",defaultValue="0") int page,
			@RequestParam(value="limit",defaultValue="2") int limit){
		
		List<UserRest> returnValue = new ArrayList<>();
		List<UserDto> users = userService.getUsers(page,limit);
		
		for(UserDto userDto: users) {
			UserRest userModel = new UserRest();
			BeanUtils.copyProperties(userDto, userModel);
			returnValue.add(userModel);
		}
		
		return returnValue;
	}
	
	@ApiImplicitParams({
		@ApiImplicitParam(name="authorization",value="${userController.authorizationHeader.description}",paramType="header")
	})
	@GetMapping(path="/{id}/addresses")
	public List<AddressesRest> getUserAddresses(@PathVariable String id){
		List<AddressesRest> returnValue = new ArrayList<>();

		List<AddressDto> addressesDto = addressesService.getAddresses(id);
		
		if(addressesDto!=null && !addressesDto.isEmpty()) {
			Type listType = new TypeToken<List<AddressesRest>>() {}.getType();
			returnValue = new ModelMapper().map(addressesDto, listType);

		}

		return returnValue;
		
	}
	
	@ApiImplicitParams({
		@ApiImplicitParam(name="authorization",value="${userController.authorizationHeader.description}",paramType="header")
	})
	@GetMapping(path="/{userId}/addresses/{addressId}")
	public AddressesRest getUserAddress(@PathVariable String addressId) {
		AddressDto addressDto = addressesService.getAddress(addressId);
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map(addressDto, AddressesRest.class);
	}
	
	@GetMapping(path="/email-verification")
	public OperationStatusModel verifyEmailToken(@RequestParam(value="token") String token) {
		OperationStatusModel returnValue = new OperationStatusModel();
		returnValue.setOperationName(RequestOperationName.VERIFY_EMAIL.name());
		
		boolean isVerified = userService.verifyEmailToken(token);
		
		if(isVerified) {
			returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());
		}
		else {
			returnValue.setOperationResult(RequestOperationStatus.ERROR.name());

		}
			
		return returnValue;
	}
	

}
