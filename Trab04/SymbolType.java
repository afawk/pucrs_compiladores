/*
*
* Trabalho 04 Compiladores
* Anderson Fraga - 13180375
* contato@andersonfraga.net
*
*/

public class SymbolType
{
    private String type;
    private String name;

    public SymbolType(String type, String name)
    {
        this.type = type;
        this.name = name;
    }

    public String type()
    {
        return this.type;
    }

    public String name()
    {
        return this.name;
    }

    public SymbolType changeName(String name)
    {
        return new SymbolType(this.type, name);
    }

    public boolean equals(SymbolType symbol)
    {
        return symbol.type == type && symbol.name == name;
    }
}