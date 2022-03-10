package com.gradproject.alarm.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;


import java.io.Serializable;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlarmEntity implements Serializable {

    @Id
    private String id;

    private String deviceId;

    private String timestamp;

    private String severity;

    private String humidity;

    private String temperature;
}
