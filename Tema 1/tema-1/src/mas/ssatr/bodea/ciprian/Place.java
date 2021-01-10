package mas.ssatr.bodea.ciprian;

public class Place {
    private int Tokens;
    private final String Name;

    public Place(String name, int tokens) {
        this.Tokens = tokens;
        this.Name = name;
    }

    public boolean HasToken() {
        return Tokens > 0;
    }

    public void AddToken() {
        this.Tokens = Tokens + 1;
    }

    public void RemoveToken() {
        this.Tokens = Tokens - 1;
    }

    public String getName() {
        return this.Name;
    }

    public int getTokens() {
        return this.Tokens;
    }
}
