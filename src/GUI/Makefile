#----------------------------------------------------------------------
# Das "Master"-Makefile 
#----------------------------------------------------------------------


all:    others $(patsubst %.java,%.class,$(wildcard *.java))  
debug:	; make JAVACOPTS="-g" all


others:
	(cd ../absyn ; make)
	(cd ../util ; make) 
	(cd ../editor ; make)
	(cd ../check ; make)
	(cd ../simu ; make)
	(cd ../tesc1 ; make)
	(cd ../tesc2 ; make)
	(cd ../codegen ; make)
	(cd ../stm ; make)

gui:    $(patsubst %.java,%.class,$(wildcard *.java))  

%.class: %.java 
	javac $(JAVACOPTS) $< 


clean:
	cd .. 
	rm -rf *.class *~ 
	rm -f pest.jar
	(cd ../absyn ; make clean) 
	(cd ../editor ; make clean) 
	(cd ../check ; make clean) 
	(cd ../simu ; make clean) 
	(cd ../tesc1 ; make clean) 
	(cd ../tesc2 ; make clean) 
	(cd ../codegen ; make clean) 
	(cd ../stm ; make clean) 

localdocu:	
	javadoc  -version -author -d Docu *.java

#----------------------------------------------------------------------
# ALLE PEST Klassen werden in ein Archiv zusammengepackt
# Mit dem Ergebnis pest.jar kann man PEST ohne Quelle benutzen.
# Dafür setze CLASSPATH so daß
#  (1) jdk1.1.6, PerlTools-1.1 und pest.jar im CLASSPATH enthalten sind
#  (2) Der Pfad zu einer Workarea von PEST darf nicht enthalten sein!
# z.B. für (t)csh:
# setenv CLASSPATH .:<Pfad zu pest.jar>/pest.jar:/opt/jdk/jdk1.1.6/lib/classes.zip:/home/swtech12/PerlTools-1.1/PerlTools.jar
#----------------------------------------------------------------------
 
pest.jar:	all
	(cd .. ; \
	set CLASSPATH= .:/opt/jdk/jdk1.1.6/lib/classes.zip:/home/swtech12/PerlTools-1.1/PerlTools.jar ;\
	jar cvf0 gui/pest.jar `find . -name "*.class"`  )
#----------------------------------------------------------------------
