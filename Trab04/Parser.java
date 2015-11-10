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
    0,    7,    5,    9,    5,    5,   10,    6,   12,    6,
    6,   14,    8,   17,   11,   16,   16,   13,   13,   18,
   19,   19,   15,   15,   20,   20,   23,   20,   28,   20,
   29,   30,   20,   31,   32,   20,   33,   20,   35,   20,
   36,   20,   20,   34,   34,   22,   27,   27,   37,   37,
   37,    3,    3,    3,    3,   38,   40,   21,   39,   41,
   41,   24,   24,   42,   43,   43,   25,   25,   26,   26,
   45,   45,   44,   44,    4,   46,   47,   47,    2,    2,
    2,    2,    2,    2,    2,    2,    2,    2,    2,    2,
    2,    2,    2,    2,    2,    1,    1,    1,    1,    1,
};
final static short yylen[] = {                            2,
    1,    0,    4,    0,    4,    0,    0,    3,    0,    5,
    1,    0,    8,    0,    3,    2,    0,    2,    0,    2,
    3,    0,    2,    0,    2,    2,    0,    4,    0,   10,
    0,    0,    7,    0,    0,    8,    0,    6,    0,    3,
    0,    3,    7,    2,    0,    3,    1,    3,    3,    2,
    0,    1,    1,    1,    1,    3,    0,    3,    2,    2,
    0,    2,    0,    4,    2,    0,    1,    0,    3,    0,
    2,    0,    1,    1,    4,    2,    2,    0,    3,    3,
    3,    3,    3,    3,    3,    3,    3,    3,    3,    3,
    3,    2,    3,    1,    1,    1,    1,    1,    1,    1,
};
final static short yydefred[] = {                         0,
  100,   98,   97,    0,   96,   99,    0,    0,    1,    4,
    2,    0,    0,    0,    5,    7,    9,    3,   11,    0,
    0,    0,    0,    0,   20,   12,    0,   18,    8,   14,
    0,    0,    0,    0,    0,    0,   21,    0,   15,   10,
    0,    0,    0,   34,    0,    0,    0,   39,   41,   57,
    0,    0,    0,    0,   16,    0,    0,    0,    0,    0,
    0,   53,    0,   54,   55,    0,    0,    0,   95,   94,
    0,    0,    0,    0,   23,   25,   26,    0,    0,    0,
    0,   47,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   40,   42,    0,    0,   58,   13,    0,
    0,    0,    0,    0,   62,    0,    0,    0,    0,   93,
   85,   84,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   28,    0,   59,    0,   32,   48,    0,
    0,    0,    0,    0,    0,   76,   75,   60,    0,    0,
    0,    0,    0,    0,    0,    0,   38,   77,   52,    0,
    0,   50,   43,   33,    0,    0,   64,    0,    0,   44,
   49,    0,   36,   65,   73,   74,    0,   29,   56,    0,
   69,    0,   71,   30,
};
final static short yydgoto[] = {                          7,
   50,  118,   69,   70,    9,   18,   13,   15,   12,   23,
   31,   24,   21,   32,   51,   39,   34,   22,   28,   82,
   53,   54,  103,   85,  143,  169,   83,  182,  111,  152,
   59,  165,  117,  157,   71,   72,  151,  162,  108,   73,
  136,  115,  167,  177,  181,  119,  146,
};
final static short yysindex[] = {                       108,
    0,    0,    0, -244,    0,    0,    0, -238,    0,    0,
    0,  -15,   -2,  -75,    0,    0,    0,    0,    0, -230,
   -1,    9,  108, -198,    0,    0,  -75,    0,    0,    0,
   24,  -37,    9,   44,  108,  -78,    0, -198,    0,    0,
 -188,   54,   57,    0,   59,   68,  -31,    0,    0,    0,
  -12,  -78,   62,   65,    0,  -31,  -31,  -31, -117,  -75,
  -31,    0,   76,    0,    0,  -31,  -31,   69,    0,    0,
   67,   85, -141,  108,    0,    0,    0,   69,  -30,   69,
  -78,    0, -134, -119,   87,   69,  -31,   69,  -19,  -31,
  -31,  -31,  -31,  -31,  -31,  -31,  -31,  -31,  -31,  -31,
  -31,  -31,   91,    0,    0, -188,  107,    0,    0,   33,
  122,   39,  135,  -86,    0,  -31,  166,   47,  169,    0,
    0,    0,  -38,  -38,   80,   80,   80,   80,   80,   80,
  -38, -228, -228,    0, -141,    0, -196,    0,    0,  -31,
  -31,   69,  152,  -61,  -31,    0,    0,    0, -124,  155,
   89, -117,   69,   58,  -43, -117,    0,    0,    0,  155,
 -117,    0,    0,    0,  175, -119,    0, -207,  179,    0,
    0, -196,    0,    0,    0,    0,  177,    0,    0,  -43,
    0, -117,    0,    0,
};
final static short yyrindex[] = {                       222,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  184,    0,    0,    0,    0,    0,    0,
    0,  187,  222,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  187,  170,  222,  109,    0,    0,    0,    0,
  -26,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  109,    0,    0,    0,    0,    0,    0,    0,  174,
    0,    0,  -41,    0,    0,    0,    0,  176,    0,    0,
    0,    0,    0,  222,    0,    0,    0,  -28,    0,  197,
  109,    0,    0,    0,    0,  209,    0,   20,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  192,    0,    0,    0,
    0,    0,    0,    0,    0,  201,    0,  220,    0,    0,
    0,    0,   14,   25,   86,  158,  159,  160,  164,  165,
   36,   -8,    3,    0,    0,    0,  137,    0,    0,    0,
    0,  210,    0,  -98,    0,    0,    0,    0,    0,    0,
    0,    0,  229,  212,  231,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  137,    0,    0,    0,    0,  232,    0,    0,  231,
    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
  105,  303,  131,    0,   -6,    0,    0,  268,    0,    0,
  244,    0,    0,    0,  -42,    0,    0,  256,  251,   23,
    0,  -65,    0,    0,    0,  111,  -46,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  120,  133,  167,    0,
    0,  128,    0,    0,    0,  150,    0,
};
final static int YYTABLESIZE=444;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         52,
   52,   52,   52,   52,  101,   81,  102,  107,   67,   75,
  110,  100,  101,   10,  102,   46,   29,   52,   52,   11,
   52,  120,  100,  101,   14,  102,   45,   25,   40,   98,
   46,   99,   79,   79,   79,   79,   79,   14,  112,   26,
   98,   17,   99,   80,   80,   80,   80,   80,   90,   91,
   79,   79,   27,   79,   82,   82,   16,   82,   52,   30,
   92,   80,   80,   92,   80,   83,   83,  109,   83,  107,
  149,  150,   82,   82,   52,   82,   81,   81,   92,   81,
  175,  176,   35,   83,   83,   36,   83,   38,  100,  101,
  145,  102,   56,   57,   81,   81,   58,   81,   60,  100,
  101,  166,  102,   52,    8,  164,   98,   61,   99,  170,
  100,  101,   74,  102,  172,   87,  106,   98,   20,   99,
   76,  100,  101,   77,  102,  104,   88,    8,   98,   88,
   99,   20,   62,  159,  113,  184,   64,   65,  114,    8,
   41,    2,    3,  105,   88,  116,    5,    6,   42,  134,
  135,   43,   44,   45,   46,  137,   47,   48,   49,   45,
   45,   45,  138,  139,   84,   45,   45,   45,   45,   45,
   45,   45,   45,   45,  140,   45,   45,   45,    8,   41,
    2,    3,    1,    2,    3,    5,    6,   42,    5,    6,
   43,   44,   45,   46,  141,   47,   48,   49,   91,   87,
   89,   91,   87,   89,   86,   90,  144,   86,   90,  147,
  155,  156,  161,  163,  168,  173,   91,   87,   89,  178,
  180,    6,   86,   90,   19,   62,   63,   22,   17,   64,
   65,  100,   63,   24,   27,   52,   52,   31,   90,   91,
   52,   52,   52,   52,   52,   52,   90,   91,   66,   37,
   61,   92,   93,   94,   95,   96,   97,   90,   91,   68,
   78,   51,   92,   93,   94,   95,   96,   97,   67,   35,
   66,   70,   72,   79,   79,   79,   79,   79,   79,  160,
   19,   55,   33,   37,   80,   80,   80,   80,   80,   80,
  183,  179,  171,  174,  158,   82,   82,   82,   82,   82,
   82,  148,    0,    0,    0,    0,   83,   83,   83,   83,
   83,   83,    0,    0,    0,    0,    0,   81,   81,   81,
   81,   81,   81,   90,   91,    0,    0,    0,   92,   93,
   94,   95,   96,   97,   90,   91,    0,    0,    0,   92,
   93,   94,   95,   96,   97,   90,   91,    0,    0,   68,
   92,   93,   94,   95,   96,   97,   90,   91,   78,   79,
   80,   92,   93,   86,    0,    1,    2,    3,   88,   89,
    4,    5,    6,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  121,  122,  123,  124,  125,  126,  127,  128,
  129,  130,  131,  132,  133,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  142,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  153,  154,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         41,
   42,   43,   44,   45,   43,  123,   45,   73,   40,   52,
   41,   42,   43,  258,   45,   44,   23,   59,   60,  258,
   62,   41,   42,   43,   40,   45,  125,  258,   35,   60,
   59,   62,   41,   42,   43,   44,   45,   40,   81,   41,
   60,   44,   62,   41,   42,   43,   44,   45,  277,  278,
   59,   60,   44,   62,   41,   42,   59,   44,   36,  258,
   41,   59,   60,   44,   62,   41,   42,   74,   44,  135,
  267,  268,   59,   60,   52,   62,   41,   42,   59,   44,
  288,  289,   59,   59,   60,  123,   62,   44,   42,   43,
   44,   45,  281,   40,   59,   60,   40,   62,   40,   42,
   43,   44,   45,   81,    0,  152,   60,   40,   62,  156,
   42,   43,  125,   45,  161,   40,  258,   60,   14,   62,
   59,   42,   43,   59,   45,   59,   41,   23,   60,   44,
   62,   27,  257,  258,  269,  182,  261,  262,  258,   35,
  258,  259,  260,   59,   59,   59,  264,  265,  266,   59,
   44,  269,  270,  271,  272,  123,  274,  275,  276,  258,
  259,  260,   41,  125,   60,  264,  265,  266,  267,  268,
  269,  270,  271,  272,   40,  274,  275,  276,   74,  258,
  259,  260,  258,  259,  260,  264,  265,  266,  264,  265,
  269,  270,  271,  272,  281,  274,  275,  276,   41,   41,
   41,   44,   44,   44,   41,   41,   41,   44,   44,   41,
   59,  273,   58,  125,  258,   41,   59,   59,   59,   41,
   44,    0,   59,   59,   41,  257,  258,   41,   59,  261,
  262,  258,   59,  125,   59,  277,  278,   41,  277,  278,
  282,  283,  284,  285,  286,  287,  277,  278,  280,   41,
   59,  282,  283,  284,  285,  286,  287,  277,  278,   59,
   41,  125,  282,  283,  284,  285,  286,  287,   59,   41,
   59,   41,   41,  282,  283,  284,  285,  286,  287,  149,
   13,   38,   27,   33,  282,  283,  284,  285,  286,  287,
  180,  172,  160,  166,  145,  282,  283,  284,  285,  286,
  287,  135,   -1,   -1,   -1,   -1,  282,  283,  284,  285,
  286,  287,   -1,   -1,   -1,   -1,   -1,  282,  283,  284,
  285,  286,  287,  277,  278,   -1,   -1,   -1,  282,  283,
  284,  285,  286,  287,  277,  278,   -1,   -1,   -1,  282,
  283,  284,  285,  286,  287,  277,  278,   -1,   -1,   47,
  282,  283,  284,  285,  286,  287,  277,  278,   56,   57,
   58,  282,  283,   61,   -1,  258,  259,  260,   66,   67,
  263,  264,  265,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   90,   91,   92,   93,   94,   95,   96,   97,
   98,   99,  100,  101,  102,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  116,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,  140,  141,
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
null,null,null,null,null,null,null,"NUM","IDENT","INT","BOOL","TRUE","FALSE",
"VOID","DOUBLE","STRING","SWITCH","CASE","DEFAULT","WHILE","DO","FOR","IF",
"ELSE","RETURN","BREAK","CONTINUE","AND","OR","XOR","NOT","ATTRIB","DIV","MOD",
"EQ","NEQ","LEQ","GEQ","INCREMENT","DECREMENT",
};
final static String yyrule[] = {
"$accept : program",
"program : declList",
"$$1 :",
"declList : typeVar IDENT $$1 declarations",
"$$2 :",
"declList : VOID IDENT $$2 methodDecl",
"declList :",
"$$3 :",
"declarations : ';' $$3 declList",
"$$4 :",
"declarations : ',' $$4 listNameDecls ';' declList",
"declarations : methodDecl",
"$$5 :",
"methodDecl : '(' argsList ')' $$5 '{' statementList '}' declList",
"$$6 :",
"listNameDecls : IDENT $$6 nameDecls",
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
"$$7 :",
"statement : RETURN expression $$7 ';'",
"$$8 :",
"statement : FOR '(' scopeForVarDecls ';' expressionForDecls ';' counterForDecls ')' $$8 statementElements",
"$$9 :",
"$$10 :",
"statement : WHILE '(' expression $$9 ')' $$10 statementElements",
"$$11 :",
"$$12 :",
"statement : DO $$11 statementElements WHILE '(' expression $$12 ')'",
"$$13 :",
"statement : IF '(' expression $$13 ')' elseifStatement",
"$$14 :",
"statement : BREAK $$14 ';'",
"$$15 :",
"statement : CONTINUE $$15 ';'",
"statement : SWITCH '(' expression ')' '{' listSwitchCase '}'",
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
"$$16 :",
"scopeFunVarDecls : typeVar $$16 listScopeFunVar",
"listScopeFunVar : attribWithExpr extendListScopeFunVar",
"extendListScopeFunVar : ',' listScopeFunVar",
"extendListScopeFunVar :",
"scopeForVarDecls : typeVar attribScopeForVars",
"scopeForVarDecls :",
"attribScopeForVars : IDENT ATTRIB expression extendScopeForVars",
"extendScopeForVars : ',' attribScopeForVars",
"extendScopeForVars :",
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

//#line 182 "grammar.yacc"

  private SymbolTable symbolTable;

  private Yylex lexer;
  int yyl_return;

  private SymbolType symbolType;
  private String symbolName;

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
//#line 535 "Parser.java"
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
//#line 31 "grammar.yacc"
{ symbolType = (SymbolType) val_peek(1).obj; symbolName = val_peek(0).sval; }
break;
case 4:
//#line 32 "grammar.yacc"
{ symbolType = new SymbolType("void"); symbolName = val_peek(0).sval; }
break;
case 7:
//#line 36 "grammar.yacc"
{ symbolTable.addVar(symbolType, symbolName); }
break;
case 9:
//#line 37 "grammar.yacc"
{ symbolTable.addVar(symbolType, symbolName); }
break;
case 12:
//#line 41 "grammar.yacc"
{ symbolTable.addFunc(symbolType, symbolName); symbolTable.currentScope(symbolName); symbolType = null; }
break;
case 14:
//#line 44 "grammar.yacc"
{ symbolTable.addVar(symbolType, val_peek(0).sval); }
break;
case 20:
//#line 55 "grammar.yacc"
{ symbolTable.addArg(symbolName, (SymbolType) val_peek(1).obj, val_peek(0).sval); }
break;
case 27:
//#line 69 "grammar.yacc"
{ symbolTable.validReturn(symbolName, (SymbolType) val_peek(0).obj); }
break;
case 29:
//#line 70 "grammar.yacc"
{ symbolTable.scoppedLoopIncr(); }
break;
case 31:
//#line 71 "grammar.yacc"
{ symbolTable.validTypesLogic((SymbolType) val_peek(0).obj); }
break;
case 32:
//#line 71 "grammar.yacc"
{ symbolTable.scoppedLoopIncr(); }
break;
case 34:
//#line 72 "grammar.yacc"
{ symbolTable.scoppedLoopIncr(); }
break;
case 35:
//#line 72 "grammar.yacc"
{ symbolTable.validTypesLogic((SymbolType) val_peek(0).obj); }
break;
case 37:
//#line 73 "grammar.yacc"
{ symbolTable.validTypesLogic((SymbolType) val_peek(0).obj); }
break;
case 39:
//#line 74 "grammar.yacc"
{ symbolTable.scoppedLoopCheck("break"); }
break;
case 41:
//#line 75 "grammar.yacc"
{ symbolTable.scoppedLoopCheck("continue"); }
break;
case 46:
//#line 83 "grammar.yacc"
{
  if (symbolType != null) { symbolTable.addVar(symbolType, val_peek(2).sval, (SymbolType)val_peek(0).obj); symbolType = null; }
  else {symbolTable.addValueVar(val_peek(2).sval, (SymbolType)val_peek(0).obj);}
}
break;
case 52:
//#line 98 "grammar.yacc"
{ yyval.obj = symbolTable.modelateResult(val_peek(0).sval); }
break;
case 53:
//#line 99 "grammar.yacc"
{ yyval.obj = symbolTable.modelateResult(val_peek(0).sval); }
break;
case 54:
//#line 100 "grammar.yacc"
{ yyval.obj = symbolTable.modelateResult(val_peek(0).sval); }
break;
case 55:
//#line 101 "grammar.yacc"
{ yyval.obj = symbolTable.modelateResult(val_peek(0).sval); }
break;
case 57:
//#line 107 "grammar.yacc"
{ symbolType = (SymbolType) val_peek(0).obj; }
break;
case 67:
//#line 128 "grammar.yacc"
{ symbolTable.validTypesLogic((SymbolType) val_peek(0).obj); }
break;
case 75:
//#line 144 "grammar.yacc"
{ yyval.obj = symbolTable.searchTypeFunc(val_peek(3).sval); }
break;
case 79:
//#line 154 "grammar.yacc"
{ yyval.obj = symbolTable.validTypesArit((SymbolType) val_peek(2).obj, (SymbolType) val_peek(0).obj); }
break;
case 80:
//#line 155 "grammar.yacc"
{ yyval.obj = symbolTable.validTypesArit((SymbolType) val_peek(2).obj, (SymbolType) val_peek(0).obj); }
break;
case 81:
//#line 156 "grammar.yacc"
{ yyval.obj = symbolTable.validTypesArit((SymbolType) val_peek(2).obj, (SymbolType) val_peek(0).obj); }
break;
case 82:
//#line 157 "grammar.yacc"
{ yyval.obj = symbolTable.validTypesArit((SymbolType) val_peek(2).obj, (SymbolType) val_peek(0).obj); }
break;
case 83:
//#line 158 "grammar.yacc"
{ yyval.obj = symbolTable.validTypesArit((SymbolType) val_peek(2).obj, (SymbolType) val_peek(0).obj); }
break;
case 84:
//#line 159 "grammar.yacc"
{ yyval.obj = symbolTable.validTypesLogic((SymbolType) val_peek(2).obj, (SymbolType) val_peek(0).obj); }
break;
case 85:
//#line 160 "grammar.yacc"
{ yyval.obj = symbolTable.validTypesLogic((SymbolType) val_peek(2).obj, (SymbolType) val_peek(0).obj); }
break;
case 86:
//#line 161 "grammar.yacc"
{ yyval.obj = symbolTable.validTypesComp((SymbolType) val_peek(2).obj, (SymbolType) val_peek(0).obj); }
break;
case 87:
//#line 162 "grammar.yacc"
{ yyval.obj = symbolTable.validTypesComp((SymbolType) val_peek(2).obj, (SymbolType) val_peek(0).obj); }
break;
case 88:
//#line 163 "grammar.yacc"
{ yyval.obj = symbolTable.validTypesComp((SymbolType) val_peek(2).obj, (SymbolType) val_peek(0).obj); }
break;
case 89:
//#line 164 "grammar.yacc"
{ yyval.obj = symbolTable.validTypesComp((SymbolType) val_peek(2).obj, (SymbolType) val_peek(0).obj); }
break;
case 90:
//#line 165 "grammar.yacc"
{ yyval.obj = symbolTable.validTypesComp((SymbolType) val_peek(2).obj, (SymbolType) val_peek(0).obj); }
break;
case 91:
//#line 166 "grammar.yacc"
{ yyval.obj = symbolTable.validTypesComp((SymbolType) val_peek(2).obj, (SymbolType) val_peek(0).obj); }
break;
case 92:
//#line 167 "grammar.yacc"
{ yyval.obj = symbolTable.validTypesLogic((SymbolType) val_peek(0).obj); }
break;
case 93:
//#line 168 "grammar.yacc"
{ yyval.obj = (SymbolType) val_peek(1).obj; }
break;
case 96:
//#line 173 "grammar.yacc"
{ yyval.obj = new SymbolType("double"); }
break;
case 97:
//#line 174 "grammar.yacc"
{ yyval.obj = new SymbolType("bool"); }
break;
case 98:
//#line 175 "grammar.yacc"
{ yyval.obj = new SymbolType("int"); }
break;
case 99:
//#line 176 "grammar.yacc"
{ yyval.obj = new SymbolType("string"); }
break;
case 100:
//#line 177 "grammar.yacc"
{ yyval.obj = new SymbolType("ident"); }
break;
//#line 863 "Parser.java"
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
