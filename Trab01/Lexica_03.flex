%%

/**
* Anderson Jean Fraga
* 13180375
* Compiladores - Trabalho 1 - Exercicio 3
*/

%public
%class Lexica_03
%standalone
%ignorecase

%unicode

%line

NEWLINE = \t|\n|\r|\n\r

digit  = [0-9]
letter = [a-zA-Z0-9_\ ]

%%

\"{letter}+\" {
    String itemstr = new String(yytext());
    System.out.println("Code: STRING\n - Elem: " + itemstr.substring(1, itemstr.length() - 1) + "\n - Line: " + yyline);
}

"[" {
    System.out.println("Code: ABRE_COLCHETE\n - Elem: " + yytext() + "\n - Line: " + yyline);
}

"]" {
    System.out.println("Code: FECHA_COLCHETE\n - Elem: " + yytext() + "\n - Line: " + yyline);
}

"{" {
    System.out.println("Code: ABRE_CHAVE\n - Elem: " + yytext() + "\n - Line: " + yyline);
}

"}" {
    System.out.println("Code: FECHA_CHAVE\n - Elem: " + yytext() + "\n - Line: " + yyline);
}

"," {
    System.out.println("Code: VIRGULA\n - Elem: " + yytext() + "\n - Line: " + yyline);
}

":" {
    System.out.println("Code: DOIS-PONTOS\n - Elem: " + yytext() + "\n - Line: " + yyline);
}

[-+]?{digit}+   {
    System.out.println("Code: INTEIRO\n - Elem: " + yytext() + "\n - Line: " + yyline);
}

[-+]?{digit}*\.{digit}+ {
    System.out.println("Code: PONTO FLUTUANTE\n - Elem: " + yytext() + "\n - Line: " + yyline);
}

{NEWLINE}+ { }

. { }