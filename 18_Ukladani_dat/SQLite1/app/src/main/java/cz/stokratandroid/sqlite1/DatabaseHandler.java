package cz.stokratandroid.sqlite1;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {

    public SQLiteDatabase db;

    // konstruktor se jmenem databaze a jeji verzi
    // zde dojde k zalozeni databaze aplikace
    public DatabaseHandler(Context context) {
        super(context, "VerzeAndroidu", null, 1);
    }

    // metoda volana pri vzniku databaze
    // zde zalozime databazove tabulky
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE VerzeAndroidu (ID INTEGER PRIMARY KEY, Nazev TEXT, Verze TEXT)");
    }

    // metoda volana pri zmene verze databaze
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // smazat starou tabulku, pokud existuje
        db.execSQL("DROP TABLE IF EXISTS VerzeAndroidu");

        // znovu vytvorit tabulku
        onCreate(db);
    }

    // pripojeni k databazi
    public void pripojitDB() {
        db = getWritableDatabase();
    }

    // nacte a vrati vsechna data z tabulky VerzeAndroidu
    public Cursor nacistData() {
        return db.rawQuery("select * from VerzeAndroidu", null);
    }

    // do tabulky VerzeAndroidu vlozi zaznam
    public void vlozitZaznam(String nazev, String verze) {
        db.execSQL("INSERT INTO VerzeAndroidu ('Nazev', 'Verze') VALUES ('" + nazev + "', '" + verze + "')");
    }

    // zmeni zaznam v tabulce VerzeAndroidu
    public void upravitZaznam(String id, String nazev, String verze) {
        db.execSQL("UPDATE VerzeAndroidu SET Nazev='" + nazev + "', Verze='" + verze + "' WHERE id='" + id + "'");
    }

    // smaze obsah tabulky VerzeAndroidu
    public void smazatData() {
        db.execSQL("DELETE FROM VerzeAndroidu");
    }

    // odpojeni se od databaze
    public void odpojitDB(){
        db.close();
    }
}