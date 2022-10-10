package com.app.ws.io.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.app.ws.io.entity.UserEntity;

@Repository
public interface UserRepository extends PagingAndSortingRepository<UserEntity, Long> {
	UserEntity findByEmail(String email);
	UserEntity findByUserId(String userId);
	UserEntity findUserByEmailVerificationToken(String token);
	
	@Query(value="select * from Users u where u.EMAIL_VERIFICATION_STATUS=TRUE",
			countQuery="select count(*) from Users u where u.EMAIL_VERIFICATION_STATUS=TRUE",
			nativeQuery=true)
	Page<UserEntity> findAllUsersWithConfirmedEmailAddress(Pageable pageableRequest);
	
	
	@Query(value="select * from Users u where u.first_name=?1",
			nativeQuery=true)
	List<UserEntity> findUsersByFirstName(String firstName);
	
	@Query(value="select * from Users u where u.last_name=:lastName",
			nativeQuery=true)
	List<UserEntity> findUsersByLastName(@Param("lastName") String lastName);
	
	@Query(value="select * from Users u where u.first_name LIKE %:keyword% or u.last_name LIKE %:keyword%",
			nativeQuery=true)
	List<UserEntity> findUsersByKeyword(@Param("keyword") String keyword);
	
	@Query(value="select u.first_name,u.last_name from Users u where u.first_name LIKE %:keyword% or u.last_name LIKE %:keyword%",
			nativeQuery=true)
	List<Object[]> findUsersFirstNameAndLastNameByKeyword(@Param("keyword") String keyword);
	
	@Transactional
	@Modifying
	@Query(value="update users u set u.email_verification_status=:emailVerificationStatus "
			+ "where u.user_id=:userId",
			nativeQuery=true)
	void updateUserEmailVerificationStatus(
			@Param("emailVerificationStatus") boolean emailVerificationStatus,
			@Param("userId") String userId);
}
