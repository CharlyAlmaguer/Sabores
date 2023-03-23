package com.car.sabores.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.car.dsapp.databinding.ActivityCarritoBinding
import com.car.sabores.adapters.CarritoAdapter
import com.car.sabores.database.SqlHelper
import com.car.sabores.model.CarritoSqlModel

class CarritoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCarritoBinding
    private lateinit var sqlHelper: SqlHelper
    private lateinit var costos: ArrayList<CarritoSqlModel>
    private var total: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCarritoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sqlHelper = SqlHelper(this)
        var i = 0

        costos = sqlHelper.getAllItems()

        while (i < costos.size){
            total += (costos[i].costo * costos[i].cantidad)
            i++
        }

        binding.tvTotal.text = "Total: $${total}"

        binding.rvCarrito.layoutManager = LinearLayoutManager(this)
        binding.rvCarrito.adapter = CarritoAdapter(this, sqlHelper.getAllItems())
    }

    //funcion que recupera el producto seleccionado
    fun selectedItem(item: CarritoSqlModel) {

        total -= (item.costo * item.cantidad)
        val result = sqlHelper.deleteItem(item.id)
        if (result > 0){
            Toast.makeText(this, "Eliminado", Toast.LENGTH_SHORT).show()
        }else {
            Toast.makeText(this, "No eliminado", Toast.LENGTH_SHORT).show()
        }
        binding.tvTotal.text = "Total: $${total}"
        binding.rvCarrito.layoutManager = LinearLayoutManager(this)
        binding.rvCarrito.adapter = CarritoAdapter(this, sqlHelper.getAllItems())
    }

}