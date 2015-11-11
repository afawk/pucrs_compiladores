
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

%type <sval> IDENT FALSE TRUE NUM typeVar
%type <obj> expression validCaseSwitch expressionWithFun

%%

program : { head = new SymbolTree(new SymbolType("_main", "_main")); } declList
        ;

declList : typeVar IDENT { currentSymbol = new SymbolType($1, $2); } declarations
         | VOID IDENT { currentSymbol = new SymbolType("void", $2); } methodDecl
         |
         ;

declarations : ';' { head = symbolTable.addVar(head, currentSymbol); } declList
             | ',' { head = symbolTable.addVar(head, currentSymbol); } listNameDecls ';' declList
             | methodDecl
             ;

methodDecl : '(' { symbolType = null; head = symbolTable.addFunc(head, currentSymbol); } argsList ')' '{' statementList '}' declList
           ;

listNameDecls : IDENT { head = symbolTable.addVar(head, currentSymbol.changeName($1)); } nameDecls
              ;

nameDecls : ',' listNameDecls
          |
          ;

argsList : argRule argsListComma
         |
         ;

argRule : typeVar IDENT { head = symbolTable.addArg(head, new SymbolType($1, $2)); }
        ;

argsListComma : ',' argRule argsListComma
              |
              ;


statementList : statement statementList
              |
              ;

statement : scopeFunVarDecls ';' { symbolType = null; }
          | attribWithExpr ';' { symbolType = null; }
          | RETURN expression { symbolTable.validReturn(head, (SymbolType) $2); } ';'
          | FOR '(' { head = symbolTable.addScope(head, "for"); } scopeForVarDecls ';' expressionForDecls ';' counterForDecls ')' { symbolTable.scoppedLoopIncr(); }  statementElements
          | WHILE  { head = symbolTable.addScope(head, "while"); } '(' expression { symbolTable.validTypesLogic(head, (SymbolType) $4); } ')' { symbolTable.scoppedLoopIncr(); } statementElements
          | DO  { head = symbolTable.addScope(head, "do-while"); } { symbolTable.scoppedLoopIncr(); } statementElements WHILE '(' expression { symbolTable.validTypesLogic(head, (SymbolType) $7); } ')'
          | IF '(' expression { symbolTable.validTypesLogic(head, (SymbolType) $3); } ')' statementElements elseifStatement
          | BREAK { symbolTable.scoppedLoopCheck("break"); } ';'
          | CONTINUE { symbolTable.scoppedLoopCheck("continue"); } ';'
          | SWITCH '(' expression ')' '{' listSwitchCase '}'
          ;

scopeForVarDecls : typeVar { symbolType = $1; } attribScopeForVars
                 |
                 ;

attribScopeForVars : IDENT ATTRIB expression { symbolTable.addVar(head, new SymbolType(symbolType, $1), (SymbolType) $3); } extendScopeForVars
                   ;

extendScopeForVars : ',' attribScopeForVars
                   |
                   ;

elseifStatement : ELSE statementElements
                |
                ;

attribWithExpr : IDENT ATTRIB expression {
  if (symbolType == null) {
    symbolTable.addVar(head, new SymbolType(symbolTable.searchTypeFunc(head, $1), $1), (SymbolType) $3);
  }
  else {
    symbolTable.addVar(head, new SymbolType(symbolType, $1), (SymbolType) $3);
  }

 }
               ;

statementElements : statement
                  | '{' statementList '}'
                  ;

listSwitchCase : CASE validCaseSwitch statementSwitchCase
               | DEFAULT statementSwitchCase
               |
               ;

validCaseSwitch : IDENT   { $$ = symbolTable.modelateResult(head, new SymbolType("ident", $1)); }
                | NUM     { $$ = symbolTable.modelateResult(head, new SymbolType("num", $1)); }
                | TRUE    { $$ = new SymbolType("bool", $1); }
                | FALSE   { $$ = new SymbolType("bool", $1); }
                ;

statementSwitchCase : ':' statementElements listSwitchCase
                    ;

scopeFunVarDecls : typeVar { symbolType = $1; } listScopeFunVar
                 ;

listScopeFunVar : attribWithExpr extendListScopeFunVar
                ;

extendListScopeFunVar : ',' listScopeFunVar
                      |
                      ;

expressionForDecls : expression { symbolTable.validTypesLogic(head, (SymbolType) $1); }
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

expressionWithFun : IDENT '(' argsSourceList ')' { $$ = new SymbolType(symbolTable.searchTypeFunc(head, $1), $1); }
                  ;

argsSourceList : expression expressionList
               ;

expressionList : ',' argsSourceList
               |
               ;

expression : expression '+' expression { $$ = symbolTable.validTypesArit(head, (SymbolType) $1, (SymbolType) $3); }
           | expression '-' expression  { $$ = symbolTable.validTypesArit(head, (SymbolType) $1, (SymbolType) $3); }
           | expression '*' expression  { $$ = symbolTable.validTypesArit(head, (SymbolType) $1, (SymbolType) $3); }
           | expression DIV expression  { $$ = symbolTable.validTypesArit(head, (SymbolType) $1, (SymbolType) $3); }
           | expression MOD expression  { $$ = symbolTable.validTypesArit(head, (SymbolType) $1, (SymbolType) $3); }
           | expression OR expression  { $$ = symbolTable.validTypesLogic(head, (SymbolType) $1, (SymbolType) $3); }
           | expression AND expression  { $$ = symbolTable.validTypesLogic(head, (SymbolType) $1, (SymbolType) $3); }
           | expression '<' expression  { $$ = symbolTable.validTypesComp(head, (SymbolType) $1, (SymbolType) $3); }
           | expression LEQ expression  { $$ = symbolTable.validTypesComp(head, (SymbolType) $1, (SymbolType) $3); }
           | expression EQ expression  { $$ = symbolTable.validTypesComp(head, (SymbolType) $1, (SymbolType) $3); }
           | expression GEQ expression  { $$ = symbolTable.validTypesComp(head, (SymbolType) $1, (SymbolType) $3); }
           | expression '>' expression  { $$ = symbolTable.validTypesComp(head, (SymbolType) $1, (SymbolType) $3); }
           | expression NEQ expression  { $$ = symbolTable.validTypesComp(head, (SymbolType) $1, (SymbolType) $3); }
           | NOT expression { $$ = symbolTable.validTypesLogic(head, (SymbolType) $2); }
           | '(' expression ')' { $$ = (SymbolType) $2; }
           | expressionWithFun
           | validCaseSwitch { $$ = (SymbolType) $1; }
           ;

typeVar : DOUBLE { $$ = "double"; }
        | BOOL { $$ = "bool"; }
        | INT { $$ = "int"; }
        | STRING { $$ = "string"; }
        | IDENT { $$ = "ident"; }
        ;

// codigo
%%

  private SymbolTable symbolTable;
  private SymbolTree head;

  private Yylex lexer;
  int yyl_return;

  private String symbolType;
  private SymbolType currentSymbol;
  private String symbolName;
  private String scopeCode = "";

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