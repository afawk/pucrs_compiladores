
public class AsdrGrammar
{
    private AsdrParser parser;

    public AsdrGrammar(AsdrParser parser)
    {
        this.parser = parser;
    }

    public void run()
    {
        Program();
    }

    private void Program()
    {
        parser.consoleMsg("Program");

        if (ParserTokens.isTypeDecl(parser.getActualToken())) {
            DeclList();
        }
        else if (!parser.isEOF()) {
            parser.errorExpected("INT | DOUBLE | BOOL | VOID");
        }
    }

    private void DeclList()
    {
        parser.consoleMsg("DeclList");

        if (parser.isActualToken(ParserTokens.INT)) {
            parser.check(ParserTokens.INT);
        }
        else if (parser.isActualToken(ParserTokens.DOUBLE)) {
            parser.check(ParserTokens.DOUBLE);
        }
        else if (parser.isActualToken(ParserTokens.BOOL)) {
            parser.check(ParserTokens.BOOL);
        }
        else if (parser.isActualToken(ParserTokens.VOID)) {
            parser.check(ParserTokens.VOID);
        }
        else {
            parser.errorExpected("INT | DOUBLE | BOOL | VOID");
        }

        parser.check(ParserTokens.IDENT);

        if (parser.isActualToken(',')) {
            DeclProperty();
        }
        else if (parser.isActualToken('(')) {
            DeclMethod();
        }

        if (parser.isActualToken(';')) {
            parser.check(';');
            Program();
        }
    }

    private void DeclProperty()
    {
        parser.consoleMsg("DeclProperty");

        if (!parser.isActualToken(',')) {
            return;
        }

        parser.check(',').check(ParserTokens.IDENT);

        DeclProperty();
    }

    private void DeclElement()
    {
        parser.consoleMsg("DeclElement");

        if (!parser.isActualToken(',')) {
            return;
        }

        parser.check(',');

        if (parser.isActualToken(ParserTokens.INT)) {
            parser.check(ParserTokens.INT);
        }
        else if (parser.isActualToken(ParserTokens.DOUBLE)) {
            parser.check(ParserTokens.DOUBLE);
        }
        else if (parser.isActualToken(ParserTokens.BOOL)) {
            parser.check(ParserTokens.BOOL);
        }
        else {
            parser.errorExpected("INT | DOUBLE | BOOL");
        }

        parser.check(ParserTokens.IDENT);

        DeclElement();
    }

    private void DeclMethod()
    {
        parser.consoleMsg("DeclMethod");

        parser.check('(');

        if (!parser.isActualToken(')')) {
            MethodElement();
        }

        parser.check(')');

        CommandFunction();
    }

    private void MethodElement()
    {
        parser.consoleMsg("MethodElement");

        if (parser.isActualToken(ParserTokens.INT)) {
            parser.check(ParserTokens.INT);
        }
        else if (parser.isActualToken(ParserTokens.DOUBLE)) {
            parser.check(ParserTokens.DOUBLE);
        }
        else if (parser.isActualToken(ParserTokens.BOOL)) {
            parser.check(ParserTokens.BOOL);
        }
        else {
            parser.errorExpected("INT | DOUBLE | BOOL");
        }

        parser.check(ParserTokens.IDENT);

        DeclElement();
    }

    private void CommandFunction()
    {
        parser.consoleMsg("CommandFunction");

        parser.check('{');

        CommandStat();

        parser.check('}');

        Program();
    }

