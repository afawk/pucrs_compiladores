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
public final static short IDENT=258;
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
    6,    0,    8,    5,   10,    5,    5,   11,    7,   13,
    7,    7,   15,    9,   18,   12,   17,   17,   14,   14,
   19,   20,   20,   16,   16,   21,   21,   24,   21,   26,
   30,   21,   31,   32,   33,   21,   34,   35,   36,   21,
   37,   21,   39,   21,   40,   21,   21,   43,   25,   25,
   45,   42,   44,   44,   38,   38,   23,   29,   29,   41,
   41,   41,    3,    3,    3,    3,   46,   48,   22,   47,
   49,   49,   27,   27,   28,   28,   51,   51,   50,   50,
    4,   52,   53,   53,    2,    2,    2,    2,    2,    2,
    2,    2,    2,    2,    2,    2,    2,    2,    2,    2,
    2,    1,    1,    1,    1,    1,
};
final static short yylen[] = {                            2,
    0,    2,    0,    4,    0,    4,    0,    0,    3,    0,
    5,    1,    0,    8,    0,    3,    2,    0,    2,    0,
    2,    3,    0,    2,    0,    2,    2,    0,    4,    0,
    0,   11,    0,    0,    0,    8,    0,    0,    0,    9,
    0,    7,    0,    3,    0,    3,    7,    0,    3,    0,
    0,    5,    2,    0,    2,    0,    3,    1,    3,    3,
    2,    0,    1,    1,    1,    1,    3,    0,    3,    2,
    2,    0,    1,    0,    3,    0,    2,    0,    1,    1,
    4,    2,    2,    0,    3,    3,    3,    3,    3,    3,
    3,    3,    3,    3,    3,    3,    3,    2,    3,    1,
    1,    1,    1,    1,    1,    1,
};
final static short yydefred[] = {                         1,
    0,    0,  106,  104,  103,    0,  102,  105,    0,    2,
    5,    3,    0,    0,   13,    6,    8,   10,    4,   12,
    0,    0,    0,    0,    0,    0,    9,   15,    0,   21,
    0,    0,   19,    0,    0,    0,    0,    0,   16,   11,
    0,    0,   33,   37,    0,    0,    0,   43,   45,   68,
    0,    0,    0,    0,   22,   17,    0,    0,    0,   38,
   30,    0,   64,    0,   65,   66,    0,    0,    0,  101,
  100,    0,    0,    0,    0,   24,   26,   27,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   44,   46,    0,    0,   69,   14,    0,    0,    0,
   58,    0,   48,    0,    0,    0,    0,   99,   91,   90,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   29,    0,   70,    0,    0,    0,    0,    0,    0,
    0,    0,   82,   81,   71,    0,    0,    0,   35,   59,
    0,    0,   49,    0,    0,    0,   83,   63,    0,    0,
   61,   47,    0,    0,    0,    0,    0,   42,   60,    0,
   36,    0,    0,    0,    0,   55,   67,   40,    0,   79,
   80,    0,   31,    0,   52,    0,   75,    0,   53,   77,
   32,
};
final static short yydgoto[] = {                          1,
   50,  116,   70,   71,   10,    2,   19,   14,   16,   13,
   22,   29,   23,   25,   21,   51,   39,   34,   26,   33,
  111,   53,   54,  101,  114,   83,  155,  175,  112,  188,
   59,  136,  163,   60,   82,  172,  115,  168,   72,   73,
  148,  153,  139,  185,  179,  161,  106,   74,  134,  182,
  187,  117,  143,
};
final static short yysindex[] = {                         0,
    0,   97,    0,    0,    0, -250,    0,    0, -244,    0,
    0,    0,  -23,   13,    0,    0,    0,    0,    0,    0,
  -45,   97, -233, -230,   -1,    6,    0,    0,   22,    0,
  -40,  -45,    0,   42,   97,  -79,    6, -233,    0,    0,
 -193,   53,    0,    0,   54,   59,  -31,    0,    0,    0,
  -21,  -79,   46,   49,    0,    0,  -31,  -31,   66,    0,
    0,  -31,    0,   70,    0,    0,  -31,  -31,   58,    0,
    0,   56,   65, -145,   97,    0,    0,    0,   58,  -30,
  -31, -117,  -45,   58,  -31,   58,  -19,  -31,  -31,  -31,
  -31,  -31,  -31,  -31,  -31,  -31,  -31,  -31,  -31,  -31,
   67,    0,    0, -193,   73,    0,    0,    2,   58,  -79,
    0, -140,    0,   75,   95,   47,   96,    0,    0,    0,
  -38,  -38,   69,   69,   69,   69,   69,   69,  -38, -239,
 -239,    0, -145,    0, -207,   98,   15,  110, -120,  -31,
 -117,  -31,    0,    0,    0,  -33,   93,   31,    0,    0,
  -31, -118,    0,   58,  105, -108,    0,    0,   93, -117,
    0,    0, -117,   58,  -31,  -76, -117,    0,    0, -207,
    0,  143,   58, -218,  147,    0,    0,    0,  145,    0,
    0,  150,    0, -120,    0,  -76,    0, -117,    0,    0,
    0,
};
final static short yyrindex[] = {                         0,
    0,  206,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  168,  206,    0,    0,    0,  169,    0,    0,    0,    0,
    0,    0,    0,  152,  206,  107,  169,    0,    0,    0,
  -46,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  107,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  -41,    0,    0,    0,    0,  162,    0,
    0,    0,    0,    0,  206,    0,    0,    0,  -28,    0,
    0,    0,  174,  193,    0,   86,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  176,    0,    0,    0,  197,  107,
    0,    0,    0,    0,    0,  209,    0,    0,    0,    0,
   14,   25,   87,  157,  158,  159,  163,  164,   36,   -8,
    3,    0,    0,    0,  126,    0,    0,    0,    0,  201,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  202,    0,  -98,    0,    0,    0,    0,
    0,    0,    0,  221,    0,  228,    0,    0,    0,  126,
    0,    0,    5,    0,    0,    0,    0,    0,  211,    0,
    0,  230,    0,    0,    0,  228,    0,    0,    0,    0,
    0,
};
final static short yygindex[] = {                         0,
  100,  291,  127,    0,    7,    0,    0,    0,  258,    0,
    0,  242,    0,    0,    0,  -42,    0,    0,  249,  245,
   23,    0,  -54,    0,    0,    0,    0,  106,  -44,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  113,  109,    0,    0,    0,  125,  161,    0,    0,    0,
    0,  149,    0,
};
final static int YYTABLESIZE=456;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         63,
   63,   63,   63,   63,   99,  110,  100,   11,   68,   76,
  108,   98,   99,   12,  100,   57,   15,   63,   63,  105,
   63,  118,   98,   99,   28,  100,   56,   30,   27,   96,
   57,   97,   85,   85,   85,   85,   85,   88,   89,   31,
   96,   40,   97,   86,   86,   86,   86,   86,   51,   32,
   85,   85,   15,   85,   88,   88,   18,   88,   52,  146,
  147,   86,   86,   51,   86,   89,   89,  137,   89,  180,
  181,   17,   88,   88,   52,   88,   87,   87,  105,   87,
   35,  107,   36,   89,   89,   38,   89,   57,   98,   99,
  142,  100,   58,   61,   87,   87,  156,   87,   62,   98,
   99,    9,  100,   75,   77,   81,   96,   78,   97,   85,
   98,   99,  104,  100,  102,  170,  133,   96,  171,   97,
   24,    9,  176,  103,  135,  132,   98,   94,  138,   98,
   94,   24,   52,  140,    9,  141,  144,  152,  149,  150,
   41,    4,    5,  191,   98,   94,    7,    8,   42,  151,
  160,   43,   44,   45,   46,  162,   47,   48,   49,   56,
   56,   56,  165,  166,  167,   56,   56,   56,   56,   56,
   56,   56,   56,   56,    9,   56,   56,   56,   41,    4,
    5,  174,  113,  178,    7,    8,   42,  183,  184,   43,
   44,   45,   46,  186,   47,   48,   49,   97,   93,   95,
   97,   93,   95,   92,   96,    7,   92,   96,   20,   23,
   18,  106,    3,    4,    5,   97,   93,   95,    7,    8,
   28,   92,   96,   63,  158,   63,   64,   65,   66,   65,
   66,   25,   50,   41,   72,   63,   63,   34,   88,   89,
   63,   63,   63,   63,   63,   63,   88,   89,   67,   84,
   62,   90,   91,   92,   93,   94,   95,   88,   89,   74,
   73,   39,   90,   91,   92,   93,   94,   95,   76,   54,
   78,   20,  159,   85,   85,   85,   85,   85,   85,   56,
   37,   55,  177,  169,   86,   86,   86,   86,   86,   86,
  157,  190,  189,  145,    0,   88,   88,   88,   88,   88,
   88,    0,    0,    0,    0,    0,   89,   89,   89,   89,
   89,   89,    0,    0,    0,    0,    0,   87,   87,   87,
   87,   87,   87,   88,   89,    0,    0,    0,   90,   91,
   92,   93,   94,   95,   88,   89,    0,   69,    0,   90,
   91,   92,   93,   94,   95,   88,   89,   79,   80,    0,
   90,   91,   84,    0,    3,    4,    5,   86,   87,    6,
    7,    8,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  109,    0,    0,    0,    0,    0,    0,  119,  120,
  121,  122,  123,  124,  125,  126,  127,  128,  129,  130,
  131,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  154,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  164,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  173,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         41,
   42,   43,   44,   45,   43,  123,   45,  258,   40,   52,
   41,   42,   43,  258,   45,   44,   40,   59,   60,   74,
   62,   41,   42,   43,  258,   45,  125,  258,   22,   60,
   59,   62,   41,   42,   43,   44,   45,  277,  278,   41,
   60,   35,   62,   41,   42,   43,   44,   45,   44,   44,
   59,   60,   40,   62,   41,   42,   44,   44,   36,  267,
  268,   59,   60,   59,   62,   41,   42,  110,   44,  288,
  289,   59,   59,   60,   52,   62,   41,   42,  133,   44,
   59,   75,  123,   59,   60,   44,   62,  281,   42,   43,
   44,   45,   40,   40,   59,   60,  141,   62,   40,   42,
   43,    2,   45,  125,   59,   40,   60,   59,   62,   40,
   42,   43,  258,   45,   59,  160,   44,   60,  163,   62,
   21,   22,  167,   59,  123,   59,   41,   41,  269,   44,
   44,   32,  110,   59,   35,   41,   41,  258,   41,  125,
  258,  259,  260,  188,   59,   59,  264,  265,  266,   40,
   58,  269,  270,  271,  272,  125,  274,  275,  276,  258,
  259,  260,  281,   59,  273,  264,  265,  266,  267,  268,
  269,  270,  271,  272,   75,  274,  275,  276,  258,  259,
  260,  258,   83,   41,  264,  265,  266,   41,   44,  269,
  270,  271,  272,   44,  274,  275,  276,   41,   41,   41,
   44,   44,   44,   41,   41,    0,   44,   44,   41,   41,
   59,  258,  258,  259,  260,   59,   59,   59,  264,  265,
   59,   59,   59,  257,  258,  257,  258,  261,  262,  261,
  262,  125,   59,   41,   59,  277,  278,   41,  277,  278,
  282,  283,  284,  285,  286,  287,  277,  278,  280,   41,
  125,  282,  283,  284,  285,  286,  287,  277,  278,   59,
   59,   41,  282,  283,  284,  285,  286,  287,   41,   59,
   41,   14,  146,  282,  283,  284,  285,  286,  287,   38,
   32,   37,  170,  159,  282,  283,  284,  285,  286,  287,
  142,  186,  184,  133,   -1,  282,  283,  284,  285,  286,
  287,   -1,   -1,   -1,   -1,   -1,  282,  283,  284,  285,
  286,  287,   -1,   -1,   -1,   -1,   -1,  282,  283,  284,
  285,  286,  287,  277,  278,   -1,   -1,   -1,  282,  283,
  284,  285,  286,  287,  277,  278,   -1,   47,   -1,  282,
  283,  284,  285,  286,  287,  277,  278,   57,   58,   -1,
  282,  283,   62,   -1,  258,  259,  260,   67,   68,  263,
  264,  265,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   81,   -1,   -1,   -1,   -1,   -1,   -1,   88,   89,
   90,   91,   92,   93,   94,   95,   96,   97,   98,   99,
  100,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  140,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,  151,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,  165,
};
}
final static short YYFINAL=1;
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
null,null,null,null,null,null,null,"NUM","IDENT","INT","BOOL","TRUE","FALSE",
"VOID","DOUBLE","STRING","SWITCH","CASE","DEFAULT","WHILE","DO","FOR","IF",
"ELSE","RETURN","BREAK","CONTINUE","AND","OR","XOR","NOT","ATTRIB","DIV","MOD",
"EQ","NEQ","LEQ","GEQ","INCREMENT","DECREMENT",
};
final static String yyrule[] = {
"$accept : program",
"$$1 :",
"program : $$1 declList",
"$$2 :",
"declList : typeVar IDENT $$2 declarations",
"$$3 :",
"declList : VOID IDENT $$3 methodDecl",
"declList :",
"$$4 :",
"declarations : ';' $$4 declList",
"$$5 :",
"declarations : ',' $$5 listNameDecls ';' declList",
"declarations : methodDecl",
"$$6 :",
"methodDecl : '(' $$6 argsList ')' '{' statementList '}' declList",
"$$7 :",
"listNameDecls : IDENT $$7 nameDecls",
"nameDecls : ',' listNameDecls",
"nameDecls :",
"argsList : argRule argsListComma",
"argsList :",
"argRule : typeVar IDENT",
"argsListComma : ',' argRule argsListComma",
"argsListComma :",
"statementList : statement statementList",
"statementList :",
"statement : scopeFunVarDecls ';'",
"statement : attribWithExpr ';'",
"$$8 :",
"statement : RETURN expression $$8 ';'",
"$$9 :",
"$$10 :",
"statement : FOR '(' $$9 scopeForVarDecls ';' expressionForDecls ';' counterForDecls ')' $$10 statementElements",
"$$11 :",
"$$12 :",
"$$13 :",
"statement : WHILE $$11 '(' expression $$12 ')' $$13 statementElements",
"$$14 :",
"$$15 :",
"$$16 :",
"statement : DO $$14 $$15 statementElements WHILE '(' expression $$16 ')'",
"$$17 :",
"statement : IF '(' expression $$17 ')' statementElements elseifStatement",
"$$18 :",
"statement : BREAK $$18 ';'",
"$$19 :",
"statement : CONTINUE $$19 ';'",
"statement : SWITCH '(' expression ')' '{' listSwitchCase '}'",
"$$20 :",
"scopeForVarDecls : typeVar $$20 attribScopeForVars",
"scopeForVarDecls :",
"$$21 :",
"attribScopeForVars : IDENT ATTRIB expression $$21 extendScopeForVars",
"extendScopeForVars : ',' attribScopeForVars",
"extendScopeForVars :",
"elseifStatement : ELSE statementElements",
"elseifStatement :",
"attribWithExpr : IDENT ATTRIB expression",
"statementElements : statement",
"statementElements : '{' statementList '}'",
"listSwitchCase : CASE validCaseSwitch statementSwitchCase",
"listSwitchCase : DEFAULT statementSwitchCase",
"listSwitchCase :",
"validCaseSwitch : IDENT",
"validCaseSwitch : NUM",
"validCaseSwitch : TRUE",
"validCaseSwitch : FALSE",
"statementSwitchCase : ':' statementElements listSwitchCase",
"$$22 :",
"scopeFunVarDecls : typeVar $$22 listScopeFunVar",
"listScopeFunVar : attribWithExpr extendListScopeFunVar",
"extendListScopeFunVar : ',' listScopeFunVar",
"extendListScopeFunVar :",
"expressionForDecls : expression",
"expressionForDecls :",
"counterForDecls : IDENT operatorIncr listCounterForDecls",
"counterForDecls :",
"listCounterForDecls : ',' counterForDecls",
"listCounterForDecls :",
"operatorIncr : INCREMENT",
"operatorIncr : DECREMENT",
"expressionWithFun : IDENT '(' argsSourceList ')'",
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
"typeVar : IDENT",
};

