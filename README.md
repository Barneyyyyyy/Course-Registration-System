# Course-Registration-System

Project Overview
The Course Registration System (CRS) is a Java-based application developed to facilitate easy and efficient course registration and management for a university setting. This system is designed to cater to two main user types: Admins and Students, providing each with tailored functionalities to suit their specific needs.

Features:

Admin Features -
Course Management: Create, delete, edit, and display course information.
Student Registration: Register students and assign them to courses.
Reports: Generate and view reports on courses, including full courses, and list students registered in specific courses.
Data Persistence: Utilize serialization to save and load system state, ensuring data persistence across sessions.

Student Features -
Course Registration: View available courses and register for courses.
Course Management: Withdraw from courses and view currently registered courses.
View Course Availability: Check which courses are full and view only those with available slots.

Technical Overview
System Design
The CRS is built following solid object-oriented programming (OOP) principles. It comprises several key components:

User Class: The base class for Admin and Student classes, encapsulating common attributes like username, password, etc.
Interfaces: AdminInterface and StudentInterface define the operations available to admins and students, respectively.
Course Class: Represents course details and includes attributes such as course name, ID, maximum students, etc.
Serialization: For storing and retrieving course and student data, ensuring system state is maintained.
