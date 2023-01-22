package tree.table;

public class Signature {
    private String name;
    private String signature;

    public Signature(String name, String signature) {
        this.name = name;
        this.signature = signature;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    @Override
    public String toString() {
        return "Signature{" +
                "name='" + name + '\'' +
                ", signature='" + signature + '\'' +
                '}';
    }
}