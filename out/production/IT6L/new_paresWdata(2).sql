-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Mar 10, 2025 at 07:42 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `new_pares`
--

-- --------------------------------------------------------

--
-- Table structure for table `orderitems`
--

CREATE TABLE `orderitems` (
  `OrderItemID` varchar(15) NOT NULL,
  `OrderID` varchar(15) NOT NULL,
  `item_name` varchar(60) NOT NULL,
  `item_quantity` int(11) NOT NULL,
  `item_price` decimal(10,2) NOT NULL,
  `total_price` decimal(10,2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `orderitems`
--

INSERT INTO `orderitems` (`OrderItemID`, `OrderID`, `item_name`, `item_quantity`, `item_price`, `total_price`) VALUES
('OI001', 'ORD001', 'PARES', 2, 80.00, 160.00),
('OI002', 'ORD001', 'RICE', 2, 15.00, 30.00),
('OI003', 'ORD002', 'Beef PARES', 1, 80.00, 80.00),
('OI004', 'ORD002', 'HOTDOG', 3, 15.00, 45.00),
('OI005', 'ORD003', 'OVERLOAD PARES', 4, 100.00, 400.00),
('OI006', 'ORD003', 'KASALO', 2, 40.00, 80.00),
('OI007', 'ORD004', 'KAWALI', 1, 90.00, 90.00),
('OI008', 'ORD004', 'ISAW', 5, 10.00, 50.00),
('OI009', 'ORD005', 'BULAK-BULAK', 3, 90.00, 270.00),
('OI010', 'ORD005', 'RICE', 3, 15.00, 45.00),
('OI011', 'ORD006', 'LIEMPO', 1, 95.00, 95.00),
('OI012', 'ORD006', 'ATAY', 2, 15.00, 30.00),
('OI013', 'ORD007', 'PAA', 2, 85.00, 170.00),
('OI014', 'ORD007', 'POUL', 3, 15.00, 45.00),
('OI015', 'ORD008', 'PETCHO', 1, 85.00, 85.00),
('OI016', 'ORD008', 'BAT', 4, 15.00, 60.00),
('OI017', 'ORD009', 'CHOT', 2, 85.00, 170.00),
('OI018', 'ORD009', 'ISAW', 5, 10.00, 50.00),
('OI019', 'ORD010', 'POUL', 3, 15.00, 45.00),
('OI020', 'ORD010', 'HOTDOG', 2, 15.00, 30.00),
('OI021', 'ORD011', 'BAT', 5, 15.00, 75.00),
('OI022', 'ORD011', 'ISOL', 2, 25.00, 50.00),
('OI023', 'ORD012', 'ATAY', 4, 15.00, 60.00),
('OI024', 'ORD012', 'LIOG', 3, 20.00, 60.00),
('OI025', 'ORD013', 'ISAW', 6, 10.00, 60.00),
('OI026', 'ORD013', 'CS', 2, 20.00, 40.00),
('OI027', 'ORD014', 'ISOL', 2, 25.00, 50.00),
('OI028', 'ORD014', 'CM', 3, 20.00, 60.00),
('OI029', 'ORD015', 'LIOG', 1, 20.00, 20.00),
('OI030', 'ORD015', 'PICHO', 4, 20.00, 80.00),
('OI031', 'ORD016', 'CS', 3, 20.00, 60.00),
('OI032', 'ORD016', 'HOTDOG TJ', 2, 20.00, 40.00),
('OI033', 'ORD017', 'CM', 2, 20.00, 40.00),
('OI034', 'ORD017', 'PANGA BANGUS', 1, 150.00, 150.00),
('OI035', 'ORD018', 'PICHO', 3, 20.00, 60.00),
('OI036', 'ORD018', 'PANGAS', 1, 150.00, 150.00),
('OI037', 'ORD019', 'HOTDOG', 5, 15.00, 75.00),
('OI038', 'ORD019', 'PUSIT', 2, 150.00, 300.00),
('OI039', 'ORD020', 'HOTDOG TJ', 4, 20.00, 80.00),
('OI040', 'ORD020', 'BELLY', 1, 120.00, 120.00),
('OI041', 'ORD021', 'PANGA BANGUS', 3, 150.00, 450.00),
('OI042', 'ORD021', 'RICE', 3, 15.00, 45.00),
('OI043', 'ORD022', 'PANGAS', 2, 150.00, 300.00),
('OI044', 'ORD022', 'KASALO', 2, 40.00, 80.00),
('OI045', 'ORD023', 'PUSIT', 1, 150.00, 150.00),
('OI046', 'ORD023', 'PARES Extra', 2, 85.00, 170.00),
('OI047', 'ORD024', 'BELLY', 1, 120.00, 120.00),
('OI048', 'ORD024', 'Beef PARES Deluxe', 2, 90.00, 180.00),
('OI049', 'ORD025', 'RICE', 5, 15.00, 75.00),
('OI050', 'ORD025', 'OVERLOAD PARES Plus', 1, 110.00, 110.00),
('OI051', 'ORD026', 'KASALO', 3, 40.00, 120.00),
('OI052', 'ORD026', 'KAWALI Special', 2, 95.00, 190.00),
('OI053', 'ORD027', 'PARES', 2, 80.00, 160.00),
('OI054', 'ORD027', 'BULAK-BULAK Spicy', 1, 95.00, 95.00),
('OI055', 'ORD028', 'Beef PARES', 3, 80.00, 240.00),
('OI056', 'ORD028', 'LIEMPO Grilled', 1, 100.00, 100.00),
('OI057', 'ORD029', 'OVERLOAD PARES', 2, 100.00, 200.00),
('OI058', 'ORD029', 'PAA Fried', 1, 90.00, 90.00),
('OI059', 'ORD030', 'KAWALI', 4, 90.00, 360.00),
('OI060', 'ORD030', 'PETCHO Spicy', 2, 90.00, 180.00),
('OI061', 'ORD031', 'BULAK-BULAK', 1, 90.00, 90.00),
('OI062', 'ORD031', 'CHOT Grilled', 1, 90.00, 90.00),
('OI063', 'ORD032', 'LIEMPO', 2, 95.00, 190.00),
('OI064', 'ORD032', 'POUL Roasted', 3, 20.00, 60.00),
('OI065', 'ORD033', 'PAA', 1, 85.00, 85.00),
('OI066', 'ORD033', 'BAT Spicy', 4, 20.00, 80.00),
('OI067', 'ORD034', 'PETCHO', 2, 85.00, 170.00),
('OI068', 'ORD034', 'ATAY Fried', 3, 20.00, 60.00),
('OI069', 'ORD035', 'CHOT', 1, 85.00, 85.00),
('OI070', 'ORD035', 'ISAW Grilled', 5, 15.00, 75.00),
('OI071', 'ORD036', 'POUL', 2, 15.00, 30.00),
('OI072', 'ORD036', 'ISOL Spicy', 2, 30.00, 60.00),
('OI073', 'ORD037', 'BAT', 3, 15.00, 45.00),
('OI074', 'ORD037', 'LIOG Roasted', 2, 25.00, 50.00),
('OI075', 'ORD038', 'ATAY', 4, 15.00, 60.00),
('OI076', 'ORD038', 'CS Extra', 3, 25.00, 75.00),
('OI077', 'ORD039', 'ISAW', 6, 10.00, 60.00),
('OI078', 'ORD039', 'CM Spicy', 2, 25.00, 50.00),
('OI079', 'ORD040', 'ISOL', 2, 25.00, 50.00),
('OI080', 'ORD040', 'PICHO Grilled', 3, 25.00, 75.00),
('OI081', 'ORD041', 'LIOG', 1, 20.00, 20.00),
('OI082', 'ORD041', 'HOTDOG Special', 4, 20.00, 80.00),
('OI083', 'ORD042', 'CS', 3, 20.00, 60.00),
('OI084', 'ORD042', 'HOTDOG TJ Plus', 2, 25.00, 50.00),
('OI085', 'ORD043', 'CM', 2, 20.00, 40.00),
('OI086', 'ORD043', 'PANGA BANGUS Fried', 1, 160.00, 160.00),
('OI087', 'ORD044', 'PICHO', 3, 20.00, 60.00),
('OI088', 'ORD044', 'PANGAS Grilled', 1, 160.00, 160.00),
('OI089', 'ORD045', 'HOTDOG', 5, 15.00, 75.00),
('OI090', 'ORD045', 'PUSIT Spicy', 2, 160.00, 320.00),
('OI091', 'ORD046', 'HOTDOG TJ', 4, 20.00, 80.00),
('OI092', 'ORD046', 'BELLY Roasted', 1, 130.00, 130.00),
('OI093', 'ORD047', 'PANGA BANGUS', 3, 150.00, 450.00),
('OI094', 'ORD047', 'RICE', 3, 15.00, 45.00),
('OI095', 'ORD048', 'PANGAS', 2, 150.00, 300.00),
('OI096', 'ORD048', 'KASALO', 2, 40.00, 80.00),
('OI097', 'ORD049', 'PUSIT', 1, 150.00, 150.00),
('OI098', 'ORD049', 'PARES Extra', 2, 85.00, 170.00),
('OI099', 'ORD050', 'BELLY', 1, 120.00, 120.00),
('OI100', 'ORD050', 'Beef PARES Deluxe', 2, 90.00, 180.00),
('OI101', 'ORD051', 'RICE', 5, 15.00, 75.00),
('OI102', 'ORD051', 'OVERLOAD PARES Plus', 1, 110.00, 110.00),
('OI103', 'ORD052', 'KASALO', 3, 40.00, 120.00),
('OI104', 'ORD052', 'KAWALI Special', 2, 95.00, 190.00),
('OI105', 'ORD053', 'PARES', 2, 80.00, 160.00),
('OI106', 'ORD053', 'BULAK-BULAK Spicy', 1, 95.00, 95.00),
('OI107', 'ORD054', 'Beef PARES', 3, 80.00, 240.00),
('OI108', 'ORD054', 'LIEMPO Grilled', 1, 100.00, 100.00),
('OI109', 'ORD055', 'OVERLOAD PARES', 2, 100.00, 200.00),
('OI110', 'ORD055', 'PAA Fried', 1, 90.00, 90.00),
('OI111', 'ORD056', 'KAWALI', 4, 90.00, 360.00),
('OI112', 'ORD056', 'PETCHO Spicy', 2, 90.00, 180.00),
('OI113', 'ORD057', 'BULAK-BULAK', 1, 90.00, 90.00),
('OI114', 'ORD057', 'CHOT Grilled', 1, 90.00, 90.00),
('OI115', 'ORD058', 'LIEMPO', 2, 95.00, 190.00),
('OI116', 'ORD058', 'POUL Roasted', 3, 20.00, 60.00),
('OI117', 'ORD059', 'PAA', 1, 85.00, 85.00),
('OI118', 'ORD059', 'BAT Spicy', 4, 20.00, 80.00),
('OI119', 'ORD060', 'PETCHO', 2, 85.00, 170.00),
('OI120', 'ORD060', 'ATAY Fried', 3, 20.00, 60.00),
('OI121', 'ORD061', 'CHOT', 1, 85.00, 85.00),
('OI122', 'ORD061', 'ISAW Grilled', 5, 15.00, 75.00),
('OI123', 'ORD062', 'POUL', 2, 15.00, 30.00),
('OI124', 'ORD062', 'ISOL Spicy', 2, 30.00, 60.00),
('OI125', 'ORD063', 'BAT', 3, 15.00, 45.00),
('OI126', 'ORD063', 'LIOG Roasted', 2, 25.00, 50.00),
('OI127', 'ORD064', 'ATAY', 4, 15.00, 60.00),
('OI128', 'ORD064', 'CS Extra', 3, 25.00, 75.00),
('OI129', 'ORD065', 'ISAW', 6, 10.00, 60.00),
('OI130', 'ORD065', 'CM Spicy', 2, 25.00, 50.00),
('OI131', 'ORD066', 'ISOL', 2, 25.00, 50.00),
('OI132', 'ORD066', 'PICHO Grilled', 3, 25.00, 75.00),
('OI133', 'ORD067', 'LIOG', 1, 20.00, 20.00),
('OI134', 'ORD067', 'HOTDOG Special', 4, 20.00, 80.00),
('OI135', 'ORD068', 'CS', 3, 20.00, 60.00),
('OI136', 'ORD068', 'HOTDOG TJ Plus', 2, 25.00, 50.00),
('OI137', 'ORD069', 'CM', 2, 20.00, 40.00),
('OI138', 'ORD069', 'PANGA BANGUS Fried', 1, 160.00, 160.00),
('OI139', 'ORD070', 'PICHO', 3, 20.00, 60.00),
('OI140', 'ORD070', 'PANGAS Grilled', 1, 160.00, 160.00),
('OI141', 'ORD071', 'HOTDOG', 5, 15.00, 75.00),
('OI142', 'ORD071', 'PUSIT Spicy', 2, 160.00, 320.00),
('OI143', 'ORD072', 'HOTDOG TJ', 4, 20.00, 80.00),
('OI144', 'ORD072', 'BELLY Roasted', 1, 130.00, 130.00),
('OI145', 'ORD073', 'PANGA BANGUS', 3, 150.00, 450.00),
('OI146', 'ORD073', 'RICE', 3, 15.00, 45.00),
('OI147', 'ORD074', 'PANGAS', 2, 150.00, 300.00),
('OI148', 'ORD074', 'KASALO', 2, 40.00, 80.00),
('ORD075-I001', 'ORD075', 'PARES', 1, 80.00, 80.00),
('ORD075-I002', 'ORD075', 'RICE', 2, 15.00, 30.00);

--
-- Triggers `orderitems`
--
DELIMITER $$
CREATE TRIGGER `reduce_inventory_after_order` AFTER INSERT ON `orderitems` FOR EACH ROW BEGIN
    DECLARE latest_stock_date DATE;
    DECLARE product_id VARCHAR(60);

    -- Find the ProductID for the item_name
    SELECT ProductID INTO product_id 
    FROM products 
    WHERE ProductName = NEW.item_name 
    LIMIT 1;

    -- Get the latest stock date for this product
    SELECT MAX(stock_date) INTO latest_stock_date 
    FROM inventory 
    WHERE ProductID = product_id;

    -- Update the stock quantity for the latest entry
    UPDATE inventory 
    SET stock_quantity = stock_quantity - NEW.item_quantity
    WHERE ProductID = product_id 
    AND stock_date = latest_stock_date;

    -- Optional: Prevent negative stock (remove if not needed)
    IF (SELECT stock_quantity FROM inventory WHERE ProductID = product_id AND stock_date = latest_stock_date) < 0 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Insufficient stock for product!';
    END IF;
END
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `total_pricing` BEFORE INSERT ON `orderitems` FOR EACH ROW SET NEW.total_price = NEW.item_quantity * NEW.item_price
$$
DELIMITER ;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `orderitems`
--
ALTER TABLE `orderitems`
  ADD PRIMARY KEY (`OrderItemID`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
