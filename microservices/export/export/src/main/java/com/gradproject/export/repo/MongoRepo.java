package com.gradproject.export.repo;

import com.gradproject.export.entity.DeviceInfo;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MongoRepo extends MongoRepository<DeviceInfo, String> {
    List<DeviceInfo> findDeviceInfoByDeviceId(String deviceId);
    List<DeviceInfo> findDeviceInfoByDeviceIdIn(List<String> deviceId);
}
