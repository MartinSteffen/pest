COMPILER = javac
VM = java
COPTIONS = -g -deprecation
ROPTIONS = 
 
CLASSES = \
AlgorithmException.class \
LayoutAlgorithm.class \
MapTransition.class \
MapEndTr.class \
MapElement.class \
MapState.class \
MapConnector.class \
MapUNDEF.class \
ProperMap.class \
SugiyamaBCMAlgorithm.class \
GraphOptimizer.class \
CircleLayoutAlgorithm.class \
GeneralLayoutAlgorithm.class

.SUFFIXES: .java .class
 
all : $(CLASSES)
  
# Remove all class files from the project directory.
clean:
	rm *.class
 
# Implicit rule for making Java class files from Java
# source files.
.java.class:
	$(COMPILER) $(COPTIONS) $?

