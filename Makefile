BackendIndividual.class:
	javac BackendIndividual.java
Backend.class:
	javac Backend.java
BackendInterface.class:
	javac BackendInterface.java
BackendDeveloperTests.class:
	javac -cp .:../junit5.jar BackendDeveloperTests.java
runBDTests: BackendIndividual.class Backend.class BackendInterface.class BackendDeveloperTests.class
	java -jar ../junit5.jar -cp . -c BackendDeveloperTests
runFDTests:
	javac -cp .:../junit5.jar FrontendDeveloperTests.java
	java -jar ../junit5.jar -cp . -c FrontendDeveloperTests
runApp: Backend.class BackendIndividual.class Frontend.class
	java Frontend.java
Frontend.class:
	javac Frontend.java
clean:
	rm *.class
