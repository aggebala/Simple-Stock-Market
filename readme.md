## Synopsis

This is a basic project that is aimed to be a solution to a test.

Some points to be made:  

* It was made as simple as it gets just to provide an answer to the test questions.  

* It consists of only one package since the project is small  

* It uses BigDecimal class for many numbers in order to achieve highest precision  

* It uses synchronized data structures to be thread safe (although it does not use threads)  

* We assume that the trades come ordered by time  

* It does not use any framework

## Installation

In order to install this project you can  run 
`mvn package` to create the jar needed in the target directory and then run 
`java -jar <name of jar file>` to execute the application.