# Style Upload middleware
- API end point for managing flashair, where flashair shall upload the file and those

## install/build for development usage
- please copy application-dev.yml.sample and rename it to application-dev.yml 
- modify application-dev.yml file
- Then run mvn spring-boot:run -Dspring.profiles.active=dev
 
## production deployment
please fill <br>
DB_CONNECTION<br>
HIBERNATE_SHOW_SQL<br>
HIBERNATE_USE_SQL_COMMENTS<br>
HIBERNATE_FORMAT_SQL<br>
PORT<br>
APPLICATION_LOGGING_LEVEL<br>
SPRING_LOGGING_LEVEL<br>
HIBERNATE_LOGGING_LEVEL<br>
APIURL<br>
ADMINTOKEN<br>

inside the local variable or modify the application-prod.yml for extra configuration not listed above

mvn package -Dspring.profiles.active=prod


