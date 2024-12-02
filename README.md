<div align="center">
	<h1>CoffeeClasses <i>(Vanilla)</i></h1>
</div>

*CoffeeClasses* is a web application designed for academic management, catering to the needs of students, teachers, and administrators. The application supports essential functionalities such as managing students, courses, enrollments, and grades while ensuring secure role-based access.

Built with Jakarta EE, this version of CoffeeClasses implements a robust MVC architecture, utilizing Hibernate for database interactions and JSP for user interfaces.

CoffeeClasses requires Java **21** or later.

*Click [here](https://github.com/CoffeeClasses/CoffeeClasses-Spring) for the Spring Boot version of CoffeeClasses.*

## How to build

### Instructions

First, clone the project with `git` :

```bash
git clone https://github.com/CoffeeClasses/CoffeeClasses-Vanilla
```

Then simply run this command, which uses the bundled *maven* wrapper (or you can use your existing `mvn` installation instead) :

> [!WARNING]
>
> `mvnw.cmd` currently has a Windows-specific issue making it unusable with users that have spaces in their username. **This includes the default account on CY Tech computers "CYTech Student"**.
>
> Your options are :
> - Use a different account without spaces in the username.
> - Use a proper `mvn` installation (installed with [Chocolatey](https://chocolatey.org/) for example).
> - Use a Linux-based system (like Ubuntu or even WSL).

> [!NOTE]
> In some situations, it may be necessary to run `mvn clean` to avoid dependency issues before running the `package` command.


| Linux | Windows |
| --- | --- | 
| ```./mvnw package``` | ```mvnw.cmd package``` |

This command will package the application into a `.war` file, which you can easily run at any time through a Tomcat server.

## How to run

*(Note : running the application also requires Java 21 or later. The application has been successfully tested on GraalVM and OpenJDK.)*

### Database setup

CoffeeClasses uses a PostgreSQL database, only supporting localhost + default port configurations. You can set up the necessary database with the following SQL commands :

```sql
CREATE USER coffeeclasses WITH PASSWORD 'coffeeclasses';
DROP DATABASE IF EXISTS coffeeclassesdb;
CREATE DATABASE coffeeclassesdb OWNER coffeeclasses;
```

### Running the application

You can either deploy the built `.war` file to a Tomcat server or run the application directly with the following command :

> [!NOTE]
> In some situations, it may be necessary to run `mvn clean` to avoid dependency issues before running the `package` command.

| Linux | Windows |
| --- | --- | 
| ```./mvnw package cargo:run``` | ```mvnw.cmd package cargo:run``` |

*Note : Default admin user is `admin-coffeeclasses@yopmail.com` with password `admin123`.*