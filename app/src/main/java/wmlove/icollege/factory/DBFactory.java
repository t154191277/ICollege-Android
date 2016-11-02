package wmlove.icollege.factory;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by wmlove on 2016/10/19.
 */

public class DBFactory extends SQLiteOpenHelper {
    private final static int VERSION = 1;
    private final static String DB_NAME = "courses.db";
    private final static String TABLE_NAME = "Course";
    private final static String Drop_TBL_IF_EXISTS = "DROP TABLE IF EXISTS Course";
    private final static String CREATE_TBL = "CREATE TABLE Course ( " +
                            "_id VARCHAR PRIMARY KEY, courseCode VARCHAR,  courseName VARCHAR,  courseType VARCHAR, teacher VARCHAR,  credit FLOAT," +
                            "day int,  startLine int, deadLine int,  openWeek int, closeWeek int,"  +
                            "isSingle VARCHAR,  place VARCHAR"  +
                            ")";
    private final static String CREATE_TEMP_TBL = "CREATE TEMPORARY TABLE temp ( " +
                             "_id VARCHAR PRIMARY KEY, courseCode VARCHAR,  courseName VARCHAR,  courseType VARCHAR, teacher VARCHAR,  credit FLOAT," +
                             "day int,  startLine int, deadLine int,  openWeek int, closeWeek int,"  +
                             "isSingle VARCHAR,  place VARCHAR"  +
                             ")";
    private SQLiteDatabase db;

    public DBFactory(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DBFactory(Context context, String name, int version){
        this(context, name, null, version);
    }

    public DBFactory(Context context){
        this(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;
        db.execSQL(Drop_TBL_IF_EXISTS);
        db.execSQL(CREATE_TBL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(Drop_TBL_IF_EXISTS);

    }

    public void create()
    {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(Drop_TBL_IF_EXISTS);
        db.execSQL(CREATE_TBL);
    }

    public void insert(ContentValues values) throws Exception{
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_NAME, null, values);
    }

    public Cursor query(String selection, String[] selectionArgs){
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query(TABLE_NAME, null, selection, selectionArgs, null, null, null, null);
        return c;
    }

    public Cursor queryCourseOrderByWeek()
    {
        SQLiteDatabase db = getReadableDatabase();
        db.beginTransaction();
        try
        {
            db.execSQL(CREATE_TEMP_TBL);
            db.execSQL("INSERT INTO temp SELECT * FROM Course group BY courseCode");
            db.execSQL("UPDATE temp SET openWeek = (SELECT min(openWeek) as openWeek from Course where temp.courseCode = Course.courseCode GROUP BY courseCode)");
            db.execSQL("UPDATE temp SET closeWeek = (SELECT max(closeWeek) as closeWeek from Course where temp.courseCode = Course.courseCode GROUP BY courseCode);");
            db.setTransactionSuccessful();
        }
        catch (Exception e)
        {

        }
        finally {
            db.endTransaction();
        }
        Cursor cursor = db.query("temp", null, null, null , null, null, null, null);
        return cursor;
    }

    public Cursor query(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query(TABLE_NAME, null, null, null , null, null, null, null);
        return c;
    }

    public void drop()
    {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(Drop_TBL_IF_EXISTS);
    }

    public void delete(int id){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_NAME, "id=?", new String[]{String.valueOf(id)});
    }


    public void update(ContentValues values, String whereClause, String[]whereArgs){
        SQLiteDatabase db = getWritableDatabase();
        db.update(TABLE_NAME, values, whereClause, whereArgs);
    }

    public void close(){
        if(db != null){
            db.close();
        }
    }

}