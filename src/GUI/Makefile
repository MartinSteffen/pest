#----------------------------------------------------------------------
# Das "Master"-Makefile 
#----------------------------------------------------------------------


all:    others $(patsubst %.java,%.class,$(wildcard *.java))  


others:
	(cd ../absyn ; make)
	(cd ../editor ; make)
	(cd ../check ; make)
	(cd ../simu ; make)
	(cd ../tesc1 ; make)
	(cd ../tesc2 ; make)
	(cd ../codegen ; make)
	(cd ../stm ; make)

gui:    $(patsubst %.java,%.class,$(wildcard *.java))  

%.class: %.java 
	javac $< 


clean:
	cd .. 
	rm -rf *.class *~ 
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
