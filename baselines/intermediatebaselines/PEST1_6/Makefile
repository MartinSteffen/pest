#----------------------------------------------------------------------
#	javadoc collected HTML pages for all packages: makefile.
#	--------------------------------------------------------
#
#	$Id: Makefile,v 1.8 1999/01/05 09:01:05 swtech00 Exp $
#
#	$Log: Makefile,v $
#	Revision 1.8  1999/01/05 09:01:05  swtech00
#	Minor corrections.
#
#	Revision 1.7  1999/01/04 14:47:02  swtech20
#	Pfade fuer Beispieldateien sind nun unabhaengig
#
#	Revision 1.6  1998/12/15 18:30:59  swtech00
#	New naming convention
#
#	Revision 1.5  1998/12/07 11:59:14  swtech00
#	Util hinzugefuegt.
#
#	Revision 1.4  1998/12/02 09:59:58  swtech00
#	test
#
#	Revision 1.3  1998/12/01 16:37:54  swtech00
#	Image link added.
#
#	Revision 1.2  1998/11/30 09:28:09  swtech00
#	Comments corrected
#
#	Revision 1.1  1998/11/30 09:25:31  swtech00
#	Initial version.
#
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
