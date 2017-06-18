#----------------------------------------------------------------------
#	javadoc collected HTML pages for all packages: makefile.
#	--------------------------------------------------------
#
#	$Id: Makefile,v 1.5 1998/12/07 11:59:14 swtech00 Exp $
#
#	$Log: Makefile,v $
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
 

DIRS	= "Absyn Editor STM Check GUI Simu CodeGen TESC1 TESC2 Util"
JDKHOME = /opt/jdk/jdk1.1.6

all:	images
	(cd .. ; javadoc -version -author -d PestDocu $(DIRS) )

images:
		ln -s $(JDKHOME)/docs/api/images/ .
