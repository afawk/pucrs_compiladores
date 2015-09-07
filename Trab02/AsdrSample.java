import java.io.*;

public class AsdrSample {

  private static final int BASE_TOKEN_NUM = 301;

  public static final int IDENT  = 301;
  public static final int NUM 	 = 302;
  public static final int WHILE  = 303;
  public static final int IF	   = 304;
  public static final int INT 	 = 305;
  public static final int BOOL	 = 306;
  public static final int DOUBLE = 307;
  public static final int ELSE   = 308;
  public static final int VOID   = 309;
  public static final int RETURN = 310;

    public static final String tokenList[] = {
      "IDENT","NUM", "WHILE", "IF", "INT", "BOOL", "DOUBLE", "ELSE", "VOID", "RETURN"
    };

  /* referencia ao objeto Scanner gerado pelo JFLEX */
  private Yylex lexer;

  public ParserVal yylval;

  private static int laToken;
  private boolean debug;


  /* construtor da classe */
  public AsdrSample (Reader r) {
      lexer = new Yylex (r, this);
  }

  /**
    Original:
      Prog -> LDecl Bloco
      LDecl -> Decl LDecl | &
      Decl -> Tipo ListaID ;
      ListaID -> IDENT RestoLID
      RestoLID -> , IDENT RestoLID | &
      Tipo -> INT | DOUBLE
      Bloco -> { Cmd }
      Cmd -> WHILE ( E ) Cmd | IDENT = E ; | IF ( E ) Cmd RestoIF |
      RestoIF -> ELSE Cmd | &
      E -> IDENT | NUM | ( E )

    Adaptado:
      Prog -> LDecl Bloco
      LDecl -> Decl LDecl | &
      Decl -> Tipo ListaID ;
      ListaID -> IDENT RestoLID
      RestoLID -> , IDENT RestoLID | &
      Tipo -> INT | DOUBLE
      Bloco -> { Cmd }
      Cmd -> WHILE ( E ) Cmd | IDENT = E ; | IF ( E ) Cmd RestoIF
      RestoIF -> ELSE Cmd | &
      E -> IDENT | NUM | ( E )

    int a, b, c;
    double calcularFrequencia(int aulas, int frequenc) {
     double temp;
     temp = 0;
     return temp;
    }
    void main() { a = calcularFrequencia(b,c); }


    Prog -> DeclList | &
    DeclList -> Tipo IDENT RestDecl Prog
    RestDecl -> ElmDecl ; | ( RestMet
    ElmDecl -> , Tipo IDENT ElmDecl | &
    RestMet -> Tipo IDENT ElmDecl CmdFun | CmdFun
    CmdFun -> ) { Cmd }
    Cmd -> WHILE ( E ) Cmd
          | Tipo IDENT ;
          | IDENT = E ;
          | IF ( E ) Cmd RestoIF
          | return IDENT ;

    E --> B ">" B | B "<" B | B

    B -> F RestB

    RestB --> "&&" C
        | "||" C
        | RestC

    C -> F RestC

    RestC --> "+" D
        | "-" D
        | RestD

    D -> F RestD
    RestD --> "*" F
        | "/" F
        | &

    F --> NUM | "!"E | "("E")" | IDENT RestF
    RestF -> "("InitLEx")"
          | &

    InitLEx -> LEx | &

    LEx --> E RestLEx
    RestLEx -> "," LEx | &

    RestoIF -> ELSE Cmd | &

    Tipo -> int | double | bool | void;


  */

  private void Prog() {

     if ( laToken == INT || laToken == DOUBLE || laToken == '{'  ) {
            if (debug) System.out.println("Prog --> Decl Bloco");
     		LDecl();
     		Bloco();
     }
     else yyerror("Esperado: int, double ou {");
    }

  private void LDecl() {
     if (laToken == INT || laToken == DOUBLE ) {
     		if (debug) System.out.println("LDecl --> Decl LDecl");
            Decl();
     		LDecl();
     } else {
             if (debug) System.out.println("LDecl -->       // prod. vazia");
            }
  }

  private void Decl() {
	 if (debug) System.out.println("Decl --> Tipo ListaID ;");
	 Tipo();
     ListaID();
	 verifica(';');
  }

  private void ListaID(){
	    if (debug) System.out.println("ListaID -->  IDENT  RestoLID");
       verifica(IDENT);
       RestoLID();
  }

  private void RestoLID() {
     if (laToken == ',' ) {
	    if (debug) System.out.println("RestoLID --> , IDENT RestoLID");
        verifica(',');
        verifica(IDENT);
        RestoLID();
     } else
          if (debug) System.out.println("RestoLID --> vazio");
  }

