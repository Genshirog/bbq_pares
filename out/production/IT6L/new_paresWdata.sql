-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Mar 09, 2025 at 09:47 PM
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

DELIMITER $$
--
-- Procedures
--
CREATE DEFINER=`root`@`localhost` PROCEDURE `insert_employee` (IN `id` VARCHAR(60), IN `fname` VARCHAR(60), IN `lname` VARCHAR(60), IN `mi` VARCHAR(60), IN `role` VARCHAR(10), IN `mail` VARCHAR(60), IN `num` VARCHAR(60), IN `pass` VARCHAR(60))   BEGIN
	DECLARE roleName varchar(60);
  
	SELECT role_name INTO roleName FROM job_role WHERE JobRoleID = role;
    IF roleName IN ('Manager','Cashier','Inventory Clerk') THEN
    	IF pass IS NULL OR pass = '' THEN
        	SIGNAL SQLSTATE '45000'
        	SET MESSAGE_TEXT = 'Invalid No Empty Password!';
        ELSE
        	INSERT INTO employee(EmployeeID,first_name,last_name,middle_initial,JobRoleID,email,phone_number,password) VALUES (id,fname,lname,mi,role,mail,num,pass);
    	END IF;
    ELSE
    	INSERT INTO employee(EmployeeID,first_name,last_name,middle_initial,JobRoleID,email,phone_number,password) VALUES (id,fname,lname,mi,role,mail,num,'No Password');
    END IF;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `insert_product` (IN `id` VARCHAR(60), IN `prodName` VARCHAR(60), IN `prodPrice` VARCHAR(60), IN `prodCost` VARCHAR(60), IN `prodSup` VARCHAR(60))   BEGIN
	INSERT INTO products(ProductID,ProductName,Price,Cost,SupplierID) VALUES (id,prodName,prodPrice,prodCost,prodSup);
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `insert_role` (IN `id` VARCHAR(10), IN `roleName` VARCHAR(60), IN `roleDescript` VARCHAR(60), IN `roleShift` VARCHAR(60))   BEGIN
	INSERT INTO job_role(JobRoleID,role_name,role_description,role_shift) VALUES (id,roleName,roleDescript,roleShift);
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `insert_supplier` (IN `id` VARCHAR(60), IN `fname` VARCHAR(60), IN `lname` VARCHAR(60), IN `mi` VARCHAR(60), IN `person` VARCHAR(60), IN `mail` VARCHAR(60), IN `num` VARCHAR(60), IN `supAdd` VARCHAR(60))   BEGIN
	INSERT INTO supplier(supplierID,first_name,last_name,middle_initial,contact_person,phone_number,email,address) VALUES (id,fname,lname,mi,person,num,mail,supAdd);
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `stock_in` (IN `id` VARCHAR(60), IN `stockIn` INT(11))   BEGIN
	UPDATE inventory SET stock_quantity = stockIn WHERE InventoryID = id;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `update_employee` (IN `fname` VARCHAR(60), IN `lname` VARCHAR(60), IN `mi` VARCHAR(60), IN `role` VARCHAR(10), IN `mail` VARCHAR(60), IN `num` VARCHAR(60), IN `pass` VARCHAR(60), IN `id` VARCHAR(60))   BEGIN
	DECLARE roleName varchar(60);
    
    SELECT role_name INTO roleName FROM job_role WHERE JobRoleID = role;
    
    IF roleName IN ('Manager','Cashier','Inventory Clerk') THEN
    	IF pass IS NULL OR pass = '' THEN
        	SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'Invalid No Empty Password!';
        ELSE
            UPDATE employee SET first_name = fname, last_name = lname, middle_initial = mi, JobRoleID = role, email = mail, phone_number = num, password = pass WHERE employeeID = id;
        END IF;
    ELSE
        UPDATE employee SET first_name = fname, last_name = lname, middle_initial = mi, JobRoleID = role, email = mail, phone_number = num, password = 'No Password' WHERE employeeID = id;
   END IF; 	
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `update_products` (IN `id` VARCHAR(60), IN `prodName` VARCHAR(60), IN `prodPrice` DECIMAL(10,2), IN `ProdCost` DECIMAL(10,2), IN `prodSup` VARCHAR(60))   BEGIN
	UPDATE products SET ProductName = prodName, Price = prodPrice, Cost = prodCost, SupplierID = prodSup WHERE ProductID = id;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `update_role` (IN `roleName` VARCHAR(60), IN `roleDescript` VARCHAR(60), IN `roleShift` VARCHAR(60), IN `id` VARCHAR(10))   BEGIN
	UPDATE job_role SET role_name = roleName, role_description = roleDescript, role_shift = roleShift WHERE JobRoleID = id;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `update_supplier` (IN `fname` VARCHAR(60), IN `lname` VARCHAR(60), IN `mi` VARCHAR(60), IN `person` VARCHAR(60), IN `mail` VARCHAR(60), IN `num` VARCHAR(60), IN `supAdd` VARCHAR(60), IN `id` VARCHAR(60))   BEGIN
	UPDATE supplier SET first_name = fname, last_name = lname, middle_initial = mi, contact_person = person, email = mail, phone_number = num, address = supAdd WHERE supplierID = id; 
END$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Stand-in structure for view `accounts`
-- (See below for the actual view)
--
CREATE TABLE `accounts` (
`username` varchar(184)
,`password` varchar(60)
,`JobRoleID` varchar(60)
);

-- --------------------------------------------------------

--
-- Table structure for table `customer`
--

