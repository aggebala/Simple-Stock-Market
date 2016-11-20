## Synopsis

This is a basic project that is aimed to be a solution to a test.

Some points to be made:  

* It consists of only one package since the project is small  

* It uses BigDecimal class for many numbers in order to achieve highest precision  

* It uses synchronized data structures to be thread safe (although it does not use threads)  

* We assume that the trades come ordered by time  

* It does not use any framework or any external library (except JUnit)  

* Exception handling was kept minimal and handles at the top lvl of the "app"

## Installation

In order to install this project you can  run 
`mvn package` to create the jar needed in the target directory and then run 
`java -jar <name of jar file>` to execute the application.  
You can also run `mvn integration-test` or `mvn test` for packaging and doing the all the tests or 
just doing the unit tests.