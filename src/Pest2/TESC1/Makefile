#----------------------------------------------------------------------
#	TESC for PEST Statecharts
#	-------------------------
#
#	$Id: Makefile,v 1.4 1998-12-17 11:05:47 swtech20 Exp $
#
#	$Log: not supported by cvs2svn $
#
#----------------------------------------------------------------------


all:	$(patsubst %.java,%.class,$(wildcard *.java))

%.class:	%.java
		javac $<


clean:
	\rm -f *.class *~

localdocu:	;
	javadoc  -version -author -d Docu *.java

