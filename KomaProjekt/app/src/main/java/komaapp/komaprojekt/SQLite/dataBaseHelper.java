package komaapp.komaprojekt.SQLite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class dataBaseHelper extends SQLiteOpenHelper {

  private static final int DATABASE_VERSION = 1;
  private static final String DATABASE_NAME = "appDatabase.db";

  public static final String TABLE_SETTINGS = "settings",
    KEY_ID = "id",
    KEY_MUSIC = "music",
    KEY_SOUND = "sound";


  // Database creation sql statement
  private static final String DATABASE_CREATE = "create table "
      + TABLE_SETTINGS + "(" + KEY_ID + " INTEGER PRIMARY KEY, "  + KEY_MUSIC + " INTEGER" + KEY_SOUND + " INTEGER)";

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