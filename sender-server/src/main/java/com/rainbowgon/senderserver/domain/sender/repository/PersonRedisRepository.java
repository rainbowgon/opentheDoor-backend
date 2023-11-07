package com.rainbowgon.senderserver.domain.sender.repository;

import com.rainbowgon.senderserver.domain.sender.entity.Person;
import org.springframework.data.repository.CrudRepository;

public interface PersonRedisRepository extends CrudRepository<Person, Long> {

}
