package com.car.sabores.activities

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.car.dsapp.databinding.ActivityLoginBinding
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    private lateinit var firebaseAuth: FirebaseAuth

    private var email = ""
    private var contrasenia = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        binding.pbConexion.visibility = View.INVISIBLE

        //boton para omitir el inicio de sesion
        binding.btnOmitir.setOnClickListener {
            val intent = Intent(this, CategoriasActivity::class.java)
            startActivity(intent)
            finish()
        }

        //INICIAR SESION CON EMAIL Y CONTRASEÑA
        binding.btnLogin.setOnClickListener {
            if (!validaCampos()) return@setOnClickListener

            binding.pbConexion.visibility = View.VISIBLE

            autenticaUsuario(email,contrasenia)
        }

        //CREAR UNA CUENTA NUEVA
        binding.btnCrearCuenta.setOnClickListener {
            if(!validaCampos()) return@setOnClickListener

            binding.pbConexion.visibility = View.VISIBLE

            //Registrando al usuario
            firebaseAuth.createUserWithEmailAndPassword(email, contrasenia).addOnCompleteListener { authResult->
                if(authResult.isSuccessful){
                    //Enviar correo para verificación de email
                    var user_fb = firebaseAuth.currentUser
                    user_fb?.sendEmailVerification()?.addOnSuccessListener {
                        AlertDialog.Builder(this@LoginActivity)
                            .setTitle("¡Aviso!")
                            .setMessage("Se ha enviado un correo de verificacion, porfavor ve a tu bandeja de entrada y confirma tu correo lo mas pronto posible")
                            .setPositiveButton("Entendido", DialogInterface.OnClickListener { _, _ ->
                                val intent = Intent(this, CategoriasActivity::class.java)
                                intent.putExtra("psw", contrasenia)
                                startActivity(intent)
                                finish()
                            })
                            .create()
                            .show()
                    }?.addOnFailureListener {
                        Toast.makeText(this, "No se pudo enviar el correo de verificación", Toast.LENGTH_SHORT).show()
                    }

                    Toast.makeText(this, "Usuario creado", Toast.LENGTH_SHORT).show()

                }else{
                    binding.pbConexion.visibility = View.GONE
                    manejaErrores(authResult)
                }
            }
        }

        //REESTRABLECER LA CONTRASEÑA
        binding.tvRestablecerPassword.setOnClickListener {
            val intent = Intent(this, ResConActivity::class.java)
            startActivity(intent)
        }

    }

    //FUNCION PARA VALIDAR LOS CAMPOS DEL USUARIO
    private fun validaCampos(): Boolean{
        email = binding.tietEmail.text.toString().trim() //para que quite espacios en blanco
        contrasenia = binding.tietContrasenia.text.toString().trim()

        if(email.isEmpty()){
            binding.tietEmail.error = "Se requiere el correo"
            binding.tietEmail.requestFocus()
            return false
        }

        if(contrasenia.isEmpty() || contrasenia.length < 6){
            binding.tietContrasenia.error = "Se requiere una contraseña o la contraseña no tiene por lo menos 6 caracteres"
            binding.tietContrasenia.requestFocus()
            return false
        }

        return true
    }

    //FUNCION PARA MANEJAR LOS ERRORES DE FIREBASE AUTH
    private fun manejaErrores(task: Task<AuthResult>){
        var errorCode = ""

        try{
            errorCode = (task.exception as FirebaseAuthException).errorCode
        }catch(e: Exception){
            errorCode = "NO_NETWORK"
        }

        when(errorCode){
            "ERROR_INVALID_EMAIL" -> {
                Toast.makeText(this, "Error: El correo electrónico no tiene un formato correcto", Toast.LENGTH_SHORT).show()
                binding.tietEmail.error = "Error: El correo electrónico no tiene un formato correcto"
                binding.tietEmail.requestFocus()
            }
            "ERROR_WRONG_PASSWORD" -> {
                Toast.makeText(this, "Error: La contraseña no es válida", Toast.LENGTH_SHORT).show()
                binding.tietContrasenia.error = "La contraseña no es válida"
                binding.tietContrasenia.requestFocus()
                binding.tietContrasenia.setText("")
            }
            "ERROR_ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL" -> {
                //An account already exists with the same email address but different sign-in credentials. Sign in using a provider associated with this email address.
                Toast.makeText(this, "Error: Una cuenta ya existe con el mismo correo, pero con diferentes datos de ingreso", Toast.LENGTH_SHORT).show()
            }
            "ERROR_EMAIL_ALREADY_IN_USE" -> {
                Toast.makeText(this, "Error: el correo electrónico ya está en uso con otra cuenta.", Toast.LENGTH_LONG).show()
                binding.tietEmail.error = ("Error: el correo electrónico ya está en uso con otra cuenta.")
                binding.tietEmail.requestFocus()
            }
            "ERROR_USER_TOKEN_EXPIRED" -> {
                Toast.makeText(this, "Error: La sesión ha expirado. Favor de ingresar nuevamente.", Toast.LENGTH_LONG).show()
            }
            "ERROR_USER_NOT_FOUND" -> {
                Toast.makeText(this, "Error: No existe el usuario con la información proporcionada.", Toast.LENGTH_LONG).show()
            }
            "ERROR_WEAK_PASSWORD" -> {
                Toast.makeText(this, "La contraseña porporcionada es inválida", Toast.LENGTH_LONG).show()
                binding.tietContrasenia.error = "La contraseña debe de tener por lo menos 6 caracteres"
                binding.tietContrasenia.requestFocus()
            }
            "NO_NETWORK" -> {
                Toast.makeText(this, "Red no disponible o se interrumpió la conexión", Toast.LENGTH_LONG).show()
            }
            else -> {
                Toast.makeText(this, "Error. No se pudo autenticar exitosamente.", Toast.LENGTH_SHORT).show()
            }
        }

    }

    //FUNCION QUE AUTENTICA AL USUARIO
    private fun autenticaUsuario(usr: String, psw: String){

        firebaseAuth.signInWithEmailAndPassword(usr, psw).addOnCompleteListener { authResult ->
            if(authResult.isSuccessful){

                val intent = Intent(this, CategoriasActivity::class.java)
                intent.putExtra("psw", psw)
                startActivity(intent)
                finish()

            }else{
                binding.pbConexion.visibility = View.GONE
                manejaErrores(authResult)
            }
        }
    }
}