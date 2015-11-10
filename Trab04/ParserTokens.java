
class ParserTokens
{
    public static final int BASE_TOKEN_NUM = 256;

	public static final int NUM = 257;
	public static final int IDENT = 258;
	public static final int INT = 259;
	public static final int BOOL = 260;
	public static final int TRUE = 261;
	public static final int FALSE = 262;
	public static final int VOID = 263;
	public static final int DOUBLE = 264;
	public static final int STRING = 265;
	public static final int SWITCH = 266;
	public static final int CASE = 267;
	public static final int DEFAULT = 268;
	public static final int WHILE = 269;
	public static final int DO = 270;
	public static final int FOR = 271;
	public static final int IF = 272;
	public static final int ELSE = 273;
	public static final int RETURN = 274;
	public static final int BREAK = 275;
	public static final int CONTINUE = 276;
	public static final int AND = 277;
	public static final int OR = 278;
	public static final int XOR = 279;
	public static final int NOT = 280;
	public static final int ATTRIB = 281;
	public static final int DIV = 282;
	public static final int MOD = 283;
	public static final int EQ = 284;
	public static final int NEQ = 285;
	public static final int LEQ = 286;
	public static final int GEQ = 287;
	public static final int INCREMENT = 288;
	public static final int DECREMENT = 289;
	public static final int LITERAL = 290;

    private static final String tokenList[] = {"","NUM", "IDENT", "INT", "BOOL", "TRUE", "FALSE", "VOID", "DOUBLE", "STRING", "SWITCH", "CASE", "DEFAULT", "WHILE", "DO", "FOR", "IF", "ELSE", "RETURN", "BREAK", "CONTINUE", "AND", "OR", "XOR", "NOT", "ATTRIB", "DIV", "MOD", "EQ", "NEQ", "LEQ", "GEQ", "INCREMENT", "DECREMENT", "LITERAL"};

    static public String get(int code)
    {
        return tokenList[code - BASE_TOKEN_NUM];
    }
}