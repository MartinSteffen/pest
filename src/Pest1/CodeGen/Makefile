# $Id: Makefile,v 1.5 1999-01-18 11:00:43 swtech25 Exp $
JAVAC=javac
JAVACFLAGS=-g -deprecation -depend
JAVADOC=javadoc
JAVADOCFLAGS=-author -version
files=CodeGen.java CodeGenException.java dumpHA.java dumpTables.java

all: $(files:.java=.class)

clean:
	rm -rf *class *html

%.class: %.java
	$(JAVAC) $(JAVACFLAGS) $<

local-docu: $(files)
	$(JAVADOC) $(JAVADOCFLAGS) $(files)
