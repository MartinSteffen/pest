#----------------------------------------------------------------------
#	local tests for abstract syntax
#	------------------------------------
#
#	$Id: Makefile,v 1.1 1998-12-17 15:57:20 swtech00 Exp $
#
#
#
#----------------------------------------------------------------------


all:	$(patsubst %.java,%.class,$(wildcard *.java))

%.class:	%.java
		javac $<


clean:
	\rm -f *.class *~

localdocu:	;
	javadoc  -version -author -d Docu *.java