package tree.table;

import java.util.Stack;

public class StackTable {
    private static final Stack<SymbolTable> stack = new Stack<>();

    public static void newScope() {
        stack.push(new SymbolTable());
    }

    public static SymbolTable getScope() {
        return stack.lastElement();
    }

    public static void removeScope() {
        stack.pop();
    }

    public static void addEntry(Entry e) {

        if (e.getKindOfEntry().equals("var"))
            getScope().addVarEntry((VarEntry) e);
        else
            getScope().addFunEntry((FunEntry) e);
    }


    public static Entry lookup(String name) {
        for (int i = stack.size() - 1; i >= 0; i--) {
            SymbolTable s = stack.get(i);
            if (s.containEntry(name))
                return s.getEntry(name);

        }
        return null;
    }
}
