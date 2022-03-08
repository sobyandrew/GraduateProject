package com.gradproject.rawdata.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
@AllArgsConstructor
public class DeviceInfo {

    @Id
    public String id;

    public String deviceId;

    public String timestamp;

    public String deviceData;

}
