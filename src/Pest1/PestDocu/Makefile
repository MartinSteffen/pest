#----------------------------------------------------------------------
#	javadoc collected HTML pages for all packages: makefile.
#	--------------------------------------------------------
#
#	$Id: Makefile,v 1.1 1999-01-06 08:47:26 swtech12 Exp $
#
#	$Log: not supported by cvs2svn $
#----------------------------------------------------------------------
 

DIRS	= "absyn editor stm check gui simu codegen tesc1 tesc2 util"
JDKHOME = /opt/jdk/jdk1.1.6


all:	images
	(cd .. ; javadoc -version -author -d pestdocu $(DIRS) )

images:	
	ln -s $(JDKHOME)/docs/api/images/ .

