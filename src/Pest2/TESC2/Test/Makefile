JDK = /opt/jdk/jdk1.1.6/lib/classes.zip:/usr/lib/jdk1.1.5/lib/classes.zip
PEST = .:..:../..
CLASSPATH = $(PEST):$(JDK)
COMPILER = javac
VM = java
COPTIONS = -g -deprecation
ROPTIONS = 
 
CLASSES = \
TestProperMap.class \
TestGraphAlgorithm.class \
TestFrame.class

.SUFFIXES: .java .class
 
all : $(CLASSES)
  
# Remove all class files from the project directory.
clean:
	rm *.class
 
# Implicit rule for making Java class files from Java
# source files.
.java.class:
	$(COMPILER) $(COPTIONS) -classpath "$(CLASSPATH)"  $?

run1:
	$(VM) -classpath "$(CLASSPATH)" TestGraphAlgorithm

frame:
	$(VM) -classpath "$(CLASSPATH)" TestFrame
