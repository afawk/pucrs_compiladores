
%{
  import java.io.*;
%}

%token NUM IDENT
%token INT BOOL TRUE FALSE VOID DOUBLE STRING
%token SWITCH CASE DEFAULT WHILE DO FOR IF ELSE RETURN BREAK CONTINUE
%token AND OR XOR NOT ATTRIB DIV MOD EQ NEQ LEQ GEQ INCREMENT DECREMENT

// http://php.net/manual/en/language.operators.precedence.php
%right ATTRIB NOT
%nonassoc EQ NEQ LEQ GEQ '<' '>' INCREMENT DECREMENT
%left DIV MOD '*'
%left '+' '-'
%left AND OR XOR
%left '('

%start program

%type <sval> IDENT FALSE TRUE NUM
%type <obj> typeVar
%type <obj> expression
%type <obj> validCaseSwitch expressionWithFun

%%

program : declList
        ;

declList : typeVar IDENT { symbolType = (SymbolType) $1; symbolName = $2; } declarations
         | VOID IDENT { symbolType = new SymbolType("void"); symbolName = $2; } methodDecl
         |
         ;

declarations : ';' { symbolTable.addVar(symbolType, symbolName); } declList
             | ',' { symbolTable.addVar(symbolType, symbolName); } listNameDecls ';' declList
             | methodDecl
             ;

methodDecl : '(' argsList ')' { symbolTable.addFunc(symbolType, symbolName); symbolTable.currentScope(symbolName); symbolType = null; } '{' statementList '}' declList
           ;

listNameDecls : IDENT { symbolTable.addVar(symbolType, $1); } nameDecls
              ;

nameDecls : ',' listNameDecls
          |
          ;

argsList : argRule argsListComma
         |
         ;

argRule : typeVar IDENT { symbolTable.addArg(symbolName, (SymbolType) $1, $2); }
        ;

argsListComma : ',' argRule argsListComma
              |
              ;


statementList : statement statementList
              |
              ;

statement : scopeFunVarDecls ';'
          | attribWithExpr ';'
          | RETURN expression { symbolTable.validReturn(symbolName, (SymbolType) $2); } ';'
          | FOR '(' scopeForVarDecls ';' expressionForDecls ';' counterForDecls ')' { symbolTable.scoppedLoopIncr(); }  statementElements
          | WHILE '(' expression { symbolTable.validTypesLogic((SymbolType) $3); } ')' { symbolTable.scoppedLoopIncr(); } statementElements
          | DO { symbolTable.scoppedLoopIncr(); } statementElements WHILE '(' expression { symbolTable.validTypesLogic((SymbolType) $6); } ')'
          | IF '(' expression { symbolTable.validTypesLogic((SymbolType) $3); } ')' elseifStatement
          | BREAK { symbolTable.scoppedLoopCheck("break"); } ';'
          | CONTINUE { symbolTable.scoppedLoopCheck("continue"); } ';'
          | SWITCH '(' expression ')' '{' listSwitchCase '}'
          ;

elseifStatement : ELSE statementElements
                |
                ;

attribWithExpr : IDENT ATTRIB expression {
  if (symbolType != null) { symbolTable.addVar(symbolType, $1, (SymbolType)$3); symbolType = null; }
  else {symbolTable.addValueVar($1, (SymbolType)$3);}
}
               ;

statementElements : statement
                  | '{' statementList '}'
                  ;

listSwitchCase : CASE validCaseSwitch statementSwitchCase
               | DEFAULT statementSwitchCase
               |
               ;

validCaseSwitch : IDENT   { $$ = symbolTable.modelateResult($1); }
                | NUM     { $$ = symbolTable.modelateResult($1); }
                | TRUE    { $$ = symbolTable.modelateResult($1); }
                | FALSE   { $$ = symbolTable.modelateResult($1); }
                ;

statementSwitchCase : ':' statementElements listSwitchCase
                    ;

scopeFunVarDecls : typeVar { symbolType = (SymbolType) $1; } listScopeFunVar
                 ;

listScopeFunVar : attribWithExpr extendListScopeFunVar
                ;

extendListScopeFunVar : ',' listScopeFunVar
                      |
                      ;

scopeForVarDecls : typeVar attribScopeForVars
                 |
                 ;

attribScopeForVars : IDENT ATTRIB expression extendScopeForVars
                   ;

extendScopeForVars : ',' attribScopeForVars
                   |
                   ;

