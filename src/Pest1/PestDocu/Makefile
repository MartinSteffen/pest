#----------------------------------------------------------------------
#	javadoc collected HTML pages for all packages: makefile.
#	--------------------------------------------------------
#
#	$Id: Makefile,v 1.4 1999-01-12 13:33:41 swtech00 Exp $
#
#	$Log: not supported by cvs2svn $
#	Revision 1.3  1999/01/11 23:19:05  swtech13
#	tesc1: cp korrigiert
#
#	Revision 1.2  1999/01/09 15:49:01  swtech13
#	tesc1 eingefuegt, rm -f images eingebaut
#
#	Revision 1.1  1999/01/06 08:47:26  swtech12
#	Inherited freom the common Makefile for both PESTs
#
#----------------------------------------------------------------------
 

DIRS	= "absyn editor stm check gui simu codegen tesc1 tesc2 util"
JDKHOME = /opt/jdk/jdk1.1.6


all:	images tesc1 report_list
	(cd .. ; javadoc -version -author -d pestdocu $(DIRS) )

images:	
	rm -f images
	ln -s $(JDKHOME)/docs/api/images/ .

tesc1:	../tesc1/Docu/grammatik.txt ../tesc1/Test/example.tesc ../tesc1/Docu/Doku.txt
	mkdir -p ./tesc1/Docu
	mkdir -p ./tesc1/Test
	cp ../tesc1/Docu/grammatik.txt ./tesc1/Docu/grammatik.txt
	cp ../tesc1/Test/example.tesc ./tesc1/Test/example.tesc
	cp ../tesc1/Docu/Doku.txt ./tesc1/Docu/Doku.txt

report_list:	../ErrorReport/report_list
	cp ../ErrorReport/report_list .

