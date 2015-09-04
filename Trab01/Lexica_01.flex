%%

/**
* Anderson Jean Fraga
* 13180375
* Compiladores - Trabalho 1 - Exercicio 1
*/

%public
%class Lexica_01
%standalone
%ignorecase

%unicode

%line

NEWLINE = \n|\r|\n\r

digit  = [0-9]
letter = [a-zA-Z0-9_]

Drive        = {letter}
Id           = ({letter}|{digit})({letter}|{digit})*
PathName     = {Id}
FileName     = {Id}
FileType     = {Id}

%%

^({Drive}\:)?(\\)?({PathName}\\)*{FileName}(\.{FileType})?$ {
    System.out.println("Está ok: " + yytext());
}

{NEWLINE} {}

.* { System.out.println("Está inválido: " + yytext()); }