#----------------------------------------------------------------------
#	Abstract Syntax for PEST Statecharts
#	------------------------------------
#
#	$Id: Makefile,v 1.3 1998-12-09 14:28:50 swtech11 Exp $
#
#	$Log: not supported by cvs2svn $
#	Revision 1.5  1998/12/08 14:49:33  swtech00
#	Optimierung.
#
#	Revision 1.4  1998/11/27 16:11:20  swtech00
#	Ich habe damit angefangen, die Abstrakte Syntax so zu kommentieren, dass
#	javadoc das auch verstehen kann. Das bereitet die Benutzung von javadoc vor.
#
#	Revision 1.3  1998/11/26 17:10:41  swtech00
#	*** empty log message ***
#
#	Revision 1.2  1998/11/26 16:32:19  swtech00
#	Id and Log extension
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