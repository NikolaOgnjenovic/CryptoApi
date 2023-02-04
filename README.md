# Crypto APi

[![Build Status](https://travis-ci.org/codecentric/springboot-sample-app.svg?branch=master)](https://travis-ci.org/codecentric/springboot-sample-app)
[![License: GPL v3](https://img.shields.io/badge/License-GPLv3-blue.svg)](https://www.gnu.org/licenses/gpl-3.0)

Software which processes buy and sell orders for a cryptocurrency exchange marketplace.

This software was made using [Spring Boot](http://projects.spring.io/spring-boot/) for the Levi9 hackathon challenge.

It follows the REST architecture, contains tests and CRUD operations extracted from the `Challenge.pdf` file. 

## Requirements

For building and running the software you need:

- [JDK 1.8+](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- [Maven 3.5+](https://maven.apache.org)

## Running the application locally

There are several ways to run a Spring Boot application on your local machine.

One way is to execute the `main` method in the `com.mrmi.cryptoapi.CryptoApiApplication` class from your IDE.

Alternatively you can use the [Spring Boot Maven plugin](https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins-maven-plugin.html) like so:

```shell
mvn spring-boot:run
```

## Copyright

Released under the GNU General Public License v3.0. See `license.txt`.