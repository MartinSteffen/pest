
JAVAC=javac
JAVACFLAGS=-g -deprecation -depend
files=CodeGen.java CodeGenException.java

all: $(files:.java=.class)

clean:
	rm -rf *class

%.class: %.java
	$(JAVAC) $(JAVACFLAGS) $<

