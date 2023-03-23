package com.car.sabores.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.car.dsapp.databinding.ActivityCategoriasBinding

class CategoriasActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCategoriasBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoriasBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var categoria: String?

        binding.categoriesCard1.setOnClickListener {
            categoria = "chocolates"
            val parametros = Bundle().apply {
                putString("categoria", categoria)
            }
            val intent = Intent(this, ListActivity::class.java).apply {
                putExtras(parametros)
            }
            startActivity(intent)
        }

        binding.categoriesCard2.setOnClickListener {
            categoria = "palomitas"
            val parametros = Bundle().apply {
                putString("categoria", categoria)
            }
            val intent = Intent(this, ListActivity::class.java).apply {
                putExtras(parametros)
            }
            startActivity(intent)
        }

        binding.categoriesCard3.setOnClickListener {
            categoria = "manzanas"
            val parametros = Bundle().apply {
                putString("categoria", categoria)
            }
            val intent = Intent(this, ListActivity::class.java).apply {
                putExtras(parametros)
            }
            startActivity(intent)
        }

        binding.categoriesCard4.setOnClickListener {
            categoria = "pastelillos"
            val parametros = Bundle().apply {
                putString("categoria", categoria)
            }
            val intent = Intent(this, ListActivity::class.java).apply {
                putExtras(parametros)
            }
            startActivity(intent)
        }

        binding.categoriesCard5.setOnClickListener {
            categoria = "otros"
            val parametros = Bundle().apply {
                putString("categoria", categoria)
            }
            val intent = Intent(this, ListActivity::class.java).apply {
                putExtras(parametros)
            }
            startActivity(intent)
        }

        binding.ivIconCarrito.setOnClickListener {
            val intent = Intent(this, CarritoActivity::class.java)
            startActivity(intent)
        }
    }
}