#=========================================================
#
#      File      :       Makefile
#      Group     :       Eike Schulz & Martin Poerksen
#      Date      :       08.01.1999
#
#=========================================================


#JDK = /opt/jdk/jdk1.1.6/lib/classes.zip
JDK = /opt/jdk/jdk1.1.6/lib/classes.zip:/usr/lib/jdk1.1.5/lib/classes.zip
PEST = .:..:../..
CLASSPATH = $(PEST):$(JDK)
COMPILER = javac
VM = java
COPTIONS = -g -deprecation
ROPTIONS =
 
CLASSES = \
Test1.class \
Test2.class

.SUFFIXES: .java .class
 
all : $(CLASSES)
  
# Remove class files 
clean:
	rm *.class
 
# Implicit rule for making Java class files from Java source files

.java.class:
	$(COMPILER) $(COPTIONS) Test1.java; \
	$(COMPILER) $(COPTIONS) Test2.java

#=================================================================

