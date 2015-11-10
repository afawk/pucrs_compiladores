//### This file created by BYACC 1.8(/Java extension  1.15)
//### Java capabilities added 7 Jan 97, Bob Jamison
//### Updated : 27 Nov 97  -- Bob Jamison, Joe Nieten
//###           01 Jan 98  -- Bob Jamison -- fixed generic semantic constructor
//###           01 Jun 99  -- Bob Jamison -- added Runnable support
//###           06 Aug 00  -- Bob Jamison -- made state variables class-global
//###           03 Jan 01  -- Bob Jamison -- improved flags, tracing
//###           16 May 01  -- Bob Jamison -- added custom stack sizing
//###           04 Mar 02  -- Yuval Oren  -- improved java performance, added options
//###           14 Mar 02  -- Tomas Hurka -- -d support, static initializer workaround
//### Please send bug reports to tom@hukatronic.cz
//### static char yysccsid[] = "@(#)yaccpar	1.8 (Berkeley) 01/20/90";






//#line 3 "grammar.yacc"
  import java.io.*;
//#line 19 "Parser.java"




public class Parser
{

boolean yydebug;        //do I want debug output?
int yynerrs;            //number of errors so far
int yyerrflag;          //was there an error?
int yychar;             //the current working character

//########## MESSAGES ##########
//###############################################################
// method: debug
//###############################################################
void debug(String msg)
{
  if (yydebug)
    System.out.println(msg);
}

//########## STATE STACK ##########
final static int YYSTACKSIZE = 500;  //maximum stack size
int statestk[] = new int[YYSTACKSIZE]; //state stack
int stateptr;
int stateptrmax;                     //highest index of stackptr
int statemax;                        //state when highest index reached
//###############################################################
// methods: state stack push,pop,drop,peek
//###############################################################
final void state_push(int state)
{
  try {
		stateptr++;
		statestk[stateptr]=state;
	 }
	 catch (ArrayIndexOutOfBoundsException e) {
     int oldsize = statestk.length;
     int newsize = oldsize * 2;
     int[] newstack = new int[newsize];
     System.arraycopy(statestk,0,newstack,0,oldsize);
     statestk = newstack;
     statestk[stateptr]=state;
  }
}
final int state_pop()
{
  return statestk[stateptr--];
}
final void state_drop(int cnt)
{
  stateptr -= cnt; 
}
final int state_peek(int relative)
{
  return statestk[stateptr-relative];
}
//###############################################################
// method: init_stacks : allocate and prepare stacks
//###############################################################
final boolean init_stacks()
{
  stateptr = -1;
  val_init();
  return true;
}
//###############################################################
// method: dump_stacks : show n levels of the stacks
//###############################################################
void dump_stacks(int count)
{
int i;
  System.out.println("=index==state====value=     s:"+stateptr+"  v:"+valptr);
  for (i=0;i<count;i++)
    System.out.println(" "+i+"    "+statestk[i]+"      "+valstk[i]);
  System.out.println("======================");
}


//########## SEMANTIC VALUES ##########
//public class ParserVal is defined in ParserVal.java


String   yytext;//user variable to return contextual strings
ParserVal yyval; //used to return semantic vals from action routines
ParserVal yylval;//the 'lval' (result) I got from yylex()
ParserVal valstk[];
int valptr;
//###############################################################
// methods: value stack push,pop,drop,peek.
//###############################################################
void val_init()
{
  valstk=new ParserVal[YYSTACKSIZE];
  yyval=new ParserVal();
  yylval=new ParserVal();
  valptr=-1;
}
void val_push(ParserVal val)
{
  if (valptr>=YYSTACKSIZE)
    return;
  valstk[++valptr]=val;
}
ParserVal val_pop()
{
  if (valptr<0)
    return new ParserVal();
  return valstk[valptr--];
}
void val_drop(int cnt)
{
int ptr;
  ptr=valptr-cnt;
  if (ptr<0)
    return;
  valptr = ptr;
}
ParserVal val_peek(int relative)
{
int ptr;
  ptr=valptr-relative;
  if (ptr<0)
    return new ParserVal();
  return valstk[ptr];
}
final ParserVal dup_yyval(ParserVal val)
{
  ParserVal dup = new ParserVal();
  dup.ival = val.ival;
  dup.dval = val.dval;
  dup.sval = val.sval;
  dup.obj = val.obj;
  return dup;
}
//#### end semantic value section ####
public final static short NUM=257;
public final static short LITERAL=258;
public final static short INT=259;
public final static short BOOL=260;
public final static short TRUE=261;
public final static short FALSE=262;
public final static short VOID=263;
public final static short DOUBLE=264;
public final static short STRING=265;
public final static short SWITCH=266;
public final static short CASE=267;
public final static short DEFAULT=268;
public final static short WHILE=269;
public final static short DO=270;
public final static short FOR=271;
public final static short IF=272;
public final static short ELSE=273;
public final static short RETURN=274;
public final static short BREAK=275;
public final static short CONTINUE=276;
public final static short AND=277;
public final static short OR=278;
public final static short XOR=279;
public final static short NOT=280;
public final static short ATTRIB=281;
public final static short DIV=282;
public final static short MOD=283;
public final static short EQ=284;
public final static short NEQ=285;
public final static short LEQ=286;
public final static short GEQ=287;
public final static short INCREMENT=288;
public final static short DECREMENT=289;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    4,    1,    1,    3,    3,    3,    5,    8,    8,
    6,    6,    9,   10,   10,    7,    7,   11,   11,   11,
   11,   11,   11,   11,   11,   11,   11,   11,   13,   18,
   18,   19,   19,   19,   20,   20,   20,   20,   21,   12,
   22,   23,   23,   15,   15,   24,   25,   25,   16,   16,
   17,   17,   27,   27,   26,   26,   28,   29,   30,   30,
   14,   14,   14,   14,   14,   14,   14,   14,   14,   14,
   14,   14,   14,   14,   14,   14,   14,    2,    2,    2,
    2,    2,    2,
};
final static short yylen[] = {                            2,
    1,    0,    4,    0,    2,    4,    7,    2,    2,    0,
    2,    0,    2,    3,    0,    2,    0,    2,    2,    3,
    9,    5,    6,    5,    7,    2,    2,    7,    3,    1,
    3,    3,    2,    0,    1,    1,    1,    1,    3,    2,
    2,    2,    0,    2,    0,    4,    2,    0,    1,    0,
    3,    0,    2,    0,    1,    1,    4,    2,    2,    0,
    3,    3,    3,    3,    3,    3,    3,    3,    3,    3,
    3,    3,    3,    2,    3,    1,    1,    1,    1,    1,
    1,    1,    1,
};
final static short yydefred[] = {                         0,
   83,   80,   79,   82,   78,   81,    0,    1,    0,    2,
    0,    0,    0,    0,    3,    0,    0,    0,    5,    0,
    0,   13,    0,    0,   11,    0,    8,    0,    0,    0,
    9,    6,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   14,    0,    0,    0,
    0,   30,    0,    0,    0,   36,    0,   37,   38,    0,
    0,    0,   77,   76,   26,   27,    0,    0,   40,    0,
   16,   18,   19,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   20,    0,   41,
    7,    0,    0,   31,    0,    0,   44,    0,    0,    0,
    0,   75,   67,   66,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   42,    0,   22,    0,    0,
    0,    0,    0,    0,   58,   57,    0,    0,    0,   23,
    0,    0,    0,   59,   35,    0,    0,   33,   28,    0,
   46,    0,    0,   25,   32,    0,   47,   55,   56,    0,
    0,   39,    0,   51,   21,   53,
};
final static short yydgoto[] = {                          7,
    8,   42,   15,   11,   21,   17,   43,   27,   18,   25,
   52,   45,   46,  110,   80,  132,  153,   53,  139,   63,
  148,   69,  100,  107,  151,  160,  164,   64,  111,  135,
};
final static short yysindex[] = {                       217,
    0,    0,    0,    0,    0,    0,    0,    0, -252,    0,
   42,  217,  217, -238,    0, -227,   -5,  -36,    0,    7,
  -20,    0,  -65,  217,    0, -238,    0,  217,  162,  -36,
    0,    0, -219,   43,   52,  -81,   63,   64,  -31,   34,
   46, -150,  -11,  162,   56,   57,    0,  -31,  -31,  -31,
  162,    0, -148,  217,  -31,    0,   79,    0,    0,  -31,
  -31,   80,    0,    0,    0,    0, -219,   82,    0,  217,
    0,    0,    0,  113,  -30,  -19,   -1,   87, -128,   73,
   -8,  -31,  113,    3,  -31,  -31,  -31,  -31,  -31,  -31,
  -31,  -31,  -31,  -31,  -31,  -31,  -31,    0, -150,    0,
    0,   15,  -81,    0,  -31, -144,    0,  -31,  -81,   91,
  100,    0,    0,    0,  -38,  -38,  124,  124,  124,  124,
  124,  124,  -38, -250, -250,    0, -218,    0,   14,  -31,
  113,   84, -125,  -31,    0,    0,  -33,   92,   24,    0,
  102, -106,  -81,    0,    0,   92,  -81,    0,    0, -128,
    0, -228,  116,    0,    0, -218,    0,    0,    0,  110,
  -81,    0, -106,    0,    0,    0,
};
final static short yyrindex[] = {                       159,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  119,  159,    0,    0,    0,    0,  120,    0,  104,
    0,    0,    0,    0,    0,    0,    0,  159,   40,  120,
    0,    0,  -88,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   40,    0,    0,    0,    0,    0,    0,
   40,    0,    0,  112,    0,    0,  -41,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  115,    0,  159,
    0,    0,    0,   -6,    0,    0,    0,    0,    0,    0,
    0,    0,   31,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  117,    0,  131,
    0,    0,    0,    0,   47,   58,   53,  175,  176,  370,
  414,  415,   69,   25,   36,    0,   61,    0,    0,    0,
  128,    0,  -61,    0,    0,    0,    0,    0,    0,    0,
  141,  160,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   61,    0,    0,    0,  171,
    0,    0,  160,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    1,  168,    0,    0,  192,    0,  -34,    0,  197,  193,
   -4,    0,  -26,  355,    0,    0,   70,  310,   76,  114,
  125,  151,    0,  111,    0,    0,    0,    0,  126,    0,
};
final static int YYTABLESIZE=485;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         35,
   35,   35,   35,   35,   96,   10,   97,   24,   61,   71,
  102,   95,   96,   19,   97,   68,   77,   35,   35,   20,
   35,  103,   95,   96,   44,   97,   85,   86,   32,   93,
   22,   94,  109,   95,   96,   23,   97,   29,   28,   44,
   93,   51,   94,  112,   95,   96,   44,   97,  137,  138,
   26,   93,   29,   94,  140,   95,   96,   29,   97,  158,
  159,   48,   93,   24,   94,   61,   61,   61,   61,   61,
  101,   74,   68,   93,   74,   94,   62,   62,   62,   62,
   62,   12,   49,   61,   61,   14,   61,   64,   64,   74,
   64,   50,   65,   70,   62,   62,   70,   62,   65,   65,
   13,   65,   54,   55,   66,   64,   64,   67,   64,   63,
   63,   70,   63,   70,   72,   73,   65,   65,   82,   65,
   78,   95,   96,  104,   97,   99,  105,   63,   63,  106,
   63,  108,   95,   96,  134,   97,  130,  127,   98,   93,
  136,   94,  142,   95,   96,  150,   97,  143,  149,  147,
   93,  152,   94,  163,   95,   96,  161,   97,    4,   12,
   15,   93,   10,   94,   17,   95,   96,    9,   97,   83,
   45,   60,   93,   43,   94,   50,   33,    2,    3,   16,
    9,    4,    5,    6,   34,   34,   49,   35,   36,   37,
   38,   16,   39,   40,   41,    9,   24,   24,   24,   48,
   52,   24,   24,   24,   24,   24,   24,   24,   24,   24,
   24,   54,   24,   24,   24,   73,   69,   31,   73,   69,
   30,   79,   47,   56,  145,   56,   57,   58,   59,   58,
   59,  162,  166,   73,   69,   35,   35,    9,   85,   86,
   35,   35,   35,   35,   35,   35,   85,   86,   60,  126,
  146,   87,   88,   89,   90,   91,   92,   85,   86,  144,
  157,    0,   87,   88,   89,   90,   91,   92,   85,   86,
  155,    0,    0,   87,   88,   89,   90,   91,   92,   85,
   86,    0,    0,    0,   87,   88,   89,   90,   91,   92,
   85,   86,    0,    0,    0,   87,   88,   89,   90,   91,
   92,    0,    0,    0,    0,    0,   61,   61,   61,   61,
   61,   61,    0,    0,    0,    0,    0,   62,   62,   62,
   62,   62,   62,    0,    0,    0,    0,    0,   64,   64,
   64,   64,   64,   64,    0,    0,    0,    0,    0,   65,
   65,   65,   65,   65,   65,    0,    0,    0,    0,    0,
   63,   63,   63,   63,   63,   63,   85,   86,    0,    0,
    0,   87,   88,   89,   90,   91,   92,   85,   86,    0,
    0,    0,   87,   88,   89,   90,   91,   92,   85,   86,
    0,    0,    0,   87,   88,   89,   90,   91,   92,   85,
   86,    0,    0,   62,   87,   88,   89,   90,   91,   92,
   85,   86,   74,   75,   76,   87,   88,    0,    0,   81,
   71,    0,  128,   71,   83,   84,    0,    0,  133,   33,
    2,    3,    0,    0,    4,    5,    6,   34,   71,    0,
   35,   36,   37,   38,    0,   39,   40,   41,    0,  113,
  114,  115,  116,  117,  118,  119,  120,  121,  122,  123,
  124,  125,  154,    0,   68,   72,  156,   68,   72,  129,
    0,    0,  131,    0,    0,    0,    0,    0,    0,    0,
  165,    0,   68,   72,    1,    2,    3,    0,    0,    4,
    5,    6,    0,    0,  141,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         41,
   42,   43,   44,   45,   43,  258,   45,   44,   40,   44,
   41,   42,   43,   13,   45,   42,   51,   59,   60,  258,
   62,   41,   42,   43,   29,   45,  277,  278,   28,   60,
  258,   62,   41,   42,   43,   41,   45,   44,   59,   44,
   60,  123,   62,   41,   42,   43,   51,   45,  267,  268,
   44,   60,   59,   62,   41,   42,   43,  123,   45,  288,
  289,  281,   60,  125,   62,   41,   42,   43,   44,   45,
   70,   41,   99,   60,   44,   62,   41,   42,   43,   44,
   45,   40,   40,   59,   60,   44,   62,   41,   42,   59,
   44,   40,   59,   41,   59,   60,   44,   62,   41,   42,
   59,   44,   40,   40,   59,   59,   60,  258,   62,   41,
   42,   59,   44,  125,   59,   59,   59,   60,   40,   62,
  269,   42,   43,  125,   45,   44,   40,   59,   60,  258,
   62,   59,   42,   43,   44,   45,  281,  123,   59,   60,
   41,   62,   59,   42,   43,   44,   45,  273,  125,   58,
   60,  258,   62,   44,   42,   43,   41,   45,    0,   41,
   41,   60,   59,   62,  125,   42,   43,    0,   45,  258,
   59,   41,   60,   59,   62,   59,  258,  259,  260,   12,
   13,  263,  264,  265,  266,  125,   59,  269,  270,  271,
  272,   24,  274,  275,  276,   28,  258,  259,  260,   59,
   41,  263,  264,  265,  266,  267,  268,  269,  270,  271,
  272,   41,  274,  275,  276,   41,   41,   26,   44,   44,
   24,   54,   30,  257,  258,  257,  258,  261,  262,  261,
  262,  156,  163,   59,   59,  277,  278,   70,  277,  278,
  282,  283,  284,  285,  286,  287,  277,  278,  280,   99,
  137,  282,  283,  284,  285,  286,  287,  277,  278,  134,
  150,   -1,  282,  283,  284,  285,  286,  287,  277,  278,
  146,   -1,   -1,  282,  283,  284,  285,  286,  287,  277,
  278,   -1,   -1,   -1,  282,  283,  284,  285,  286,  287,
  277,  278,   -1,   -1,   -1,  282,  283,  284,  285,  286,
  287,   -1,   -1,   -1,   -1,   -1,  282,  283,  284,  285,
  286,  287,   -1,   -1,   -1,   -1,   -1,  282,  283,  284,
  285,  286,  287,   -1,   -1,   -1,   -1,   -1,  282,  283,
  284,  285,  286,  287,   -1,   -1,   -1,   -1,   -1,  282,
  283,  284,  285,  286,  287,   -1,   -1,   -1,   -1,   -1,
  282,  283,  284,  285,  286,  287,  277,  278,   -1,   -1,
   -1,  282,  283,  284,  285,  286,  287,  277,  278,   -1,
   -1,   -1,  282,  283,  284,  285,  286,  287,  277,  278,
   -1,   -1,   -1,  282,  283,  284,  285,  286,  287,  277,
  278,   -1,   -1,   39,  282,  283,  284,  285,  286,  287,
  277,  278,   48,   49,   50,  282,  283,   -1,   -1,   55,
   41,   -1,  103,   44,   60,   61,   -1,   -1,  109,  258,
  259,  260,   -1,   -1,  263,  264,  265,  266,   59,   -1,
  269,  270,  271,  272,   -1,  274,  275,  276,   -1,   85,
   86,   87,   88,   89,   90,   91,   92,   93,   94,   95,
   96,   97,  143,   -1,   41,   41,  147,   44,   44,  105,
   -1,   -1,  108,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  161,   -1,   59,   59,  258,  259,  260,   -1,   -1,  263,
  264,  265,   -1,   -1,  130,
};
}
final static short YYFINAL=7;
final static short YYMAXTOKEN=289;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,"'('","')'","'*'","'+'","','",
"'-'",null,null,null,null,null,null,null,null,null,null,null,null,"':'","';'",
"'<'",null,"'>'",null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
"'{'",null,"'}'",null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,"NUM","LITERAL","INT","BOOL","TRUE","FALSE",
"VOID","DOUBLE","STRING","SWITCH","CASE","DEFAULT","WHILE","DO","FOR","IF",
"ELSE","RETURN","BREAK","CONTINUE","AND","OR","XOR","NOT","ATTRIB","DIV","MOD",
"EQ","NEQ","LEQ","GEQ","INCREMENT","DECREMENT",
};
final static String yyrule[] = {
"$accept : program",
"program : declList",
"$$1 :",
"declList : typeVar LITERAL $$1 declarations",
"declList :",
"declarations : ';' declList",
"declarations : ',' listNameDecls ';' declList",
"declarations : '(' argsList ')' '{' statementList '}' declList",
"listNameDecls : LITERAL nameDecls",
"nameDecls : ',' listNameDecls",
"nameDecls :",
"argsList : argRule argsListComma",
"argsList :",
"argRule : typeVar LITERAL",
"argsListComma : ',' argRule argsListComma",
"argsListComma :",
"statementList : statement statementList",
"statementList :",
"statement : scopeFunVarDecls ';'",
"statement : attribWithExpr ';'",
"statement : RETURN expression ';'",
"statement : FOR '(' scopeForVarDecls ';' expressionForDecls ';' counterForDecls ')' statementElements",
"statement : WHILE '(' expression ')' statementElements",
"statement : DO statementElements WHILE '(' expression ')'",
"statement : IF '(' expression ')' statementElements",
"statement : IF '(' expression ')' statementElements ELSE statementElements",
"statement : BREAK ';'",
"statement : CONTINUE ';'",
"statement : SWITCH '(' expression ')' '{' listSwitchCase '}'",
"attribWithExpr : LITERAL ATTRIB expression",
"statementElements : statement",
"statementElements : '{' statementList '}'",
"listSwitchCase : CASE validCaseSwitch statementSwitchCase",
"listSwitchCase : DEFAULT statementSwitchCase",
"listSwitchCase :",
"validCaseSwitch : LITERAL",
"validCaseSwitch : NUM",
"validCaseSwitch : TRUE",
"validCaseSwitch : FALSE",
"statementSwitchCase : ':' statementElements listSwitchCase",
"scopeFunVarDecls : typeVar listScopeFunVar",
"listScopeFunVar : attribWithExpr extendListScopeFunVar",
"extendListScopeFunVar : ',' listScopeFunVar",
"extendListScopeFunVar :",
"scopeForVarDecls : typeVar attribScopeForVars",
"scopeForVarDecls :",
"attribScopeForVars : LITERAL ATTRIB expression extendScopeForVars",
"extendScopeForVars : ',' attribScopeForVars",
"extendScopeForVars :",
"expressionForDecls : expression",
"expressionForDecls :",
"counterForDecls : LITERAL operatorIncr listCounterForDecls",
"counterForDecls :",
"listCounterForDecls : ',' counterForDecls",
"listCounterForDecls :",
"operatorIncr : INCREMENT",
"operatorIncr : DECREMENT",
"expressionWithFun : LITERAL '(' argsSourceList ')'",
"argsSourceList : expression expressionList",
"expressionList : ',' argsSourceList",
"expressionList :",
"expression : expression '+' expression",
"expression : expression '-' expression",
"expression : expression '*' expression",
"expression : expression DIV expression",
"expression : expression MOD expression",
"expression : expression OR expression",
"expression : expression AND expression",
"expression : expression '<' expression",
"expression : expression LEQ expression",
"expression : expression EQ expression",
"expression : expression GEQ expression",
"expression : expression '>' expression",
"expression : expression NEQ expression",
"expression : NOT expression",
"expression : '(' expression ')'",
"expression : expressionWithFun",
"expression : validCaseSwitch",
"typeVar : DOUBLE",
"typeVar : BOOL",
"typeVar : INT",
"typeVar : STRING",
"typeVar : VOID",
"typeVar : LITERAL",
};

