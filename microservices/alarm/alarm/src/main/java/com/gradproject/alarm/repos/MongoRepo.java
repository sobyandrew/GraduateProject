package com.gradproject.alarm.repos;

import com.gradproject.alarm.entity.AlarmEntity;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface MongoRepo extends MongoRepository<AlarmEntity, String> {

}
