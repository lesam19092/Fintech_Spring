CREATE TABLE Locations
(

    id   INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    slug VARCHAR(50) NOT NULL,
    name VARCHAR(50) NOT NULL,
)

CREATE TABLE Events
(
    id          INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    title       VARCHAR(50)              NOT NULL,
    date        timestamp with time zone not null,
    price       FLOAT                    NOT NULL,
    location_id INT,
    FOREIGN KEY (location_id) REFERENCES Locations (id)
)