//#line 169 "grammar.yacc"

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
//#line 508 "Parser.java"
//###############################################################
// method: yylexdebug : check lexer state
//###############################################################
void yylexdebug(int state,int ch)
{
String s=null;
  if (ch < 0) ch=0;
  if (ch <= YYMAXTOKEN) //check index bounds
     s = yyname[ch];    //now get it
  if (s==null)
    s = "illegal-symbol";
  debug("state "+state+", reading "+ch+" ("+s+")");
}





//The following are now global, to aid in error reporting
int yyn;       //next next thing to do
int yym;       //
int yystate;   //current parsing state from state table
String yys;    //current token string


//###############################################################
// method: yyparse : parse input and execute indicated items
//###############################################################
int yyparse()
{
boolean doaction;
  init_stacks();
  yynerrs = 0;
  yyerrflag = 0;
  yychar = -1;          //impossible char forces a read
  yystate=0;            //initial state
  state_push(yystate);  //save it
  val_push(yylval);     //save empty value
  while (true) //until parsing is done, either correctly, or w/error
    {
    doaction=true;
    if (yydebug) debug("loop"); 
    //#### NEXT ACTION (from reduction table)
    for (yyn=yydefred[yystate];yyn==0;yyn=yydefred[yystate])
      {
      if (yydebug) debug("yyn:"+yyn+"  state:"+yystate+"  yychar:"+yychar);
      if (yychar < 0)      //we want a char?
        {
        yychar = yylex();  //get next token
        if (yydebug) debug(" next yychar:"+yychar);
        //#### ERROR CHECK ####
        if (yychar < 0)    //it it didn't work/error
          {
          yychar = 0;      //change it to default string (no -1!)
          if (yydebug)
            yylexdebug(yystate,yychar);
          }
        }//yychar<0
      yyn = yysindex[yystate];  //get amount to shift by (shift index)
      if ((yyn != 0) && (yyn += yychar) >= 0 &&
          yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
        {
        if (yydebug)
          debug("state "+yystate+", shifting to state "+yytable[yyn]);
        //#### NEXT STATE ####
        yystate = yytable[yyn];//we are in a new state
        state_push(yystate);   //save it
        val_push(yylval);      //push our lval as the input for next rule
        yychar = -1;           //since we have 'eaten' a token, say we need another
        if (yyerrflag > 0)     //have we recovered an error?
           --yyerrflag;        //give ourselves credit
        doaction=false;        //but don't process yet
        break;   //quit the yyn=0 loop
        }

    yyn = yyrindex[yystate];  //reduce
    if ((yyn !=0 ) && (yyn += yychar) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
      {   //we reduced!
      if (yydebug) debug("reduce");
      yyn = yytable[yyn];
      doaction=true; //get ready to execute
      break;         //drop down to actions
      }
    else //ERROR RECOVERY
      {
      if (yyerrflag==0)
        {
        yyerror("syntax error");
        yynerrs++;
        }
      if (yyerrflag < 3) //low error count?
        {
        yyerrflag = 3;
        while (true)   //do until break
          {
          if (stateptr<0)   //check for under & overflow here
            {
            yyerror("stack underflow. aborting...");  //note lower case 's'
            return 1;
            }
          yyn = yysindex[state_peek(0)];
          if ((yyn != 0) && (yyn += YYERRCODE) >= 0 &&
                    yyn <= YYTABLESIZE && yycheck[yyn] == YYERRCODE)
            {
            if (yydebug)
              debug("state "+state_peek(0)+", error recovery shifting to state "+yytable[yyn]+" ");
            yystate = yytable[yyn];
            state_push(yystate);
            val_push(yylval);
            doaction=false;
            break;
            }
          else
            {
            if (yydebug)
              debug("error recovery discarding state "+state_peek(0)+" ");
            if (stateptr<0)   //check for under & overflow here
              {
              yyerror("Stack underflow. aborting...");  //capital 'S'
              return 1;
              }
            state_pop();
            val_pop();
            }
          }
        }
      else            //discard this token
        {
        if (yychar == 0)
          return 1; //yyabort
        if (yydebug)
          {
          yys = null;
          if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
          if (yys == null) yys = "illegal-symbol";
          debug("state "+yystate+", error recovery discards token "+yychar+" ("+yys+")");
          }
        yychar = -1;  //read another
        }
      }//end error recovery
    }//yyn=0 loop
    if (!doaction)   //any reason not to proceed?
      continue;      //skip action
    yym = yylen[yyn];          //get count of terminals on rhs
    if (yydebug)
      debug("state "+yystate+", reducing "+yym+" by rule "+yyn+" ("+yyrule[yyn]+")");
    if (yym>0)                 //if count of rhs not 'nil'
      yyval = val_peek(yym-1); //get current semantic value
    yyval = dup_yyval(yyval); //duplicate yyval if ParserVal is used as semantic value
    switch(yyn)
      {
//########## USER-SUPPLIED ACTIONS ##########
case 2:
//#line 28 "grammar.yacc"
{ System.out.println(val_peek(0).sval); }
break;
case 78:
//#line 160 "grammar.yacc"
{ System.out.println("I AM DOUBLE"); }
break;
case 79:
//#line 161 "grammar.yacc"
{ System.out.println("I AM BOOL"); }
break;
case 80:
//#line 162 "grammar.yacc"
{ System.out.println("I AM INT"); }
break;
case 81:
//#line 163 "grammar.yacc"
{ System.out.println("I AM STRING"); }
break;
case 82:
//#line 164 "grammar.yacc"
{ System.out.println("I AM VOID"); }
break;
case 83:
//#line 165 "grammar.yacc"
{ System.out.println("I AM LITERAL"); }
break;
//#line 685 "Parser.java"
//########## END OF USER-SUPPLIED ACTIONS ##########
    }//switch
    //#### Now let's reduce... ####
    if (yydebug) debug("reduce");
    state_drop(yym);             //we just reduced yylen states
    yystate = state_peek(0);     //get new state
    val_drop(yym);               //corresponding value drop
    yym = yylhs[yyn];            //select next TERMINAL(on lhs)
    if (yystate == 0 && yym == 0)//done? 'rest' state and at first TERMINAL
      {
      if (yydebug) debug("After reduction, shifting from state 0 to state "+YYFINAL+"");
      yystate = YYFINAL;         //explicitly say we're done
      state_push(YYFINAL);       //and save it
      val_push(yyval);           //also save the semantic value of parsing
      if (yychar < 0)            //we want another character?
        {
        yychar = yylex();        //get next character
        if (yychar<0) yychar=0;  //clean, if necessary
        if (yydebug)
          yylexdebug(yystate,yychar);
        }
      if (yychar == 0)          //Good exit (if lex returns 0 ;-)
         break;                 //quit the loop--all DONE
      }//if yystate
    else                        //else not done yet
      {                         //get next state and push, for next yydefred[]
      yyn = yygindex[yym];      //find out where to go
      if ((yyn != 0) && (yyn += yystate) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yystate)
        yystate = yytable[yyn]; //get new state
      else
        yystate = yydgoto[yym]; //else go to new defred
      if (yydebug) debug("after reduction, shifting from state "+state_peek(0)+" to state "+yystate+"");
      state_push(yystate);     //going again, so push state & val...
      val_push(yyval);         //for next action
      }
    }//main loop
  return 0;//yyaccept!!
}
//## end of method parse() ######################################



//## run() --- for Thread #######################################
/**
 * A default run method, used for operating this parser
 * object in the background.  It is intended for extending Thread
 * or implementing Runnable.  Turn off with -Jnorun .
 */
public void run()
{
  yyparse();
}
//## end of method run() ########################################



//## Constructors ###############################################
/**
 * Default constructor.  Turn off with -Jnoconstruct .

 */
public Parser()
{
  //nothing to do
}


/**
 * Create a parser, setting the debug to true or false.
 * @param debugMe true for debugging, false for no debug.
 */
public Parser(boolean debugMe)
{
  yydebug=debugMe;
}
//###############################################################



}
//################### END OF CLASS ##############################
