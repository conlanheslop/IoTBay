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
    billId VARCHAR(8) PRIMARY KEY,                   
    orderId VARCHAR(8) NOT NULL,                       
    amount DECIMAL(10,2) NOT NULL CHECK (amount > 0),  
    billDate DATETIME NOT NULL,                        

    -- Foreign key constraint
    FOREIGN KEY (orderId) REFERENCES Orders(orderId)
);

INSERT INTO Orders (orderId) VALUES ('ORD00001'), ('ORD00002');

INSERT INTO Bill (billId, orderId, amount, billDate) 
VALUES 
('BILL0001', 'ORD00001', 150.00, '2025-04-27 10:30:00'),
('BILL0002', 'ORD00002', 200.50, '2025-04-27 11:00:00');
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

-- Insert sample orders into the Orders table
INSERT INTO Orders (orderId, userId, orderDate, totalAmount, status, isAnonymousOrder, anonymousEmail)
VALUES
('ORD00001', 'CUST00001', '2025-04-27 09:00:00', 200.00, 'Pending', FALSE, NULL),
('ORD00002', 'CUST00002', '2025-04-27 09:30:00', 300.50, 'Processing', TRUE, 'anon@example.com');
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