#----------------------------------------------------------------------
#	Utiliies
#	------------------------------------
#
#	$Id: Makefile,v 1.2 1999-02-09 11:07:11 swtech00 Exp $
#
#----------------------------------------------------------------------

all:	$(patsubst %.java,%.class,$(wildcard *.java))
debug:	; make JAVACOPTS="-g" all

%.class:	%.java
		javac $(JAVACOPTS) $<


clean:
	\rm -f *.class *~

localdocu:	;
	javadoc  -version -author -d Docu *.java
	javadoc  -version -author -d Docu *.java