# Deployment and Execution Guide
## Extension Install

Install the following extensions before executing the project:
+  Extension Pack for Java
+  JSP Language Support
+  Maven for Java
+  Community Server Connector

## Environment Setup
- Install Java JDK
- Setup environment path for Java
- Download Maven
- Setup environment path for Maven
- Reference:

[How to set JAVA_HOME environment variable on Windows 10](https://www.codejava.net/java-core/how-to-set-java-home-environment-variable-on-windows-10)
https://www.qamadness.com/knowledge-base/how-to-install-maven-and-configure-environment-variables/

## How to Deploy
1. In the “SERVERS” panel to the left, click “Create New Server”, click “yes”, and select “Apache Tomcat-11.0.0” to download server.
2. Open terminal on the project root folder (that contain /src folder)
3. Run ```mvn clean compile package``` on terminal
4. Open “/database/queries/create_tables.sql”, then right-click and choose “Run Queries” to initialize the database
5. Open “/database/queries/sample.sql”, then right-click and choose “Run Queries” to populate the database with sample objects.
6. Open “src/main/java/model/dao/DB.java”, update the variable URL to be “jdbc:sqlite:” + the absolute path of the “database/iotbay.db”. Change “\” to “/” if you are using Windows.
7. Right click on the “/target/IoTBay” folder in the Explorer, select “Run on Server”, and select the “Apache Tomcat” server you’ve installed. [Note: deploy your “/target/IoTBay” folder, not individual files]
8. Right click the tomcat server in the “SERVERS” panel and click “Start Server”, then right click again and click “Server Actions”, then “Show in Browser”, and then select “IoTBay”.
9. You should see a page pop up in your default browser with the “index.jsp” content.