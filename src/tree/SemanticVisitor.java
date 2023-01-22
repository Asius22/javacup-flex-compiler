package tree;

import nodes.leafs.ConstLeaf;
import nodes.leafs.IdLeaf;
import nodes.leafs.TypeLeaf;
import nodes.ops.*;
import nodes.stmt.*;
import tree.table.Entry;
import tree.table.FunEntry;
import tree.table.StackTable;
import tree.table.VarEntry;

import java.util.ArrayList;

public class SemanticVisitor implements Visitor {
    @Override
    public Object visit(TypeLeaf typLeaf) {
        return typLeaf.getType();
    }

    @Override
    public Object visit(FunCall call) {
        FunEntry fun = (FunEntry) StackTable.lookup(call.getFunName().getName());
        String rType;
        //Controllo che la funzione esista
        if (fun == null)
            throw new Error("La funzione '" + call.getFunName().getName() + "' non esiste");

        //Controllo numero parametri
        if (call.getParamValues().size() != fun.getParamsName().size())
            throw new Error("IL numero di parametri dovrebbe essere " + fun.getParamsName().size()
                    + "invece è " + call.getParamValues().size());

        //Controllo tipi dei parametri
        for (int i = 0; i < fun.getParamsName().size(); i++) {
            rType = (String) call.getParamValues().get(i).accept(this);
            if (!fun.getParamsType().get(i).equals(rType)) {
                System.out.println("--- " + fun.getName() + " " + rType);
                throw new Error("Il tipo del parametro " + i + " dovrebbe essere '" + fun.getParamsType().get(i) +
                        "' invece è '" + rType + "'");
            }
        }
        call.setReturnType(fun.getReturnType());
        return fun.getReturnType();
    }

    /**
     * si occupa di eseguire una berifica nello scope e in caso la variabile non sia stata già
     * dichiarata la registra nella symbol table, altrimenti lancia un errore.
     *
     * @param idInit: l'id da registrare nella tabella
     * @return
     */
    @Override
    public Object visit(IdInitOp idInit) {

        String name = idInit.getId().getName();
        if (StackTable.getScope().containEntry(name))
            throw new Error("Id '" + name + "' duplicato!!!");

        String type = "";
        if (idInit.getExpr() != null)
            type = idInit.getExpr().accept(this).toString();
        StackTable.addEntry(new VarEntry(name, type, idInit.getId().isOutPatam()));
        idInit.getId().setIdType(type);
        return "notype";
    }

    @Override
    public Object visit(ConstLeaf leaf) {
        return leaf.getConstType();
    }

    @Override
    public Object visit(BinaryOp bop) {
        String type1 = null, type2 = null, op = bop.getOp();
        type1 = bop.getLeft().accept(this).toString();
        type2 = bop.getRight().accept(this).toString();
        String resType = "";
        if (op.equals("plus") || op.equals("minus") || op.equals("times") || op.equals("pow")) {
            //int, int -> int
            if (type1.equals("integer") && type2.equals("integer"))
                resType = "integer";
                //int, float -> float
            else if (type1.equals("integer") && type2.equals("float"))
                resType = "float";
                //float, int -> float
            else if (type1.equals("float") && type2.equals("integer"))
                resType = "float";
                //float, float -> float
            else if (type1.equals("float") && type2.equals("float"))
                resType = "float";
            else throw new Error("le operazioni aritetiche non possono essere eseguite su tipi non numerici!");
        } else if (op.equals("div")) {
            //int, int -> int
            if (type1.equals("integer") && type2.equals("integer"))
                resType = "float";
                //int, float -> float
            else if (type1.equals("integer") && type2.equals("float"))
                resType = "float";
                //float, int -> float
            else if (type1.equals("float") && type2.equals("integer"))
                resType = "float";
                //float, float -> float
            else if (type1.equals("float") && type2.equals("float"))
                resType = "float";
            else throw new Error("le operazioni aritetiche non possono essere eseguite su tipi non numerici!");

        } else if (op.equals("strconcat")) {
            //string, string -> string
            if (type1.equals("string") && type2.equals("string"))
                resType = "string";
            else throw new Error("L'operazione di concatenazione può essere eseguita solo tra due i più stringhe!");
        } else if (op.equals("or") || op.equals("and")) {
            //boolean, boolean -> boolean
            if (type1.equals("bool") && type2.equals("bool"))
                resType = "bool";
            else throw new Error("Le operazioni AND e OR possono essere eseguite solo tra booleani!");
        } else if (op.equals("gt") || op.equals("ge") || op.equals("ne") || op.equals("eq") || op.equals("lt") || op.equals("le")) {
            //int, int -> boolean
            if (type1.equals("integer") && type2.equals("integer"))
                resType = "bool";
                //int, float -> boolean
            else if (type1.equals("integer") && type2.equals("float"))
                resType = "bool";
                //float, int -> boolean
            else if (type1.equals("float") && type2.equals("integer"))
                resType = "bool";
                //float, float -> boolean
            else if (type1.equals("float") && type2.equals("float"))
                resType = "bool";
            else throw new Error("le operazioni logico-aritmetiche non possono essere eseguite su tipi non numerici!");
        }
        bop.setExprType(resType);
        return resType;
    }

