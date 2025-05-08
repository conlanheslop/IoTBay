/** 
* Where we will create all the tables for our data objects in our data dictionary
* Make sure all the details of our data dictionary are reflected in this
*/

/* Not Done yet */
CREATE TABLE USER (
    id VARCHAR(8) PRIMARY KEY, 
    name VARCHAR(50) NOT NULL,
    password VARCHAR(128) NULL,
    email VARCHAR(100) UNIQUE,
    phone VARCHAR(15),
    address VARCHAR(200)
);

--------------------------------------------------------------------------------------------------------------------------------------------------------------
-- Drop the Bill table if it already exists
DROP TABLE IF EXISTS Bill;

-- Create the Bill table with full constraints
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

--------------------------------------------------------------------------------------------------------------------------------------------------------------

-- Drop the Order table if it already exists
DROP TABLE IF EXISTS Orders;

-- Create the Orders table with full constraints
CREATE TABLE Orders (
    orderId VARCHAR(8) PRIMARY KEY,      
    userId VARCHAR(8) NOT NULL,               
    orderDate DATETIME NOT NULL,              
    totalAmount DECIMAL(10, 2) CHECK (totalAmount >= 0), 
    status VARCHAR(20) NOT NULL,            
    isAnonymousOrder BOOLEAN DEFAULT FALSE,  
    anonymousEmail VARCHAR(100),             

    -- Foreign key to link to the Customer or User table
    FOREIGN KEY (userId) REFERENCES Customer(userId)
);

--------------------------------------------------------------------------------------------------------------------------------------------------------------

-- CART TABLE
CREATE TABLE Cart (
    cart_id VARCHAR(50) PRIMARY KEY,
    user_id VARCHAR(50) NOT NULL,
    date_created TIMESTAMP NOT NULL,
    last_updated TIMESTAMP NOT NULL
);

--------------------------------------------------------------------------------------------------------------------------------------------------------------

-- CART_ITEM TABLE
CREATE TABLE CartItem (
    cart_id VARCHAR(50),
    item_id VARCHAR(50),
    quantity INT NOT NULL,
    unit_price DOUBLE NOT NULL,
    PRIMARY KEY (cart_id, item_id),
    FOREIGN KEY (cart_id) REFERENCES Cart(cart_id)
);

--------------------------------------------------------------------------------------------------------------------------------------------------------------

-- Payment Table
CREATE TABLE payments (
    payment_id VARCHAR(255) PRIMARY KEY,
    user_id VARCHAR(255) NOT NULL,
    added_date TIMESTAMP NOT NULL,
    payment_method VARCHAR(255) NOT NULL,
    is_verified BOOLEAN NOT NULL
);

--------------------------------------------------------------------------------------------------------------------------------------------------------------