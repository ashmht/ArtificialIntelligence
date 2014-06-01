I have made a jar file for easy execution of my source code. The cmd is 
java -jar hw4 train-images train-label test-images output.txt

// Notes "idx1.ubyte" and "idx3.ubyte" is auto appended in the code
// Output.txt has all the predicted values.

I use the framework specified in 
aima-java.googlecode.com/svn/trunk/aima-core/src/main/java/aima

I basically reuse the code and do a wrapper over it to make it work for our input.
The output.txt has \n appended at the end of each line,so a diff must catch it.

The src folder has all the source code. 

Currently it take about 8-9 min on my PC with an accuracy of about 90%

Java 7 Used

IDE is eclipse.

-Ashish