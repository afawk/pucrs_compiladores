##
#
# Trabalho 04 Compiladores
# Anderson Fraga - 13180375
# contato@andersonfraga.net
#
##

JFLEX  = jflex
BYACCJ = byaccj -tv -J
JAVAC  = javac

all: author Parser run

rebuild: author clean Parser

help:
	@echo $$ make \(rebuild only changes and execute tests\)
	@echo $$ make rebuild \(clean and compile\)
	@echo $$ make clean \(clean only\)
	@echo $$ java Parser testfile.txt \(run testfile.txt test file\)

author:
	@echo ------ Trabalho 04 Compiladores
	@echo ------ Anderson Fraga - 13180375
	@echo

clean:
	rm -f *~ *.class Yylex.java *.output Parser.java ParserVal.java
	@echo Files: `ls`
	@echo

run:
	./tests.sh

Parser:
	$(JFLEX) lexico.flex
	$(BYACCJ) grammar.yacc
	$(JAVAC) ParserTokens.java
	$(JAVAC) SymbolType.java
	$(JAVAC) SymbolTree.java
	$(JAVAC) SymbolTable.java
	$(JAVAC) ParserVal.java
	$(JAVAC) Parser.java