  private void Tipo() {
      if (laToken == INT) {
         if (debug) System.out.println("Tipo --> int");
         verifica(INT);
	   } else if (laToken == DOUBLE) {
         if (debug) System.out.println("Tipo --> double");
         verifica(DOUBLE);
	   } else yyerror("Esperado: int ou double");
   }

  private void Bloco() {
      if (debug) System.out.println("Bloco --> { Cmd }");

      verifica('{');
      Cmd();
	  verifica('}');
  }

  private void Cmd() {
      if (laToken == '{') {
         if (debug) System.out.println("Cmd --> Bloco");
         Bloco();
	   }
      else if (laToken == WHILE) {
         if (debug) System.out.println("Cmd --> WHILE ( E ) Cmd");
         verifica(WHILE);    // laToken = this.yylex();
  		 verifica('(');
  		 E();
         verifica(')');
         Cmd();
	   }
      else if (laToken == IDENT) {
         if (debug) System.out.println("Cmd --> ident = E ;");
         verifica(IDENT);
  		 verifica('=');
         E();
		 verifica(';');
	   }
    else if (laToken == IF) {
         if (debug) System.out.println("Cmd --> if E Cmd RestoIF");
         verifica(IF);
         verifica('(');
  		 E();
         verifica(')');
         Cmd();
         RestoIF();
	   }
 	else yyerror("Esperado {, if, while ou identificador");
   }


   private void RestoIF() {
       if (laToken == ELSE) {
         if (debug) System.out.println("RestoIF --> else Cmd ");
         verifica(ELSE);
         Cmd();
	   } else {
         if (debug) System.out.println("RestoIF -->     // producao vazia");
         }
     }



  private void E() {
      if (laToken == IDENT) {
         if (debug) System.out.println("E --> IDENT");
         verifica(IDENT);
	   }
      else if (laToken == NUM) {
         if (debug) System.out.println("E --> NUM");
         verifica(NUM);
	   }
      else if (laToken == '(') {
         if (debug) System.out.println("E --> ( E )");
         verifica('(');
         E();
		 verifica(')');
	   }
 	else yyerror("Esperado operando (, identificador ou numero");
   }

  private void verifica(int expected) {
      if (laToken == expected)
         laToken = this.yylex();
      else {
         String expStr, laStr;

		expStr = ((expected < BASE_TOKEN_NUM )
                ? ""+(char)expected
			     : tokenList[expected-BASE_TOKEN_NUM]);

		laStr = ((laToken < BASE_TOKEN_NUM )
                ? new Character((char)laToken).toString()
                : tokenList[laToken-BASE_TOKEN_NUM]);

          yyerror( "esperado token : " + expStr +
                   " na entrada: " + laStr);
     }
   }

   /* metodo de acesso ao Scanner gerado pelo JFLEX */
   private int yylex() {
       int retVal = -1;
       try {
           yylval = new ParserVal(0); //zera o valor do token
           retVal = lexer.yylex(); //le a entrada do arquivo e retorna um token
       } catch (IOException e) {
           System.err.println("IO Error:" + e);
          }
       return retVal; //retorna o token para o Parser
   }

  /* metodo de manipulacao de erros de sintaxe */
  public void yyerror (String error) {
     System.err.println("Erro: " + error);
     System.err.println("Entrada rejeitada");
     System.out.println("\n\nFalhou!!!");
     System.exit(1);

  }

  public void setDebug(boolean trace) {
      debug = true;
  }


  /**
   * Runs the scanner on input files.
   *
   * This main method is the debugging routine for the scanner.
   * It prints debugging information about each returned token to
   * System.out until the end of file is reached, or an error occured.
   *
   * @param args   the command line, contains the filenames to run
   *               the scanner on.
   */
  public static void main(String[] args) {
     AsdrSample parser = null;
     try {
         if (args.length == 0)
            parser = new AsdrSample(new InputStreamReader(System.in));
         else
            parser = new  AsdrSample( new java.io.FileReader(args[0]));

          parser.setDebug(false);
          laToken = parser.yylex();

          parser.Prog();

          if (laToken== Yylex.YYEOF)
             System.out.println("\n\nSucesso!");
          else
             System.out.println("\n\nFalhou - esperado EOF.");

        }
        catch (java.io.FileNotFoundException e) {
          System.out.println("File not found : \""+args[0]+"\"");
        }
//        catch (java.io.IOException e) {
//          System.out.println("IO error scanning file \""+args[0]+"\"");
//          System.out.println(e);
//        }
//        catch (Exception e) {
//          System.out.println("Unexpected exception:");
//          e.printStackTrace();
//      }

  }

}

