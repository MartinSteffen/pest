JDK = /opt/jdk/jdk1.1.6/lib/classes.zip:/usr/lib/jdk1.1.6/lib/classes.zip:
PEST = .:..
CLASSPATH = $(PEST):$(JDK)
COMPILER = javac
VM = java
COPTIONS = -g -deprecation

CLASSES = TESCLoader.class\
TESCParser.class\
TESCTokenizer.class

.SUFFIXES: .java .class

all : $(CLASSES)

clean:
	rm -f *.class *~

.java.class:
	$(COMPILER) $(COPTIONS) -classpath "$(CLASSPATH)"  $?