    //TODO
    @Override
    public Object visit(UnaryOp uop) {
        String type = (String) uop.getExpr().accept(this),
                op = uop.getOp(), res = "";
        if (op.equals("minus")) {
            if (type.equals("integer"))
                res = "integer";
            else if (type.equals("float"))
                res = "float";
            else
                throw new Error("Impossibile applicare l'operazione '-' per il tipo " + type);
        } else if (op.equals("not")) {
            if (type.equals("bool"))
                res = "bool";
            else throw new Error("Impossibile applicare l'operazione '!' per il tipo " + type);
        }
        uop.setExprType(res);

        return res;
    }

    @Override
    public Object visit(ParamDecl param) {
        VarEntry entry;
        for (IdLeaf id : param.getId()) {
            entry = new VarEntry(id.getName(), param.getType().getType(), param.isOutParams());
            id.setIdType(param.getType().getType());
            StackTable.getScope().addVarEntry(entry);
        }
        return "notype";
    }

    @Override
    public Object visit(Body body) {
        String returnType = "notype";

        if (body.isFirstVisit()) {
            StackTable.newScope();
            body.setSymbolTable(StackTable.getScope());
            body.setFirstVisit(false);


            //non facciamo controlli per la null safety poichè l'attributo variables di body non può essere null
            for (VarDeclOp v : body.getVariables())
                v.accept(this);

            //controlli sugli statements
            for (StmtNode s : body.getStatements()) {

                if (s.getNode() instanceof ReturnExprStmt) {
                    returnType = ((ReturnExprStmt) s.getNode()).accept(this).toString();
                    if (body.getStatements().indexOf(s) < (body.getStatements().size() - 1))
                        throw new Error("il return deve essere l'ultimo statement all'interno dello scope");
                } else s.accept(this);
            }
            StackTable.removeScope();
        }
        return returnType.equals("void") ? "notype" : returnType;
    }

    @Override
    public Object visit(FunDeclOp fun) {
        //creazione di una FunEntry ed aggiunta alla symbol table
        String name = fun.getId().getName();
        ArrayList<String> paramsName = new ArrayList<>();
        ArrayList<String> paramsMode = new ArrayList<>();
        ArrayList<String> paramsType = new ArrayList<>();
        String returnType = fun.getReturnType().getType();

        for (ParamDecl p : fun.getParams())
            for (IdLeaf i : p.getId()) {
                paramsName.add(i.getName());
                paramsType.add(p.getType().getType());
                i.setIdType(p.getType().getType());
                paramsMode.add(String.valueOf(p.isOutParams()));
            }

        FunEntry funEntry = new FunEntry(name, paramsName, paramsType, paramsMode, returnType);
        StackTable.addEntry(funEntry);

        //creazione scope per il corpo della funzione
        if (fun.isFirstVisit()) {
            StackTable.newScope();
            fun.setSymbolTable(StackTable.getScope());
            fun.setFirstVisit(false);

            for (ParamDecl par : fun.getParams())
                par.accept(this);
            //aggiunta parametri della funzione alla symbol table
            String returnBody = fun.getBody().accept(this).toString();
            String funReturnType = fun.getReturnType().getType();
            if (funReturnType.equals("void")) {
                if (!returnBody.equals("notype"))
                    throw new Error("La funzione deve ritornare un valore di tipo " + fun.getReturnType().getType() +
                            "! Invece ritorna " + returnBody);
            } else if (!fun.getReturnType().getType().equals(returnBody)) {
                System.out.println("---- tipo body = " + returnBody);
                System.out.println("---- funzione = " + fun.getId());
                throw new Error("La funzione deve ritornare un valore di tipo " + fun.getReturnType().getType() +
                        "! Invece ritorna " + returnBody);
            }
            StackTable.removeScope();
        }
        return "notype";
    }

    @Override
    public Object visit(DeclList s) {
        for (VarDeclOp v : s.getVariables())
            v.accept(this);
        for (FunDeclOp v : s.getFunctions())
            v.accept(this);
        return "notype";
    }

    @Override
    public Object visit(ProgramOp s) {
        if (s.isFirstVisit()) {
            s.setFirstVisit(false);
            StackTable.newScope();
            s.setSymbolTable(StackTable.getScope());

            s.getDeclList1().accept(this);
            s.getDeclList2().accept(this);

            s.getMain().accept(this);


            StackTable.removeScope();
        }

        return "notype";
    }

