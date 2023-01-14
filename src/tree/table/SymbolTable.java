package tree.table;

import java.util.HashMap;

public class SymbolTable {
    private HashMap<String, Entry> symbolTable;

    public SymbolTable(){
        this.symbolTable = new HashMap<>();
    }
    public void addFunEntry(FunEntry fun){
        if (symbolTable.containsKey(fun.getName()))
            throw new Error("Funzione già dichiarata - " + fun.getName());
        else
            symbolTable.put(fun.getName(), fun);
    }

    public void addVarEntry(VarEntry var){
        if (symbolTable.containsKey(var.getName()))
            throw new Error("Variabile già dichiarata - " + var.getName());
        else
            symbolTable.put(var.getName(), var);
    }
    public boolean containEntry(String key){
        return this.symbolTable.containsKey(key);
    }

    public Entry getEntry(String key){
        return containEntry(key) ? symbolTable.get(key) : null;
    }
}
