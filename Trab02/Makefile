JFLEX  = jflex
JAVAC  = javac

##
# Anderson Jean Fraga
# 13180375
# Compiladores - Trabalho 2
##

all: AsdrParser.class

rebuild: clean AsdrParser.class

clean:
	rm -f *~ *.class Yylex.java

AsdrParser.class: Yylex.java
	$(JAVAC) ParserTokens.java
	$(JAVAC) ParserVal.java
	$(JAVAC) AsdrGrammar.java
	$(JAVAC) AsdrParser.java

Yylex.java: asdr_lex.flex
	$(JFLEX) asdr_lex.flex
