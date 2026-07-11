-- ==========================================
-- Estimate Management System - SQL Schema
-- Module 4 of Code-B IMS
-- ==========================================

-- Create database if not exists
CREATE DATABASE IF NOT EXISTS estimate_management;
USE estimate_management;

-- Drop table if exists (for fresh setup)
DROP TABLE IF EXISTS estimates;

-- Create estimates table
CREATE TABLE estimates (
    estimated_id INT PRIMARY KEY AUTO_INCREMENT,
    chain_id INT NOT NULL,
    group_name VARCHAR(50),
    brand_name VARCHAR(50),
    zone_name VARCHAR(50),
    service VARCHAR(100),
    qty INT,
    cost_per_unit FLOAT,
    total_cost FLOAT,
    delivery_date DATE,
    delivery_details VARCHAR(100),
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_chain_id (chain_id),
    INDEX idx_group_name (group_name),
    INDEX idx_brand_name (brand_name),
    INDEX idx_delivery_date (delivery_date)
);

-- ==========================================
-- Sample Data for Testing
-- ==========================================

INSERT INTO estimates (chain_id, group_name, brand_name, zone_name, service, qty, cost_per_unit, total_cost, delivery_date, delivery_details) VALUES
(1, 'Retail Group A', 'Fashion Brand X', 'Colombo Central', 'Website Development', 1, 2500.00, 2500.00, '2024-02-15', 'Main office delivery'),
(1, 'Retail Group A', 'Fashion Brand X', 'Colombo Central', 'SEO Services', 3, 500.00, 1500.00, '2024-02-20', 'Monthly retainer'),
(2, 'Tech Corp', 'Gadget Pro', 'Kandy Zone', 'Mobile App Development', 1, 5000.00, 5000.00, '2024-03-01', 'Beta testing phase'),
(3, 'Food Chain Inc', 'Burger King', 'Galle District', 'POS System Installation', 5, 300.00, 1500.00, '2024-02-10', '5 branch rollout'),
(4, 'Fashion Hub', 'Style Street', 'Jaffna Area', 'Marketing Campaign', 1, 1200.00, 1200.00, '2024-02-25', 'Social media + ads'),
(5, 'Auto Dealers', 'Motor World', 'Negombo', 'Inventory Management System', 1, 1800.00, 1800.00, '2024-03-15', 'Training included');

-- ==========================================
-- Views for Reporting
-- ==========================================

-- View: Monthly estimate summary
CREATE OR REPLACE VIEW v_monthly_estimates AS
SELECT
    DATE_FORMAT(created_at, '%Y-%m') AS month,
    COUNT(*) AS total_estimates,
    SUM(total_cost) AS total_amount,
    AVG(total_cost) AS avg_amount
FROM estimates
GROUP BY DATE_FORMAT(created_at, '%Y-%m')
ORDER BY month DESC;

-- View: Estimates by chain
CREATE OR REPLACE VIEW v_estimates_by_chain AS
SELECT
    chain_id,
    COUNT(*) AS estimate_count,
    SUM(total_cost) AS total_amount
FROM estimates
GROUP BY chain_id
ORDER BY total_amount DESC;

-- View: Pending deliveries
CREATE OR REPLACE VIEW v_pending_deliveries AS
SELECT
    estimated_id,
    chain_id,
    service,
    delivery_date,
    delivery_details
FROM estimates
WHERE delivery_date >= CURDATE()
ORDER BY delivery_date ASC;

-- ==========================================
-- Queries for Dashboard
-- ==========================================

-- Total estimates count
SELECT COUNT(*) FROM estimates;

-- Total estimated amount
SELECT COALESCE(SUM(total_cost), 0) FROM estimates;

-- Estimates this month
SELECT COUNT(*) FROM estimates
WHERE MONTH(created_at) = MONTH(CURDATE())
AND YEAR(created_at) = YEAR(CURDATE());

-- Unique chains count
SELECT COUNT(DISTINCT chain_id) FROM estimates;
