# Facebook Clone Project

This project is a clone of Facebook with functionalities such as creating posts, stories, sending messages in real-time using WebSockets, product checkout, and profile data management. The front-end is built using Angular and the back-end uses Java with Quarkus.

## Table of Contents

- [Introduction](#introduction)
- [Features](#features)
- [Technologies Used](#technologies-used)
- [Setup](#setup)
- [Usage](#usage)
- [Front-End Setup](#front-end-setup)
- [Back-End Setup](#back-end-setup)
- [API Endpoints](#api-endpoints)
- [Database Schema](#database-schema)
- [Contributing](#contributing)
- [License](#license)

## Introduction

This repository contains the implementation of a Facebook clone application. It provides functionalities for managing posts, stories, real-time messaging, product checkouts, and user profiles.

## Features

- Create and view posts
- Create and view stories
- Send and receive messages in real-time using WebSockets
- Product checkout process
- Profile data management

## Technologies Used

### Front-End

- Angular
- Angular Material
- CoreUI
- Ngx-Webstorage
- RxJS
- WebSockets

### Back-End

- Java
- Quarkus
- Hibernate ORM
- RESTEasy Reactive
- JSON Web Tokens (JWT)
- MySQL
- WebSockets
- Scheduler

## Setup

### Prerequisites

- Node.js and npm
- Java 17
- MySQL
- Maven

## Usage

### Front-End Setup

1. **Clone the repository:**

   ```bash
   git clone <repository-url>
   cd <repository-name>/frontend
   ```

2. **Install dependencies:**

   ```bash
   npm install
   ```

3. **Start the Angular development server:**

   ```bash
   npm start
   ```

   The Angular app will be available at `http://localhost:4200`.

### Back-End Setup

1. **Clone the repository:**

   ```bash
   git clone <repository-url>
   cd <repository-name>/backend
   ```

2. **Set up MySQL database:**

   Create a database named `facebook_clone_db` and update the connection details in `src/main/resources/application.properties`.

3. **Build and run the Quarkus application:**

   ```bash
   ./mvnw compile quarkus:dev
   ```

   The Quarkus application will be available at `http://localhost:8080`.

## API Endpoints

Below are some of the main API endpoints:

- **POST /api/auth/login**: User login
- **POST /api/auth/register**: User registration
- **GET /api/posts**: Fetch all posts
- **POST /api/posts**: Create a new post
- **GET /api/stories**: Fetch all stories
- **POST /api/stories**: Create a new story
- **GET /api/messages**: Fetch all messages
- **POST /api/messages**: Send a new message
- **GET /api/profile**: Fetch user profile data
- **PUT /api/profile**: Update user profile data
- **POST /api/checkout**: Checkout products

## Database Schema

The database schema includes tables for `users`, `posts`, `stories`, `messages`, `products`, and more. For detailed schema, refer to the entity classes in the `backend/src/main/java/org/acme/model` directory.

## Contributing

Contributions are welcome. For major changes, please open an issue first to discuss what you would like to change.

## License

MIT