package cz.stokratandroid.sqlite2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {

 //   private Context context;


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
        db.execSQL("CREATE TABLE VerzeAndroidu (_id INTEGER PRIMARY KEY, Nazev TEXT, Verze TEXT)");

        // vlozime do tabulky iniciacni data
        db.execSQL("INSERT INTO VerzeAndroidu ('Nazev', 'Verze') VALUES ('bez kódového označení', '1.0')");
        db.execSQL("INSERT INTO VerzeAndroidu ('Nazev', 'Verze') VALUES ('bez kódového označení', '1.1')");
        db.execSQL("INSERT INTO VerzeAndroidu ('Nazev', 'Verze') VALUES ('Cupcake', '1.5')");
        db.execSQL("INSERT INTO VerzeAndroidu ('Nazev', 'Verze') VALUES ('Donut', '1.6')");
        db.execSQL("INSERT INTO VerzeAndroidu ('Nazev', 'Verze') VALUES ('Eclair', '2.0')");
        db.execSQL("INSERT INTO VerzeAndroidu ('Nazev', 'Verze') VALUES ('Eclair', '2.1')");
        db.execSQL("INSERT INTO VerzeAndroidu ('Nazev', 'Verze') VALUES ('Froyo', '2.2')");
        db.execSQL("INSERT INTO VerzeAndroidu ('Nazev', 'Verze') VALUES ('Gingerbread', '2.3')");
        db.execSQL("INSERT INTO VerzeAndroidu ('Nazev', 'Verze') VALUES ('Honeycomb', '3.0')");
        db.execSQL("INSERT INTO VerzeAndroidu ('Nazev', 'Verze') VALUES ('Honeycomb', '3.1')");
        db.execSQL("INSERT INTO VerzeAndroidu ('Nazev', 'Verze') VALUES ('Honeycomb', '3.2')");
        db.execSQL("INSERT INTO VerzeAndroidu ('Nazev', 'Verze') VALUES ('Ice Cream Sandwich', '4.0')");
        db.execSQL("INSERT INTO VerzeAndroidu ('Nazev', 'Verze') VALUES ('Jelly Bean', '4.1')");
        db.execSQL("INSERT INTO VerzeAndroidu ('Nazev', 'Verze') VALUES ('Jelly Bean', '4.2')");
        db.execSQL("INSERT INTO VerzeAndroidu ('Nazev', 'Verze') VALUES ('Jelly Bean', '4.3')");
        db.execSQL("INSERT INTO VerzeAndroidu ('Nazev', 'Verze') VALUES ('KitKat', '4.4')");
        db.execSQL("INSERT INTO VerzeAndroidu ('Nazev', 'Verze') VALUES ('Lollipop', '5.0')");
        db.execSQL("INSERT INTO VerzeAndroidu ('Nazev', 'Verze') VALUES ('Lollipop', '5.1')");
        db.execSQL("INSERT INTO VerzeAndroidu ('Nazev', 'Verze') VALUES ('Marshmallow', '6.0')");
        db.execSQL("INSERT INTO VerzeAndroidu ('Nazev', 'Verze') VALUES ('Nougat', '7.0')");
        db.execSQL("INSERT INTO VerzeAndroidu ('Nazev', 'Verze') VALUES ('Nougat', '7.1')");
        db.execSQL("INSERT INTO VerzeAndroidu ('Nazev', 'Verze') VALUES ('Oreo', '8.0')");
        db.execSQL("INSERT INTO VerzeAndroidu ('Nazev', 'Verze') VALUES ('Oreo', '8.1')");
        db.execSQL("INSERT INTO VerzeAndroidu ('Nazev', 'Verze') VALUES ('Pie', '9.0')");
        db.execSQL("INSERT INTO VerzeAndroidu ('Nazev', 'Verze') VALUES ('Android 10', '10')");
        db.execSQL("INSERT INTO VerzeAndroidu ('Nazev', 'Verze') VALUES ('Android 11', '11')");
        db.execSQL("INSERT INTO VerzeAndroidu ('Nazev', 'Verze') VALUES ('Android 12', '12')");
        db.execSQL("INSERT INTO VerzeAndroidu ('Nazev', 'Verze') VALUES ('Android 13', '13')");
        db.execSQL("INSERT INTO VerzeAndroidu ('Nazev', 'Verze') VALUES ('Android 14', '14')");
    }

    // metoda volana pri zmene verze databaze smerem nahoru
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // smazat starou tabulku, pokud existuje
        db.execSQL("DROP TABLE IF EXISTS VerzeAndroidu");

        // znovu vytvorit tabulku
        onCreate(db);
    }

    // metoda volana pri zmene verze databaze smerem dolu
    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
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
        return db.rawQuery("select _id, Verze, Nazev from VerzeAndroidu", null);
    }

    // do tabulky VerzeAndroidu vlozi zaznam
    public void vlozitZaznam(String nazev, String verze) {
        // insert SQL dotazem
        // db.execSQL("INSERT INTO VerzeAndroidu ('Nazev', 'Verze') VALUES ('" + nazev + "', '" + verze + "')");

        ContentValues vkladaneHodnoty = new ContentValues();
        vkladaneHodnoty.put("Nazev", nazev);
        vkladaneHodnoty.put("Verze", verze);
        db.insert("VerzeAndroidu", null, vkladaneHodnoty);
    }

    // zmeni zaznam v tabulce VerzeAndroidu
    public void upravitZaznam(String id, String nazev, String verze) {
        // update SQL dotazem
        // db.execSQL("UPDATE VerzeAndroidu SET Nazev='" + nazev + "', Verze='" + verze + "' WHERE _id='" + id + "'");

        ContentValues vkladaneHodnoty = new ContentValues();
        vkladaneHodnoty.put("Nazev", nazev);
        vkladaneHodnoty.put("Verze", verze);
        db.update("VerzeAndroidu", vkladaneHodnoty, "_id=" + id, null);
    }

    // smaze zaznam z tabulky VerzeAndroidu
    public void smazatZaznam(String id) {
        // delete SQL dotazem
        // db.execSQL("DELETE FROM VerzeAndroidu WHERE _id='" + id + "'");

        db.delete("VerzeAndroidu", "_id=" + id, null);
    }

    // smaze obsah tabulky VerzeAndroidu
    public void smazatData() {
        // delete SQL dotazem
        // db.execSQL("DELETE FROM VerzeAndroidu");

        db.delete("VerzeAndroidu", null, null);
    }

    // odpojeni se od databaze
    public void odpojitDB(){
        db.close();
    }
}