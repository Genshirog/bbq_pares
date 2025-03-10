-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Mar 05, 2025 at 07:45 AM
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
('test', 'test', 'test', 'C001', 'test');

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
(5, 'P001', 60, '2025-03-01', 'Available'),
(6, 'P002', 0, '2025-03-05', 'Not Available');

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
  `OrderItemID` varchar(10) NOT NULL,
  `OrderID` varchar(10) NOT NULL,
  `item_name` varchar(60) NOT NULL,
  `item_quantity` int(11) NOT NULL,
  `item_price` decimal(10,2) NOT NULL,
  `total_price` decimal(10,2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `orderitems`
--

INSERT INTO `orderitems` (`OrderItemID`, `OrderID`, `item_name`, `item_quantity`, `item_price`, `total_price`) VALUES
('O001', 'Order001', 'Burger', 2, 50.00, 100.00);

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
  `OrderID` varchar(10) NOT NULL,
  `order_date` date DEFAULT NULL,
  `order_description` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

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
  `SupplierID` varchar(60) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `products`
--

INSERT INTO `products` (`ProductID`, `ProductName`, `Price`, `Cost`, `SupplierID`) VALUES
('P001', 'Beef Pares', 50.00, 35.00, 'S001'),
('P002', 'Barbecue', 50.00, 20.00, 'S002');

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
  `ReceiptID` varchar(10) NOT NULL,
  `OrderID` varchar(10) NOT NULL,
  `price` decimal(10,2) DEFAULT NULL,
  `payment_method` varchar(20) NOT NULL,
  `receipt_date` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `supplier`
--

CREATE TABLE `supplier` (
  `supplierID` varchar(10) NOT NULL,
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
('S001', 'Mama', 'Joe', 's', 'Jamal', 'gyatt@rizzler.goon', '123123124124', 'Ohio, Opps Clinic'),
('S002', 'Gear', 'Knee', 'S', 'Ninja', 'test@test.test', 'Davao City', '0912-345-6789');

-- --------------------------------------------------------

--
-- Stand-in structure for view `supplier_view`
-- (See below for the actual view)
--
CREATE TABLE `supplier_view` (
`supplierID` varchar(10)
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

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `inventory_view`  AS SELECT `i`.`InventoryID` AS `InventoryID`, `p`.`ProductName` AS `ProductName`, concat(`s`.`last_name`,', ',`s`.`first_name`,' ',`s`.`middle_initial`,'.') AS `SupplierName`, `i`.`stock_quantity` AS `stock_quantity`, `i`.`stock_date` AS `stock_date`, `i`.`availability` AS `availability` FROM ((`inventory` `i` join `products` `p` on(`i`.`ProductID` = `p`.`ProductID`)) join `supplier` `s` on(`p`.`SupplierID` = `s`.`supplierID`)) ;

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

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `product_view`  AS SELECT `p`.`ProductID` AS `ProductID`, `p`.`ProductName` AS `ProductName`, `p`.`Price` AS `Price`, `p`.`Cost` AS `Cost`, concat(`s`.`last_name`,', ',`s`.`first_name`,' ',`s`.`middle_initial`,'.') AS `SupplierID` FROM (`products` `p` join `supplier` `s` on(`p`.`SupplierID` = `s`.`supplierID`)) ;

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
  ADD KEY `fk_suppler_id` (`SupplierID`);

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
  MODIFY `InventoryID` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

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
  ADD CONSTRAINT `fk_suppler_id` FOREIGN KEY (`SupplierID`) REFERENCES `supplier` (`supplierID`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
