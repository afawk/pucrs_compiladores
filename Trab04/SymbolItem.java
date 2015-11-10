
import java.util.HashMap;

class SymbolItem
{
    private SymbolType type;
    private String value = null;
    private HashMap<String, SymbolType> args = new HashMap<>();

    public SymbolItem(SymbolType type)
    {
        this.type = type;
    }

    public SymbolItem(SymbolType type, String value)
    {
        this.type = type;
        this.value = value;
    }

    public SymbolItem(SymbolType type, HashMap<String, SymbolType> args)
    {
        this.type = type;
        this.args = args;
    }

    public SymbolType type()
    {
        return this.type;
    }

    public String value()
    {
        return this.value;
    }

    public HashMap<String, SymbolType> args()
    {
        return this.args;
    }
}