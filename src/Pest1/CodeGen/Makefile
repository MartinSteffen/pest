# $Id: Makefile,v 1.11 1999-03-11 15:37:02 swtech25 Exp $
JAVAC=javac
JAVACFLAGS=-deprecation -depend -g
JAVADOC=javadoc
JAVADOCFLAGS=-author -version -private -verbose
files=CodeGen.java CodeGenException.java CodeGenOpt.java dumpHA.java dumpTables.java

all: $(files:.java=.class)
debug:	; make JAVACOPTS="-g" all

clean:
	rm -rf *class *html *~

%.class: %.java
	$(JAVAC) $(JAVACFLAGS)  $(JAVACOPTS) $<

localdocu: $(files)
	$(JAVADOC) $(JAVADOCFLAGS) -d Docu codegen
