package com.sctp.accessingdatamysql.repository;

import com.sctp.accessingdatamysql.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Integer> {
    // Note: Using CRUDRepository, these default methods are implemented
    // save()
    // findOne()
    // findById()
    // findAll()
    // count()
    // delete()
    // deleteById()
}
