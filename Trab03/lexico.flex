/**
* Anderson Jean Fraga
* 13180375
* Compiladores - Trabalho 3
*/

%%

%{
  private int comment_count = 0;

  private Parser yyparser;

  public Yylex(java.io.Reader r, Parser yyparser) {
    this(r);
    this.yyparser = yyparser;
  }

  public int getLine()
  {
    return yyline;
  }

%}


%byaccj
%integer
%line
%char
%standalone
%full

NUM=([0-9]+(\.[0-9]+)?)
LITERAL=([a-zA-Z]([a-zA-Z0-9\_]+)?)

NONNEWLINE_WHITE_SPACE_CHAR=[\ \t\b\012]
NEWLINE=\r|\n|\r\n

%%

"$TRACE_ON"   { yyparser.setDebug(true); }
"$TRACE_OFF"  { yyparser.setDebug(false); }

"int"      { return ParserTokens.INT; }
"bool"     { return ParserTokens.BOOL; }
"true"     { return ParserTokens.TRUE; }
"false"    { return ParserTokens.FALSE; }
"void"     { return ParserTokens.VOID; }
"double"   { return ParserTokens.DOUBLE; }
"string"   { return ParserTokens.STRING; }
"switch"   { return ParserTokens.SWITCH; }
"case"     { return ParserTokens.CASE; }
"default"  { return ParserTokens.DEFAULT; }
"while"    { return ParserTokens.WHILE; }
"do"       { return ParserTokens.DO; }
"for"      { return ParserTokens.FOR; }
"if"       { return ParserTokens.IF; }
"else"     { return ParserTokens.ELSE; }
"return"   { return ParserTokens.RETURN; }
"break"    { return ParserTokens.BREAK; }
"continue" { return ParserTokens.CONTINUE; }

"&&"  { return ParserTokens.AND; }
"AND" { return ParserTokens.AND; }
"||"  { return ParserTokens.OR; }
"OR"  { return ParserTokens.OR; }
"XOR" { return ParserTokens.XOR; }
"!"   { return ParserTokens.NOT; }
"NOT" { return ParserTokens.NOT; }
"="   { return ParserTokens.ATTRIB; }

"/" { return ParserTokens.DIV; }
"%" { return ParserTokens.MOD; }

"==" { return ParserTokens.EQ;  }
"!=" { return ParserTokens.NEQ; }
"<=" { return ParserTokens.LEQ; }
">=" { return ParserTokens.GEQ; }
"++" { return ParserTokens.INCREMENT; }
"--" { return ParserTokens.DECREMENT; }

">" |
"<" |
";" |
":" |
"," |
"{" |
"}" |
"(" |
")" |
"[" |
"]" |
"+" |
"-" |
"*"  { return yytext().charAt(0); }


{NUM}  {
  yyparser.yylval = new ParserVal(yytext());
  return ParserTokens.NUM;
}

{LITERAL} {
  yyparser.yylval = new ParserVal(yytext());
  return ParserTokens.LITERAL;
}

[ \t]+ {  }

"//" [^\n]* { }

{NEWLINE} { }

. { System.out.println("Erro lexico: caracter invalido: <" + yytext() + ">"); }
