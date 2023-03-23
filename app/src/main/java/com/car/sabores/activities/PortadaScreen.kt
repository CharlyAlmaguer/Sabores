package com.car.sabores.activities

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.car.dsapp.databinding.ActivityPortadaScreenBinding

class PortadaScreen : AppCompatActivity() {

    private lateinit var binding: ActivityPortadaScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPortadaScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val cm = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        val isConnected: Boolean = activeNetwork?.isConnectedOrConnecting == true

        if (isConnected==false) {
            AlertDialog.Builder(this)
                .setTitle("¡ERROR!")
                .setMessage("Porfavor revisa tu conexion a internet e intentalo mas tarde")
                .setPositiveButton("OK", DialogInterface.OnClickListener { _, _ ->
                    finish()
                })
                .create()
                .show()
        }else{
            var conexion = ""
            val isMetered = cm.isActiveNetworkMetered()

            if(isMetered){
                conexion = "Datos Moviles"
            }else{
                conexion = "WiFi"
            }

            Toast.makeText(this, "NetWork: $conexion", Toast.LENGTH_SHORT).show()

            binding.btComenzar.setOnClickListener {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }

        }

    }
}