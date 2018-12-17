Intuitive and powerful grading system for professors at BU
You will need the CredentialConstatnts file to access the db

GradeSafe
Group one 12/17/2018
  
Our code is built on jdk 9 
Dependencies Required:
MySQL 8.0.13.
jfoeniz-9.0.8.

Step1:
	Unzip the file.
Step2:
	Add mysql connector to the project. If your are using Intellij IDEA. File->project structure->Modules
	->Press "Alt+Insert"->choose "JARs or directories"->choose "mysql-connector-java-8.0.13.jar"

Step3:
	Change dbPass to your MySQL password in CredentialConstants.java .
Step4:
	Run the Login.java
Step5:
	The string you input first time will be your password.