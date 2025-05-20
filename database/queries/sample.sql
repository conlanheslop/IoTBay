INSERT INTO USER (id, name, password, email, phone, address, lastLoginDate, createdDate, lastModifiedDate)
VALUES 
('U0000001', 'Alice Smith', 'pass1234', 'alice@example.com', '1234567890', '101 Apple St, Cupertino', DATETIME('now'), DATETIME('now'), DATETIME('now')),
('U0000002', 'Bob Johnson', 'securePass', 'bob@example.com', '0987654321', '202 Orange Ave, Mountain View', DATETIME('now'), DATETIME('now'), DATETIME('now')),
('U0000003', 'Carol White', 'carolPass99', 'carol@example.com', '1122334455', '303 Grape Rd, Palo Alto', DATETIME('now'), DATETIME('now'), DATETIME('now'));

INSERT INTO Customer (userId, isRegistered)
VALUES 
('U0000001', 1),
('U0000002', 0),
('U0000003', 1);

INSERT INTO Cart (cartId, userId, dateCreated, lastUpdated)
VALUES 
('CART001', 'U0000001', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('CART002', 'U0000002', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('CART003', 'U0000003', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO CartItem (cartId, itemId, quantity, unitPrice)
VALUES 
('CART001', 'ITEM001', 1, 1599.99),
('CART001', 'ITEM002', 2, 29.99),
('CART002', 'ITEM003', 1, 89.99),
('CART003', 'ITEM002', 3, 29.99);

INSERT INTO Orders (orderId, userId, orderDate, totalAmount, status, isAnonymousOrder, anonymousEmail)
VALUES 
('O0000001', 'U0000001', '2024-12-01 14:30:00', 199.99, 'shipped', FALSE, NULL),
('O0000002', 'U0000002', '2025-01-10 09:15:00', 59.50, 'pending', TRUE, 'anonuser@example.com'),
('O0000003', 'U0000001', '2025-02-22 18:45:00', 350.75, 'completed', FALSE, NULL),
('O0000004', 'U0000001', '2025-03-05 13:00:00', 120.00, 'cancelled', FALSE, NULL);

-- Sample Bill data
INSERT INTO Bill (billId, orderId, amount, billDate, paymentId, isPaid) VALUES
('BILL001', 'O0000001', 120.50, '2025-05-01 14:30:00', 'PAY001', TRUE),
('BILL002', 'O0000002', 89.99, '2025-05-03 10:15:00', NULL, FALSE),
('BILL003', 'O0000004', 45.00, '2025-04-25 16:45:00', 'PAY002', FALSE);

INSERT INTO Payment (payment_id, user_id, added_date, payment_method, is_verified) VALUES
('PAY001', 'USER123', '2025-05-11 10:00:00', 'Cardholder: Alice Smith, Card Number: 4111111111111111, Expiry Date: 12/25, CVV: 123', true),
('PAY002', 'USER456', '2025-05-12 14:30:00', 'Cardholder: Bob Johnson, Card Number: 5500000000000004, Expiry Date: 11/24, CVV: 456', true),
('PAY003', 'USER789', '2025-05-13 09:15:00', 'Cardholder: Charlie Davis, Card Number: 340000000000009, Expiry Date: 06/26, CVV: 789', false);

INSERT INTO OrderItem (orderId, itemId, quantity, unitPrice) VALUES
('O0000001', 'ITM00001', 2, 49.99),
('O0000001', 'ITM00002', 1, 19.99),
('O0000001', 'ITM00003', 3, 15.50),
('O0000001', 'ITM00004', 1, 89.00),
('O0000002', 'ITM00005', 5, 9.99),
('O0000002', 'ITM00001', 1, 49.99),
('O0000003', 'ITM00003', 2, 15.50),
('O0000003', 'ITM00006', 4, 25.00),
('O0000003', 'ITM00007', 2, 39.95),
('O0000004', 'ITM00008', 1, 120.00);
