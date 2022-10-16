package com.app.ws.ui.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.app.ws.ui.model.request.LoginRequestModel;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ResponseHeader;



@RestController
public class AuthenticationController {
	
	@ApiOperation(value="User Login",notes="This is the url for logging into the application.")
	@ApiResponses(value= {
			@ApiResponse(code=200,
					message="Response Headers",
					responseHeaders= {
							@ResponseHeader(name="authorization",
									description="Bearer <JWT value here>",
									response=String.class),
							@ResponseHeader(name="userId",
									description="<Public User Id value here",
									response=String.class)
					})
	})
	@PostMapping("/users/login")
	public void theFakeLogin(@RequestBody LoginRequestModel loginRequestModel) {
		throw new IllegalStateException("This method should not be called. "
				+ "This method is implemented by Spring Security.");
	}

}
