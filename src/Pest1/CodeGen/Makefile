# $Id: Makefile,v 1.3 1999-01-04 13:45:56 swtech25 Exp $
JAVAC=javac
JAVACFLAGS=-g -deprecation -depend
files=CodeGen.java CodeGenException.java

all: $(files:.java=.class)

clean:
	rm -rf *class *html

%.class: %.java
	$(JAVAC) $(JAVACFLAGS) $<

local-docu: $(files)
	javadoc -author -version $(files)
