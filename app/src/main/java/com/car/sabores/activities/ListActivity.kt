package com.car.sabores.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.car.dsapp.R
import com.car.dsapp.databinding.ActivityListBinding
import com.car.sabores.adapters.ListAdapter
import com.car.sabores.database.SqlHelper
import com.car.sabores.model.CarritoSqlModel
import com.car.sabores.model.ListS

class ListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListBinding
    private lateinit var sqlHelper: SqlHelper
    private lateinit var itemList: ArrayList<CarritoSqlModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sqlHelper = SqlHelper(this)
        itemList = arrayListOf<CarritoSqlModel>()

        val bundle = intent.extras

        val categoria = bundle?.getString("categoria","")

        if (categoria == "chocolates"){
            //Para que se vea el circulo de carga
            binding.pbConexion.visibility = View.GONE

            binding.rvList.layoutManager = LinearLayoutManager(this@ListActivity)
            binding.rvList.adapter = ListAdapter(this@ListActivity, getChocolates())

        }else if(categoria == "palomitas"){
            //Para que se vea el circulo de carga
            binding.pbConexion.visibility = View.GONE

            binding.rvList.layoutManager = LinearLayoutManager(this@ListActivity)
            binding.rvList.adapter = ListAdapter(this@ListActivity, getPalomitas())

        }else if(categoria == "manzanas"){
            //Para que se vea el circulo de carga
            binding.pbConexion.visibility = View.GONE

            binding.rvList.layoutManager = LinearLayoutManager(this@ListActivity)
            binding.rvList.adapter = ListAdapter(this@ListActivity, getManzanas())

        }else if(categoria == "pastelillos"){
            //Para que se vea el circulo de carga
            binding.pbConexion.visibility = View.GONE

            binding.rvList.layoutManager = LinearLayoutManager(this@ListActivity)
            binding.rvList.adapter = ListAdapter(this@ListActivity, getPastelillos())

        }else if(categoria == "otros"){
            //Para que se vea el circulo de carga
            binding.pbConexion.visibility = View.GONE

            binding.rvList.layoutManager = LinearLayoutManager(this@ListActivity)
            binding.rvList.adapter = ListAdapter(this@ListActivity, getOtros())
        }

        binding.ivIconCarrito.setOnClickListener {
            val intent = Intent(this, CarritoActivity::class.java)
            startActivity(intent)
        }

    }

    //Cargamos los recursos
    private fun getChocolates(): ArrayList<ListS>{
        val data = arrayListOf<ListS>()

        data.add(ListS(100,"Mini Magnum","Paletas en forma de mini Magnum rellenas de pastel cubiertas de chocolate.","$15.00 c/u",15.00,
            R.drawable.ch_1))
        data.add(ListS(101,"Trufas de Chocolate","Trufas de chocolate cubiertas de granillo de chocolate amargo/con leche/ blanco.","$12.00 paquete con 4 pz.",12.00,
            R.drawable.ch_2))
        data.add(ListS(102,"Bombon de Cafe","Bombón sabor café cubierto de chocolate semi amargo.","$15.00 paquete con 3 pz.",15.00,
            R.drawable.ch_3))
        data.add(ListS(103,"Bombones de Amaranto","Bombones cubierto de chocolate con leche espolvoreados de amaranto.","$15.00 paquete de 3 pz.",15.00,
            R.drawable.ch_4))

        return data
    }

    private fun getPalomitas(): ArrayList<ListS>{
        val data = arrayListOf<ListS>()

        data.add(ListS(200,"Palomitas Oreo","Palomitas sabor galleta oreo bañadas en chocolate blanco.","$15.00 bolsa con 30 gr.",15.00,
            R.drawable.pal_1))
        data.add(ListS(201,"Palomitas Moka","Palomitas de café, con malvaviscos, salpicado con chocolate y trozos de nuez.","$15.00 bolsa con 30 gr.",15.00,
            R.drawable.pal_2))

        return data
    }

    private fun getManzanas(): ArrayList<ListS>{
        val data = arrayListOf<ListS>()

        data.add(ListS(300,"Manzanas de Caramelo","Deliciosas manzanas cubiertas de nuestra receta de caramelo clasico.","$15.50 c/u",15.50,
            R.drawable.man_1))

        return data
    }

    private fun getPastelillos(): ArrayList<ListS>{
        val data = arrayListOf<ListS>()

        data.add(ListS(400,"Brownies","Nuestros deliciosos brownies de la casa con el mejor sabor a chocolate que te encantaran.","$12.50 c/u",12.00,
            R.drawable.pas_1))
        data.add(ListS(401,"Cup Cake Limon","Panesillos sabor vainilla con nuestro delicioso chantilli de limon.","$18.00 c/u",18.00,
            R.drawable.pas_2))
        data.add(ListS(402,"Tarta de Frutas","Tarta de queso con relleno de crema pastelera decorada con fruta fresca.","$25.00 c/u",25.00,
            R.drawable.pas_3))

        return data
    }

    private fun getOtros(): ArrayList<ListS>{
        val data = arrayListOf<ListS>()

        data.add(ListS(500,"Paletas Corazon","Paletas de corazón geométrico rellenas de chocoamaranto y cubiertas de chocolate.","$17.00 c/u",17.00,
            R.drawable.ot_1))
        data.add(ListS(501,"Galletas Decoradas","Galletas de mantequilla decoradas con chocolate y confeti comestible.","$17.00 c/u",17.00,
            R.drawable.ot_2))
        data.add(ListS(502,"Cake Pop","Paletas de pastel sabor chocolate/mantequilla/vainilla cubiertas de chocolate.","$17.00 c/u",17.00,
            R.drawable.ot_3))

        return data
    }

    //funcion que recupera el producto seleccionado
    fun selectedProduct(product: ListS) {
            itemList = sqlHelper.getAllItems()
    //implementamos el algoritmo para el carrito de compras
            if (itemList.size>0){
                var i = 0
                var bandera = true
                while (i<itemList.size){

                    if (itemList[i].id == product.id){

                        val cantidad = itemList[i].cantidad + 1
                        val item = CarritoSqlModel(id = product.id,nombre = product.nombre, cantidad = cantidad , precio = product.precio, costo = product.costo, imagen = product.imagen)
                        var result = sqlHelper.updateItem(item)
                        if (result > -1){
                            Toast.makeText(this, "Agregado", Toast.LENGTH_SHORT).show()
                        }else {
                            Toast.makeText(this, "No Agregado", Toast.LENGTH_SHORT).show()
                        }
                        bandera = false
                    }

                    i++
                }

                if(bandera){
                     val item = CarritoSqlModel(id = product.id,nombre = product.nombre, cantidad = 1 , precio = product.precio, costo = product.costo, imagen = product.imagen)
                     var result = sqlHelper.insertItem(item)
                     if (result > -1){
                         Toast.makeText(this, "Agregado", Toast.LENGTH_SHORT).show()
                     }else {
                         Toast.makeText(this, "No Agregado", Toast.LENGTH_SHORT).show()
                     }
                }

            }else{
                val item = CarritoSqlModel(id = product.id,nombre = product.nombre, cantidad = 1 , precio = product.precio, costo = product.costo, imagen = product.imagen)
                var result = sqlHelper.insertItem(item)
                if (result > -1){
                    Toast.makeText(this, "Agregado", Toast.LENGTH_SHORT).show()
                }else {
                    Toast.makeText(this, "No Agregado", Toast.LENGTH_SHORT).show()
                }
            }

    }


}