-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Feb 17, 2025 at 03:21 PM
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
-- Database: `bbq_pares`
--

-- --------------------------------------------------------

--
-- Table structure for table `customer`
--

CREATE TABLE `customer` (
  `first_name` varchar(60) NOT NULL,
  `last_name` varchar(60) NOT NULL,
  `middle_name` varchar(60) NOT NULL,
  `special_case` varchar(20) NOT NULL,
  `CustomerID` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

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
  `EmployeeID` varchar(10) NOT NULL,
  `first_name` varchar(60) NOT NULL,
  `last_name` varchar(60) NOT NULL,
  `JobRoleID` varchar(10) DEFAULT NULL,
  `phone_number` varchar(20) NOT NULL,
  `email` varchar(60) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `employee`
--

INSERT INTO `employee` (`EmployeeID`, `first_name`, `last_name`, `JobRoleID`, `phone_number`, `email`) VALUES
('E001', 'Knee', 'Gears', 'R001', 'asd', 'asd'),
('E002', 'Joe', 'Mama', 'R001', 'dfsgdfb', 'gsfdd');

-- --------------------------------------------------------

--
-- Stand-in structure for view `employee_view`
-- (See below for the actual view)
--
CREATE TABLE `employee_view` (
`employeeID` varchar(10)
,`employeeName` varchar(121)
,`role` varchar(10)
,`email` varchar(60)
,`phone_number` varchar(20)
);

-- --------------------------------------------------------

--
-- Table structure for table `inventory`
--

CREATE TABLE `inventory` (
  `InventoryID` varchar(10) NOT NULL,
  `item_name` varchar(60) NOT NULL,
  `item_category` varchar(60) NOT NULL,
  `stock_quantity` int(11) NOT NULL,
  `stock_date` date NOT NULL,
  `stock_details` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

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
('R001', 'Manager', 'A nigger holder that controls joe mama', 'Day');

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
  `supplier_name` varchar(60) NOT NULL,
  `contact_person` varchar(60) NOT NULL,
  `phone_number` varchar(30) NOT NULL,
  `email` varchar(60) NOT NULL,
  `address` varchar(60) NOT NULL,
  `supplier_items` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Structure for view `employee_view`
--
DROP TABLE IF EXISTS `employee_view`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `employee_view`  AS SELECT `e`.`EmployeeID` AS `employeeID`, concat(`e`.`first_name`,' ',`e`.`last_name`) AS `employeeName`, `e`.`JobRoleID` AS `role`, `e`.`email` AS `email`, `e`.`phone_number` AS `phone_number` FROM `employee` AS `e` ;

-- --------------------------------------------------------

--
-- Structure for view `job_view`
--
DROP TABLE IF EXISTS `job_view`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `job_view`  AS SELECT `j`.`JobRoleID` AS `JobRoleID`, `j`.`role_name` AS `role_name`, `j`.`role_description` AS `role_description`, `j`.`role_shift` AS `role_shift` FROM `job_role` AS `j` ;

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
  ADD KEY `JobRoleID` (`JobRoleID`) USING BTREE;

--
-- Indexes for table `inventory`
--
ALTER TABLE `inventory`
  ADD PRIMARY KEY (`InventoryID`);

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
-- Indexes for table `supplier`
--
ALTER TABLE `supplier`
  ADD PRIMARY KEY (`supplierID`);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `employee`
--
ALTER TABLE `employee`
  ADD CONSTRAINT `fk_role_id` FOREIGN KEY (`JobRoleID`) REFERENCES `job_role` (`JobRoleID`) ON DELETE SET NULL ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
