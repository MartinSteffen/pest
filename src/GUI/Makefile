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

localdocu:	
	javadoc  -version -author -d Docu *.java
