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
	$(COMPILER) $(COPTIONS)  $?

