HTMLDIR = Docu
DOCCFLAGS = -author -version -d $(HTMLDIR)
DOCC = javadoc $(DOCCFLAGS) $(CLASSES)
CLASSES =-classpath $$CLASSPATH:./HAImport/PerlTools.jar
CC = javac -depend $(CLASSES) 

all:	classes

classes:	
	$(CC) *.java

docu:	
	$(DOCC) *.java
 
clean:	
	rm *.class *.~jav

cleandocu:	
	rm $(HTMLDIR)/*.html
