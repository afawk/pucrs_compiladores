import java.io.*;

class AsdrParser {

    public static void main(String[] args)
    {
        AsdrParser parser = null;

        try {
            if (args.length == 0)
                parser = new AsdrParser(new InputStreamReader(System.in));
            else
                parser = new AsdrParser(new java.io.FileReader(args[0]));

            laToken = parser.yylex();

            AsdrGrammar adsr = new AsdrGrammar(parser);
            adsr.run();

            if (parser.isEOF())
                System.out.println("\n\nSucesso!");
            else
                System.out.println("\n\nFalhou - esperado EOF.");
        }
        catch (java.io.FileNotFoundException e) {
            System.out.println("File not found : \""+args[0]+"\"");
        }
        catch (Exception e) {
            System.out.println("Ops! " + e.getMessage());
        }
    }

    private Yylex lexer;

    public ParserVal yylval;

    private static int laToken;
    private boolean debug = false;

    public AsdrParser(Reader r)
    {
        lexer = new Yylex(r, this);
    }

    public void setDebug(boolean debug)
    {
        this.debug = debug;
    }

    public int getLine()
    {
        return lexer.getLine();
    }

    public int yylex()
    {
        int retVal = -1;

        try {
            yylval = new ParserVal(0); //zera o valor do token
            retVal = lexer.yylex(); //le a entrada do arquivo e retorna um token
        }
        catch (IOException e) {
            System.err.println("IO Error:" + e);
        }

        return retVal; //retorna o token para o Parser
    }

    public boolean isEOF()
    {
        return laToken == Yylex.YYEOF;
    }

    public AsdrParser check(int expected)
    {
        if (laToken == expected) {
            debug("#" + (lexer.getLine() + 1) + " ____  " + lastString(), 0);

            //lbToken = laToken;
            laToken = this.yylex();
        }
        else {
            String expStr = (expected < ParserTokens.BASE_TOKEN_NUM)
                ? ""+(char)expected
                : ParserTokens.get(expected);

            errorExpected(expStr);
        }

        return this;
    }

    public boolean isActualToken(int possibleToken)
    {
        return laToken == possibleToken;
    }

    public int getActualToken()
    {
        return laToken;
    }

    public void yyerror(String msg)
    {
        debug(msg, 1);
    }

    public void consoleMsg(String msg)
    {
        debug(msg + " [ " + lastString() + " - " + yylval.sval + " ]", 0);
    }

    public void errorExpected(String expected)
    {
        errorExpected(expected, lastString());
    }

    public String lastString()
    {
      return (laToken < ParserTokens.BASE_TOKEN_NUM)
        ? new Character((char) laToken).toString()
        : ParserTokens.get(laToken);
    }

    public void errorExpected(String expected, String last)
    {
        debug("Parse Error: unexpected '" + last + "', expecting '" + expected + "' in line " + (lexer.getLine() + 1), 1);
    }

    public void debug(String msg, int exitable)
    {
        if (this.debug == false && exitable == 0) {
            return;
        }

        System.out.println(msg);

        if (exitable == 1) {
            System.exit(1);
        }
    }
}