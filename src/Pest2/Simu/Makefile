JDK = /opt/jdk/jdk1.1.6/lib/classes.zip
COMPILER = javac
VM = java
COPTIONS = -g -deprecation
ROPTIONS =
 
CLASSES = \
Simu.class

.SUFFIXES: .java .class
 
all : $(CLASSES)
debug:	; make JAVACOPTS="-g" all
  
# Remove all class files from the project directory.
clean:
	rm *.class
 
# Implicit rule for making Java class files from Java
# source files.
.java.class:
	$(COMPILER) $(COPTIONS) $(JAVACOPTS) $?

