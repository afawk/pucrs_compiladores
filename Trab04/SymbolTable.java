
import java.util.HashMap;

public class SymbolTable
{

    private HashMap<String, SymbolItem> map;
    private HashMap<String, HashMap<String, SymbolType>> argsMap;
    private Yylex lexer;
    private int scopeLoop = 0;
    private String currentScope = null;

    public SymbolTable(Yylex lexer)
    {
        this.lexer = lexer;
        map = new HashMap<String, SymbolItem>();
        argsMap = new HashMap<String, HashMap<String, SymbolType>>();
    }

    public void currentScope(String current)
    {
        this.currentScope = current;
    }

    public void fatalError(String message)
    {
        System.out.println("FATAL ERROR: " + message);
        System.exit(0);
    }


    public void showRedeclaredError(String identifier)
    {
        fatalError("o identificador '" + identifier + "', na linha " + lexer.getLine() + ", já foi declarado anteriormente!");
    }

    public void addArg(String identifierFunc, SymbolType type, String identifier)
    {
        HashMap<String, SymbolType> item;

        if (argsMap.containsKey(identifierFunc)) {
            item = argsMap.get(identifierFunc);
        }
        else {
            item = new HashMap<>();
        }

        if (item.containsKey(identifier)) {
            showRedeclaredError(identifier);
        }

        item.put(identifier, type);
        argsMap.put(identifierFunc, item);
    }

    public void addFunc(SymbolType type, String identifier)
    {
        if (map.containsKey(identifier)) {
            showRedeclaredError(identifier);
        }

        if (argsMap.containsKey(identifier)) {
            map.put(identifier, new SymbolItem(type, argsMap.get(identifier)));
        }
        else {
            map.put(identifier, new SymbolItem(type));
        }
    }

    public void addVar(SymbolType type, String identifier)
    {
        if (map.containsKey(identifier)) {
            showRedeclaredError(identifier);
        }

        map.put(identifier, new SymbolItem(type));
    }

    public void addVar(SymbolType type, String identifier, SymbolType value)
    {
        if (map.containsKey(identifier)) {
            showRedeclaredError(identifier);
        }

        map.put(identifier, new SymbolItem(type, String.valueOf(value.value())));
    }

    public void addValueVar(String identifier, SymbolType value)
    {
        if (map.containsKey(currentScope)) {
            SymbolItem elm = map.get(currentScope);
            HashMap<String, SymbolType> elmargs = elm.args();

            if (elmargs.containsKey(identifier)) {
                fatalError("Identificador '" + identifier + "', na linha " + lexer.getLine() + ", foi declarado como argumento, e não pode ter valor definido dentro do seu escopo");
            }
        }

        if (!map.containsKey(identifier)) {
            fatalError("Identificador '" + identifier + "', na linha " + lexer.getLine() + ", não teve seu tipo declarado");
        }

        SymbolItem item = map.get(identifier);
        map.replace(identifier, new SymbolItem(value));
    }

    public SymbolType validTypesArit(SymbolType item, SymbolType compare)
    {
        boolean itemIsValid = (item.name().equals("int") || item.name().equals("double"));

        boolean compareIsValid = (compare.name().equals("int") || compare.name().equals("double"));

        if (!itemIsValid || !compareIsValid) {
            fatalError("Operações aritméticas devem ocorrer, somente, com inteiros (int) ou ponto flutuante (double), encontrados porém '"+ item.name() +"' e '"+ compare.name() +"', na linha " + lexer.getLine());
        }

        if (item.name().equals("double") || compare.name().equals("double")) {
            return new SymbolType("double");
        }

        return new SymbolType("int");
    }

    public SymbolType validTypesLogic(SymbolType item, SymbolType compare)
    {
        if (item.name().equals("bool") && compare.name().equals("bool")) {
            return new SymbolType("bool");
        }

        fatalError("Operações lógicas devem ocorrer, somente, com booleanos (bool), encontrados porém '"+ item.name() +"' e '"+ compare.name() +"', na linha " + lexer.getLine());

        return null;
    }

