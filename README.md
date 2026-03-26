# 🛒 ElectroTienda - Microservices E-Commerce Platform

## 📝 Overview

**ElectroTienda** is a microservices-based e-commerce platform for home appliances, built using **Spring Boot, Spring Cloud, and Event-Driven Architecture**.

The system is composed of independent services, each responsible for a specific business domain, ensuring:

* Scalability
* Fault tolerance
* Independent deployment
* Clean architecture

---

## 🏗️ Architecture

### 🔹 Core Microservices

* 📦 **Product-Service** → Manages product catalog
* 🛒 **Cart-Service** → Handles shopping cart logic
* 💳 **Sales-Service** → Orchestrates checkout process
* 📊 **Inventory-Service** → Manages stock validation & updates
* 📧 **Notification-Service** → Sends email notifications asynchronously

---

### 🔹 Infrastructure Services

* 🚪 **API Gateway** → Single entry point
* 🧭 **Eureka Server** → Service discovery
* ⚙️ **Config Server** → Centralized configuration

---

## 🔄 Communication

### Synchronous

* OpenFeign (REST communication between services)

### Asynchronous

* Apache Kafka (event-driven communication)

---

## 🧠 Resilience

* Resilience4j (Circuit Breaker pattern)
* Prevents cascading failures between services

---

## 📘 API Documentation (Swagger)

All microservices are documented using **Springdoc OpenAPI (Swagger)**.

### 🔎 Swagger per Service

* Product → http://localhost:8081/swagger-ui.html
* Cart → http://localhost:8082/swagger-ui.html
* Inventory → http://localhost:8083/swagger-ui.html
* Sales → http://localhost:8084/swagger-ui.html

---

## 🌐 Swagger Aggregation (API Gateway)

The API Gateway exposes a **unified Swagger UI**:

👉 http://localhost:8080/swagger-ui.html

This UI aggregates all microservices:

* Product API
* Cart API
* Inventory API
* Sales API

---

## ⚙️ How Swagger Aggregation Works

Each microservice exposes:

/v3/api-docs

The Gateway collects them:

* /v3/api-docs/products
* /v3/api-docs/cart
* /v3/api-docs/inventory
* /v3/api-docs/sales

---

## 🛠️ Running the Project

### ✅ Prerequisites

* Java 21
* Maven
* Docker Desktop

---

### 🐳 Infrastructure (Docker)

Currently, Docker is used **only for Kafka and Zookeeper**:

```bash
docker-compose up -d
```

This will start:

* Kafka
* Zookeeper

---

### ⚠️ Important Note

Microservices are **NOT dockerized yet**.

You must run them manually:

```bash
# Example
mvn spring-boot:run
```

Run each service:

* Eureka Server
* Config Server
* API Gateway
* Product Service
* Cart Service
* Inventory Service
* Sales Service
* Notification Service

---

### 🚀 Access the System

Once everything is running:

👉 API Gateway
http://localhost:8080

---

## 🔧 Main Workflows

### 🟢 Product Creation

* Product-Service publishes event to Kafka
* Inventory-Service initializes stock

---

### 🟡 Add to Cart

* Cart-Service calls Inventory-Service (Feign)
* Circuit Breaker protects the call

---

### 🔵 Checkout

* Sales-Service publishes "Sale Created" event

Then:

* Inventory-Service updates stock
* Notification-Service sends email

---

## 🧰 Tech Stack

* Java 21
* Spring Boot 3
* Spring Cloud
* OpenFeign
* Resilience4j
* Apache Kafka
* MySQL
* Docker (Kafka + Zookeeper)
* Swagger / OpenAPI

---

## 🎯 Key Features

* Microservices architecture
* Event-driven design
* API Gateway routing
* Service discovery (Eureka)
* Centralized configuration
* Fault tolerance
* API documentation

---

## 🚧 Future Improvements

* Dockerize all microservices
* Add an User interface with Angular

---
