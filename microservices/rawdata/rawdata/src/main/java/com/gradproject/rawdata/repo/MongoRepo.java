package com.gradproject.rawdata.repo;

import com.gradproject.rawdata.entity.DeviceInfo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MongoRepo extends MongoRepository<DeviceInfo, String> {

}
