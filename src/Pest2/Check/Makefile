all:	$(patsubst %.java,%.class,$(wildcard ../Check/*.java))

%.class:	%.java
		javac $<
 
clean:
	cd ..; rm -f ../Check/*.class *~

localdocu:
	cd ..; javadoc  -version -author -d Docu *.java