    public SymbolType validTypesLogic(SymbolType item)
    {
        if (item.name().equals("bool") ) {
            return item;
        }

        fatalError("Operações lógicas devem ocorrer, somente, com booleanos (bool), encontrado porém '"+ item.name() +"', na linha " + lexer.getLine());

        return null;
    }

    public SymbolType validTypesComp(SymbolType item, SymbolType compare)
    {
        boolean itemIsNumber = (item.name().equals("num") || item.name().equals("int") || item.name().equals("double") || item.name().equals("literal"));

        boolean compareIsNumber = (compare.name().equals("num") || compare.name().equals("int") || compare.name().equals("double") || compare.name().equals("literal"));

        if (itemIsNumber && compareIsNumber) {
            return new SymbolType("bool");
        }

        fatalError("Operações comparativas devem ocorrer, somente, com literais, inteiros (int) ou ponto flutuante (double), encontrados porém '"+ item.name() +"' e '"+ compare.name() +"', na linha " + lexer.getLine());

        return null;
    }

    public SymbolType validTypesEq(SymbolType item, SymbolType compare)
    {
        if (item.name().equals("string") && compare.name().equals("string")) {
            return new SymbolType("bool");
        }

        if (item.name().equals("bool") && compare.name().equals("bool")) {
            return new SymbolType("bool");
        }

        boolean itemIsNumber = (item.name().equals("num") || item.name().equals("int") || item.name().equals("double"));

        boolean compareIsNumber = (compare.name().equals("num") || compare.name().equals("int") || compare.name().equals("double"));

        if (itemIsNumber && compareIsNumber) {
            return new SymbolType("bool");
        }

        fatalError("Operações lógicas devem ocorrer, somente, entre booleanos (bool), strings (string), inteiros (int) ou ponto flutuante (double), encontrados porém '"+ item.name() +"' e '"+ compare.name() +"', na linha " + lexer.getLine());

        return null;
    }

    public void validReturn(String identifier, SymbolType returnElm)
    {
        if (!map.containsKey(identifier)) {
            fatalError("Identificador '" + identifier + "' não encontrado no escopo, na linha " + lexer.getLine());
        }

        SymbolItem item = map.get(identifier);

        if (returnElm.name() != item.type().name()) {
            fatalError("A função '" + identifier + "' solicita um tipo de retorno '" + item.type().name() + "', porém retorna '" + returnElm.name() + "', na linha " + lexer.getLine());
        }
    }

    public SymbolType modelateResult(String item)
    {
        if (map.containsKey(currentScope)) {
            SymbolItem elm = map.get(currentScope);

            if (elm.args().containsKey(item)) {
                return (SymbolType) elm.args().get(item);
            }
        }

        if (map.containsKey(item)) {
            return (SymbolType) map.get(item).type();
        }

        if (item.equals("true") || item.equals("false") || item.equals("bool")) {
            return new SymbolType("bool");
        }

        try {
            if (item.contains(".")) {
                Double.parseDouble(item);
                return new SymbolType("double", item);
            }
        }
        catch (Exception e) {}

        try {
            return new SymbolType("int", Integer.valueOf(item));
        }
        catch (Exception e) {}

        return new SymbolType("string");
    }

    public void scoppedLoopIncr()
    {
        scopeLoop++;
    }

    public void scoppedLoopCheck(String identifier)
    {
        if (scopeLoop == 0) {
            fatalError("Nenhum loop localizado para uso de '"+identifier+"', na linha "+lexer.getLine());
        }

        scopeLoop--;
    }

    public SymbolType searchTypeFunc(String identifier)
    {
        if (map.containsKey(identifier)) {
            return (SymbolType) map.get(identifier).type();
        }

        fatalError("Função '"+identifier+"' não localizada, linha "+lexer.getLine());

        return null;
    }
}