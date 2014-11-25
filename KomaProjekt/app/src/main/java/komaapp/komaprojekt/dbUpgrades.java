package komaapp.komaprojekt;

public class dbUpgrades
{
    private String upgrade;
    private int level;
    private int price;

    public dbUpgrades(String upgrade, int level, int cost)
    {
        this.upgrade = upgrade;
        this.level = level;
        this.price = cost;
    }

    public String getUpgrade() {
        return upgrade;
    }

    public int getLevel() {
        return level;
    }

    public void addLevel() {
        this.level++;
    }

    public int getPrice() { return price; }

    public void setPrice(int cost) { this.price = cost; }
}

