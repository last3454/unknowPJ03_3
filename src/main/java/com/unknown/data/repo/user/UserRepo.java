package com.unknown.data.repo.user;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.unknown.data.entity.user.UserEntity;

@Repository
public interface UserRepo extends CrudRepository<UserEntity, Long> {

	public UserEntity findByLoginIdIgnoreCase(String loginId);
}
