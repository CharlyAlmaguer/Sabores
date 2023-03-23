package com.car.sabores.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.car.sabores.model.CarritoSqlModel

class SqlHelper (context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object{
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "sabores.db"
        private const val TBL_CARRITO = "tbl_carrito"
        private const val ID = "id"
        private const val NOMBRE = "nombre"
        private const val CANTIDAD = "cantidad"
        private const val PRECIO = "precio"
        private const val COSTO = "costo"
        private const val IMAGEN = "imagen"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val sqlCreate = "CREATE TABLE $TBL_CARRITO ($ID INTEGER PRIMARY KEY, $NOMBRE TEXT,$CANTIDAD INTEGER,$PRECIO TEXT,$COSTO REAl,$IMAGEN INTEGER)"
        db?.execSQL(sqlCreate)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val sqlUpdate = "DROP TABLE IF EXISTS $TBL_CARRITO"
        db?.execSQL(sqlUpdate)
        onCreate(db)
    }



    fun insertItem(item: CarritoSqlModel): Long {
        val db = writableDatabase
        val contentValues = ContentValues().apply {
            put(ID, item.id)
            put(NOMBRE, item.nombre)
            put(CANTIDAD, item.cantidad)
            put(PRECIO, item.precio)
            put(COSTO, item.costo)
            put(IMAGEN, item.imagen)

        }

        return db.insert(TBL_CARRITO, null, contentValues)
        db.close()
    }


    fun getAllItems(): ArrayList<CarritoSqlModel> {

        val itemList = arrayListOf<CarritoSqlModel>()

        val query = "SELECT * FROM $TBL_CARRITO"
        val db = readableDatabase

        val cursor: Cursor?
        try {
            cursor = db.rawQuery(query, null)

        } catch (e: Exception) {
            e.printStackTrace()
            return itemList
        }
        var id: Int
        var nombre: String
        var cantidad : Int
        var precio : String
        var costo: Double
        var imagen : Int

        with(cursor) {
            while (moveToNext()) {
                id = getInt(getColumnIndexOrThrow(ID))
                nombre = getString(getColumnIndexOrThrow(NOMBRE))
                cantidad = getInt(getColumnIndexOrThrow(CANTIDAD))
                precio = getString(getColumnIndexOrThrow(PRECIO))
                costo = getDouble(getColumnIndexOrThrow(COSTO))
                imagen = getInt(getColumnIndexOrThrow(IMAGEN))

                val item = CarritoSqlModel(id,nombre,cantidad,precio,costo,imagen)
                itemList.add(item)
            }
        }
        cursor.close()
        db.close()
        return itemList
    }

    fun updateItem(item: CarritoSqlModel): Int{
        val db = writableDatabase
        val contentValues = ContentValues().apply {
            put(CANTIDAD, item.cantidad)
        }
        val result = db.update(TBL_CARRITO, contentValues, "id = ${item.id}", null)
        db.close()
        return result
    }

    fun deleteItem(itemId: Int): Int {
        val db = writableDatabase
        return db.delete(TBL_CARRITO, "id=?", arrayOf("$itemId"))
        db.close()
    }

}