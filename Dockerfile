FROM tomcat:8
MAINTAINER izum
COPY shop-myshop/target/shop.war /usr/local/tomcat/webapps/shop.war