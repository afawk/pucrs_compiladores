
%%

%{
  private int comment_count = 0;

  private AsdrParser yyparser;

  public Yylex(java.io.Reader r, AsdrParser yyparser) {
    this(r);
    this.yyparser = yyparser;
  }

  public int getLine()
  {
    return yyline;
  }


%}

%integer
%line
%char

WHITE_SPACE_CHAR=[\n\r\ \t\b\012]

%%

"$TRACE_ON"   { yyparser.setDebug(true); }
"$TRACE_OFF"  { yyparser.setDebug(false); }

"int"       { return ParserTokens.INT; }
"bool"      { return ParserTokens.BOOL; }
"void" 		{ return ParserTokens.VOID; }
"double"	{ return ParserTokens.DOUBLE; }
"while"	 	{ return ParserTokens.WHILE; }
"if"		{ return ParserTokens.IF; }
"else"      { return ParserTokens.ELSE; }
"return"	{ return ParserTokens.RETURN; }

[:jletter:][:jletterdigit:]* { return ParserTokens.IDENT; }

[0-9]+ 	{ return ParserTokens.NUM; }

"&&" { return ParserTokens.AND_OP; }
"||" { return ParserTokens.OR_OP; }
"!"  { return ParserTokens.NOT_OP; }

">" |
"<" |
";" |
"," |
"{" |
"}" |
"(" |
")" |
"+" |
"-" |
"*" |
"/" |
"="    	{ return yytext().charAt(0); }


{WHITE_SPACE_CHAR}+ { }

. { System.out.println("Erro lexico: caracter invalido: <" + yytext() + ">"); }
