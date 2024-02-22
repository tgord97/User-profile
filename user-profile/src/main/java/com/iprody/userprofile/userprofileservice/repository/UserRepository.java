package com.iprody.userprofile.userprofileservice.repository;

import com.iprody.userprofile.userprofileservice.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
}