    @Override
    public Object visit(IdLeaf id) {
        Entry e = StackTable.lookup(id.getName());
        if (e == null)
            throw new Error("Identificatore: " + id.getName() + " non definito!");

        if (e.getKindOfEntry().equals("var")) {
            VarEntry var = (VarEntry) e;
            id.setIdType(var.getType());
            if (var.isOut())
                id.setOutPatam(true);
            return var.getType();
        } else if (e.getKindOfEntry().equals("fun")) {
            return ((FunEntry) e).getReturnType();
        } else throw new Error("id non definito bene");
    }

    @Override
    public Object visit(IdInitObbOp id) {
        //scoping
        if (StackTable.getScope().containEntry(id.getId().getName()))
            throw new Error("id " + id.getId().getName() + " già dichiarato ");
        else {
            String type = id.getCon().accept(this).toString();
            StackTable.addEntry(new VarEntry(id.getId().getName(), type, id.getId().isOutPatam()));
            id.getId().setIdType(type);
        }

        //non c'è bisogno di type checking in questa fase poichè ad ogni variabile viene associata una costante
        return "notype";
    }

    @Override
    public Object visit(StmtNode stmtNode) {
        return switch (stmtNode.getType()) {
            case "if" -> ((IfStmt) stmtNode.getNode()).accept(this);
            case "for" -> ((ForStmt) stmtNode.getNode()).accept(this);
            case "read" -> ((ReadStmt) stmtNode.getNode()).accept(this);
            case "write" -> ((WriteStmt) stmtNode.getNode()).accept(this);
            case "switch" ->((SwitchStmt) stmtNode.getNode()).accept(this);
            case "assign" -> ((AssignStmt) stmtNode.getNode()).accept(this);
            case "while" -> ((WhileStmt) stmtNode.getNode()).accept(this);
            case "funCall" -> ((FunCall) stmtNode.getNode()).accept(this);
            case "return" -> ((ReturnExprStmt) stmtNode.getNode()).accept(this);
            default -> throw new Error("Statement type Error");
        };
    }

    @Override
    public Object visit(NonEmptyParamDecl nonEmptyParamDecl) {
        for (ParamDecl p : nonEmptyParamDecl.getParams())
            p.accept(this);
        return "noType";
    }

    @Override
    public Object visit(ReturnExprStmt returnExprStmt) {
        ExprNode expr = returnExprStmt.getNode();
        if (expr == null)
            return "void";

        return expr.accept(this);
    }

    @Override
    public Object visit(AssignStmt a) {

        //Controllo sulle size
        if (a.getIdList().size() != a.getValues().size())
            throw new Error("il numero di elementi non combacia col numero di valori da assegnare");

        IdLeaf elem;
        ExprNode expr;

        for (int i = 0; i < a.getIdList().size(); i++) {
            elem = a.getIdList().get(i);
            expr = a.getValues().get(i);
            //Controllo che le variabili esistano
            VarEntry entry = (VarEntry) StackTable.lookup(elem.getName());
            if (entry == null)
                throw new Error("La variabile " + elem.getName() + "non esiste");
            String exprType = expr.accept(this).toString();
            elem.setType(entry.getType());
            //Controllo che i tipi corrispondano
            if (!entry.getType().equals(exprType))
                throw new Error("'" + elem.getName() + "' è tipo " + entry.getType() + " ma si sta tentando di assegnare un valore di tipo '" +
                        exprType + "'");
        }

        return "notype";
    }

    @Override
    public Object visit(ElseStmt es) {
        String bodyType = "notype";
        if (es.isFirstVisit()) {
            es.setFirstVisit(false);
            StackTable.newScope();
            es.setSymbolTable(StackTable.getScope());
            if (!es.getBody().isEmpty())
                bodyType = es.getBody().accept(this).toString();
            StackTable.removeScope();
        }
        return bodyType;
    }

    @Override
    public Object visit(ForStmt fs) {
        String bodyType = "notype";
        if (fs.isFirstVisit()) {
            fs.setFirstVisit(false);

            StackTable.newScope();

            fs.setSymbolTable(StackTable.getScope());
            fs.getId().setIdType("integer");
            VarEntry v = new VarEntry(
                    fs.getId().getName(),
                    "integer",
                    false
            );
            if (StackTable.lookup(fs.getId().getName()) != null)
                StackTable.addEntry(v);
            //String exprType = fs.getExpr().accept(this).toString();
            if (!fs.getStartValue().getConstType().equals("integer") || !fs.getEndValue().getConstType().equals("integer"))
                throw new Error("Il tipo della condizione del For deve essere Integer");
            bodyType = fs.getBody().accept(this).toString();

            StackTable.removeScope();
        }

        return bodyType;
    }

