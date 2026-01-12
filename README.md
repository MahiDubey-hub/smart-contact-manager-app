Smart Contact Manager – Spring Boot Project

Project Overview

Smart Contact Manager is a Spring Boot web application built to simplify the way contacts are stored and managed.
Along with core contact features, it includes searching, sorting, and pagination, so the application remains smooth and responsive even when handling a large number of contacts.

The project also implements basic Spring Security to handle user authentication and ensure that only authorized users can access protected parts of the application.

From a backend development perspective, the application follows modern engineering practices. It is Dockerized to maintain consistency across different environments and is deployed on Railway.
The application uses MySQL as its database, with all sensitive configuration handled through environment variables.
To keep development and production setups clean and separate, Spring Profiles are used for environment-specific configuration.

Tech Stack

Backend: Java, Spring Boot

Database: MySQL

ORM: Spring Data JPA (Hibernate)

Build Tool: Maven

Containerization: Docker (Multi-stage build)

Deployment Platform: Railway

Version Control: Git & GitHub

Key Learnings

Created multi-stage Docker builds to reduce image size and improve build efficiency

Used Spring Profiles (dev and prod) to manage environment-specific configurations

Configured environment variables in production to keep sensitive data secure

Deployed a Spring Boot application on Railway and integrated it with a Railway-hosted MySQL database

Gained hands-on experience debugging real production issues, including:

  - JDBC URL and database connectivity problems
  
  - Hibernate and JPA initialization errors
  
  - Environment variable and configuration resolution issues

Deployed Project :

<img width="1898" height="1055" alt="Screenshot 2026-01-12 144013" src="https://github.com/user-attachments/assets/c2532492-4559-4f1e-b2c0-8103bd4801e8" />
<img width="1803" height="966" alt="Screenshot 2026-01-12 150621" src="https://github.com/user-attachments/assets/7fc68f6a-3b0e-4453-aabb-02f2ced5bc89" />



