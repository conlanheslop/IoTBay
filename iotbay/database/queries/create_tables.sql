-- /** 
-- * Where we will create all the tables for our data objects in our data dictionary
-- * Make sure all the details of our data dictionary are reflected in this
-- */

-- /* Not Done yet */
-- CREATE TABLE USER (
--     id VARCHAR(8) PRIMARY KEY, 
--     name VARCHAR(50) NOT NULL,
--     password VARCHAR(128) NULL,
--     email VARCHAR(100) UNIQUE,
--     phone VARCHAR(15),
--     address VARCHAR(200),
-- );

-- --------------------------------------------------------------------------------------------------------------------------------------------------------------
-- -- Drop the Bill table if it already exists
-- DROP TABLE IF EXISTS Bill;

-- -- Create the Bill table with full constraints
-- CREATE TABLE Bill (
--     billId VARCHAR(8) PRIMARY KEY,                   
--     orderId VARCHAR(8) NOT NULL,                       
--     amount DECIMAL(10,2) NOT NULL CHECK (amount > 0),  
--     billDate DATETIME NOT NULL,                        

--     -- Foreign key constraint
--     FOREIGN KEY (orderId) REFERENCES Orders(orderId)
-- );

-- INSERT INTO Orders (orderId) VALUES ('ORD00001'), ('ORD00002');

-- INSERT INTO Bill (billId, orderId, amount, billDate) 
-- VALUES 
-- ('BILL0001', 'ORD00001', 150.00, '2025-04-27 10:30:00'),
-- ('BILL0002', 'ORD00002', 200.50, '2025-04-27 11:00:00');
-- --------------------------------------------------------------------------------------------------------------------------------------------------------------

-- -- Drop the Order table if it already exists
-- DROP TABLE IF EXISTS Orders;

-- -- Create the Orders table with full constraints
-- CREATE TABLE Orders (
--     orderId VARCHAR(8) PRIMARY KEY,           -- Primary Key for orderId
--     userId VARCHAR(8) NOT NULL,               -- Foreign Key to link to Customer (or User)
--     orderDate DATETIME NOT NULL,              -- Date and time when the order was placed
--     totalAmount DECIMAL(10, 2) CHECK (totalAmount >= 0), -- The total order amount (>= 0)
--     status VARCHAR(20) NOT NULL,              -- Order status (Pending, Processing, etc.)
--     isAnonymousOrder BOOLEAN DEFAULT FALSE,   -- Flag for anonymous orders (default is false)
--     anonymousEmail VARCHAR(100),              -- Email for anonymous orders (if applicable)

--     -- Foreign key to link to the Customer or User table
--     FOREIGN KEY (userId) REFERENCES Customer(userId)
-- );

-- -- Insert sample orders into the Orders table
-- INSERT INTO Orders (orderId, userId, orderDate, totalAmount, status, isAnonymousOrder, anonymousEmail)
-- VALUES
-- ('ORD00001', 'CUST00001', '2025-04-27 09:00:00', 200.00, 'Pending', FALSE, NULL),
-- ('ORD00002', 'CUST00002', '2025-04-27 09:30:00', 300.50, 'Processing', TRUE, 'anon@example.com');
-- --------------------------------------------------------------------------------------------------------------------------------------------------------------

-- Drop the Items table if it already exists
DROP TABLE IF EXISTS Items;

-- Create the Items table with full constraints
CREATE TABLE Items (
    itemId VARCHAR(8) PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    quantity INT NOT NULL CHECK (quantity >= 0),
    description VARCHAR(255),
    price DECIMAL(10, 2) NOT NULL CHECK (price > 0),
    type VARCHAR(50) NOT NULL,
    manufacturer VARCHAR(50),
    dateAdded TIMESTAMP NOT NULL,
    lastRestocked TIMESTAMP,
    lastModifiedDate TIMESTAMP NOT NULL
);

