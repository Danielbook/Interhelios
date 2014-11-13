package komaapp.komaprojekt;

public class Database
{
    private String setting;
    private int val;

    public Database(String setting, int val)
    {
        this.setting = setting;
        this.val = val;
    }

    public String getSetting() {
        return setting;
    }

    public void setSetting(String setting) {
        this.setting = setting;
    }

    public int getVal() {
        return val;
    }

    public void setVal(int val) {
        this.val = val;
    }
}

