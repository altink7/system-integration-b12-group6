DROP DATABASE IF EXISTS citibike_db;
CREATE DATABASE citibike_db;

\c citibike_db;

CREATE USER citibike_user WITH ENCRYPTED PASSWORD 'citibike';
GRANT ALL PRIVILEGES ON DATABASE citibike_db TO citibike_user;

CREATE TABLE UserType
(
    userTypeId  SERIAL PRIMARY KEY,
    userType    VARCHAR(50) NOT NULL,
    description VARCHAR(250)
);

CREATE TABLE Station
(
    id      VARCHAR(50) PRIMARY KEY,
    name    VARCHAR(50) NOT NULL,
    oldName VARCHAR(50)
);

CREATE TABLE Date
(
    id       SERIAL PRIMARY KEY,
    hour     INT       NOT NULL,
    day      INT       NOT NULL,
    month    INT       NOT NULL,
    year     INT       NOT NULL,
    minute   INT       NOT NULL,
    dateTime TIMESTAMP NOT NULL
);

CREATE TABLE FACT_Trip
(
    tripId       VARCHAR(50) PRIMARY KEY,
    startStation VARCHAR(50) NOT NULL,
    stopStation  VARCHAR(50) NOT NULL,
    stopTimeId   INT         NOT NULL,
    userTypeId   INT         NOT NULL,
    startTimeId  INT         NOT NULL,
    duration     INTERVAL,

    CONSTRAINT FK_FACT_Trip_startStation FOREIGN KEY (startStation) REFERENCES Station (id),
    CONSTRAINT FK_FACT_Trip_stopStation FOREIGN KEY (stopStation) REFERENCES Station (id),
    CONSTRAINT FK_FACT_Trip_userTypeId FOREIGN KEY (userTypeId) REFERENCES UserType (userTypeId),
    CONSTRAINT FK_FACT_Trip_startTimeId FOREIGN KEY (startTimeId) REFERENCES Date (id),
    CONSTRAINT FK_FACT_Trip_stopTimeId FOREIGN KEY (stopTimeId) REFERENCES Date (id)
);
