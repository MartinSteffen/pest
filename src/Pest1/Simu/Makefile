# Makefile fuer Simulator

cc = javac


all: $(patsubst %.java,%.class,$(wildcard *.java))
debug:	; make JAVACOPTS="-g" all

%.class: %.java
	javac $(JAVACOPTS) $< 

clean:
	rm -rf *.class

# Ende Makefile