expressionForDecls : expression { symbolTable.validTypesLogic((SymbolType) $1); }
                   |
                   ;

counterForDecls : IDENT operatorIncr listCounterForDecls
                |
                ;

listCounterForDecls : ',' counterForDecls
                    |
                    ;

operatorIncr : INCREMENT
             | DECREMENT
             ;

expressionWithFun : IDENT '(' argsSourceList ')' { $$ = symbolTable.searchTypeFunc($1); }
                  ;

argsSourceList : expression expressionList
               ;

expressionList : ',' argsSourceList
               |
               ;

expression : expression '+' expression { $$ = symbolTable.validTypesArit((SymbolType) $1, (SymbolType) $3); }
           | expression '-' expression  { $$ = symbolTable.validTypesArit((SymbolType) $1, (SymbolType) $3); }
           | expression '*' expression  { $$ = symbolTable.validTypesArit((SymbolType) $1, (SymbolType) $3); }
           | expression DIV expression  { $$ = symbolTable.validTypesArit((SymbolType) $1, (SymbolType) $3); }
           | expression MOD expression  { $$ = symbolTable.validTypesArit((SymbolType) $1, (SymbolType) $3); }
           | expression OR expression  { $$ = symbolTable.validTypesLogic((SymbolType) $1, (SymbolType) $3); }
           | expression AND expression  { $$ = symbolTable.validTypesLogic((SymbolType) $1, (SymbolType) $3); }
           | expression '<' expression  { $$ = symbolTable.validTypesComp((SymbolType) $1, (SymbolType) $3); }
           | expression LEQ expression  { $$ = symbolTable.validTypesComp((SymbolType) $1, (SymbolType) $3); }
           | expression EQ expression  { $$ = symbolTable.validTypesComp((SymbolType) $1, (SymbolType) $3); }
           | expression GEQ expression  { $$ = symbolTable.validTypesComp((SymbolType) $1, (SymbolType) $3); }
           | expression '>' expression  { $$ = symbolTable.validTypesComp((SymbolType) $1, (SymbolType) $3); }
           | expression NEQ expression  { $$ = symbolTable.validTypesComp((SymbolType) $1, (SymbolType) $3); }
           | NOT expression { $$ = symbolTable.validTypesLogic((SymbolType) $2); }
           | '(' expression ')' { $$ = (SymbolType) $2; }
           | expressionWithFun
           | validCaseSwitch
           ;

typeVar : DOUBLE { $$ = new SymbolType("double"); }
        | BOOL { $$ = new SymbolType("bool"); }
        | INT { $$ = new SymbolType("int"); }
        | STRING { $$ = new SymbolType("string"); }
        | IDENT { $$ = new SymbolType("ident"); }
        ;

// codigo
%%

  private SymbolTable symbolTable;

  private Yylex lexer;
  int yyl_return;

  private SymbolType symbolType;
  private String symbolName;

  public int yylex () {
    try {
      yylval = new ParserVal(0);
      yyl_return = lexer.yylex();

      consoleMsg("");
    }
    catch (IOException e) {
      System.err.println("IO error :"+e.getMessage());
    }
    return yyl_return;
  }


  public void yyerror (String error) {
    System.err.println ("Error: " + error);
  }


  public Parser(Reader r) {
    lexer = new Yylex(r, this);
    symbolTable = new SymbolTable(lexer);
  }


  static boolean interactive;

  public static void main(String args[]) throws IOException {
    System.out.println("");

    Parser yyparser;
    if ( args.length > 0 ) {
      // parse a file
      yyparser = new Parser(new FileReader(args[0]));
    }
    else {
      // interactive mode
      System.out.println("[Quit with CTRL-D]");
      System.out.print("> ");
      interactive = true;
        yyparser = new Parser(new InputStreamReader(System.in));
    }

    yyparser.yyparse();

    if (interactive) {
      System.out.println();
      System.out.println("done!");
    }
    else if (Yylex.YYEOF == yyparser.yylex()) {
        System.out.println("Gramática OK!");
    }
  }

  public void setDebug(boolean debug)
    {
        yydebug = debug;
    }

  public void consoleMsg(String msg) {
    if (yydebug) {
        System.out.println("#" + (lexer.getLine() + 1) + " ____  " + lastString());
    }
  }

  public String lastString() {
    int actual = yyl_return;
    return (actual < ParserTokens.BASE_TOKEN_NUM)
        ? ""+(char)actual
        : ParserTokens.get(actual);
  }