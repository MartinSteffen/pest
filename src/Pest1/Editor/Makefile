# Makefile fuer Editor

cc = javac


all : $(patsubst %.java,%.class,$(wildcard *.java))

#Editor.class 

%.class:	%.java
		$(cc) $<


#Editor.class :
#	$(cc) *.java 

clean :
	find . -name "*.class" -exec rm {} \;

# Ende Makefile

