#----------------------------------------------------------------------
# Das "Master"-Makefile 
#----------------------------------------------------------------------


all:    $(patsubst %.java,%.class,$(wildcard *.java))  

	make -C ../Absyn
	make -C ../Editor
	make -C ../Check
	make -C ../Simu
	make -C ../TESC1
	make -C ../TESC2
	make -C ../CodeGen
	make -C ../STM

gui:    $(patsubst %.java,%.class,$(wildcard *.java))  

%.class: %.java 
	javac $< 


clean:
	cd ..
	rm -rf *.class *~

localdocu:	
	javadoc  -version -author -d Docu *.java
