# $Id: Makefile,v 1.6 1999-01-25 15:10:16 swtech25 Exp $
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

localdocu: $(files)
	$(JAVADOC) $(JAVADOCFLAGS) $(files)