CREATE TABLE `customer` (
  `first_name` varchar(60) NOT NULL,
  `last_name` varchar(60) NOT NULL,
  `middle_name` varchar(60) NOT NULL,
  `CustomerID` varchar(10) NOT NULL,
  `service_type` varchar(60) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `customer`
--

INSERT INTO `customer` (`first_name`, `last_name`, `middle_name`, `CustomerID`, `service_type`) VALUES
('Juan', 'Dela Cruz', 'A', 'C001', 'Dine-in'),
('Maria', 'Santos', 'B', 'C002', 'Takeout'),
('Pedro', 'Reyes', 'C', 'C003', 'Dine-in'),
('Ana', 'Garcia', 'D', 'C004', 'Takeout'),
('Luis', 'Mendoza', 'E', 'C005', 'Dine-in'),
('Clara', 'Lopez', 'F', 'C006', 'Takeout'),
('Jose', 'Tan', 'G', 'C007', 'Dine-in'),
('Elena', 'Cruz', 'H', 'C008', 'Takeout'),
('Mark', 'Villanueva', 'I', 'C009', 'Dine-in'),
('Sofia', 'Perez', 'J', 'C010', 'Takeout'),
('Rico', 'Gomez', 'K', 'C011', 'Dine-in'),
('Tina', 'Yap', 'L', 'C012', 'Takeout'),
('James', 'Ong', 'M', 'C013', 'Dine-in'),
('Liza', 'Lim', 'N', 'C014', 'Takeout'),
('Ben', 'Yu', 'O', 'C015', 'Dine-in'),
('Nina', 'Chua', 'P', 'C016', 'Takeout'),
('Alex', 'Kho', 'Q', 'C017', 'Dine-in'),
('Vera', 'Tan', 'R', 'C018', 'Takeout'),
('Paul', 'Reyes', 'S', 'C019', 'Dine-in'),
('Rose', 'Cruz', 'T', 'C020', 'Takeout'),
('Tom', 'Santos', 'U', 'C021', 'Dine-in'),
('Joy', 'Lopez', 'V', 'C022', 'Takeout'),
('Eric', 'Mendoza', 'W', 'C023', 'Dine-in'),
('Grace', 'Ramos', 'X', 'C024', 'Takeout'),
('Dan', 'Tan', 'Y', 'C025', 'Dine-in'),
('Ella', 'Vega', 'Z', 'C026', 'Takeout'),
('Fred', 'Perez', 'A', 'C027', 'Dine-in'),
('Iris', 'Luna', 'B', 'C028', 'Takeout'),
('Glen', 'Gomez', 'C', 'C029', 'Dine-in'),
('Hana', 'Yap', 'D', 'C030', 'Takeout'),
('Ian', 'Ong', 'E', 'C031', 'Dine-in'),
('Jill', 'Lim', 'F', 'C032', 'Takeout'),
('Ken', 'Yu', 'G', 'C033', 'Dine-in'),
('Lea', 'Chua', 'H', 'C034', 'Takeout'),
('Milo', 'Kho', 'I', 'C035', 'Dine-in'),
('Nora', 'Tan', 'J', 'C036', 'Takeout'),
('Omar', 'Reyes', 'K', 'C037', 'Dine-in'),
('Pia', 'Cruz', 'L', 'C038', 'Takeout'),
('Quin', 'Santos', 'M', 'C039', 'Dine-in'),
('Rhea', 'Lopez', 'N', 'C040', 'Takeout'),
('Sam', 'Mendoza', 'O', 'C041', 'Dine-in'),
('Tara', 'Ramos', 'P', 'C042', 'Takeout'),
('Ugo', 'Tan', 'Q', 'C043', 'Dine-in'),
('Vina', 'Vega', 'R', 'C044', 'Takeout'),
('Wes', 'Perez', 'S', 'C045', 'Dine-in'),
('Zara', 'Luna', 'T', 'C046', 'Takeout'),
('Lito', 'Fernandez', 'U', 'C047', 'Dine-in'),
('Mara', 'Villanueva', 'V', 'C048', 'Takeout'),
('Tomas', 'Smith', 'W', 'C049', 'Dine-in'),
('Cora', 'Garcia', 'X', 'C050', 'Takeout');

-- --------------------------------------------------------

--
-- Stand-in structure for view `customer_view`
-- (See below for the actual view)
--
CREATE TABLE `customer_view` (
`CustomerID` varchar(10)
,`Name` varchar(183)
,`Service` varchar(60)
);

-- --------------------------------------------------------

--
-- Table structure for table `discount`
--

CREATE TABLE `discount` (
  `DiscountID` varchar(10) NOT NULL,
  `discount_type` varchar(15) NOT NULL,
  `discount_percentage` decimal(10,2) NOT NULL,
  `discount_authenticity` varchar(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `employee`
--

CREATE TABLE `employee` (
  `EmployeeID` varchar(60) NOT NULL,
  `first_name` varchar(60) NOT NULL,
  `last_name` varchar(60) NOT NULL,
  `middle_initial` varchar(60) NOT NULL,
  `JobRoleID` varchar(10) DEFAULT NULL,
  `email` varchar(60) NOT NULL,
  `phone_number` varchar(60) NOT NULL,
  `password` varchar(60) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `employee`
--

INSERT INTO `employee` (`EmployeeID`, `first_name`, `last_name`, `middle_initial`, `JobRoleID`, `email`, `phone_number`, `password`) VALUES
('E001', 'Tom German', 'Arizobal', 'G', 'R001', 'arizobal@hotmail.com', '09667225516', 'skibidi'),
('E002', 'Tom German', 'Arizobal', 'G', 'R002', 'test@test.test', '0912-345-6789', '123'),
('E003', 'Tom German', 'Arizobal', 'G', 'R003', 'test@test.test', '0912-345-6789', 'admin'),
('E004', 'Fan', 'Zhou', 'C', 'R000', 'test@test.test', '0912-345-6789', 'No Password'),
('E005', 'Antonio Jr', 'Del Rosario', 'S', 'R001', 'test@test.test', '0912-345-6789', 'test');

-- --------------------------------------------------------

--
-- Stand-in structure for view `employee_view`
-- (See below for the actual view)
--
CREATE TABLE `employee_view` (
`EmployeeID` varchar(60)
,`EmployeeName` varchar(184)
,`JobRoleID` varchar(60)
,`email` varchar(60)
,`phone_number` varchar(60)
,`password` varchar(60)
);

-- --------------------------------------------------------

--
-- Table structure for table `inventory`
--

CREATE TABLE `inventory` (
  `InventoryID` int(10) NOT NULL,
  `ProductID` varchar(60) NOT NULL,
  `stock_quantity` int(11) NOT NULL,
  `stock_date` date NOT NULL,
  `availability` varchar(60) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `inventory`
--

INSERT INTO `inventory` (`InventoryID`, `ProductID`, `stock_quantity`, `stock_date`, `availability`) VALUES
(57, 'P001', 50, '2025-03-07', 'Available'),
(58, 'P002', 40, '2025-03-07', 'Available'),
(59, 'P003', 30, '2025-03-07', 'Available'),
(60, 'P004', 20, '2025-03-07', 'Available'),
(61, 'P005', 15, '2025-03-07', 'Available'),
(62, 'P006', 25, '2025-03-07', 'Available'),
(63, 'P007', 35, '2025-03-07', 'Available'),
(64, 'P008', 45, '2025-03-07', 'Available'),
(65, 'P009', 10, '2025-03-07', 'Available'),
(66, 'P010', 60, '2025-03-07', 'Available'),
(67, 'P011', 55, '2025-03-07', 'Available'),
(68, 'P012', 50, '2025-03-07', 'Available'),
(69, 'P013', 70, '2025-03-07', 'Available'),
(70, 'P014', 65, '2025-03-07', 'Available'),
(71, 'P015', 30, '2025-03-07', 'Available'),
(72, 'P016', 25, '2025-03-07', 'Available'),
(73, 'P017', 20, '2025-03-07', 'Available'),
(74, 'P018', 15, '2025-03-07', 'Available'),
(75, 'P019', 40, '2025-03-07', 'Available'),
(76, 'P020', 35, '2025-03-07', 'Available'),
(77, 'P021', 10, '2025-03-07', 'Available'),
(78, 'P022', 5, '2025-03-07', 'Available'),
(79, 'P023', 0, '2025-03-07', 'Not Available'),
(80, 'P024', 8, '2025-03-07', 'Available'),
(81, 'P025', 100, '2025-03-07', 'Available'),
(82, 'P026', 45, '2025-03-07', 'Available'),
(83, 'P027', 50, '2025-03-07', 'Available'),
(84, 'P028', 40, '2025-03-07', 'Available'),
(85, 'P029', 30, '2025-03-07', 'Available'),
(86, 'P030', 20, '2025-03-07', 'Available'),
(87, 'P031', 15, '2025-03-07', 'Available'),
(88, 'P032', 25, '2025-03-07', 'Available'),
(89, 'P033', 35, '2025-03-07', 'Available'),
(90, 'P034', 45, '2025-03-07', 'Available'),
(91, 'P035', 10, '2025-03-07', 'Available'),
(92, 'P036', 60, '2025-03-07', 'Available'),
(93, 'P037', 55, '2025-03-07', 'Available'),
(94, 'P038', 50, '2025-03-07', 'Available'),
(95, 'P039', 70, '2025-03-07', 'Available'),
(96, 'P040', 65, '2025-03-07', 'Available'),
(97, 'P041', 30, '2025-03-07', 'Available'),
(98, 'P042', 25, '2025-03-07', 'Available'),
(99, 'P043', 20, '2025-03-07', 'Available'),
(100, 'P044', 15, '2025-03-07', 'Available'),
(101, 'P045', 40, '2025-03-07', 'Available'),
(102, 'P046', 35, '2025-03-07', 'Available'),
(103, 'P047', 10, '2025-03-07', 'Available'),
(104, 'P048', 5, '2025-03-07', 'Available'),
(105, 'P049', 0, '2025-03-07', 'Not Available'),
(106, 'P050', 8, '2025-03-07', 'Available'),
(107, 'P001', 20, '2025-03-06', 'Available'),
(108, 'P002', 15, '2025-03-06', 'Available'),
(109, 'P003', 10, '2025-03-06', 'Available'),
(110, 'P004', 25, '2025-03-06', 'Available'),
(111, 'P005', 30, '2025-03-06', 'Available'),
(112, 'P006', 35, '2025-03-06', 'Available'),
(113, 'P007', 40, '2025-03-06', 'Available'),
(114, 'P008', 45, '2025-03-06', 'Available'),
(115, 'P009', 50, '2025-03-06', 'Available'),
(116, 'P010', 55, '2025-03-06', 'Available'),
(117, 'P011', 60, '2025-03-06', 'Available'),
(118, 'P012', 65, '2025-03-06', 'Available'),
(119, 'P013', 70, '2025-03-06', 'Available'),
(120, 'P014', 75, '2025-03-06', 'Available'),
(121, 'P015', 80, '2025-03-06', 'Available'),
(122, 'P016', 85, '2025-03-06', 'Available'),
(123, 'P017', 90, '2025-03-06', 'Available'),
(124, 'P018', 95, '2025-03-06', 'Available'),
(125, 'P019', 100, '2025-03-06', 'Available'),
(126, 'P020', 105, '2025-03-06', 'Available'),
(127, 'P021', 110, '2025-03-06', 'Available'),
(128, 'P022', 115, '2025-03-06', 'Available'),
(129, 'P023', 120, '2025-03-06', 'Available'),
(130, 'P024', 125, '2025-03-06', 'Available'),
(131, 'P025', 130, '2025-03-06', 'Available');

--
-- Triggers `inventory`
--
DELIMITER $$
CREATE TRIGGER `set_initial_availability` BEFORE INSERT ON `inventory` FOR EACH ROW BEGIN
    IF NEW.stock_quantity = 0 THEN
        SET NEW.availability = 'Not Available';
    ELSE
        SET NEW.availability = 'Available';
    END IF;
END
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `update_availability` BEFORE UPDATE ON `inventory` FOR EACH ROW BEGIN
IF NEW.stock_quantity = 0 THEN
	SET NEW.availability = 'Not Available';
ELSE
	SET NEW.availability = 'Available';
END IF;
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Stand-in structure for view `inventory_view`
-- (See below for the actual view)
--
CREATE TABLE `inventory_view` (
`InventoryID` int(10)
,`ProductName` varchar(60)
,`SupplierName` varchar(125)
,`stock_quantity` int(11)
,`stock_date` date
,`availability` varchar(60)
);

-- --------------------------------------------------------

--
-- Table structure for table `job_role`
--

CREATE TABLE `job_role` (
  `JobRoleID` varchar(10) NOT NULL,
  `role_name` varchar(60) NOT NULL,
  `role_description` text NOT NULL,
  `role_shift` varchar(60) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `job_role`
--

INSERT INTO `job_role` (`JobRoleID`, `role_name`, `role_description`, `role_shift`) VALUES
('R000', 'Unassigned', 'Employees That Are Yet To Be Assigned', 'Full-Time'),
('R001', 'Manager', 'A Person who handles the business and passwords', 'Full-Time'),
('R002', 'Cashier', 'Receives The Customers Order And Handles Payment', 'Full-Time'),
('R003', 'Inventory Clerk', 'Handles And Monitor The Movement Of Products', 'Full-Time');

--
-- Triggers `job_role`
--
DELIMITER $$
CREATE TRIGGER `after_role_delete` AFTER DELETE ON `job_role` FOR EACH ROW UPDATE employee
    SET JobRoleID = 'R000'
    WHERE JobRoleID IS NULL
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Stand-in structure for view `job_view`
-- (See below for the actual view)
--
CREATE TABLE `job_view` (
`JobRoleID` varchar(10)
,`role_name` varchar(60)
,`role_description` text
,`role_shift` varchar(60)
);

-- --------------------------------------------------------

--
-- Table structure for table `manager`
--

CREATE TABLE `manager` (
  `ManagerID` varchar(10) NOT NULL,
  `first_name` varchar(60) NOT NULL,
  `last_name` varchar(60) NOT NULL,
  `email` varchar(60) NOT NULL,
  `phone_number` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Stand-in structure for view `menu_view`
-- (See below for the actual view)
--
CREATE TABLE `menu_view` (
`ProductID` varchar(60)
,`ProductName` varchar(60)
,`Price` decimal(10,2)
);

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
('OI149', 'ORD075', 'PUSIT', 1, 150.00, 150.00),
('OI150', 'ORD075', 'PARES Extra', 2, 85.00, 170.00);

--
-- Triggers `orderitems`
--
DELIMITER $$
CREATE TRIGGER `total_pricing` BEFORE INSERT ON `orderitems` FOR EACH ROW SET NEW.total_price = NEW.item_quantity * NEW.item_price
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `orders`
--

CREATE TABLE `orders` (
  `OrderID` varchar(15) NOT NULL,
  `order_date` date DEFAULT NULL,
  `order_description` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `orders`
--

INSERT INTO `orders` (`OrderID`, `order_date`, `order_description`) VALUES
('ORD001', '2025-03-07', 'Customer order for dine-in'),
('ORD002', '2025-03-07', 'Takeout order'),
('ORD003', '2025-03-07', 'Large group order'),
('ORD004', '2025-03-07', 'Quick meal order'),
('ORD005', '2025-03-07', 'Family pack order'),
('ORD006', '2025-03-07', 'Solo meal order'),
('ORD007', '2025-03-07', 'Lunch special order'),
('ORD008', '2025-03-07', 'Dinner order'),
('ORD009', '2025-03-07', 'Breakfast order'),
('ORD010', '2025-03-07', 'Snack order'),
('ORD011', '2025-03-07', 'Bulk order'),
('ORD012', '2025-03-07', 'Party order'),
('ORD013', '2025-03-07', 'Regular customer order'),
('ORD014', '2025-03-07', 'Special request order'),
('ORD015', '2025-03-07', 'Custom combo order'),
('ORD016', '2025-03-07', 'Quick pickup order'),
('ORD017', '2025-03-07', 'Group meal order'),
('ORD018', '2025-03-07', 'Individual order'),
('ORD019', '2025-03-07', 'Event catering order'),
('ORD020', '2025-03-07', 'Takeout combo order'),
('ORD021', '2025-03-07', 'Dine-in family order'),
('ORD022', '2025-03-07', 'Lunch rush order'),
('ORD023', '2025-03-07', 'Dinner rush order'),
('ORD024', '2025-03-07', 'Late night order'),
('ORD025', '2025-03-07', 'Early morning order'),
('ORD026', '2025-03-07', 'Weekend special order'),
('ORD027', '2025-03-07', 'Weekday lunch order'),
('ORD028', '2025-03-07', 'Takeout bulk order'),
('ORD029', '2025-03-07', 'Dine-in quick order'),
('ORD030', '2025-03-07', 'Large party order'),
('ORD031', '2025-03-07', 'Solo takeout order'),
('ORD032', '2025-03-07', 'Family dine-in order'),
('ORD033', '2025-03-07', 'Group takeout order'),
('ORD034', '2025-03-07', 'Special event order'),
('ORD035', '2025-03-07', 'Custom meal order'),
('ORD036', '2025-03-07', 'Quick dine-in order'),
('ORD037', '2025-03-07', 'Bulk takeout order'),
('ORD038', '2025-03-07', 'Party catering order'),
('ORD039', '2025-03-07', 'Regular dine-in order'),
('ORD040', '2025-03-07', 'Special takeout order'),
('ORD041', '2025-03-07', 'Combo dine-in order'),
('ORD042', '2025-03-07', 'Quick takeout order'),
('ORD043', '2025-03-07', 'Group dine-in order'),
('ORD044', '2025-03-07', 'Individual takeout order'),
('ORD045', '2025-03-07', 'Event dine-in order'),
('ORD046', '2025-03-07', 'Combo takeout order'),
('ORD047', '2025-03-07', 'Family takeout order'),
('ORD048', '2025-03-07', 'Lunch dine-in order'),
('ORD049', '2025-03-07', 'Dinner takeout order'),
('ORD050', '2025-03-07', 'Breakfast dine-in order'),
('ORD051', '2025-03-07', 'Snack takeout order'),
('ORD052', '2025-03-07', 'Bulk dine-in order'),
('ORD053', '2025-03-07', 'Party takeout order'),
('ORD054', '2025-03-07', 'Regular dine-in order'),
('ORD055', '2025-03-07', 'Special takeout order'),
('ORD056', '2025-03-07', 'Combo dine-in order'),
('ORD057', '2025-03-07', 'Quick takeout order'),
('ORD058', '2025-03-07', 'Group dine-in order'),
('ORD059', '2025-03-07', 'Individual takeout order'),
('ORD060', '2025-03-07', 'Event dine-in order'),
('ORD061', '2025-03-07', 'Combo takeout order'),
('ORD062', '2025-03-07', 'Family dine-in order'),
('ORD063', '2025-03-07', 'Lunch takeout order'),
('ORD064', '2025-03-07', 'Dinner dine-in order'),
('ORD065', '2025-03-07', 'Breakfast takeout order'),
('ORD066', '2025-03-07', 'Snack dine-in order'),
('ORD067', '2025-03-07', 'Bulk takeout order'),
('ORD068', '2025-03-07', 'Party dine-in order'),
('ORD069', '2025-03-07', 'Regular takeout order'),
('ORD070', '2025-03-07', 'Special dine-in order'),
('ORD071', '2025-03-07', 'Combo takeout order'),
('ORD072', '2025-03-07', 'Quick dine-in order'),
('ORD073', '2025-03-07', 'Group takeout order'),
('ORD074', '2025-03-07', 'Individual dine-in order'),
('ORD075', '2025-03-07', 'Event takeout order');

--
-- Triggers `orders`
--
DELIMITER $$
CREATE TRIGGER `trig_date` BEFORE INSERT ON `orders` FOR EACH ROW SET NEW.order_date = CURDATE()
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `ordersupply`
--

CREATE TABLE `ordersupply` (
  `OrderSupplyID` varchar(10) NOT NULL,
  `supply_date` date NOT NULL,
  `supply_details` text NOT NULL,
  `total_cost` decimal(10,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `products`
--

CREATE TABLE `products` (
  `ProductID` varchar(60) NOT NULL,
  `ProductName` varchar(60) NOT NULL,
  `Price` decimal(10,2) NOT NULL,
  `Cost` decimal(10,2) NOT NULL,
  `supplierID` varchar(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `products`
--

INSERT INTO `products` (`ProductID`, `ProductName`, `Price`, `Cost`, `supplierID`) VALUES
('P001', 'PARES', 80.00, 56.00, 'S001'),
('P002', 'Beef PARES', 80.00, 56.00, 'S002'),
('P003', 'OVERLOAD PARES', 100.00, 70.00, 'S003'),
('P004', 'KAWALI', 90.00, 63.00, 'S004'),
('P005', 'BULAK-BULAK', 90.00, 63.00, 'S005'),
('P006', 'LIEMPO', 95.00, 66.50, 'S006'),
('P007', 'PAA', 85.00, 59.50, 'S007'),
('P008', 'PETCHO', 85.00, 59.50, 'S008'),
('P009', 'CHOT', 85.00, 59.50, 'S009'),
('P010', 'POUL', 15.00, 10.50, 'S010'),
('P011', 'BAT', 15.00, 10.50, 'S011'),
('P012', 'ATAY', 15.00, 10.50, 'S012'),
('P013', 'ISAW', 10.00, 7.00, 'S013'),
('P014', 'ISOL', 25.00, 17.50, 'S014'),
('P015', 'LIOG', 20.00, 14.00, 'S015'),
('P016', 'CS', 20.00, 14.00, 'S016'),
('P017', 'CM', 20.00, 14.00, 'S017'),
('P018', 'PICHO', 20.00, 14.00, 'S018'),
('P019', 'HOTDOG', 15.00, 10.50, 'S019'),
('P020', 'HOTDOG TJ', 20.00, 14.00, 'S020'),
('P021', 'PANGA BANGUS', 150.00, 105.00, 'S021'),
('P022', 'PANGAS', 150.00, 105.00, 'S022'),
('P023', 'PUSIT', 150.00, 105.00, 'S023'),
('P024', 'BELLY', 120.00, 84.00, 'S024'),
('P025', 'RICE', 15.00, 10.50, 'S025'),
('P026', 'KASALO', 40.00, 28.00, 'S026'),
('P027', 'PARES Extra', 85.00, 59.50, 'S027'),
('P028', 'Beef PARES Deluxe', 90.00, 63.00, 'S028'),
('P029', 'OVERLOAD PARES Plus', 110.00, 77.00, 'S029'),
('P030', 'KAWALI Special', 95.00, 66.50, 'S030'),
('P031', 'BULAK-BULAK Spicy', 95.00, 66.50, 'S031'),
('P032', 'LIEMPO Grilled', 100.00, 70.00, 'S032'),
('P033', 'PAA Fried', 90.00, 63.00, 'S033'),
('P034', 'PETCHO Spicy', 90.00, 63.00, 'S034'),
('P035', 'CHOT Grilled', 90.00, 63.00, 'S035'),
('P036', 'POUL Roasted', 20.00, 14.00, 'S036'),
('P037', 'BAT Spicy', 20.00, 14.00, 'S037'),
('P038', 'ATAY Fried', 20.00, 14.00, 'S038'),
('P039', 'ISAW Grilled', 15.00, 10.50, 'S039'),
('P040', 'ISOL Spicy', 30.00, 21.00, 'S040'),
('P041', 'LIOG Roasted', 25.00, 17.50, 'S041'),
('P042', 'CS Extra', 25.00, 17.50, 'S042'),
('P043', 'CM Spicy', 25.00, 17.50, 'S043'),
('P044', 'PICHO Grilled', 25.00, 17.50, 'S044'),
('P045', 'HOTDOG Special', 20.00, 14.00, 'S045'),
('P046', 'HOTDOG TJ Plus', 25.00, 17.50, 'S046'),
('P047', 'PANGA BANGUS Fried', 160.00, 112.00, 'S047'),
('P048', 'PANGAS Grilled', 160.00, 112.00, 'S048'),
('P049', 'PUSIT Spicy', 160.00, 112.00, 'S049'),
('P050', 'BELLY Roasted', 130.00, 91.00, 'S050');

--
-- Triggers `products`
--
DELIMITER $$
CREATE TRIGGER `fk_insert_product` AFTER INSERT ON `products` FOR EACH ROW INSERT INTO inventory(inventory.ProductID, inventory.stock_quantity, inventory.stock_date, inventory.availability) 
VALUES (NEW.ProductID, 0, CURDATE(), NULL)
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Stand-in structure for view `product_view`
-- (See below for the actual view)
--
CREATE TABLE `product_view` (
`ProductID` varchar(60)
,`ProductName` varchar(60)
,`Price` decimal(10,2)
,`Cost` decimal(10,2)
,`SupplierID` varchar(125)
);

-- --------------------------------------------------------

--
-- Table structure for table `receipt`
--

CREATE TABLE `receipt` (
  `ReceiptID` varchar(15) NOT NULL,
  `OrderID` varchar(15) NOT NULL,
  `price` decimal(10,2) DEFAULT NULL,
  `payment_method` varchar(20) NOT NULL,
  `receipt_date` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `receipt`
--

INSERT INTO `receipt` (`ReceiptID`, `OrderID`, `price`, `payment_method`, `receipt_date`) VALUES
('R001', 'ORD001', 190.00, 'Cash', '2025-03-07'),
('R002', 'ORD002', 125.00, 'Card', '2025-03-07'),
('R003', 'ORD003', 480.00, 'Cash', '2025-03-07'),
('R004', 'ORD004', 140.00, 'Card', '2025-03-07'),
('R005', 'ORD005', 315.00, 'Cash', '2025-03-07'),
('R006', 'ORD006', 125.00, 'Card', '2025-03-07'),
('R007', 'ORD007', 215.00, 'Cash', '2025-03-07'),
('R008', 'ORD008', 145.00, 'Card', '2025-03-07'),
('R009', 'ORD009', 220.00, 'Cash', '2025-03-07'),
('R010', 'ORD010', 75.00, 'Card', '2025-03-07'),
('R011', 'ORD011', 125.00, 'Cash', '2025-03-07'),
('R012', 'ORD012', 120.00, 'Card', '2025-03-07'),
('R013', 'ORD013', 100.00, 'Cash', '2025-03-07'),
('R014', 'ORD014', 110.00, 'Card', '2025-03-07'),
('R015', 'ORD015', 100.00, 'Cash', '2025-03-07'),
('R016', 'ORD016', 100.00, 'Card', '2025-03-07'),
('R017', 'ORD017', 190.00, 'Cash', '2025-03-07'),
('R018', 'ORD018', 210.00, 'Card', '2025-03-07'),
('R019', 'ORD019', 375.00, 'Cash', '2025-03-07'),
('R020', 'ORD020', 200.00, 'Card', '2025-03-07'),
('R021', 'ORD021', 495.00, 'Cash', '2025-03-07'),
('R022', 'ORD022', 380.00, 'Card', '2025-03-07'),
('R023', 'ORD023', 320.00, 'Cash', '2025-03-07'),
('R024', 'ORD024', 300.00, 'Card', '2025-03-07'),
('R025', 'ORD025', 185.00, 'Cash', '2025-03-07'),
('R026', 'ORD026', 310.00, 'Card', '2025-03-07'),
('R027', 'ORD027', 255.00, 'Cash', '2025-03-07'),
('R028', 'ORD028', 340.00, 'Card', '2025-03-07'),
('R029', 'ORD029', 290.00, 'Cash', '2025-03-07'),
('R030', 'ORD030', 540.00, 'Card', '2025-03-07'),
('R031', 'ORD031', 180.00, 'Cash', '2025-03-07'),
('R032', 'ORD032', 250.00, 'Card', '2025-03-07'),
('R033', 'ORD033', 165.00, 'Cash', '2025-03-07'),
('R034', 'ORD034', 230.00, 'Card', '2025-03-07'),
('R035', 'ORD035', 160.00, 'Cash', '2025-03-07'),
('R036', 'ORD036', 90.00, 'Card', '2025-03-07'),
('R037', 'ORD037', 95.00, 'Cash', '2025-03-07'),
('R038', 'ORD038', 135.00, 'Card', '2025-03-07'),
('R039', 'ORD039', 110.00, 'Cash', '2025-03-07'),
('R040', 'ORD040', 125.00, 'Card', '2025-03-07'),
('R041', 'ORD041', 100.00, 'Cash', '2025-03-07'),
('R042', 'ORD042', 110.00, 'Card', '2025-03-07'),
('R043', 'ORD043', 200.00, 'Cash', '2025-03-07'),
('R044', 'ORD044', 220.00, 'Card', '2025-03-07'),
('R045', 'ORD045', 395.00, 'Cash', '2025-03-07'),
('R046', 'ORD046', 210.00, 'Card', '2025-03-07'),
('R047', 'ORD047', 495.00, 'Cash', '2025-03-07'),
('R048', 'ORD048', 380.00, 'Card', '2025-03-07'),
('R049', 'ORD049', 320.00, 'Cash', '2025-03-07'),
('R050', 'ORD050', 300.00, 'Card', '2025-03-07'),
('R051', 'ORD051', 185.00, 'Cash', '2025-03-06'),
('R052', 'ORD052', 310.00, 'Card', '2025-03-06'),
('R053', 'ORD053', 255.00, 'Cash', '2025-03-06'),
('R054', 'ORD054', 340.00, 'Card', '2025-03-06'),
('R055', 'ORD055', 290.00, 'Cash', '2025-03-06'),
('R056', 'ORD056', 540.00, 'Card', '2025-03-06'),
('R057', 'ORD057', 180.00, 'Cash', '2025-03-06'),
('R058', 'ORD058', 250.00, 'Card', '2025-03-06'),
('R059', 'ORD059', 165.00, 'Cash', '2025-03-06'),
('R060', 'ORD060', 230.00, 'Card', '2025-03-06'),
('R061', 'ORD061', 160.00, 'Cash', '2025-03-06'),
('R062', 'ORD062', 90.00, 'Card', '2025-03-06'),
('R063', 'ORD063', 95.00, 'Cash', '2025-03-06'),
('R064', 'ORD064', 135.00, 'Card', '2025-03-06'),
('R065', 'ORD065', 110.00, 'Cash', '2025-03-06'),
('R066', 'ORD066', 125.00, 'Card', '2025-03-06'),
('R067', 'ORD067', 100.00, 'Cash', '2025-03-06'),
('R068', 'ORD068', 110.00, 'Card', '2025-03-06'),
('R069', 'ORD069', 200.00, 'Cash', '2025-03-06'),
('R070', 'ORD070', 220.00, 'Card', '2025-03-06'),
('R071', 'ORD071', 395.00, 'Cash', '2025-03-06'),
('R072', 'ORD072', 210.00, 'Card', '2025-03-06'),
('R073', 'ORD073', 495.00, 'Cash', '2025-03-06'),
('R074', 'ORD074', 380.00, 'Card', '2025-03-06'),
('R075', 'ORD075', 320.00, 'Cash', '2025-03-06');

-- --------------------------------------------------------

--
-- Table structure for table `supplier`
--

CREATE TABLE `supplier` (
  `supplierID` varchar(15) NOT NULL,
  `first_name` varchar(60) NOT NULL,
  `last_name` varchar(60) NOT NULL,
  `middle_initial` varchar(1) NOT NULL,
  `contact_person` varchar(60) NOT NULL,
  `phone_number` varchar(30) NOT NULL,
  `email` varchar(60) NOT NULL,
  `address` varchar(60) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `supplier`
--

INSERT INTO `supplier` (`supplierID`, `first_name`, `last_name`, `middle_initial`, `contact_person`, `phone_number`, `email`, `address`) VALUES
('S001', 'Lola', 'Fernandez', 'S', 'Jamal', '0912-345-1241', 'lola.fernandez@email.com', 'Ohio, Opps Clinic'),
('S002', 'Tito', 'Villanueva', 'S', 'Ninja', '0912-345-6789', 'tito.villanueva@email.com', 'Davao City'),
('S003', 'John', 'Smith', 'A', 'John Smith', '0912-345-6781', 'john.smith@email.com', '123 Grill St, Manila'),
('S004', 'Maria', 'Garcia', 'B', 'Maria Garcia', '0912-345-6782', 'maria.garcia@email.com', '456 Food Ave, Quezon'),
('S005', 'Pedro', 'Reyes', 'C', 'Pedro Reyes', '0912-345-6783', 'pedro.reyes@email.com', '789 Meat Rd, Davao'),
('S006', 'Anna', 'Cruz', 'D', 'Anna Cruz', '0912-345-6784', 'anna.cruz@email.com', '101 BBQ Lane, Cebu'),
('S007', 'Luis', 'Santos', 'E', 'Luis Santos', '0912-345-6785', 'luis.santos@email.com', '202 Pork St, Makati'),
('S008', 'Clara', 'Lopez', 'F', 'Clara Lopez', '0912-345-6786', 'clara.lopez@email.com', '303 Chicken Blvd, Pasig'),
('S009', 'Jose', 'Mendoza', 'G', 'Jose Mendoza', '0912-345-6787', 'jose.mendoza@email.com', '404 Seafood Ave, Taguig'),
('S010', 'Elena', 'Ramos', 'H', 'Elena Ramos', '0912-345-6788', 'elena.ramos@email.com', '505 Rice Rd, Baguio'),
('S011', 'Mark', 'Tan', 'I', 'Mark Tan', '0912-345-6819', 'mark.tan@email.com', '606 Drink St, Manila'),
('S012', 'Lina', 'Vega', 'J', 'Lina Vega', '0912-345-6790', 'lina.vega@email.com', '707 Grill Rd, Quezon'),
('S013', 'Rico', 'Perez', 'K', 'Rico Perez', '0912-345-6791', 'rico.perez@email.com', '808 Hotdog Lane, Davao'),
('S014', 'Sofia', 'Luna', 'L', 'Sofia Luna', '0912-345-6792', 'sofia.luna@email.com', '909 Specialty Ave, Cebu'),
('S015', 'Carlo', 'Gomez', 'M', 'Carlo Gomez', '0912-345-6793', 'carlo.gomez@email.com', '1010 Pares St, Makati'),
('S016', 'Tina', 'Yap', 'N', 'Tina Yap', '0912-345-6794', 'tina.yap@email.com', '1111 Fried Rd, Pasig'),
('S017', 'James', 'Ong', 'O', 'James Ong', '0912-345-6795', 'james.ong@email.com', '1212 Grill St, Taguig'),
('S018', 'Liza', 'Lim', 'P', 'Liza Lim', '0912-345-6796', 'liza.lim@email.com', '1313 Innards Ave, Baguio'),
('S019', 'Ben', 'Yu', 'Q', 'Ben Yu', '0912-345-6797', 'ben.yu@email.com', '1414 Seafood Rd, Manila'),
('S020', 'Nina', 'Chua', 'R', 'Nina Chua', '0912-345-6798', 'nina.chua@email.com', '1515 Chicken Lane, Quezon'),
('S021', 'Alex', 'Kho', 'S', 'Alex Kho', '0912-345-6799', 'alex.kho@email.com', '1616 Pork St, Davao'),
('S022', 'Vera', 'Tan', 'T', 'Vera Tan', '0912-345-6800', 'vera.tan@email.com', '1717 Hotdog Blvd, Cebu'),
('S023', 'Paul', 'Reyes', 'U', 'Paul Reyes', '0912-345-6801', 'paul.reyes@email.com', '1818 Pares Ave, Makati'),
('S024', 'Rose', 'Cruz', 'V', 'Rose Cruz', '0912-345-6802', 'rose.cruz@email.com', '1919 Rice Rd, Pasig'),
('S025', 'Tom', 'Santos', 'W', 'Tom Santos', '0912-345-6803', 'tom.santos@email.com', '2020 Drink St, Taguig'),
('S026', 'Joy', 'Lopez', 'X', 'Joy Lopez', '0912-345-6804', 'joy.lopez@email.com', '2121 Grill Lane, Baguio'),
('S027', 'Eric', 'Mendoza', 'Y', 'Eric Mendoza', '0912-345-6805', 'eric.mendoza@email.com', '2222 Specialty St, Manila'),
('S028', 'Grace', 'Ramos', 'Z', 'Grace Ramos', '0912-345-6806', 'grace.ramos@email.com', '2323 Fried Rd, Quezon'),
('S029', 'Dan', 'Tan', 'A', 'Dan Tan', '0912-345-6807', 'dan.tan@email.com', '2424 Innards Ave, Davao'),
('S030', 'Ella', 'Vega', 'B', 'Ella Vega', '0912-345-6808', 'ella.vega@email.com', '2525 Seafood St, Cebu'),
('S031', 'Fred', 'Perez', 'C', 'Fred Perez', '0912-345-6809', 'fred.perez@email.com', '2626 Chicken Lane, Makati'),
('S032', 'Iris', 'Luna', 'D', 'Iris Luna', '0912-345-6810', 'iris.luna@email.com', '2727 Pork Rd, Pasig'),
('S033', 'Glen', 'Gomez', 'E', 'Glen Gomez', '0912-345-6811', 'glen.gomez@email.com', '2828 Hotdog Ave, Taguig'),
('S034', 'Hana', 'Yap', 'F', 'Hana Yap', '0912-345-6812', 'hana.yap@email.com', '2929 Pares St, Baguio'),
('S035', 'Ian', 'Ong', 'G', 'Ian Ong', '0912-345-6813', 'ian.ong@email.com', '3030 Rice Rd, Manila'),
('S036', 'Jill', 'Lim', 'H', 'Jill Lim', '0912-345-6814', 'jill.lim@email.com', '3131 Drink Lane, Quezon'),
('S037', 'Ken', 'Yu', 'I', 'Ken Yu', '0912-345-6815', 'ken.yu@email.com', '3232 Grill Ave, Davao'),
('S038', 'Lea', 'Chua', 'J', 'Lea Chua', '0912-345-6816', 'lea.chua@email.com', '3333 Specialty St, Cebu'),
('S039', 'Milo', 'Kho', 'K', 'Milo Kho', '0912-345-6817', 'milo.kho@email.com', '3434 Fried Rd, Makati'),
('S040', 'Nora', 'Tan', 'L', 'Nora Tan', '0912-345-6818', 'nora.tan@email.com', '3535 Innards Lane, Pasig'),
('S041', 'Omar', 'Reyes', 'M', 'Omar Reyes', '0912-345-6819', 'omar.reyes@email.com', '3636 Seafood St, Taguig'),
('S042', 'Pia', 'Cruz', 'N', 'Pia Cruz', '0912-345-6820', 'pia.cruz@email.com', '3737 Chicken Ave, Baguio'),
('S043', 'Quin', 'Santos', 'O', 'Quin Santos', '0912-345-6821', 'quin.santos@email.com', '3838 Pork Rd, Manila'),
('S044', 'Rhea', 'Lopez', 'P', 'Rhea Lopez', '0912-345-6822', 'rhea.lopez@email.com', '3939 Hotdog Lane, Quezon'),
('S045', 'Sam', 'Mendoza', 'Q', 'Sam Mendoza', '0912-345-6823', 'sam.mendoza@email.com', '4040 Pares St, Davao'),
('S046', 'Tara', 'Ramos', 'R', 'Tara Ramos', '0912-345-6824', 'tara.ramos@email.com', '4141 Rice Ave, Cebu'),
('S047', 'Ugo', 'Tan', 'S', 'Ugo Tan', '0912-345-6825', 'ugo.tan@email.com', '4242 Drink Rd, Makati'),
('S048', 'Vina', 'Vega', 'T', 'Vina Vega', '0912-345-6826', 'vina.vega@email.com', '4343 Grill St, Pasig'),
('S049', 'Wes', 'Perez', 'U', 'Wes Perez', '0912-345-6827', 'wes.perez@email.com', '4444 Specialty Lane, Taguig'),
('S050', 'Zara', 'Luna', 'V', 'Zara Luna', '0912-345-6828', 'zara.luna@email.com', '4545 Fried Ave, Baguio');

-- --------------------------------------------------------

--
-- Stand-in structure for view `supplier_view`
-- (See below for the actual view)
--
CREATE TABLE `supplier_view` (
`supplierID` varchar(15)
,`supplierName` varchar(125)
,`contact_person` varchar(60)
,`address` varchar(60)
,`email` varchar(60)
,`phone_number` varchar(30)
);

-- --------------------------------------------------------

--
-- Table structure for table `test`
--

CREATE TABLE `test` (
  `EmployeeID` varchar(60) NOT NULL,
  `first_name` varchar(60) NOT NULL,
  `last_name` varchar(60) NOT NULL,
  `middle_initial` varchar(60) NOT NULL,
  `JobRoleID` varchar(60) DEFAULT NULL,
  `email` varchar(60) NOT NULL,
  `phone_number` varchar(60) NOT NULL,
  `password` varchar(60) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `test`
--

INSERT INTO `test` (`EmployeeID`, `first_name`, `last_name`, `middle_initial`, `JobRoleID`, `email`, `phone_number`, `password`) VALUES
('E001', 'asd', 'asdas', 'd', 'dsfsdfs', 'fdsfds', 'sdsdfs', ''),
('E002', 'dsdfd', 'fsdsf', 'f', 'fsddf', 'fsddfs', 'dfsfd', 'dfdsdf'),
('E003', 'dfngdns', 'sdfdsfndfs', 'g', 'dssdnf', 'dnfndh', 'dsfsd', ''),
('E004', 'sdfsdfn', 'fdsfndsfn', 'd', 'nsdfndsfn', 'nsgnfg', 'dsnfnsd', 'nsdnvsd'),
('E006', 'Tom', 'Arizobal', 'G', 'R001', 'test', 'test', 'tewtset');

-- --------------------------------------------------------

--
-- Structure for view `accounts`
--
DROP TABLE IF EXISTS `accounts`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `accounts`  AS SELECT concat(`e`.`last_name`,', ',`e`.`first_name`,' ',`e`.`middle_initial`,'.') AS `username`, `e`.`password` AS `password`, `r`.`role_name` AS `JobRoleID` FROM (`employee` `e` join `job_view` `r` on(`e`.`JobRoleID` = `r`.`JobRoleID`)) WHERE `r`.`role_name` in ('Manager','Cashier','Inventory Clerk') ;

-- --------------------------------------------------------

--
-- Structure for view `customer_view`
--
DROP TABLE IF EXISTS `customer_view`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `customer_view`  AS SELECT `customer`.`CustomerID` AS `CustomerID`, concat(`customer`.`last_name`,', ',`customer`.`first_name`,' ',`customer`.`middle_name`) AS `Name`, `customer`.`service_type` AS `Service` FROM `customer` ;

-- --------------------------------------------------------

--
-- Structure for view `employee_view`
--
DROP TABLE IF EXISTS `employee_view`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `employee_view`  AS SELECT `e`.`EmployeeID` AS `EmployeeID`, concat(`e`.`last_name`,', ',`e`.`first_name`,' ',`e`.`middle_initial`,'.') AS `EmployeeName`, `j`.`role_name` AS `JobRoleID`, `e`.`email` AS `email`, `e`.`phone_number` AS `phone_number`, `e`.`password` AS `password` FROM (`employee` `e` join `job_role` `j` on(`e`.`JobRoleID` = `j`.`JobRoleID`)) ;

-- --------------------------------------------------------

--
-- Structure for view `inventory_view`
--
DROP TABLE IF EXISTS `inventory_view`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `inventory_view`  AS SELECT `i`.`InventoryID` AS `InventoryID`, `p`.`ProductName` AS `ProductName`, concat(`s`.`last_name`,', ',`s`.`first_name`,' ',`s`.`middle_initial`,'.') AS `SupplierName`, `i`.`stock_quantity` AS `stock_quantity`, `i`.`stock_date` AS `stock_date`, `i`.`availability` AS `availability` FROM ((`inventory` `i` join `products` `p` on(`i`.`ProductID` = `p`.`ProductID`)) join `supplier` `s` on(`p`.`supplierID` = `s`.`supplierID`)) ;

-- --------------------------------------------------------

--
-- Structure for view `job_view`
--
DROP TABLE IF EXISTS `job_view`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `job_view`  AS SELECT `j`.`JobRoleID` AS `JobRoleID`, `j`.`role_name` AS `role_name`, `j`.`role_description` AS `role_description`, `j`.`role_shift` AS `role_shift` FROM `job_role` AS `j` ;

-- --------------------------------------------------------

--
-- Structure for view `menu_view`
--
DROP TABLE IF EXISTS `menu_view`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `menu_view`  AS SELECT `p`.`ProductID` AS `ProductID`, `p`.`ProductName` AS `ProductName`, `p`.`Price` AS `Price` FROM `products` AS `p` ;

-- --------------------------------------------------------

--
-- Structure for view `product_view`
--
DROP TABLE IF EXISTS `product_view`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `product_view`  AS SELECT `p`.`ProductID` AS `ProductID`, `p`.`ProductName` AS `ProductName`, `p`.`Price` AS `Price`, `p`.`Cost` AS `Cost`, concat(`s`.`last_name`,', ',`s`.`first_name`,' ',`s`.`middle_initial`,'.') AS `SupplierID` FROM (`products` `p` join `supplier` `s` on(`p`.`supplierID` = `s`.`supplierID`)) ;

-- --------------------------------------------------------

--
-- Structure for view `supplier_view`
--
DROP TABLE IF EXISTS `supplier_view`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `supplier_view`  AS SELECT `s`.`supplierID` AS `supplierID`, concat(`s`.`last_name`,', ',`s`.`first_name`,' ',`s`.`middle_initial`,'.') AS `supplierName`, `s`.`contact_person` AS `contact_person`, `s`.`address` AS `address`, `s`.`email` AS `email`, `s`.`phone_number` AS `phone_number` FROM `supplier` AS `s` ;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `customer`
--
ALTER TABLE `customer`
  ADD PRIMARY KEY (`CustomerID`);

--
-- Indexes for table `discount`
--
ALTER TABLE `discount`
  ADD PRIMARY KEY (`DiscountID`);

--
-- Indexes for table `employee`
--
ALTER TABLE `employee`
  ADD PRIMARY KEY (`EmployeeID`),
  ADD KEY `fk_role_id` (`JobRoleID`);

--
-- Indexes for table `inventory`
--
ALTER TABLE `inventory`
  ADD PRIMARY KEY (`InventoryID`),
  ADD KEY `inventory_prod` (`ProductID`);

--
-- Indexes for table `job_role`
--
ALTER TABLE `job_role`
  ADD PRIMARY KEY (`JobRoleID`);

--
-- Indexes for table `manager`
--
ALTER TABLE `manager`
  ADD PRIMARY KEY (`ManagerID`);

--
-- Indexes for table `orderitems`
--
ALTER TABLE `orderitems`
  ADD PRIMARY KEY (`OrderItemID`);

--
-- Indexes for table `orders`
--
ALTER TABLE `orders`
  ADD PRIMARY KEY (`OrderID`);

--
-- Indexes for table `ordersupply`
--
ALTER TABLE `ordersupply`
  ADD PRIMARY KEY (`OrderSupplyID`);

--
-- Indexes for table `products`
--
ALTER TABLE `products`
  ADD PRIMARY KEY (`ProductID`),
  ADD KEY `fk_suppler_id` (`supplierID`);

--
-- Indexes for table `supplier`
--
ALTER TABLE `supplier`
  ADD PRIMARY KEY (`supplierID`);

--
-- Indexes for table `test`
--
ALTER TABLE `test`
  ADD PRIMARY KEY (`EmployeeID`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `inventory`
--
ALTER TABLE `inventory`
  MODIFY `InventoryID` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=132;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `employee`
--
ALTER TABLE `employee`
  ADD CONSTRAINT `fk_role_id` FOREIGN KEY (`JobRoleID`) REFERENCES `job_role` (`JobRoleID`) ON DELETE SET NULL ON UPDATE SET NULL;

--
-- Constraints for table `inventory`
--
ALTER TABLE `inventory`
  ADD CONSTRAINT `fk_inventory_prod` FOREIGN KEY (`ProductID`) REFERENCES `products` (`ProductID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `products`
--
ALTER TABLE `products`
  ADD CONSTRAINT `fk_suppler_id` FOREIGN KEY (`supplierID`) REFERENCES `supplier` (`supplierID`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
