# Spring Boot Microservices Implementation

## 📌 Overview

This project demonstrates a **basic microservices architecture** implemented using **Java Spring Boot**. The goal of this project is to showcase how independent services communicate, scale, and interact using both **synchronous (REST)** and **asynchronous (Kafka)** communication patterns.
It is designed as a **learning-oriented implementation** to understand core microservices concepts such as API Gateway, service communication, resilience, and distributed systems fundamentals.

---

## 🏗️ Architecture

The system consists of multiple loosely coupled services connected through an **API Gateway**.

### Key Components:

* **API Gateway** → Entry point for all client requests
* **Auth Server** → Handles authentication & security
* **Product Service** → Manages product-related data (MongoDB)
* **Order Service** → Core service handling orders (MySQL)
* **Inventory Service** → Checks product availability (MySQL)
* **Notification Service** → Sends notifications (Kafka consumer)

---

## 🔄 Communication Patterns

### 1. Synchronous Communication (REST)

* Used between:

  * Order Service → Inventory Service
* Ensures real-time validation (e.g., stock availability)

### 2. Asynchronous Communication (Event-driven)

* Implemented using **Kafka**
* Used between:

  * Order Service → Notification Service
* Improves decoupling and scalability

---

## ⚙️ Tech Stack

* Backend: Java, Spring Boot
* Microservices Framework:Spring Cloud
* API Gateway: Spring Cloud Gateway
* Service Discovery: Eureka
* Database: MongoDB (Product Service)
            MySQL (Order & Inventory Services)
* Messaging: Apache Kafka
* Resilience: Resilience4j (Circuit Breaker)
* Security: OAuth / Auth Server
* Configuration: Spring Cloud Config Server
* Monitoring & Logging: Zipkin (Distributed Tracing)
                        ELK Stack (Elasticsearch, Logstash, Kibana)

---

## 🔐 Key Features

* API Gateway routing and centralized access
* Service-to-service communication
* Circuit Breaker implementation (Resilience4j)
* Event-driven architecture using Kafka
* Distributed tracing with Zipkin
* Centralized configuration management
* Basic authentication setup
* Independent databases per service

---

## 📂 Project Structure

```
api-gateway/
auth-server/
product-service/
order-service/
inventory-service/
notification-service/
config-server/
discovery-server/ (Eureka)
```

---

## ▶️ How to Run the Project

### 1. Clone Repository

```
git clone <your-repo-url>
cd <project-folder>
```

### 2. Build Services

```
mvn clean install
```

### 3. Start Infrastructure (Docker recommended)

```
docker-compose up -d
```

### 4. Start Services (order matters)

1. Config Server
2. Eureka Server
3. API Gateway
4. Other microservices

---

## 🔌 Example Flow

1. Client sends request → **API Gateway**
2. Request routed to **Order Service**
3. Order Service:

   * Calls **Inventory Service** (sync check)
   * Publishes event to **Kafka**
4. **Notification Service** consumes event and sends notification

---

## 🎯 Purpose of This Project

This is a **basic implementation project** intended to:

* Understand microservices architecture
* Learn service communication patterns
* Explore Spring Cloud ecosystem
* Demonstrate distributed system design fundamentals

---

## ⚠️ Note

This project is **not production-ready**. It is designed for:

* Learning purposes
* Concept demonstration
* Interview/project showcase

---

## 📎 Future Improvements

* Add centralized logging dashboard
* Implement API rate limiting
* Improve security (JWT-based auth)
* Add CI/CD pipeline
* Container orchestration (Kubernetes)

---

## 👨‍💻 Author

Apoorv Pol

---

