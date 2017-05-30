package com.example.student.database;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import static android.R.attr.description;
import static android.R.attr.id;
import static android.os.Build.VERSION_CODES.M;

public class MainActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public class TodoDbAdapter {
        private static final String DEBUG_TAG = "SqLiteTodoM anager";


        private SQLiteDatabase db;
        private Context context;
        private MainActivity.TodoDbAdapter.DatabaseHelper dbHelper;

        private static final int DB_VERSION = 1;
        private static final String DB_NAME = "database.db";
        private static final String DB_TODO_TABLE = "Contacts";

        public static final String KEY_ID = "_id";
        public static final String ID_OPTIONS = "INTEGER PRIMARY KEY AUTOINCREMENT";
        public static final int ID_COLUMN = 0;

        public static final String KEY_DESCRIPTION = "description";
        public static final String DESCRIPTION_OPTIONS = "TEXT NOT NULL";
        public static final int DESCRIPTION_COLUMN = 1;

        public static final String KEY_COMPLETED = "completed";
        public static final String COMPLETED_OPTIONS = "INTEGER DEFAULT 0";
        public static final int COMPLETED_COLUMN = 2;

        private static final String DB_CREATE_TODO_TABLE =
                "CREATE TABLE " + DB_TODO_TABLE + "( " +
                        KEY_ID + " " + ID_OPTIONS + ", " +
                        KEY_DESCRIPTION + " " + DESCRIPTION_OPTIONS + ", " +
                        KEY_COMPLETED + " " + COMPLETED_OPTIONS +
                        ");";

        private static final String DROP_TODO_TABLE =
                "DROP TABLE IF EXISTS " + DB_TODO_TABLE;

        private class DatabaseHelper extends SQLiteOpenHelper {
            public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
                super(context, name, factory, version);
            }

            @Override
            public void onCreate(SQLiteDatabase db) {
                db.execSQL(DB_CREATE_TODO_TABLE);

                Log.d(DEBUG_TAG, "Database creating...");
                Log.d(DEBUG_TAG, "Table " + DB_TODO_TABLE + " ver." + DB_VERSION + " created");
            }

            @Override
            public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
                db.execSQL(DROP_TODO_TABLE);

                Log.d(DEBUG_TAG, "Database updating...");
                Log.d(DEBUG_TAG, "Table " + DB_TODO_TABLE + " updated from ver." + oldVersion + " to ver." + newVersion);
                Log.d(DEBUG_TAG, "All data is lost.");

                onCreate(db);
            }
        }

        public MainActivity.TodoDbAdapter open(){
            dbHelper = new MainActivity.TodoDbAdapter.DatabaseHelper(context, DB_NAME, null, DB_VERSION);
            try {
                db = dbHelper.getWritableDatabase();
            } catch (SQLException e) {
                db = dbHelper.getReadableDatabase();
            }
            return this;
        }
        public void close() {
            dbHelper.close();
        }

        public TodoDbAdapter(Context context) {
            this.context = context;
        }

        public long insertContact(int number,String description) {
            ContentValues new Values = new ContentValues();
            new Values.put(KEY_DESCRIPTION , description);
            new Values.put(KEY_NUMBER, number);
            return db.insert(DB_TABLE, null, new Values);
        }


        public class Contacts extends TodoDbAdapter{
            public Contacts(long id, String description, int number) {
                this.id = id;
                this.description = description;
                this.number = number;
            }

            public long getId() {
                return id;
            }

            public void setId(long id) {
                this.id = id;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public boolean getNumber() {
                return number;
            }

            public void setNum ber(int number) {
                this.number = number;
            }

        };

    }


}

