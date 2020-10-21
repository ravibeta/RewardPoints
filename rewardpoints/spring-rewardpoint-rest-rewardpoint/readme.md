# Spring RewardPoint Sample Application [![Build Status](https://travis-ci.org/spring-projects/spring-rewardpoint.png?branch=master)](https://travis-ci.org/spring-projects/spring-rewardpoint/)

## Running rewardpoint locally
```
	git clone https://github.com/ravibeta/RewardPoints
	cd rewardpoints/spring-rewardpoint-rest-rewardpoint/
	./mvnw spring-boot:run
```

You can then access rewardpoint here: http://localhost:8080/

## In case you find a bug/suggested improvement for Spring RewardPoint
Our issue tracker is available here: https://github.com/ravibeta/RewardPoints/rewardpoints/spring-rewardpoint-rest-rewardpoint/issues


## Database configuration

In its default configuration, RewardPoint uses an in-memory database (HSQLDB) which
gets populated at startup with data. A similar setup is provided for MySql in case a persistent database configuration is needed.
Note that whenever the database type is changed, the data-access.properties file needs to be updated and the mysql-connector-java artifact from the pom.xml needs to be uncommented.

You could start a MySql database with docker:

```
docker run -e MYSQL_ROOT_PASSWORD=rewardpoint -e MYSQL_DATABASE=rewardpoint -p 3306:3306 mysql:5.7.8
```

## Working with RewardPoint in Eclipse/STS

### prerequisites
The following items should be installed in your system:
* Maven 3 (http://www.sonatype.com/books/mvnref-book/reference/installation.html)
* git command line tool (https://help.github.com/articles/set-up-git)
* Eclipse with the m2e plugin (m2e is installed by default when using the STS (http://www.springsource.org/sts) distribution of Eclipse)

Note: when m2e is available, there is an m2 icon in Help -> About dialog.
If m2e is not there, just follow the install process here: http://eclipse.org/m2e/download/


### Steps:

1) In the command line
```
git clone https://github.com/ravibeta/RewardPoints
```
2) Inside Eclipse
```
File -> Import -> Maven -> Existing Maven project
```


## Looking for something in particular?

|Spring Boot Configuration | Class or Java property files  |
|--------------------------|---|
|The Main Class | [RewardPointApplication](https://github.com/spring-projects/spring-rewardpoint/blob/master/src/main/java/org/springframework/samples/rewardpoint/RewardPointApplication.java) |
|Properties Files | [application.properties](https://github.com/spring-projects/spring-rewardpoint/blob/master/src/main/resources) |
|Caching | [CacheConfig](https://github.com/spring-projects/spring-rewardpoint/blob/master/src/main/java/org/springframework/samples/rewardpoint/system/CacheConfig.java) |

## Interesting Spring RewardPoint branches and forks

The Spring RewardPoint master branch in the main
[spring-projects](https://github.com/spring-projects/spring-rewardpoint)
GitHub org is the "canonical" implementation, currently based on
Spring Boot and Thymeleaf. There are quite a few forks in a special
GitHub org [spring-rewardpoint](https://github.com/spring-rewardpoint). If
you have a special interest in a different technology stack that could
be used to implement the Reward Point then please join the community
there.

| Link | Main technologies |
|----------------|-------------------|
| [spring-framework-rewardpoint](https://github.com/spring-rewardpoint/spring-framework-rewardpoint) | Spring Framework XML configuration, JSP pages, 3 persistence layers: JDBC, JPA and Spring Data JPA |
| [javaconfig branch](https://github.com/spring-rewardpoint/spring-framework-rewardpoint/tree/javaconfig) | Same frameworks as the [spring-framework-rewardpoint](https://github.com/spring-rewardpoint/spring-framework-rewardpoint) but with Java Configuration instead of XML |
| [spring-rewardpoint-angular](https://github.com/spring-rewardpoint/spring-rewardpoint-angularjs) | AngularJS 1.x, Spring Boot and Spring Data JPA |
| [spring-rewardpoint-microservices](https://github.com/spring-rewardpoint/spring-rewardpoint-microservices) | Distributed version of Spring RewardPoint built with Spring Cloud |
| [spring-rewardpoint-reactjs](https://github.com/spring-rewardpoint/spring-rewardpoint-reactjs) | ReactJS (with TypeScript) and Spring Boot |


## Interaction with other open source projects

One of the best parts about working on the Spring RewardPoint application is that we have the opportunity to work in direct contact with many Open Source projects. We found some bugs/suggested improvements on various topics such as Spring, Spring Data, Bean Validation and even Eclipse! In many cases, they've been fixed/implemented in just a few days.
Here is a list of them:

| Name | Issue |
|------|-------|
| Spring JDBC: simplify usage of NamedParameterJdbcTemplate | [SPR-10256](https://jira.springsource.org/browse/SPR-10256) and [SPR-10257](https://jira.springsource.org/browse/SPR-10257) |
| Bean Validation / Hibernate Validator: simplify Maven dependencies and backward compatibility |[HV-790](https://hibernate.atlassian.net/browse/HV-790) and [HV-792](https://hibernate.atlassian.net/browse/HV-792) |
| Spring Data: provide more flexibility when working with JPQL queries | [DATAJPA-292](https://jira.springsource.org/browse/DATAJPA-292) |


# Contributing

The [issue tracker](https://github.com/ravibeta/rewardpoint/spring-rewardpoint-rest-rewardpoint/issues) is the preferred channel for bug reports, features requests and submitting pull requests.

For pull requests, editor preferences are available in the [editor config](.editorconfig) for easy use in common text editors. Read more and download plugins at <http://editorconfig.org>.




