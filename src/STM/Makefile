HTMLDIR = Docu
DOCCFLAGS = -author -version -d $(HTMLDIR)
DOCC = javadoc $(DOCCFLAGS) 
CC = javac -depend  

all:	classes

classes:	
	$(CC) *.java

docu:	
	$(DOCC) *.java
 
clean:	
	rm *.class *.~jav

cleandocu:	
	rm $(HTMLDIR)/*.html
