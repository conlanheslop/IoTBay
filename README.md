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
2. Right click on the “webapp” folder in the Explorer, select “Run on Server”, and select the “Apache Tomcat” server you’ve installed. [Note: deploy your webapp folder, not individual files]
3. Right click the tomcat server in the “SERVERS” panel and click “Start Server”, then right click again and click “Server Actions”, then “Show in Browser”, and then select your webapp.
4. You should see a page pop up in your default browser with the “index.jsp” content. 

