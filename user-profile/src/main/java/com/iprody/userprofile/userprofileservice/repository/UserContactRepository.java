package com.iprody.userprofile.userprofileservice.repository;

import com.iprody.userprofile.userprofileservice.entity.UserContact;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserContactRepository extends CrudRepository<UserContact, Long> {
}
