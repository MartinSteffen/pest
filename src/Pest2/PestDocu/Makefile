#----------------------------------------------------------------------
#	javadoc collected HTML pages for all packages: makefile.
#	--------------------------------------------------------
#
#	$Id: Makefile,v 1.1 1999-01-06 08:46:36 swtech12 Exp $
#
#	$Log: not supported by cvs2svn $
#----------------------------------------------------------------------
 

DIRS	= "absyn editor stm check gui simu codegen tesc1 tesc2 util"
JDKHOME = /opt/jdk/jdk1.1.6

TESC1DOCU_FROM = ../tesc1/Docu
TESC1DOCU_TO = ./tesc1/Docu

all:	images tesc1
	(cd .. ; javadoc -version -author -d pestdocu $(DIRS) )

images:	
	ln -s $(JDKHOME)/docs/api/images/ .

tesc1:	$(TESC1DOCU_FROM)/Grammatik $(TESC1DOCU_FROM)/example.tesc
	mkdir -p $(TESC1DOCU_TO)
	cp $(TESC1DOCU_FROM)/Grammatik $(TESC1DOCU_TO)/Grammatik
	cp $(TESC1DOCU_FROM)/example.tesc $(TESC1DOCU_TO)/example.tesc
