#----------------------------------------------------------------------
# Das "Master"-Makefile 
#----------------------------------------------------------------------


all:    others $(patsubst %.java,%.class,$(wildcard *.java))  


others:
	(cd ../Absyn ; make)
	(cd ../Editor ; make)
	(cd ../Check ; make)
	(cd ../Simu ; make)
	(cd ../TESC1 ; make)
	(cd ../TESC2 ; make)
	(cd ../CodeGen ; make)
	(cd ../STM ; make)

gui:    $(patsubst %.java,%.class,$(wildcard *.java))  

%.class: %.java 
	javac $< 


clean:
	cd ..
	rm -rf *.class *~

localdocu:	
	javadoc  -version -author -d Docu *.java
