COMPILER = javac
VM = java
COPTIONS = -deprecation
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
GeneralLayoutAlgorithm.class \
SugiyamaBCMAlgorithm.class \
CircleLayoutAlgorithm.class \
GraphOptimizer.class

.SUFFIXES: .java .class
 
all : $(CLASSES)
debug:	; make JAVACOPTS="-g" all
  
# Remove all class files from the project directory.
clean:
	rm *.class
 
# Implicit rule for making Java class files from Java
# source files.
.java.class:
	$(COMPILER) $(COPTIONS) $(JAVACOPTS) $?

