#----------------------------------------------------------------------
#	TESC for PEST Statecharts
#	-------------------------
#
#	$Id: Makefile,v 1.7 1999-02-09 11:03:45 swtech00 Exp $
#
#	$Log: not supported by cvs2svn $
#	Revision 1.6  1999/01/11 12:13:53  swtech20
#	Bugfixes.
#
#	Revision 1.5  1999/01/03 21:48:17  swtech20
#	Implementierung des Parsers
#
#	Revision 1.4  1998/12/17 11:05:47  swtech20
#	An den Projektstandard angepasst.
#
#
#----------------------------------------------------------------------


all:	$(patsubst %.java,%.class,$(wildcard *.java))
debug:	; make JAVACOPTS="-g" all

test:   Test/TestTESC.java
	javac Test/TestTESC.java 
	mv Test/TestTESC.class .

%.class:	%.java
		javac $(JAVACOPTS) $<

clean:
	\rm -f -r *.class *~

localdocu:	;
	javadoc  -version -author -d Docu *.java

