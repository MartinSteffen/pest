#----------------------------------------------------------------------
#	javadoc collected HTML pages for all packages: makefile.
#	--------------------------------------------------------
#
#	$Id: Makefile,v 1.6 1999-01-25 15:46:31 swtech00 Exp $
#
#	$Log: not supported by cvs2svn $
#	Revision 1.5  1999/01/19 11:28:18  swtech00
#	Minor correction.
#
#	Revision 1.4  1999/01/12 13:33:41  swtech00
#	Reports inserted.
#
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
	cp ../ErrorReport/report_list report_list.txt

#----------------------------------------------------------------------

DEP_FILES="check-dep.html codegen-dep.html editor-dep.html gui-dep.html simu-dep.html stm-dep.html tesc1-dep.html  tesc2-dep.html"

DEP:	$(DEP_FILES)

clean_dep:	
	rm -rf $(DEP_FILES)

check-dep.html:
	(cd .. ; javadep -i -n -o pestdocu/check-dep.html check editor codegen gui simu stm tesc1 tesc2)

codegen-dep.html:
	(cd .. ; javadep -i -n -o pestdocu/codegen-dep.html codegen editor check gui simu stm tesc1 tesc2)

editor-dep.html:
	(cd .. ; javadep -i -n -o pestdocu/editor-dep.html editor check codegen gui simu stm tesc1 tesc2)

gui-dep.html:
	(cd .. ; javadep -i -n -o pestdocu/gui-dep.html gui editor codegen check simu stm tesc1 tesc2)

simu-dep.html:
	(cd .. ; javadep -i -n -o pestdocu/simu-dep.html simu editor codegen check gui stm tesc1 tesc2)

stm-dep.html:
	(cd .. ; javadep -i -n -o pestdocu/stm-dep.html stm editor codegen check gui simu tesc1 tesc2)

tesc1-dep.html:
	(cd .. ; javadep -i -n -o pestdocu/tesc1-dep.html tesc1 editor codegen check gui simu stm  tesc2)

tesc2-dep.html:
	(cd .. ; javadep -i -n -o pestdocu/tesc2-dep.html tesc2 editor codegen check gui simu stm  tesc1)

absyn-dep.html:
	(cd .. ; javadep -i -n -o pestdocu/absyn.html absyn  editor codegen check gui simu stm tesc1 tesc2)
