# Shopping Mall System

#### Java Project – Layered Architecture (MVC + Service + Repository)

# Project Overview

This project is a desktop-based Shopping Mall system developed in Java.
It simulates a real-world shopping environment where users can log in as 
Customer or Administrator, manage products, handle shopping carts, and  complete purchases.

The main focus of this project is not only functionality, but also clean 
architecture, separation of concerns, and maintainable design.

# Architecture & Design
The project follows a multi-layered architecture:
- MVC (Model – View – Controller)
- Service Layer
- Repository Layer (Data Persistence using JSON)

# Layer Responsibilities

| Layer | Responsibility |
|-------|---------------|
| Model | Represents core domain entities (User, Product, Cart, etc.) |
| View   |  Swing-based graphical user interface |
| Controller  | Handles user events and connects View to Service |
| Service      | Contains business logic and validation rules |
| Repository | Manages data storage and retrieval |

This structure ensures:

- Clear separation of concerns
- Better maintainability
- Easier scalability
- Reduced coupling between layers

# User Roles
Customer

- Browse available products
- Add/remove products to/from cart
- View cart summary
- Complete purchase (if balance is sufficient)
- View account balance

Administrator
- View product list
- Add new products
- Edit existing products
- Remove products
- Sort products by price

# Application Flow

1- User logs in (Customer or Admin)

2- System validates credentials

3- Appropriate dashboard is loaded

4- User performs operations (cart management or product management)

5- Changes are persisted in JSON files

6- Data remains saved after application restart

# Data Persistence

All data is stored using JSON files, including:

- Users

- Products

- Shopping Carts

The persistence layer is separated from business logic through repository interfaces.
This makes it possible to replace JSON with a database in the future without modifying core logic.

# Business Logic Highlights

- Cart total price calculated dynamically
- Product stock validation before adding to cart
- Balance verification before checkout
- Stock reduction after successful purchase
- Secure password handling (hashed storage)
- Standardized operation results using a unified response model

# User Interface

The graphical interface is implemented using Java Swing.

Design goals:

- Simple and clean layout
- Clear feedback messages
- Logical navigation between panels
- Separate UI from business logic

# Project Structure

```text
src/
 ├── model
 ├── view
 ├── controller
 ├── service
 ├── repository
 │     └── json
 └── commons
```

# How to Run

- Install JDK 
- Open the project in an IDE 
- Run the Main class
- Application will create required JSON files automatically

# Key Design Principles Applied

- Encapsulation
- Inheritance
- Polymorphism
- Separation of concerns
- Dependency inversion
- Interface-based design
- Clean layered architecture

# Administrator Accounts
Predefined admin accounts information:

Admin 1  
Username: armina - 
Password: 1357


Admin 2  
Username: rastin - 
Password: 2468

(Passwords are stored in hashed format inside the system.)
















