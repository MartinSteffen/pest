# Makefile für TESC1


all:	$(patsubst %.java,%.class,$(wildcard *.java))
debug:	; make JAVACOPTS="-g" all

%.class:	%.java
		javac  $(JAVACOPTS) $<

clean:
	\rm -f *.class *~

localdocu:	;
	javadoc  -version -author -d Docu *.java