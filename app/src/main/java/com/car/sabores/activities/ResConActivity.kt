package com.car.sabores.activities

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.car.dsapp.databinding.ActivityResConBinding
import com.google.firebase.auth.FirebaseAuth

class ResConActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResConBinding

    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResConBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        var correo = ""

        binding.btnEnviar.setOnClickListener {
            correo = binding.etCorreo.text.toString().trim()

            if(correo == ""){
                AlertDialog.Builder(this)
                    .setTitle("¡Aviso!")
                    .setMessage("Por favor ingresa un correo")
                    .setPositiveButton("Ok", DialogInterface.OnClickListener { _, _ ->

                    })
                    .create()
                    .show()

            }else{
                firebaseAuth.sendPasswordResetEmail(correo).addOnSuccessListener {
                    Toast.makeText(this, "Correo enviado", Toast.LENGTH_SHORT).show()
                }.addOnFailureListener {
                    Toast.makeText(this, "No se pudo enviar el correo: ${it.message}", Toast.LENGTH_SHORT).show() //it tiene la excepción
                }

                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)

            }


        }
    }
}