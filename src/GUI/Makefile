#----------------------------------------------------------------------
# Das "Master"-Makefile 
#----------------------------------------------------------------------


all:
	javac *.java
	make -C ../Absyn
	make -C ../Editor
	make -C ../Check
	make -C ../Simu
	make -C ../TESC1
	make -C ../TESC2
	make -C ../CodeGen
	make -C ../STM

gui:
	javac *.java
clean:
	cd ..
	rm -rf *.class *~

localdocu:	
	javadoc  -version -author -d Docu *.java
