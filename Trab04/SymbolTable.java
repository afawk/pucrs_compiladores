/*
*
* Trabalho 04 Compiladores
* Anderson Fraga - 13180375
* contato@andersonfraga.net
*
*/

import java.util.HashMap;
import java.util.HashSet;

public class SymbolTable
{
    private Yylex lexer;
    private int scopeLoop;

    public SymbolTable(Yylex lexer)
    {
        this.lexer = lexer;
    }

    private int getLine()
    {
        return lexer.getLine() + 1;
    }

    public SymbolTree addVar(SymbolTree scope, SymbolType symbol)
    {
        if (scope.exists(symbol)) {
            fatalError("O identificador '" + symbol.name() + "', na linha " + getLine() + " já está definido dentro desse escopo");
        }

        scope.addUnlinked(symbol);
        return scope;
    }

    public SymbolTree addVar(SymbolTree scope, SymbolType symbol, SymbolType value)
    {
        return addVar(scope, symbol, value.type());
    }

    public SymbolTree addVar(SymbolTree scope, SymbolType symbol, String valueType)
    {
        if (symbol.type() != valueType) {
            fatalError("O identificador '" + symbol.name() + "', na linha " + getLine() + ", está recebendo o tipo '" + valueType + "', sendo que ele solicita um '" + symbol.type() + "'");
        }

        if (!scope.exists(symbol)) {
            return addVar(scope, symbol);
        }

        return scope;
    }

    public SymbolTree addArg(SymbolTree scope, SymbolType symbol)
    {
        if (scope.exists(symbol)) {
            fatalError("O identificador '" + symbol.name() + "', na linha " + getLine() + ", já está definido dentro desse escopo");
        }

        scope.addUnlinked(symbol);
        return scope;
    }

    public SymbolTree addFunc(SymbolTree scope, SymbolType symbol)
    {
        if (scope.exists(symbol)) {
            fatalError("O identificador '" + symbol.name() + "', na linha " + getLine() + ", já está definido dentro desse escopo");
        }

        return scope.addChild(symbol);
    }

    public SymbolTree addScope(SymbolTree scope, String name)
    {
        return scope.addChild(new SymbolType("loop", name));
    }

    public void validReturn(SymbolTree scope, SymbolType returnElm)
    {
        SymbolTree copy = scope;
        SymbolType searched = null;

        while (copy != null) {
            if (copy.current().type().equals("loop")) {
                copy = copy.parent();
                continue;
            }

            searched = copy.current();
            break;
        }

        //SymbolType typeOf = modelateResult(scope, searched);

        if (returnElm.type() != searched.type()) {
            fatalError("A função '" + searched.name() + "' solicita um tipo de retorno '" + searched.type() + "', porém retorna '" + returnElm.type() + "', na linha " + getLine());
        }
    }

    public void fatalError(String message)
    {
        System.out.println("FATAL ERROR: " + message);
        System.exit(0);
    }

    public void showRedeclaredError(String identifier)
    {
        fatalError("o identificador '" + identifier + "', na linha " + getLine() + ", já foi declarado anteriormente!");
    }

    public SymbolType validTypesArit(SymbolTree scope, SymbolType item, SymbolType compare)
    {
        item    = search(scope, item);
        compare = search(scope, compare);

        boolean itemIsValid = (item.type().equals("int") || item.type().equals("double"));

        boolean compareIsValid = (compare.type().equals("int") || compare.type().equals("double"));

        if (!itemIsValid || !compareIsValid) {
            fatalError("Operações aritméticas devem ocorrer, somente, com inteiros (int) ou ponto flutuante (double), encontrados porém '"+ item +"' e '"+ compare +"', na linha " + getLine());
        }

        if (item.type().equals("double") || compare.type().equals("double")) {
            return new SymbolType("double", "");
        }

        return new SymbolType("int", "");
    }

