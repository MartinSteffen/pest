# Makefile fuer Editor

cc = javac


all : $(patsubst %.java,%.class,$(wildcard *.java))
debug:	; make JAVACOPTS="-g" all

#Editor.class 

%.class:	%.java
		$(cc) $(JAVACOPTS) $<


#Editor.class :
#	$(cc) *.java 

clean :
	find . -name "*.class" -exec rm {} \;

# Ende Makefile

