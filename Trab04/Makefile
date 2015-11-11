
JFLEX  = jflex
BYACCJ = byaccj -tv -J
JAVAC  = javac

all: author Parser

run: author Parser
	#$(foreach file, $(LISTFILES), $(java Parser $(file)))

rebuild: author clean Parser

help:
	@echo $$ make help \(this\)
	@echo $$ make \(rebuild only changes\)
	@echo $$ make rebuild \(clean and compile\)
	@echo $$ make clean \(clean only\)
	@echo $$ java Parser testfile.txt \(run testfile.txt test file\)

author:
	@echo ------ Anderson Fraga - 13180375
	@echo ------ $$ make help \(more options\)
	@echo

clean:
	rm -f *~ *.class Yylex.java *.output Parser.java ParserVal.java
	@echo Files: `ls`
	@echo

Parser:
	$(JFLEX) lexico.flex
	$(BYACCJ) grammar.yacc
	$(JAVAC) ParserTokens.java
	$(JAVAC) SymbolType.java
	$(JAVAC) SymbolTree.java
	$(JAVAC) SymbolTable.java
	$(JAVAC) ParserVal.java
	$(JAVAC) Parser.java
