# EJB Bank Application

## Description

The **EJB Bank Application** is a Java-based Enterprise JavaBeans (EJB) web application designed to manage bank accounts, clients, and transactions. It offers functionalities such as account creation, deposits, withdrawals, and money transfers, ensuring secure and efficient banking operations.

## Table of Contents
- [ScreenShots](#ScreenShots)
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Configuration](#configuration)
- [Building the Project](#building-the-project)
- [Running the Application](#running-the-application)
- [Technologies Used](#technologies-used)
- [Contributing](#contributing)
- [License](#license)

 ## ScreenShots

![bank](https://github.com/user-attachments/assets/2d26a179-f187-469c-a817-a31c41e35ff2)
 ![bank_dashboard](https://github.com/user-attachments/assets/09c6cd2e-6f45-4011-8895-13dcfb060109)
  ![bank_transaction](https://github.com/user-attachments/assets/5799d168-13b8-4aba-9f77-de0b5383a359)




## Prerequisites


Before setting up the project, ensure you have the following installed on your machine:

- **Java Development Kit (JDK) 21**
- **Maven 3.8+**
- **WildFly Server 26+**
- **MySQL Database**
- **Git**

## Installation

1. **Clone the Repository**

   ```bash
   git clone <REMOTE_URL>
   cd ejb-bank-app
   ```

2. **Set Up the Database**

   Create a MySQL database and user for the application. Update the database configuration in the `src/main/resources/META-INF/persistence.xml` file with your database details.

3. **Configure WildFly**

   Add a new datasource in the WildFly server configuration for the MySQL database. Ensure the JDBC driver for MySQL is available in the WildFly server.

4. **Build the Project**

   Use Maven to build the project:

   ```bash
   mvn clean install
   ```

5. **Deploy the Application**

   Deploy the generated WAR file to the WildFly server. You can do this by copying the WAR file to the WildFly deployment directory or using the WildFly management console.

6. **Start the WildFly Server**

   Start the WildFly server if it is not already running:

   ```bash
   standalone.sh (or standalone.bat for Windows)
   ```

7. **Access the Application**

   Open a web browser and navigate to `http://localhost:8080/ejb-bank-app` to access the application.

## Configuration

Update the configuration files as needed to match your environment. This includes database settings, server configurations, and any other application-specific settings.

## Building the Project

To build the project, use the following Maven command:

```bash
mvn clean install
```

## Running the Application

To run the application, deploy the WAR file to the WildFly server and start the server. Access the application via the web browser at `http://localhost:8080/ejb-bank-app`.



## Technologies Used

- **Java**
- **Enterprise JavaBeans (EJB)**
- **Maven**
- **WildFly**
- **MySQL**

## Contributing

Contributions are welcome! Please fork the repository and submit a pull request with your changes. Ensure that your code follows the project's coding standards and includes appropriate tests.

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for more details.
