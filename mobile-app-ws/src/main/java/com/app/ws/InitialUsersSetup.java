package com.app.ws;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.app.ws.io.entity.AuthorityEntity;
import com.app.ws.io.entity.RoleEntity;
import com.app.ws.io.entity.UserEntity;
import com.app.ws.io.repositories.AuthorityRepository;
import com.app.ws.io.repositories.RoleRepository;
import com.app.ws.io.repositories.UserRepository;
import com.app.ws.shared.Utils;

@Component
public class InitialUsersSetup {

	@Autowired
	AuthorityRepository authorityRepository;

	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	Utils utils;
	
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	UserRepository userRepository;

	@EventListener
	@Transactional
	public void onApplicationEvent(ApplicationReadyEvent event) {
		System.out.println("From Application Ready Event...");

		AuthorityEntity readAuthority = createAuthority("READ_AUTHORITY");
		AuthorityEntity writeAuthority = createAuthority("WRITE_AUTHORITY");
		AuthorityEntity deleteAuthority = createAuthority("DELETE_AUTHORITY");
		
		RoleEntity roleUser = createRole("ROLE_USER",Arrays.asList(readAuthority,writeAuthority));
		RoleEntity roleAdmin = createRole("ROLE_ADMIN",Arrays.asList(readAuthority,writeAuthority,deleteAuthority));

		if(roleAdmin==null) return;
		
		UserEntity adminUser = new UserEntity();
		adminUser.setFirstName("Jatin");
		adminUser.setLastName("Papreja");
		adminUser.setEmail("test@test.com");
		adminUser.setEmailVerificationStatus(true);
		adminUser.setUserId(utils.generateUserId(30));
		adminUser.setEncryptedPassword(bCryptPasswordEncoder.encode("12345678"));
		adminUser.setRoles(Arrays.asList(roleAdmin));
		
		userRepository.save(adminUser);
	}

	@Transactional
	private AuthorityEntity createAuthority(String name) {

		AuthorityEntity authority = authorityRepository.findByName(name);

		if (authority == null) {
			authority = new AuthorityEntity();
			authority.setName(name);
			authorityRepository.save(authority);
		}

		return authority;
	}

	@Transactional
	private RoleEntity createRole(String name, Collection<AuthorityEntity> authorities) {

		RoleEntity role = roleRepository.findByName(name);

		if (role == null) {
			role = new RoleEntity();
			role.setName(name);
			role.setAuthorities(authorities);
			roleRepository.save(role);
		}

		return role;
	}

}
