# Makefile fuer Simulator

cc = javac


all: $(patsubst %.java,%.class,$(wildcard *.java))

%.class: %.java
	javac $< 

clean:
	rm -rf *.class

# Ende Makefile

