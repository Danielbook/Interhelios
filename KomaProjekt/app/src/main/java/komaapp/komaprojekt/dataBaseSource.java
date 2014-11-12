package komaapp.komaprojekt;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class dataBaseSource {

    private SQLiteDatabase database;
    private dataBaseHelper dbHelper;

    public dataBaseSource(Context context)
    {
        dbHelper = new dataBaseHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }
}
