import java.io.*;

/**
* Anderson Jean Fraga
* 13180375
* Compiladores - Trabalho 1 - Exercicio 2
*/

%%
%init{
    try {
        writer = new PrintWriter("Lexica_02.html");
        writer.append("<html>\n<head>\n");
        writer.append("<style>\n");
        writer.append("body { background:#23241f; color:#fff; }\n");
        writer.append(".reserved { color:#f92672; }\n");
        writer.append(".identifiers { color:#66d9ef; }\n");
        writer.append(".strings { color:#e6db74; }\n");
        writer.append(".numbers { color:#ae81ff; }\n");
        writer.append(".comments { color:#75715e; }\n");
        writer.append(".operators { color:#a6e22e; }\n");
        writer.append(".scopes { color:#7f9f7f; }\n");
        writer.append("</style>\n");
        writer.append("<body>\n");
    }
    catch (FileNotFoundException e) {
    }
    catch (Exception e) {
    }
%init}

%eof{
    try {
        writer.append("</body>\n</html>");
        writer.flush();
        writer.close();
    }
    catch (Exception e) {
    }
%eof}

%{
    private PrintWriter writer = null;

    private void toHtml(String klass, String str) {
        if (str.length() == 0) {
            return;
        }

        try {
            writer.append("<span class=\"" + klass + "\">" + str + "</span>\n");
        }
        catch (Exception e) {
        }
    }

    private void toHtml(String str) {
        if (str.length() == 0) {
            return;
        }

        try {
            writer.append("<span>" + str + "</span>\n");
        }
        catch (Exception e) {
        }
    }

%}

%public
%class Lexica_02
%standalone
%ignorecase
%byaccj
%integer
%line
%char
%unicode

NEWLINE = \n|\r|\n\r

TAB = \t|\s+
digit  = [0-9]
letter = [a-zA-Z0-9_]
letterWithDot = [a-zA-Z0-9\.\*]

%%

=|==|\+|-|\*|\\|\|\||\/|\&\&|\~|\<|\+\+|\>|\<\=|\>\= {
    toHtml("operators", yytext());
}

import|final|static|public|protected|private|extends|implements {
    toHtml("reserved", yytext());
}

if|else|while|for {
    toHtml("reserved", yytext());
}

class|void|String|int|double|float|new|return|null|true|false {
    toHtml("identifiers", yytext());
}

\"{letter}+\" {
    toHtml("strings", yytext());
}

[-+]?{digit}*(\.{digit}+)? {
    toHtml("numbers", yytext());
}

\/\*(.|[\r\n]|\t)*?\*\/ { toHtml("comments", yytext()); }

{letterWithDot}+ { toHtml(yytext()); }

{NEWLINE} { toHtml("<br>"); }
{TAB} { toHtml("&nbsp;&nbsp;&nbsp;&nbsp;"); }

\{|\}|\[|\]|\(|\)|\,|\;|\.|\: { toHtml("scopes", yytext()); }

. { }
