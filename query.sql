CREATE TABLE CUSTOMER (
    CustomerID VARCHAR(20) PRIMARY KEY,
    CustomerName VARCHAR(50) NOT NULL,
    Email VARCHAR(255) NOT NULL UNIQUE,
    MobileNumber VARCHAR(15) NOT NULL, 
    Address VARCHAR(255) NOT NULL,
    PasswordHash VARCHAR(255) NOT NULL,
    AccountStatus  VARCHAR(20) NOT NULL CHECK (AccountStatus IN ('active', 'inactive')),
    RegistrationDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE Admin (
    AdminID VARCHAR(20) PRIMARY KEY,
    PasswordHash VARCHAR(255) NOT NULL,
    AccountStatus  VARCHAR(20) NOT NULL CHECK (AccountStatus IN ('active', 'inactive')),
    RegistrationDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);



CREATE TABLE Room (
    roomId INT GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1) PRIMARY KEY,
    roomNumber VARCHAR(10) NOT NULL UNIQUE,
    floor INT,
    roomType VARCHAR(20) CHECK (roomType IN ('Single', 'Double', 'Suite')),
    price DECIMAL(10,2),
    status VARCHAR(20) DEFAULT 'Available' CHECK (status IN ('Available', 'Booked', 'Maintenance')),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);



CREATE TABLE Reservation (
    reservationId INT GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1) PRIMARY KEY,
    customerId VARCHAR(20),
    checkInDate DATE NOT NULL,
    checkOutDate DATE NOT NULL,
    roomId INT,
    roomType VARCHAR(10) CHECK (roomType IN ('single', 'double', 'suite')),
    name VARCHAR(50) NOT NULL,
    contactNumber VARCHAR(15) NOT NULL,
    status VARCHAR(20) DEFAULT 'Confirmed' CHECK (status IN ('confirmed', 'cancelled', 'completed')),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (customerId) REFERENCES Customer(CustomerID),
    FOREIGN KEY (roomId) REFERENCES Room(RoomID)
);



CREATE TABLE Invoices (
    invoiceId INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    customerId VARCHAR(20),
    reservationId INT,
    invoicePath VARCHAR(255),
     FOREIGN KEY (customerId) REFERENCES Customer(customerId),
      FOREIGN KEY (reservationId) REFERENCES Reservation(reservationId)
);
      


----------------------------------------------------------------------------------


---- Create Payments Table
--CREATE TABLE Payments (
--    PaymentID INT PRIMARY KEY,
--    BookingID INT,
--    PaymentDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
--    Amount DECIMAL(10, 2) NOT NULL,
--    CardNumber VARCHAR(20) NOT NULL,
--    CardHolderName VARCHAR(100) NOT NULL,
--    ExpiryDate VARCHAR(5) NOT NULL,
--    CVV VARCHAR(4) NOT NULL,
--    FOREIGN KEY (BookingID) REFERENCES Bookings(BookingID)
--);

---- Create Complaints Table
--CREATE TABLE Complaints (
--    ComplaintID INT PRIMARY KEY ,
--    CustomerID varchar(20),
--    ComplaintType VARCHAR(100) NOT NULL,
--    ComplaintDescription varchar(250) NOT NULL,
--    DateFiled TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
--    FOREIGN KEY (CustomerID) REFERENCES CUSTOMER(CustomerID)
--);
--
--
--
--INSERT INTO CUSTOMER (CustomerID, CustomerName, Email, MobileNumber, Address, PasswordHash,AccountStatus)
--VALUES ('customer123', 'John Doe', 'john.doe@example.com', '+1234567890', '123 Main St, Anytown, USA', 'hashed_password_here','active');
--






-- customer123 John Doe     john.doe@example.com +1234567890  123 Main St, Anytown, USA hashed_password_here active        2024-09-06 17:07:39.951

select tableName from sys.systables;

select * from Reservation;
select * from CUSTOMER;
select * from ROOM;
select * from  INVOICES;
select * from  admin;


--INSERT INTO Admin (AdminID, PasswordHash, AccountStatus)
--VALUES ('admin123', '1000:5b42403230393634343264:590f1ebe12e6244e895afd8176353c0be3aeb96d2f3502ce46879fe9079438515626d4260ed4984b8124169893b5212f7754a909def508e320e5e675400c540a', 'active');
