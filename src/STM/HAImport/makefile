HTMLDIR = ../Docu
DOCCFLAGS = -author -version -d $(HTMLDIR)
DOCC = javadoc $(DOCCFLAGS) $(CLASSES)
CLASSES = -classpath "$$CLASSPATH":"$$HOME"/PEST1/STM/HAImport/PerlTools.jar:"$$HOME"/PEST2/STM/HAImport/PerlTools.jar
CC = javac -depend $(CLASSES) 

all:	classes docu 

classes:	
	$(CC) *.java

docu:	
	$(DOCC) *.java
 
clean:	
	rm *.class *.~jav

cleandocu:	
	rm $(HTMLDIR)/*.html
