all:	$(patsubst %.java,%.class,$(wildcard *.java))


%.class:	%.java
		javac $<


clean:
	\rm -f *.class *~

localdocu:	;
	javadoc  -version -author -d Docu *.java

compile : Editor.java
	javac Editor.java
run : Editor.java
	java Editor
