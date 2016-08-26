package magio.ohmypet.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import magio.ohmypet.util.Constants;

/**
 * Created by mini on 2016-08-25.
 */
public class SqlHelper extends SQLiteOpenHelper{

    public static final String DB_NAME = "ADOPT";

    private Context mContext;
    private String mDbName;
    private CursorFactory mFactory;
    private int mVersion;

    public SqlHelper(Context context, String name, CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
        mDbName = name;
        mFactory = factory;
        mVersion = version;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 새로운 테이블을 생성한다.
        // create table 테이블명 (컬럼명 타입 옵션);
        String sql = "CREATE TABLE " + DB_NAME + " ( _id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, price INTEGER, description TEXT);";
        Log.i(Constants.TAG, "Create DB : sql(" + sql + ")");
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "drop table " + DB_NAME + ";"; // 테이블 드랍
        db.execSQL(sql);
        onCreate(db); // 다시 테이블 생성
    }

    public Context getContext() {
        return mContext;
    }
    public String getDbName() {
        return mDbName;
    }
    public CursorFactory getCursorFactory () {
        return mFactory;
    }
    public int getVersion() {
        return mVersion;
    }

    public void insert(String _query) {
        SQLiteDatabase db = getWritableDatabase();
        Log.i(Constants.TAG, "DB : " + db);
        db.execSQL(_query);
        db.close();
    }

    public void update(String _query) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(_query);
        db.close();
    }

    public void delete(String _query) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(_query);
        db.close();
    }

    public String PrintData() {
        SQLiteDatabase db = getReadableDatabase();
        String str = "";

        Cursor cursor = db.rawQuery("select * from " + DB_NAME, null);
        while(cursor.moveToNext()) {
            str += cursor.getInt(0)
                    + " : title = "
                    + cursor.getString(1)
                    + ", price = "
                    + cursor.getInt(2)
                    + ", description = "
                    + cursor.getString(3)
                    + "\n";
        }

        return str;
    }
}
