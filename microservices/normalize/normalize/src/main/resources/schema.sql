CREATE table if not exists dataTest
(
    deviceId   varchar(50) not null,
    time       timestamp   not null,
    deviceInfo varchar(50),
    PRIMARY KEY (deviceId, time)
);

CREATE table if not exists deviceInfo
(
    id          uuid        not null,
    deviceId    varchar(50) not null,
    time        varchar(50) not null,
    humidity    varchar(5)  not null,
    temperature varchar(5)  not null,
    PRIMARY KEY (id)
);

drop table deviceInfo;
insert into dataTest (deviceId, time, deviceInfo)
VALUES ('test12', current_timestamp, '1');

select *
from dataTest;

select * from deviceInfo where deviceId like '1';


