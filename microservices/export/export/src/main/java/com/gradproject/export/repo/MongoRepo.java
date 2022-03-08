package com.gradproject.export.repo;

import com.gradproject.export.entity.DeviceInfo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MongoRepo extends MongoRepository<DeviceInfo, String> {

}
