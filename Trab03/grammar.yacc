
%{
  import java.io.*;
%}

%token NUM LITERAL
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

%type <sval> LITERAL

%%

program : declList
        ;

declList : typeVar LITERAL declarations ;
         |
         ;

declarations : ';' declList
             | ',' listNameDecls ';' declList
             | '(' argsList ')' '{' statementList '}' declList
             ;

listNameDecls : LITERAL nameDecls
              ;

nameDecls : ',' listNameDecls
          |
          ;

argsList : argRule argsListComma
         |
         ;

argRule : typeVar LITERAL
        ;

argsListComma : ',' argRule argsListComma
              |
              ;


statementList : statement statementList
              |
              ;

statement : scopeFunVarDecls ';'
          | attribWithExpr ';'
          | RETURN expression ';'
          | FOR '(' scopeForVarDecls ';' expressionForDecls ';' counterForDecls ')'  statementElements
          | WHILE '(' expression ')' statementElements
          | DO statementElements WHILE '(' expression ')'
          | IF '(' expression ')' statementElements
          | IF '(' expression ')' statementElements ELSE statementElements
          | BREAK ';'
          | CONTINUE ';'
          | SWITCH '(' expression ')' '{' listSwitchCase '}'
          ;

attribWithExpr : LITERAL ATTRIB expression
               ;

statementElements : statement
                  | '{' statementList '}'
                  ;

listSwitchCase : CASE validCaseSwitch statementSwitchCase
               | DEFAULT statementSwitchCase
               |
               ;

validCaseSwitch : LITERAL
                | NUM
                | TRUE
                | FALSE
                ;

statementSwitchCase : ':' statementElements listSwitchCase
                    ;

scopeFunVarDecls : typeVar listScopeFunVar
                 ;

listScopeFunVar : attribWithExpr extendListScopeFunVar
                ;

extendListScopeFunVar : ',' listScopeFunVar
                      |
                      ;

scopeForVarDecls : typeVar attribScopeForVars
                 |
                 ;

attribScopeForVars : LITERAL ATTRIB expression extendScopeForVars
                   ;

extendScopeForVars : ',' attribScopeForVars
                   |
                   ;

expressionForDecls : expression
                   |
                   ;

counterForDecls : LITERAL operatorIncr listCounterForDecls
                |
                ;

listCounterForDecls : ',' counterForDecls
                    |
                    ;

operatorIncr : INCREMENT
             | DECREMENT
             ;

expressionWithFun : LITERAL '(' argsSourceList ')'
                  ;

argsSourceList : expression expressionList
               ;

expressionList : ',' argsSourceList
               |
               ;

expression : expression '+' expression
           | expression '-' expression
           | expression '*' expression
           | expression DIV expression
           | expression MOD expression
           | expression OR expression
           | expression AND expression
           | expression '<' expression
           | expression LEQ expression
           | expression EQ expression
           | expression GEQ expression
           | expression '>' expression
           | expression NEQ expression
           | NOT expression
           | '(' expression ')'
           | expressionWithFun
           | validCaseSwitch
           ;

typeVar : DOUBLE
        | BOOL
        | INT
        | STRING
        | VOID
        | LITERAL

// codigo
%%

    private Yylex lexer;
    int yyl_return;

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