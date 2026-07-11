Estimate Management System

## Overview

The Estimate Management System is a web-based application developed for the Code-B Internal Management System (IMS). It enables the Sales Team to efficiently create, manage, and track customer estimates linked to specific clients and their organizational hierarchy, including Groups, Chains, Brands, and Zones.

The system simplifies estimate preparation by allowing users to enter client details, services, quantities, costs, and delivery information. Each estimate is associated with a Chain ID, ensuring seamless integration with future invoice generation and improving financial traceability.

---

## Features

### Dashboard

* View overview statistics and recent estimates
* Display total estimates and estimated revenue
* Quick access to major functionalities

### Create Estimate

* Add client information:

  * Chain ID
  * Group Name
  * Brand Name
  * Zone/Subzone
* Add service details
* Enter quantity and cost per unit
* Specify delivery date and instructions
* Automatic total cost calculation

### Update Estimate

* Modify estimate details whenever client requirements change
* Automatically update modification timestamps

### Delete Estimate

* Remove outdated or incorrect estimates
* Confirmation popup before deletion

### Search and Filter

* Search estimates by:

  * Group Name
  * Brand Name
  * Zone Name
  * Chain ID

### Chain Linking

* Estimates are linked with Chain IDs
* Supports future invoice generation and proper organizational mapping

---

## Technology Stack

* Java 25 (Compatible with Java 17+)
* Spring Boot 3.2.0
* Spring Data JPA
* MySQL 8.0
* Thymeleaf
* Bootstrap 5.3
* Lombok
* Maven

---

## Prerequisites

Before running the project, ensure the following are installed:

* Java 25 (or Java 17+)
* Maven 3.9+
* MySQL Server 8.0+
* IntelliJ IDEA (Recommended)

---

## Project Setup

### 1. Clone the Repository

```bash
git clone https://github.com/your-username/EstimateManagementSystem.git
cd EstimateManagementSystem
```

### 2. Configure Database

Login to MySQL:

```bash
mysql -u root -p
```

Create the database:

```sql
CREATE DATABASE estimate_management;
USE estimate_management;
```

Alternatively, import:

```text
src/main/resources/sql/schema.sql
```

using MySQL Workbench.

---

### 3. Configure Application Properties

Update `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/estimate_management?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=YOUR_PASSWORD

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.thymeleaf.cache=false
```

---

### 4. Build the Project

```bash
mvn clean install
```

---

### 5. Run the Application

```bash
mvn spring-boot:run
```

or run:

```bash
EstimateManagementApplication.java
```

directly from IntelliJ IDEA.

---

## Access the Application

| Module          | URL                                       |
| --------------- | ----------------------------------------- |
| Dashboard       | http://localhost:8080/estimates/dashboard |
| Create Estimate | http://localhost:8080/estimates/create    |
| Estimate List   | http://localhost:8080/estimates/list      |

---

## REST API Endpoints

| Method | Endpoint                       | Description               |
| ------ | ------------------------------ | ------------------------- |
| GET    | /estimates/api/all             | Get all estimates         |
| GET    | /estimates/api/{id}            | Get estimate by ID        |
| GET    | /estimates/api/chain/{chainId} | Get estimates by Chain ID |
| GET    | /estimates/api/search          | Search estimates          |
| GET    | /estimates/api/stats           | Get dashboard statistics  |
| POST   | /estimates/api/create          | Create estimate           |
| PUT    | /estimates/api/{id}            | Update estimate           |
| DELETE | /estimates/api/{id}            | Delete estimate           |

---

## Database Schema

### estimates

| Column           | Type         | Description            |
| ---------------- | ------------ | ---------------------- |
| estimated_id     | INT (PK)     | Unique identifier      |
| chain_id         | INT (FK)     | Links to chain table   |
| group_name       | VARCHAR(50)  | Group name             |
| brand_name       | VARCHAR(50)  | Brand name             |
| zone_name        | VARCHAR(50)  | Zone/Subzone name      |
| service          | VARCHAR(100) | Service description    |
| qty              | INT          | Quantity or units      |
| cost_per_unit    | FLOAT        | Cost per unit          |
| total_cost       | FLOAT        | Total estimated amount |
| delivery_date    | DATE         | Expected delivery date |
| delivery_details | VARCHAR(100) | Delivery instructions  |
| created_at       | DATETIME     | Creation timestamp     |
| updated_at       | DATETIME     | Last update timestamp  |

---

## Project Structure

```text
EstimateManagementSystem
│
├── pom.xml
├── src
│   └── main
│       ├── java/com/internship/estimatemanagement
│       │   ├── EstimateManagementApplication.java
│       │   ├── controller
│       │   │   ├── EstimateController.java
│       │   │   └── GlobalExceptionHandler.java
│       │   ├── dto
│       │   │   ├── DashboardStats.java
│       │   │   ├── EstimateDTO.java
│       │   │   └── EstimateRequest.java
│       │   ├── entity
│       │   │   └── Estimate.java
│       │   ├── repository
│       │   │   └── EstimateRepository.java
│       │   └── service
│       │       └── EstimateService.java
│       └── resources
│           ├── application.properties
│           ├── static/css/styles.css
│           ├── templates
│           │   ├── dashboard.html
│           │   ├── estimate-form.html
│           │   ├── estimate-edit.html
│           │   └── estimate-list.html
│           └── sql/schema.sql
```

---

## Usage Flow

1. Open Dashboard.
2. Create a new estimate by entering client and service information.
3. The system automatically calculates the total cost.
4. Save the estimate.
5. View all estimates in the list page.
6. Search and filter estimates when required.
7. Update estimates if requirements change.
8. Delete outdated estimates after confirmation.

---

## Future Enhancements

* PDF generation for estimates
* Invoice generation from estimates
* Pagination and advanced filtering
* Export to Excel
* Authentication and role-based access control
* Email notifications for delivery schedules
