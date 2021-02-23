FROM openjdk:8-alpine
MAINTAINER Your Name <you@example.com>

ADD target/rest-pedestal-clojure-0.0.1-SNAPSHOT-standalone.jar /rest-pedestal-clojure/app.jar

EXPOSE 8080

CMD ["java", "-jar", "/rest-pedestal-clojure/app.jar"]
