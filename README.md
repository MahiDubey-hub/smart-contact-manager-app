Smart Contact Manager – Spring Boot Project

Project Overview

Smart Contact Manager is a Spring Boot–based web application built to simplify the way users store and manage their contacts.
The focus of this project was not just functionality, but also writing clean, production-ready backend code.

The application is Dockerized to ensure consistency across environments and is deployed on Railway.
It uses MySQL as the database, with configurations handled through environment variables.
To keep development and production setups clean and separate, Spring Profiles are used for each environment.

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
