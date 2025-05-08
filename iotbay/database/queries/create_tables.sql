/** 
* Where we will create all the tables for our data objects in our data dictionary
* Make sure all the details of our data dictionary are reflected in this
*/


DROP TABLE IF EXISTS User;

CREATE TABLE USER (
    id VARCHAR(8) PRIMARY KEY, 
    name VARCHAR(50) NOT NULL,
    password VARCHAR(128) NULL,
    email VARCHAR(100) UNIQUE,
    phone VARCHAR(15),
    address VARCHAR(200),
    lastLoginDate TEXT,
    createdDate TEXT NOT NULL,
    lastModifiedDate TEXT
);


DROP TABLE IF EXISTS Bill;

CREATE TABLE Bill (
    billId VARCHAR(50) PRIMARY KEY,
    orderId VARCHAR(50),
    amount DECIMAL(10, 2),
    billDate DATETIME,
    paymentId VARCHAR(50),
    isPaid BOOLEAN,
    cartId VARCHAR(50),
    FOREIGN KEY (cartId) REFERENCES Cart(cart_id)
);


DROP TABLE IF EXISTS Orders;

CREATE TABLE Orders ( -- s at the end is only here because "order" is a keyword in SQL 
    orderId VARCHAR(8) PRIMARY KEY,      
    userId VARCHAR(8) NOT NULL,               
    orderDate DATETIME NOT NULL,              
    totalAmount DECIMAL(10, 2) CHECK (totalAmount >= 0), 
    status VARCHAR(20) NOT NULL,            
    isAnonymousOrder BOOLEAN DEFAULT FALSE,  
    anonymousEmail VARCHAR(100),             
    FOREIGN KEY (userId) REFERENCES Customer(userId)
);


DROP TABLE IF EXISTS Cart;

CREATE TABLE Cart (
    cart_id VARCHAR(50) PRIMARY KEY,
    user_id VARCHAR(50) NOT NULL,
    date_created TIMESTAMP NOT NULL,
    last_updated TIMESTAMP NOT NULL
);


DROP TABLE IF EXISTS CartItem;

CREATE TABLE CartItem (
    cart_id VARCHAR(50),
    item_id VARCHAR(50),
    quantity INT NOT NULL,
    unit_price DOUBLE NOT NULL,
    PRIMARY KEY (cart_id, item_id),
    FOREIGN KEY (cart_id) REFERENCES Cart(cart_id)
);


DROP TABLE IF EXISTS Payment;

CREATE TABLE Payment (
    payment_id VARCHAR(255) PRIMARY KEY,
    user_id VARCHAR(255) NOT NULL,
    added_date TIMESTAMP NOT NULL,
    payment_method VARCHAR(255) NOT NULL,
    is_verified BOOLEAN NOT NULL
);


DROP TABLE IF EXISTS Customer;

CREATE TABLE Customer (
    userId VARCHAR(8) PRIMARY KEY,
    isRegistered BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (userId) REFERENCES USER(id)
);


DROP TABLE IF EXISTS Staff;

CREATE TABLE Staff (
    userId VARCHAR(8) PRIMARY KEY,
    FOREIGN KEY (userId) REFERENCES USER(id)
);


DROP TABLE IF EXISTS Item;

CREATE TABLE Item (
    itemId VARCHAR(8) PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    quantity INT CHECK (quantity >= 0),
    description VARCHAR(500),
    price DECIMAL(10, 2) CHECK (price >= 0),
    category VARCHAR(50),
    manufacturer VARCHAR(100),
    imageURL VARCHAR(255),
    dateAdded DATETIME,
    lastRestocked DATETIME,
    lastModifiedDate DATETIME
);


DROP TABLE IF EXISTS OrderItem;

CREATE TABLE OrderItem (
    orderId VARCHAR(8),
    itemId VARCHAR(8),
    quantity INT CHECK (quantity > 0),
    unitPrice DECIMAL(10, 2) CHECK (unitPrice >= 0),
    PRIMARY KEY (orderId, itemId),
    FOREIGN KEY (orderId) REFERENCES Orders(orderId),
    FOREIGN KEY (itemId) REFERENCES Item(itemId)
);


DROP TABLE IF EXISTS Delivery;

CREATE TABLE Delivery (
    deliveryId VARCHAR(8) PRIMARY KEY,
    orderId VARCHAR(8),
    deliveringDate DATETIME NOT NULL,
    status VARCHAR(15) NOT NULL,
    deliveringAddress VARCHAR(100) NOT NULL,
    nameOnDelivery VARCHAR(100) NOT NULL,
    trackingNumber VARCHAR(50) NOT NULL,
    FOREIGN KEY (orderId) REFERENCES Orders(orderId)
);


DROP TABLE IF EXISTS Cart;

CREATE TABLE Cart (
    cartId VARCHAR(50) PRIMARY KEY,
    userId VARCHAR(50) NOT NULL,
    dateCreated TIMESTAMP NOT NULL,
    lastUpdated TIMESTAMP NOT NULL,
    FOREIGN KEY (userId) REFERENCES USER(id)
);


DROP TABLE IF EXISTS CartItem;

CREATE TABLE CartItem (
    cartId VARCHAR(50),
    itemId VARCHAR(50),
    quantity INT CHECK (quantity > 0),
    unitPrice DECIMAL(10, 2) CHECK (unitPrice >= 0),
    PRIMARY KEY (cartId, itemId),
    FOREIGN KEY (cartId) REFERENCES Cart(cartId),
    FOREIGN KEY (itemId) REFERENCES Item(itemId)
);
