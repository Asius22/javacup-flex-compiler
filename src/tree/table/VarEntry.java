package tree.table;

public class VarEntry extends Entry{

    private String type;
    private boolean isOut;

    public VarEntry(String name, String type, boolean isOut) {
        super(name, "var");
        this.type = type;
        this.isOut = isOut;
    }



    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isOut() {
        return isOut;
    }

    public void setOut(boolean out) {
        isOut = out;
    }

    @Override
    public String toString() {
        return "VarEntry{" +
                "type='" + type + '\'' +
                ", isOut=" + isOut +
                '}';
    }
}