-- Insert sample items, at least 20 as required (40 items (30 instock, 10 not instock)
INSERT INTO Items (itemId, name, quantity, description, price, type, manufacturer, dateAdded, lastRestocked, lastModifiedDate)
VALUES 
    -- Energy Management Type
    ('ITM00001', 'Smart Power Strip', 40, 'Multi-outlet smart power strip with individual outlet control', 39.99, 'Energy', 'PowerSmart', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('ITM00002', 'Energy Monitor', 0, 'Whole-home energy monitoring system with real-time usage tracking', 89.99, 'Energy', 'PowerSmart', CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP),
    ('ITM00003', 'Solar Power Controller', 20, 'Smart controller for residential solar panel systems with app monitoring', 129.99, 'Energy', 'PowerSmart', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('ITM00004', 'Smart Thermostat for HVAC', 15, 'Energy-efficient thermostat that optimizes heating and cooling cycles', 99.99, 'Energy', 'EcoSmart', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('ITM00005', 'Wireless Energy Monitor Display', 25, 'Portable display showing real-time energy consumption data from all connected devices', 59.99, 'Energy', 'PowerSmart', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('ITM00006', 'Smart Battery Monitor', 35, 'Track and optimize battery usage for home energy storage systems', 45.99, 'Energy', 'PowerSmart', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('ITM00007', 'Smart Window Film Controller', 15, 'Adjustable window tinting for optimal energy efficiency', 119.99, 'Energy', 'EcoSmart', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('ITM00008', 'Home Wind Turbine Monitor', 0, 'Performance tracking system for residential wind power generation', 89.99, 'Energy', 'PowerSmart', CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP),
    
    -- Health & Wellness Type
    ('ITM00009', 'Smart Scale', 30, 'Wi-Fi connected scale that tracks weight, BMI, and body composition', 49.99, 'Health', 'HealthTech', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('ITM00010', 'Air Quality Monitor', 0, 'Monitors indoor air quality including CO2, VOCs, and particulate matter', 79.99, 'Health', 'HealthTech', CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP),
    ('ITM00011', 'Smart Water Bottle', 35, 'Tracks water intake and reminds you to stay hydrated', 29.99, 'Health', 'HealthTech', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('ITM00012', 'Sleep Monitor', 25, 'Tracks sleep patterns and provides insights for better rest quality', 69.99, 'Health', 'HealthTech', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('ITM00013', 'UV Sensor Wristband', 30, 'Wearable device that alerts when UV exposure reaches harmful levels', 34.99, 'Health', 'HealthTech', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('ITM00014', 'Blood Pressure Monitor', 50, 'Wi-Fi connected blood pressure monitor with historical tracking', 59.99, 'Health', 'HealthTech', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('ITM00015', 'Smart Toothbrush', 40, 'Bluetooth toothbrush with brushing habits analysis and feedback', 49.99, 'Health', 'DentalTech', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('ITM00016', 'Medication Reminder Device', 0, 'Smart pill dispenser with scheduling and tracking capabilities', 69.99, 'Health', 'HealthTech', CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP),
    
    -- Home Automation Type
    ('ITM00017', 'Smart Thermostat', 25, 'Wi-Fi enabled smart thermostat with mobile app control', 89.99, 'Home Automation', 'SmartHome', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('ITM00018', 'Smart Light Bulb', 0, 'RGB smart light bulb with voice control compatibility', 15.99, 'Home Automation', 'SmartHome', CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP),
    ('ITM00019', 'Smart Plug', 80, 'Wi-Fi connected smart plug with energy monitoring', 24.99, 'Home Automation', 'SmartHome', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('ITM00020', 'Smart Lock', 15, 'Keyless entry door lock with smartphone control', 129.99, 'Home Automation', 'SecureTech', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('ITM00021', 'Smart Doorbell', 20, 'Video doorbell with motion detection and two-way audio', 99.99, 'Home Automation', 'SecureTech', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('ITM00022', 'Smart Curtain Controller', 35, 'Automate your curtains with scheduling and voice control compatibility', 79.99, 'Home Automation', 'SmartHome', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('ITM00023', 'Smart Irrigation Controller', 20, 'Wi-Fi enabled watering system with weather-adaptive scheduling', 129.99, 'Home Automation', 'GardenTech', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('ITM00024', 'Smart Mirror', 0, 'Interactive mirror with weather, calendar, and fitness tracking display', 299.99, 'Home Automation', 'SmartHome', CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP),
    
    -- Security Type
    ('ITM00025', 'Security Camera', 30, 'Indoor security camera with night vision and motion alerts', 59.99, 'Security', 'SecureTech', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('ITM00026', 'Door/Window Sensor', 0, 'Magnetic contact sensor for doors and windows', 12.50, 'Security', 'SecureTech', CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP),
    ('ITM00027', 'Glass Break Sensor', 25, 'Acoustic sensor that detects the sound of breaking glass', 34.99, 'Security', 'SecureTech', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('ITM00028', 'Water Leak Detector', 40, 'Water detection sensor to prevent damage from leaks and floods', 29.99, 'Security', 'HomeSafe', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('ITM00029', 'Smoke Detector', 45, 'Smart smoke detector with mobile alerts and battery monitoring', 39.99, 'Security', 'HomeSafe', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('ITM00030', 'Outdoor Security Camera', 40, 'Weather-resistant HD camera with night vision and motion tracking', 89.99, 'Security', 'SecureTech', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('ITM00031', 'Smart Garage Door Controller', 30, 'Monitor and control your garage door remotely via smartphone', 79.99, 'Security', 'SecureTech', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('ITM00032', 'Doorbell with Facial Recognition', 0, 'Advanced video doorbell with AI-powered visitor identification', 179.99, 'Security', 'SmartEye', CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP),

    -- Sensors Type
    ('ITM00033', 'Temperature Sensor', 50, 'Precision temperature sensor with digital output for IoT projects', 29.99, 'Sensors', 'TechSense', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('ITM00034', 'Humidity Sensor', 0, 'Accurate humidity measurement sensor for environmental monitoring', 19.99, 'Sensors', 'TechSense', CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP),
    ('ITM00035', 'Pressure Sensor', 35, 'Barometric pressure sensor for weather stations and altitude measurement', 24.99, 'Sensors', 'SenseTech', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('ITM00036', 'Motion Sensor', 60, 'PIR motion detection sensor for security and automation', 15.99, 'Sensors', 'SecureTech', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('ITM00037', 'Light Sensor', 70, 'Ambient light sensor for adaptive lighting systems', 9.99, 'Sensors', 'LightWise', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('ITM00038', 'Air Quality Sensor', 45, 'Monitors air pollutants and particulate matter for healthier indoor environments', 39.99, 'Sensors', 'SenseTech', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('ITM00039', 'Soil Moisture Sensor', 55, 'Smart plant monitoring sensor for optimal watering schedules', 14.99, 'Sensors', 'GardenTech', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('ITM00040', 'CO2 Sensor', 0, 'High-precision carbon dioxide detector for indoor air quality monitoring', 49.99, 'Sensors', 'TechSense', CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);