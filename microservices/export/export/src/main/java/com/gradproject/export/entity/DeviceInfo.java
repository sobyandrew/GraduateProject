package com.gradproject.export.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeviceInfo implements Serializable {


    public UUID id;

    public String deviceId;

    public String timestamp;

    public String humidity;

    public String temperature;

}
