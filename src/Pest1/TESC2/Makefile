#=========================================================
#
#      File      :       Makefile
#      Group     :       Eike Schulz & Martin Poerksen
#      Date      :       07.12.1998
#
#=========================================================


#JDK = /opt/jdk/jdk1.1.6/lib/classes.zip
JDK = /opt/jdk/jdk1.1.6/lib/classes.zip:/usr/lib/jdk1.1.5/lib/classes.zip
PEST = .:..
CLASSPATH = $(PEST):$(JDK)
COMPILER = javac
VM = java
COPTIONS = -g -deprecation
ROPTIONS =
 
CLASSES = \
GraphOptimizer.class
#GraphOptimizer.class \
#AlgorithmException.class \

.SUFFIXES: .java .class
 
all : $(CLASSES)
  
# Remove class files 
clean:
	rm *.class
 
# Implicit rule for making Java class files from Java source files

.java.class:
	$(COMPILER) $(COPTIONS)  GraphOptimizer.java

#=================================================================

