package tree.table;

import java.util.ArrayList;

public class SignatureList {
    private ArrayList<Signature> list;

    public SignatureList() {
        this.list = new ArrayList<>();
    }

    public ArrayList<Signature> getList() {
        return list;
    }

    public void setList(ArrayList<Signature> list) {
        this.list = list;
    }

    public ArrayList<String> getSignature(String funName){
        ArrayList<String> res = new ArrayList<>();
        for (Signature s: this.list)
            if (s.getName().equals(funName))
                res.add( s.getSignature());

        return res;
    }

    public void add(Signature s){
        this.list.add(s);
    }
}
