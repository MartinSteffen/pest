COMPILER = javac
VM = java
COPTIONS = -g -deprecation
ROPTIONS = 
 
CLASSES = \
GraphOptimizer.class \
AlgorithmException.class \
LayoutAlgorithm.class \
ProperMap.class \
MapElement.class \
MapTransition.class \
MapEndTr.class \
SugiyamaBCMAlgorithm.class

.SUFFIXES: .java .class
 
all : $(CLASSES)
  
# Remove all class files from the project directory.
clean:
	rm *.class
 
# Implicit rule for making Java class files from Java
# source files.
.java.class:
	$(COMPILER) $(COPTIONS) $?

