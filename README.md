Java Projects CRUD Application: 

This is a Java program that runs in the console. It shows you how to do the four main database actions: Create, Read, Update, and Delete (CRUD). You can use it to keep track of DIY projects, including their materials, steps, and what type of project they are. It's a simple example of how a Java program can talk to a database.



Features:

Create a Project: The program asks you for project details like its name, how long it might take, and some notes. Then, it saves this information as a new project in the database.

List Projects: This option gets all the projects from the database and shows them as a simple list with an ID number and a name. This helps you see all the projects you have.

Select a Project: You can pick a project from the list by typing its ID number. The program will then show you everything about that project, including all of its materials, steps, and categories.

Update Project Details: If you want to change something about a project you've selected, this option lets you do it. It shows you the current information and lets you type in new details to update it.

Delete a Project: You can delete a project by typing its ID. When you delete a project, the database also automatically deletes all the materials and steps that belonged to it. This shows how ON DELETE CASCADE works in SQL.


Technical Details

Language: The program is written in Java. It uses the standard Java tools and a special tool called JDBC to connect to the database.

Database: The program stores its data in a MySQL database. The data is organized into different tables that are linked together.

Build Tool: Maven is a tool that helps build the project and download the code it needs, like the special driver that lets Java talk to MySQL.

Architecture: The code is organized into three main layers. This makes the code easier to understand and change.

Presentation Layer (ProjectsApp.java): This is the part you see and interact with. It shows the menu and gets your typed commands.

Service Layer (ProjectService.java): This layer does the thinking. It takes requests from the user interface and tells the data layer what to do.

Data Access Layer (ProjectDao.java): This layer talks directly to the database. It holds all the SQL commands for creating, reading, updating, and deleting data.


Setup

Make sure MySQL is running: Your MySQL database server must be turned on before you can run the program.

Create the database user: You need to create a special user in MySQL for the program to use. The username and password should both be 'projects'.

Run the SQL script: Use a program like MySQL Workbench to run the projects_schema.sql file. This file will create all the tables and add some example data for you.

Run the application: To start the program, just run the main method in the ProjectsApp.java file from your code editor (like Eclipse).
