JFLEX  = java -jar JFlex.jar 
JAVAC  = javac

# targets:

all: AsdrSample.class

clean:
	rm -f *~ *.class Yylex.java

AsdrSample.class: AsdrSample.java Yylex.java
	$(JAVAC) AsdrSample.java

Yylex.java: asdr_lex.flex
	$(JFLEX) asdr_lex.flex

