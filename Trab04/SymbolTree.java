
import java.util.HashSet;

public class SymbolTree
{
    private SymbolTree parent = null;
    private SymbolType data;

    private HashSet<SymbolTree> childs = new HashSet<>();
    private HashSet<SymbolType> unlinked = new HashSet<>();

    public SymbolTree(SymbolType symbol)
    {
        this.data = symbol;
    }

    public SymbolTree(SymbolType symbol, SymbolTree parent)
    {
        this.data   = symbol;
        this.parent = parent;
    }

    public SymbolType current()
    {
        return this.data;
    }

    public SymbolTree parent()
    {
        return this.parent;
    }

    public SymbolTree addChild(SymbolType symbol)
    {
        SymbolTree child = new SymbolTree(symbol, this);
        childs.add(child);
        return child;
    }

    public void addUnlinked(SymbolType symbol)
    {
        unlinked.add(symbol);
    }

    public SymbolType searchAndGetByName(String name)
    {
        if (data.name().equals(name)) {
            return data;
        }

        for (SymbolType _sym : unlinked) {
            if (_sym.name().equals(name)) {
                return _sym;
            }
        }

        if (parent == null) {
            return null;
        }

        return parent.searchAndGetByName(name);
    }

    public boolean existsUnchained(SymbolType symbol)
    {
        for (SymbolType _sym : unlinked) {
            if (_sym.equals(symbol)) {
                return true;
            }
        }

        for (SymbolTree _tree : childs) {
            if (_tree.existsUnchained(symbol)) {
                return true;
            }
        }

        return false;
    }

    public boolean existsChained(SymbolType symbol)
    {
        if (parent == null) {
            return false;
        }

        if (symbol.equals(data)) {
            return true;
        }

        for (SymbolTree _tree : childs) {
            if (_tree.existsChained(symbol)) {
                return true;
            }
        }

        return false;
    }

    public boolean exists(SymbolType symbol)
    {
        return existsChained(symbol) || existsUnchained(symbol);
    }
}