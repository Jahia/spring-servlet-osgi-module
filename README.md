spring-servlet-osgi-module
==========================

A sample Jahia module that registers an OSGi servlet using Spring DM

Requirements
------------
- Maven 3.0.x
- Jahia Digital Factory 7.0+

Compiling
---------

    mvn clean install
    
Deploying
---------

Copy the generated JAR from target/spring-servlet-osgi-module-*.jar to Jahia Digital Factory's var/modules directory.

Testing
-------

Access the following URL : 

    http://localhost:8080/modules/org.jahia.modules.samples.servlet.spring/test?test
    
You should get a result that looks like this : 

    Context path=/modules
    Servlet path=/org.jahia.modules.samples.servlet.spring
    Path info=/test
    Query string=test
    