    @Override
    public Object visit(IfStmt stmt) {
        String bodyType = "notype";
        if (stmt.isFirstVisit()) {
            stmt.setFirstVisit(false);
            StackTable.newScope();
            stmt.setSymbolTable(StackTable.getScope());
            String exprType = stmt.getExpr().accept(this).toString();
            if (!exprType.equals("bool"))
                throw new Error("La condizione del if deve essere di tipo bool e non di tipo " + exprType + "!");
            bodyType = stmt.getBody().accept(this).toString();
            // if (!bodyType.equals("notype")) throw new Error("Errore nel body del if");
            if (stmt.getElseStmt().getBody() != null) {
                stmt.getElseStmt().accept(this);
            }

            StackTable.removeScope();
        }
        return bodyType;
    }

    @Override
    public Object visit(ReadStmt rs) {
        //per ogni idLead controlla se è stata inizializzata
        for (IdLeaf id : rs.getIdList()) {
            Entry e = StackTable.lookup(id.getName());
            if (e == null)
                throw new Error("Prima di potergli assegnare un valore la variabile " + id.getName() + " deve essere dichiarata");
            else {
                if (e.getKindOfEntry().equals("var"))
                    id.setIdType(((VarEntry) e).getType());
            }
        }
        return null;
    }

    @Override
    public Object visit(WhileStmt ws) {
        String bodyType = "notype";
        if (ws.isFirstVisit()) {
            ws.setFirstVisit(false);

            StackTable.newScope();
            ws.setSymbolTable(StackTable.getScope());

            String exprType = ws.getExpr().accept(this).toString();
            if (!exprType.equals("bool"))
                throw new Error("Il tipo della condizione del while deve essere bool invece è stato passato un" + exprType);
            bodyType = ws.getBody().accept(this).toString();

            StackTable.removeScope();
        }
        return bodyType;
    }

    @Override
    public Object visit(WriteStmt ws) {
        if (ws.getOp().equals(""))
            throw new Error("operazione di write non specificata");
        if (ws.getExpressions().size() == 0)
            throw new Error("L'operazione di write richiede almeno un'espressione");
        for (ExprNode e : ws.getExpressions())
            e.accept(this);

        return "notype";
    }

    @Override
    public Object visit(VarInitDeclOp v) {
        //scoping
        for (IdInitOp idInit : v.getIdList()) {
            if (idInit.getExpr() == null)
                initVar(idInit, v.getType());
            idInit.accept(this);
        }
        String type = v.getType().getType();
        //type checking.
        for (IdInitOp id : v.getIdList()) {
            VarEntry entry = (VarEntry) StackTable.getScope().getEntry(id.getId().getName());
            id.getId().setIdType(type);
            if (!entry.getType().equals(type))
                throw new Error("L'espressione associata alla variabile " + id.getId().getName() + "non è conforme al tipo associato ad essa.");
        }
        return "notype";
    }

    @Override
    public Object visit(VarObbInitDeclOp v) {
        //fai il visit di ogni variabile
        for (IdInitObbOp id : v.getIdList())
            id.accept(this);


        //il type checking non è necessario poichè le variabili sono state inizializzate con delle costanti
        //ma senza il costrutto type, infatti usando var, qualsiasi tipo andrà bene alla variabile nella fase di dichiarazione.
        return "notype";
    }

    @Override
    public Object visit(ParExpr expr) {

        return expr.getExpr().accept(this);
    }

    @Override
    public Object visit(SwitchStmt s) {
        VarEntry e =(VarEntry) StackTable.lookup(s.getId().getName());
        if (e == null)
            throw new Error("l'id '" + s.getId().getName() + "' non esiste!");
        String type = e.getType();
        for (CaseStmt c: s.getCaseList()) {
            c.getExpr().accept(this);
            if (!c.getCondType().equals(type))
                throw new Error("uan variabile di tipo '" + type + "' non può assumere valori di tipo '" + c.getCondType() + "'");
        }
        type = s.getCaseList().get(0).accept(this).toString();
        for (CaseStmt c: s.getCaseList().subList(1, s.getCaseList().size())) {
            String newType = c.accept(this).toString();
            if (!type.equals(newType))
                throw new Error("Lo switch non può tornare valori di tipi diversi!");
        }
        return type;
    }

    /**
     * l'espressione del case viene controllata già dallo switch, si deve solo controllare il body
     * @param c
     * @return
     */
    @Override
    public Object visit(CaseStmt c) {
        return c.getBody().accept(this).toString();

    }

    private void initVar(IdInitOp id, TypeLeaf t) {
        id.setExpr(
                new ConstLeaf(
                        t.getType(),
                        switch (t.getType()) {
                            case "integer" -> "0";
                            case "string" -> "";
                            case "float" -> "0.0";
                            case "char" -> "''";
                            case "bool" -> "false";
                            default -> throw new Error("idInitOp type error '" + id + "'");
                        }
                )
        );
    }
}
