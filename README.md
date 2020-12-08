# SolveTheGame
Git repo to solve a puzzle with webdriver-java.

# High-level tech stack details
This is solved using webdriver-java. The build tool is gradle. 
TestNG framework is used to trigger the tests. 

# Notes For Execution
This solution solves the game on the chrome browser.  

Different Ways to Execute
1. Can run the class "GameTest" as a TestNG class.
2. Run the testng.xml by selecting the test kind as "Suite".
3. Run the command "gradle clean test".

Potential issues during execution: -
1. Since the url is unsecured(http), TestNG blocks loading this url. Should that happen, set the 
vm parameter -Dtestng.dtd.http to true.
2. Trust the chromedriver binaries if a security issue comes up.