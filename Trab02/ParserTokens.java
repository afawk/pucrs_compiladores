
class ParserTokens
{
    public static final int BASE_TOKEN_NUM = 301;

    // next is 289
    public static final int IDENT  = 301;
    public static final int NUM    = 302;
    public static final int WHILE  = 303;
    public static final int IF     = 304;
    public static final int INT    = 305;
    public static final int BOOL   = 306;
    public static final int DOUBLE = 307;
    public static final int ELSE   = 308;
    public static final int VOID   = 309;
    public static final int RETURN = 310;
    public static final int AND_OP = 311;
    public static final int OR_OP  = 312;
    public static final int NOT_OP = 313;

    private static final String tokenList[] = {
      "IDENT","NUM", "WHILE", "IF", "INT", "BOOL", "DOUBLE", "ELSE", "VOID", "RETURN",
      "AND_OP", "OR_OP", "NOT_OP"
    };

    static public String get(int code)
    {
        return tokenList[code - BASE_TOKEN_NUM];
    }

    static public boolean isTypeDecl(int code)
    {
        return (code == VOID || code == NUM || code == INT || code == BOOL || code == DOUBLE);
    }

}