    private void CommandStat()
    {
        parser.consoleMsg("CommandStat");

        if (parser.isActualToken(ParserTokens.WHILE)) {
            parser.check(ParserTokens.WHILE).check('(');

            StatOperator();

            parser.check(')');

            CommandStat();
        }
        else if (parser.isActualToken(ParserTokens.IF)) {
            parser.check(ParserTokens.IF).check('(');

            StatOperator();

            parser.check(')');

            CommandStat();

            if (parser.isActualToken(ParserTokens.ELSE)) {
                parser.check(ParserTokens.ELSE);
                CommandStat();
            }
        }
        else if (ParserTokens.isTypeDecl(parser.getActualToken())) {
            if (parser.isActualToken(ParserTokens.INT)) {
                parser.check(ParserTokens.INT);
            }
            else if (parser.isActualToken(ParserTokens.DOUBLE)) {
                parser.check(ParserTokens.DOUBLE);
            }
            else if (parser.isActualToken(ParserTokens.BOOL)) {
                parser.check(ParserTokens.BOOL);
            }
            else {
                parser.errorExpected("INT | DOUBLE | BOOL");
            }

            parser.check(ParserTokens.IDENT);

            if (parser.isActualToken(';')) {
                parser.check(';');
            }

            CommandStat();
        }
        else if (parser.isActualToken(ParserTokens.IDENT)) {
            parser.check(ParserTokens.IDENT);

            if (parser.isActualToken('=')) {
                parser.check('=');
            }

            StatOperator();

            parser.check(';');

            CommandStat();
        }
        else if (parser.isActualToken(ParserTokens.RETURN)) {
            parser.check(ParserTokens.RETURN);

            if (parser.isActualToken(ParserTokens.IDENT)) {
                parser.check(ParserTokens.IDENT);
            }
            else if (parser.isActualToken(ParserTokens.NUM)) {
                parser.check(ParserTokens.NUM);
            }

            parser.check(';');
        }
    }

    private void StatOperator()
    {
        parser.consoleMsg("StatOperator");

        InitStatOperator();

        if (parser.isActualToken('>')) {
            parser.check('>');
            InitStatOperator();
        }
        else if (parser.isActualToken('<')) {
            parser.check('<');
            InitStatOperator();
        }
    }

    private void InitStatOperator()
    {
        parser.consoleMsg("InitStatOperator");

        ReducedElement();
        ComparisionRightOperator();
    }

    private void ComparisionRightOperator()
    {
        parser.consoleMsg("ComparisionRightOperator");

        if (parser.isActualToken(ParserTokens.AND_OP)) {
            parser.check(ParserTokens.AND_OP);
            ReducedElement();
        }
        else if (parser.isActualToken(ParserTokens.OR_OP)) {
            parser.check(ParserTokens.OR_OP);
            ReducedElement();
        }

        SumsubtractionRightOperator();
    }

    private void SumsubtractionRightOperator()
    {
        parser.consoleMsg("SumsubtractionRightOperator");

        if (parser.isActualToken('+')) {
            parser.check('+');
            ReducedElement();
        }
        else if (parser.isActualToken('-')) {
            parser.check('-');
            ReducedElement();
        }

        MultdivRightOperator();
    }

    private void MultdivRightOperator()
    {
        parser.consoleMsg("MultdivRightOperator");

        if (parser.isActualToken('/')) {
            parser.check('/');
            ReducedElement();
        }
        else if (parser.isActualToken('*')) {
            parser.check('*');
            ReducedElement();
        }
    }

    private void ReducedElement()
    {
        parser.consoleMsg("ReducedElement");

        if (parser.isActualToken(ParserTokens.NUM)) {
            parser.check(ParserTokens.NUM);
        }
        else if (parser.isActualToken(ParserTokens.NOT_OP)) {
            parser.check(ParserTokens.NOT_OP);
            StatOperator();
        }
        else if (parser.isActualToken('(')) {
            parser.check('(');
            StatOperator();
            parser.check(')');
        }
        else if (parser.isActualToken(ParserTokens.IDENT)) {
            parser.check(ParserTokens.IDENT);

            if (parser.isActualToken('(')) {
                parser.check('(');

                if (!parser.isActualToken(')')) {
                    ChainingStatOperator();
                    parser.check(')');
                }
            }
        }
        else {
            parser.errorExpected("?");
        }
    }

    private void ChainingStatOperator()
    {
        parser.consoleMsg("ChainingStatOperator");

        StatOperator();

        if (parser.isActualToken(',')) {
            parser.check(',');
            ChainingStatOperator();
        }
    }
}