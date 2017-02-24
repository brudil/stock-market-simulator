# Stock Market Simulator
Stock Market Simulator for SWE Group Project.


## Getting started

We are using Gradle, in a simple way. This means without having to setup and IDE we can run and test our application.

`./gradlew <task>` (on Unix-like platforms such as Linux and Mac OS X)

`gradlew <task>` (on Windows using the gradlew.bat batch file)


The most used tasks we will used are `run`, `test` and `javadoc`.

For example `./gradlew test` will run all of the unit tests.

(while we are working in development, the running is probably less useful)

### Structure

All source code belongs in `/src`, tests in `/test`.

The directories of test should mirror the source. For example the test for `src/engine/Traders/Trader.java` should exist as `test/engine/Traders/TraderTest.java`.