//#line 185 "grammar.yacc"

  private SymbolTable symbolTable;
  private SymbolTree head;

  private Yylex lexer;
  int yyl_return;

  private String symbolType;
  private SymbolType currentSymbol;
  private String symbolName;
  private String scopeCode = "";

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
    symbolTable = new SymbolTable(lexer);
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
//#line 553 "Parser.java"
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
case 1:
//#line 26 "grammar.yacc"
{ head = new SymbolTree(new SymbolType("_main", "_main")); }
break;
case 3:
//#line 29 "grammar.yacc"
{ currentSymbol = new SymbolType(val_peek(1).sval, val_peek(0).sval); }
break;
case 5:
//#line 30 "grammar.yacc"
{ currentSymbol = new SymbolType("void", val_peek(0).sval); }
break;
case 8:
//#line 34 "grammar.yacc"
{ head = symbolTable.addVar(head, currentSymbol); }
break;
case 10:
//#line 35 "grammar.yacc"
{ head = symbolTable.addVar(head, currentSymbol); }
break;
case 13:
//#line 39 "grammar.yacc"
{ symbolType = null; head = symbolTable.addFunc(head, currentSymbol); }
break;
case 15:
//#line 42 "grammar.yacc"
{ head = symbolTable.addVar(head, currentSymbol.changeName(val_peek(0).sval)); }
break;
case 21:
//#line 53 "grammar.yacc"
{ head = symbolTable.addArg(head, new SymbolType(val_peek(1).sval, val_peek(0).sval)); }
break;
case 26:
//#line 65 "grammar.yacc"
{ symbolType = null; }
break;
case 27:
//#line 66 "grammar.yacc"
{ symbolType = null; }
break;
case 28:
//#line 67 "grammar.yacc"
{ symbolTable.validReturn(head, (SymbolType) val_peek(0).obj); }
break;
case 30:
//#line 68 "grammar.yacc"
{ head = symbolTable.addScope(head, "for"); }
break;
case 31:
//#line 68 "grammar.yacc"
{ symbolTable.scoppedLoopIncr(); }
break;
case 33:
//#line 69 "grammar.yacc"
{ head = symbolTable.addScope(head, "while"); }
break;
case 34:
//#line 69 "grammar.yacc"
{ symbolTable.validTypesLogic(head, (SymbolType) val_peek(0).obj); }
break;
case 35:
//#line 69 "grammar.yacc"
{ symbolTable.scoppedLoopIncr(); }
break;
case 37:
//#line 70 "grammar.yacc"
{ head = symbolTable.addScope(head, "do-while"); }
break;
case 38:
//#line 70 "grammar.yacc"
{ symbolTable.scoppedLoopIncr(); }
break;
case 39:
//#line 70 "grammar.yacc"
{ symbolTable.validTypesLogic(head, (SymbolType) val_peek(0).obj); }
break;
case 41:
//#line 71 "grammar.yacc"
{ symbolTable.validTypesLogic(head, (SymbolType) val_peek(0).obj); }
break;
case 43:
//#line 72 "grammar.yacc"
{ symbolTable.scoppedLoopCheck("break"); }
break;
case 45:
//#line 73 "grammar.yacc"
{ symbolTable.scoppedLoopCheck("continue"); }
break;
case 48:
//#line 77 "grammar.yacc"
{ symbolType = val_peek(0).sval; }
break;
case 51:
//#line 81 "grammar.yacc"
{ symbolTable.addVar(head, new SymbolType(symbolType, val_peek(2).sval), (SymbolType) val_peek(0).obj); }
break;
case 57:
//#line 92 "grammar.yacc"
{
  if (symbolType == null) {
    symbolTable.addVar(head, new SymbolType(symbolTable.searchTypeFunc(head, val_peek(2).sval), val_peek(2).sval), (SymbolType) val_peek(0).obj);
  }
  else {
    symbolTable.addVar(head, new SymbolType(symbolType, val_peek(2).sval), (SymbolType) val_peek(0).obj);
  }

 }
