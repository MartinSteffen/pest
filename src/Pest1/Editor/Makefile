# Makefile fuer Simulator

cc = /opt/jdk/jdk1.1.6/bin/javac


all : Editor.class 

hlo.class : 
	$(cc) highlightObject.java


Editor.class : hlo.class
	$(cc) Editor.java 

clean :
	rm -rf *.class

# Ende Makefile