    public SymbolType validTypesLogic(SymbolTree scope, SymbolType item, SymbolType compare)
    {
        item = search(scope, item);
        compare = search(scope, compare);

        if (item.type().equals("bool") && compare.type().equals("bool")) {
            return new SymbolType("bool", "");
        }

        fatalError("Operações lógicas devem ocorrer, somente, com booleanos (bool), encontrados porém '"+ item +"' e '"+ compare +"', na linha " + getLine());

        return null;
    }

    public SymbolType validTypesLogic(SymbolTree scope, SymbolType item)
    {
        item = search(scope, item);

        if (item.type().equals("bool") ) {
            return item;
        }

        fatalError("Operações lógicas devem ocorrer, somente, com booleanos (bool), encontrado porém '"+ item +"', na linha " + getLine());

        return null;
    }

    public SymbolType validTypesComp(SymbolTree scope, SymbolType item, SymbolType compare)
    {
        item = search(scope, item);
        compare = search(scope, compare);

        boolean itemIsNumber = (item.type().equals("int") || item.type().equals("double"));

        boolean compareIsNumber = (compare.type().equals("int") || compare.type().equals("double"));

        if (itemIsNumber && compareIsNumber) {
            return new SymbolType("bool", "");
        }

        fatalError("Operações comparativas devem ocorrer, somente, com literais, inteiros (int) ou ponto flutuante (double), encontrados porém '"+ item +"' e '"+ compare +"', na linha " + getLine());

        return null;
    }

    public SymbolType validTypesEq(SymbolTree scope, SymbolType item, SymbolType compare)
    {
        item = search(scope, item);
        compare = search(scope, compare);

        if (item.type().equals("string") && compare.type().equals("string")) {
            return new SymbolType("bool", "");
        }

        if (item.type().equals("bool") && compare.type().equals("bool")) {
            return new SymbolType("bool", "");
        }

        boolean itemIsNumber = (item.type().equals("int") || item.type().equals("double"));

        boolean compareIsNumber = (compare.type().equals("int") || compare.type().equals("double"));

        if (itemIsNumber && compareIsNumber) {
            return new SymbolType("bool", "");
        }

        fatalError("Operações lógicas devem ocorrer, somente, entre booleanos (bool), strings (string), inteiros (int) ou ponto flutuante (double), encontrados porém '"+ item +"' e '"+ compare +"', na linha " + getLine());

        return null;
    }

    public SymbolType modelateResult(SymbolTree scope, SymbolType symbol)
    {
        if (symbol.type().equals("num")) {
            String elm = symbol.name();

            if (elm.indexOf('.') > 0) {
                return new SymbolType("double", elm);
            }

            return new SymbolType("int", elm);
        }

        symbol = search(scope, symbol);

        return symbol;
    }

    private SymbolType search(SymbolTree scope, SymbolType symbol)
    {
        if (!symbol.type().equals("ident")) {
            return symbol;
        }

        SymbolType searched = scope.searchAndGetByName(symbol.name());

        if (searched == null) {
            fatalError("Identificador '"+ symbol.name() + "' não encontrado, na linha " + getLine());
        }

        return searched;
    }

    public void scoppedLoopIncr()
    {
        scopeLoop++;
    }

    public void scoppedLoopCheck(SymbolTree scope, String identifier)
    {
        if (identifier.equals("continue") &&
            !(scope.current().name().equals("for") ||
              scope.current().name().equals("do-while") ||
              scope.current().name().equals("while")
            )
        ) {
            fatalError("Nenhum loop localizado para uso de 'continue', na linha "+getLine());
        }

        if (!scope.current().type().equals("loop")) {
            fatalError("Nenhum loop/switch localizado para uso de '"+identifier+"', na linha "+getLine());
        }

        scopeLoop--;
    }

    public String searchTypeFunc(SymbolTree scope, String identifier)
    {
        SymbolType item = scope.searchAndGetByName(identifier);

        if (item == null) {
            fatalError("Identificador de função '"+ identifier + "' não encontrado, na linha " + getLine());
        }

        return item.type();
    }
}