# $Id: Makefile,v 1.4 1999-01-04 14:18:36 swtech25 Exp $
JAVAC=javac
JAVACFLAGS=-g -deprecation -depend
files=CodeGen.java CodeGenException.java dumpHA.java

all: $(files:.java=.class)

clean:
	rm -rf *class *html

%.class: %.java
	$(JAVAC) $(JAVACFLAGS) $<

local-docu: $(files)
	javadoc -author -version $(files)
