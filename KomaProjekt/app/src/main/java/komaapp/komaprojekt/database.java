package komaapp.komaprojekt;

public class database
{
    private String setting;
    private int val;

    public database(String setting, int val)
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

