#----------------------------------------------------------------------
#	javadoc collected HTML pages for all packages: makefile.
#	--------------------------------------------------------
#
#	$Id: Makefile,v 1.3 1999-01-12 13:34:34 swtech00 Exp $
#
#	$Log: not supported by cvs2svn $
#	Revision 1.2  1999/01/11 12:08:00  swtech20
#	Clean Target angelegt.
#
#	Revision 1.1  1999/01/06 08:46:36  swtech12
#	Inherited freom the common Makefile for both PESTs
#
#----------------------------------------------------------------------
 

DIRS	= "absyn editor stm check gui simu codegen tesc1 tesc2 util"
JDKHOME = /opt/jdk/jdk1.1.6

TESC1DOCU_FROM = ../tesc1/Docu
TESC1DOCU_TO = ./tesc1/Docu

all:	images tesc1 report_list
	(cd .. ; javadoc -version -author -d pestdocu $(DIRS) )

images:	
	ln -s $(JDKHOME)/docs/api/images/ .

tesc1:	$(TESC1DOCU_FROM)/Grammatik $(TESC1DOCU_FROM)/Example.tesc
	mkdir -p $(TESC1DOCU_TO)
	cp $(TESC1DOCU_FROM)/Grammatik $(TESC1DOCU_TO)/Grammatik
	cp $(TESC1DOCU_FROM)/Example.tesc $(TESC1DOCU_TO)/Example.tesc

report_list:	../ErrorReport/report_list
	cp ../ErrorReport/report_list .


clean: 
	\rm -f *.html *~
	\rm -f images
	 \rm -f $(TESC1DOCU_TO)/*
	\rmdir -p $(TESC1DOCU_TO)
