CREATE table if not exists dataTest (
    deviceId varchar(50) not null,
    time timestamp not null,
    deviceInfo varchar(50),
    PRIMARY KEY (deviceId, time)
);

insert into dataTest (deviceId, time, deviceInfo) VALUES ('test12', current_timestamp, '1');

select * from dataTest;
