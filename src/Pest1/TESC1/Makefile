# Makefile für TESC1


all:	$(patsubst %.java,%.class,$(wildcard *.java))

 %.class:	%.java
		javac $<

clean:
	\rm -f *.class *~

localdocu:	;
	javadoc  -version -author -d Docu *.java