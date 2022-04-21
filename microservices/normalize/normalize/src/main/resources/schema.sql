CREATE table if not exists deviceInfo
(
    id          uuid        not null,
    deviceId    varchar(50) not null,
    time        varchar(50) not null,
    humidity    varchar(5)  not null,
    temperature varchar(5)  not null,
    PRIMARY KEY (id)
);

DELETE from deviceInfo where True;


