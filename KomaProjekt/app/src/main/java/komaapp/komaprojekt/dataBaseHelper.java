package komaapp.komaprojekt;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class dataBaseHelper extends SQLiteOpenHelper {

  private static final int DATABASE_VERSION = 1;
  private static final String DATABASE_NAME = "settings.db";

  public static final String TABLE_SETTINGS = "settings";
  public static final String COLUMN_ID = "_id";
  public static final String COLUMN_TODO = "todo";

  // Database creation sql statement
  private static final String DATABASE_CREATE = "create table "
      + TABLE_SETTINGS + "(" + COLUMN_ID
      + " integer primary key autoincrement, " + COLUMN_TODO
      + " text not null);";

  public dataBaseHelper(Context context)
  {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }

  @Override
  public void onCreate(SQLiteDatabase database)
  {
    database.execSQL(DATABASE_CREATE);
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
  {
    Log.w(dataBaseHelper.class.getName(),
        "Upgrading database from version " + oldVersion + " to "
            + newVersion + ", which will destroy all old data");
    db.execSQL("DROP TABLE IF EXISTS " + TABLE_SETTINGS);
    onCreate(db);
  }
} 