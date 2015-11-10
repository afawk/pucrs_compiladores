
public class SymbolType
{
    private String name;
    private Object value;

    public SymbolType(String type)
    {
        this.name = type;
    }

    public SymbolType(String type, Object value)
    {
        this.name  = type;
        this.value = value;
    }

    public String name()
    {
        return this.name;
    }

    public Object value()
    {
        return this.value;
    }
}