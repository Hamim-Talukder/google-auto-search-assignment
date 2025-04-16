📌 Project 1: Google Search Suggestion Analyzer
Overview:
An automation tool designed to fetch and analyze Google search suggestions for a given list of keywords. It identifies the longest and shortest auto-suggestions and updates the results in an Excel file.

Technologies Used:
1.	Java
2.	Selenium WebDriver
3.	ChromeDriver
4.	Apache POI
5.	Microsoft Excel
Key Features:
1.	Reads keywords from an Excel sheet based on the current day (e.g., “Monday”).
2.	Automates Google search and waits for suggestion dropdown.
3.	Extracts all suggestion texts and determines the longest and shortest.
4.	Writes the results back into the same Excel file.
5.	Ensures a clean setup and teardown of WebDriver.


📌 Project 2: Excel Username & Password Permutation Generator
Overview:
A console-based utility to generate unique permutations of usernames and passwords, storing them in a structured Excel file for later use (e.g., testing login functionality).

Technologies Used:
1.	Java
2.	Apache POI
3.	Scanner (Java I/O)
4.	Microsoft Excel
Key Features:
1.	Accepts user input for base username/password and desired number of combinations.
2.	Generates unique permutations using recursion.
3.	Appends "@gmail.com" to usernames.
4.	Creates an Excel file (email_passwords.xlsx) with two columns: Email and Password.
5.	Ensures necessary directories (like Resources/) are created for output.


📌 Project 3: Automated Login Testing for Student Portal
Overview:
An automation script to test login functionality of a student web portal using multiple sets of credentials.

Technologies Used:
1.	Java
2.	Selenium WebDriver
3.	ChromeDriver
Key Features:
1.	Stores multiple username-password pairs in arrays.
2.	Automates login attempts on a student portal.
3.	Validates login success based on the redirection URL (e.g., contains "dashboard" or "home").
4.	Logs success or failure for each attempt.
5.	Reinitializes the browser for each test to ensure clean session handling.