break;
case 63:
//#line 112 "grammar.yacc"
{ yyval.obj = symbolTable.modelateResult(head, new SymbolType("ident", val_peek(0).sval)); }
break;
case 64:
//#line 113 "grammar.yacc"
{ yyval.obj = symbolTable.modelateResult(head, new SymbolType("num", val_peek(0).sval)); }
break;
case 65:
//#line 114 "grammar.yacc"
{ yyval.obj = new SymbolType("bool", val_peek(0).sval); }
break;
case 66:
//#line 115 "grammar.yacc"
{ yyval.obj = new SymbolType("bool", val_peek(0).sval); }
break;
case 68:
//#line 121 "grammar.yacc"
{ symbolType = val_peek(0).sval; }
break;
case 73:
//#line 131 "grammar.yacc"
{ symbolTable.validTypesLogic(head, (SymbolType) val_peek(0).obj); }
break;
case 81:
//#line 147 "grammar.yacc"
{ yyval.obj = new SymbolType(symbolTable.searchTypeFunc(head, val_peek(3).sval), val_peek(3).sval); }
break;
case 85:
//#line 157 "grammar.yacc"
{ yyval.obj = symbolTable.validTypesArit(head, (SymbolType) val_peek(2).obj, (SymbolType) val_peek(0).obj); }
break;
case 86:
//#line 158 "grammar.yacc"
{ yyval.obj = symbolTable.validTypesArit(head, (SymbolType) val_peek(2).obj, (SymbolType) val_peek(0).obj); }
break;
case 87:
//#line 159 "grammar.yacc"
{ yyval.obj = symbolTable.validTypesArit(head, (SymbolType) val_peek(2).obj, (SymbolType) val_peek(0).obj); }
break;
case 88:
//#line 160 "grammar.yacc"
{ yyval.obj = symbolTable.validTypesArit(head, (SymbolType) val_peek(2).obj, (SymbolType) val_peek(0).obj); }
break;
case 89:
//#line 161 "grammar.yacc"
{ yyval.obj = symbolTable.validTypesArit(head, (SymbolType) val_peek(2).obj, (SymbolType) val_peek(0).obj); }
break;
case 90:
//#line 162 "grammar.yacc"
{ yyval.obj = symbolTable.validTypesLogic(head, (SymbolType) val_peek(2).obj, (SymbolType) val_peek(0).obj); }
break;
case 91:
//#line 163 "grammar.yacc"
{ yyval.obj = symbolTable.validTypesLogic(head, (SymbolType) val_peek(2).obj, (SymbolType) val_peek(0).obj); }
break;
case 92:
//#line 164 "grammar.yacc"
{ yyval.obj = symbolTable.validTypesComp(head, (SymbolType) val_peek(2).obj, (SymbolType) val_peek(0).obj); }
break;
case 93:
//#line 165 "grammar.yacc"
{ yyval.obj = symbolTable.validTypesComp(head, (SymbolType) val_peek(2).obj, (SymbolType) val_peek(0).obj); }
break;
case 94:
//#line 166 "grammar.yacc"
{ yyval.obj = symbolTable.validTypesComp(head, (SymbolType) val_peek(2).obj, (SymbolType) val_peek(0).obj); }
break;
case 95:
//#line 167 "grammar.yacc"
{ yyval.obj = symbolTable.validTypesComp(head, (SymbolType) val_peek(2).obj, (SymbolType) val_peek(0).obj); }
break;
case 96:
//#line 168 "grammar.yacc"
{ yyval.obj = symbolTable.validTypesComp(head, (SymbolType) val_peek(2).obj, (SymbolType) val_peek(0).obj); }
break;
case 97:
//#line 169 "grammar.yacc"
{ yyval.obj = symbolTable.validTypesComp(head, (SymbolType) val_peek(2).obj, (SymbolType) val_peek(0).obj); }
break;
case 98:
//#line 170 "grammar.yacc"
{ yyval.obj = symbolTable.validTypesLogic(head, (SymbolType) val_peek(0).obj); }
break;
case 99:
//#line 171 "grammar.yacc"
{ yyval.obj = (SymbolType) val_peek(1).obj; }
break;
case 101:
//#line 173 "grammar.yacc"
{ yyval.obj = (SymbolType) val_peek(0).obj; }
break;
case 102:
//#line 176 "grammar.yacc"
{ yyval.sval = "double"; }
break;
case 103:
//#line 177 "grammar.yacc"
{ yyval.sval = "bool"; }
break;
case 104:
//#line 178 "grammar.yacc"
{ yyval.sval = "int"; }
break;
case 105:
//#line 179 "grammar.yacc"
{ yyval.sval = "string"; }
break;
case 106:
//#line 180 "grammar.yacc"
{ yyval.sval = "ident"; }
break;
//#line 922 "Parser.java"
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
