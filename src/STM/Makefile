
JAVAC=javac
JAVACFLAGS=-g
files=HAImport.java Patterns.java

all: $(files:.java=.class)

clean:
	rm -rf *class

%.class: %.java
	$(JAVAC) $(JAVACFLAGS) $<

