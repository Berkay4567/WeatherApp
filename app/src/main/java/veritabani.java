import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class veritabani extends SQLiteOpenHelper {
    // Veritabanı versiyonu
    private static final int DATABASE_VERSION = 1;
    // Veritabanı adı
    private static final String DATABASE_NAME = "sehirler";
    public veritabani(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    // Tabloların oluşturulması
    @Override
    public void onCreate(SQLiteDatabase db) {

        // notes tablosunu oluştur
        db.execSQL("CREATE TABLE bilgiler (sehir TEXT);");
    }

    // Veritabanının güncellenmesi
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Mevcut notes tablosunu kaldır.
        db.execSQL("DROP TABLE IF EXISTS bilgiler");

        // Veritabanını tekrardan oluştur.
        onCreate(db);
    }
}
