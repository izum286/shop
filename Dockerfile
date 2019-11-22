FROM tomcat:8
MAINTAINER izum
COPY target/shop.war /usr/local/tomcat/webapps/shop.war
EXPOSE 8080
ENV spring.profiles.active=docker
CMD ["catalina.sh", "run"]