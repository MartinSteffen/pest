# $Id: Makefile,v 1.9 1999-02-17 11:00:34 swtech25 Exp $
JAVAC=javac
JAVACFLAGS=-deprecation -depend
JAVADOC=javadoc
JAVADOCFLAGS=-author -version -private
files=CodeGen.java CodeGenException.java dumpHA.java dumpTables.java

all: $(files:.java=.class)
debug:	; make JAVACOPTS="-g" all

clean:
	rm -rf *class *html

%.class: %.java
	$(JAVAC) $(JAVACFLAGS)  $(JAVACOPTS) $<

localdocu: $(files)
	$(JAVADOC) $(JAVADOCFLAGS) $(files)
