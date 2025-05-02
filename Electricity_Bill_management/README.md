# Electricity bill management

This is my first project in java for electricity bill management software. which provides comprehensive solution for
maintaining electricity consumers, generate bills, processing payment, and maintaining customer records. The application
uses MYSQL for data storage and provides a user-friendly interface for administrators to manage the entire billing
cycle.

## Features

- **User Management**: Add, modify, and delete consumer accounts
- **Bill Generation**: Automatically calculate bills based on meter readings
- **Payment Processing**: Record and track bill payments
- **Reports**: Generate various reports for analysis
- **Database Integration**: MySQL backend for reliable data storage
- **Secure Authentication**: Role-based access control

## Technical Requirements

- Java 24
- MySQL 8.0 or higher
- JDBC MySQL Connector
- Swing/JavaFX for GUI (optional)

## Database Schema

The system uses the following database structure:

## Installation

1. Clone the repository
2. Import the project into your IntelliJ IDE
3. Set up MySQL database using the provided script
4. Configure database connection in `Database.java`
5. Build and run the application

## Configuration

Edit the `Database.java` file to set up your database connection:

```properties
jdbcUrl="jdbc:mysql://localhost:3306/";
dbName="bill_system";
user="username";
password="password";
```

## Contact

For any queries or support, please contact: [info.pkrajput@gmail.com]
