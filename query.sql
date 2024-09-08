CREATE TABLE CUSTOMER (
    CustomerID VARCHAR(20) PRIMARY KEY,
    CustomerName VARCHAR(50) NOT NULL,
    Email VARCHAR(255) NOT NULL UNIQUE,
    MobileNumber VARCHAR(15) NOT NULL, 
    Address VARCHAR(255) NOT NULL,
    PasswordHash VARCHAR(255) NOT NULL,
    AccountStatus VARCHAR(20) NOT NULL,
    RegistrationDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);


INSERT INTO CUSTOMER 
(CustomerID, CustomerName, Email, MobileNumber, Address, PasswordHash, AccountStatus) 
VALUES 
('cust001', 'John Doe', 'john.doe1@example.com', '+1234567890', '123 Main St, City', 'hashed_password', 'Active');


-- Create Room Table
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

INSERT INTO Room 
(roomNumber, floor, roomType, price, status) 
VALUES 
('1121147', 1, 'Single', 1500.00, 'Available'),
('1124118', 1, 'Double', 2000.00, 'Available'),
('2224219', 2, 'Suite', 3500.00, 'Available');



-- Create Bookings Table
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

INSERT INTO Reservation 
(customerId, checkInDate, checkOutDate, roomId, roomType, name, contactNumber, status) 
VALUES 
('customer123', '2024-09-15', '2024-09-20', 1, 'single', 'John Doe', '+1234567890', 'confirmed');



----------------------------------------------------------------------------------


-- Create Payments Table
CREATE TABLE Payments (
    PaymentID INT PRIMARY KEY,
    BookingID INT,
    PaymentDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    Amount DECIMAL(10, 2) NOT NULL,
    CardNumber VARCHAR(20) NOT NULL,
    CardHolderName VARCHAR(100) NOT NULL,
    ExpiryDate VARCHAR(5) NOT NULL,
    CVV VARCHAR(4) NOT NULL,
    FOREIGN KEY (BookingID) REFERENCES Bookings(BookingID)
);

-- Create Complaints Table
CREATE TABLE Complaints (
    ComplaintID INT PRIMARY KEY ,
    CustomerID varchar(20),
    ComplaintType VARCHAR(100) NOT NULL,
    ComplaintDescription varchar(250) NOT NULL,
    DateFiled TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (CustomerID) REFERENCES CUSTOMER(CustomerID)
);



INSERT INTO CUSTOMER (CustomerID, CustomerName, Email, MobileNumber, Address, PasswordHash,AccountStatus)
VALUES ('customer123', 'John Doe', 'john.doe@example.com', '+1234567890', '123 Main St, Anytown, USA', 'hashed_password_here','active');






-- customer123 John Doe     john.doe@example.com +1234567890  123 Main St, Anytown, USA hashed_password_here active        2024-09-06 17:07:39.951

select tableName from sys.systables;

select * from Reservation;
select * from bills;
select * from COMPLAINTS;
select * from CUSTOMER;
select * from PAYMENTS;
select * from ROOM;

drop table room;


CREATE TABLE Invoices (
    invoiceId INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    customerId VARCHAR(255),
    reservationId VARCHAR(255),
    invoicePath VARCHAR(255),
     FOREIGN KEY (customerId) REFERENCES Customer(CustomerID),
      FOREIGN KEY (reservationId) REFERENCES Reservation(reservationId),
    
);


