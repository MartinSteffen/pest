# $Id: Makefile,v 1.7 1999-02-09 11:07:43 swtech00 Exp $
JAVAC=javac
JAVACFLAGS=-g -deprecation -depend
JAVADOC=javadoc
JAVADOCFLAGS=-author -version
files=CodeGen.java CodeGenException.java dumpHA.java dumpTables.java

all: $(files:.java=.class)
debug:	; make JAVACOPTS="-g" all

clean:
	rm -rf *class *html

%.class: %.java
	$(JAVAC) $(JAVACFLAGS)  $(JAVACOPTS) $<

localdocu: $(files)
	$(JAVADOC) $(JAVADOCFLAGS) $(files)
