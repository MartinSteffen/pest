
JAVAC=javac
JAVACFLAGS=-g
files=HAImport.java Patterns.java

all: $(files:.java=.class)
debug:	; make JAVACOPTS="-g" all

clean:
	rm -rf *class

%.class: %.java
	$(JAVAC) $(JAVACFLAGS)  $(JAVACOPTS) $<

