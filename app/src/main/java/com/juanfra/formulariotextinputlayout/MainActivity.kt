package com.juanfra.formulariotextinputlayout

import android.content.DialogInterface
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.view.View.OnFocusChangeListener
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout

/*
Autor: Juan Francisco Sánchez González
Fecha: 03/11/2024
Clase: Actividad que contiene un formulario de contacto. Se utilizan controles TextInputLayout para gestionar
los errores a la hora de validar el contenido.
*/

class MainActivity : AppCompatActivity() {

    // Controles a utilizar par el formulario
    lateinit var btnEnvio: Button
    lateinit var etNombre: EditText
    lateinit var etCorreo: EditText
    lateinit var etAsunto: EditText
    lateinit var etMensaje: EditText
    lateinit var campoCorreo: TextInputLayout
    lateinit var campoNombre: TextInputLayout
    lateinit var campoAsunto: TextInputLayout
    lateinit var campoMensaje: TextInputLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Instanciamos los TextInputLayout
        campoNombre = findViewById<View>(R.id.textInputLayoutNombre) as TextInputLayout
        campoCorreo = findViewById<View>(R.id.textInputLayoutCorreo) as TextInputLayout
        campoAsunto = findViewById<View>(R.id.textInputLayoutAsunto) as TextInputLayout
        campoMensaje = findViewById<View>(R.id.textInputLayoutMensaje) as TextInputLayout

        // Controlar cambio de foco en los controles del formulario para validar el campo
        etNombre = findViewById<View>(R.id.editTextNombre) as EditText
        etNombre.onFocusChangeListener = OnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                // Validmos Nombre
                val error: Boolean = esNombreValido()
            }
        }
        etCorreo = findViewById<View>(R.id.editTextCorreo) as EditText
        etCorreo.onFocusChangeListener = OnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                // Validmos Correo
                val error: Boolean = esCorreoValido()
            }
        }
        etAsunto = findViewById<View>(R.id.editTextAsunto) as EditText
        etAsunto.onFocusChangeListener = OnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                // Validmos Asunto
                val error: Boolean = esAsuntoValido()
            }
        }
        etMensaje = findViewById<View>(R.id.editTextMensaje) as EditText
        etMensaje.onFocusChangeListener = OnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                // Validmos Mensaje
                val error: Boolean = esMensajeValido()
            }
        }

        // Componente Button
        btnEnvio = findViewById<View>(R.id.buttonEnvio) as Button
        btnEnvio.setOnClickListener { v -> validarDatos(v) }
    }

    // Validación campos del formulario
    private fun validarDatos(v: View) {
        var cadError = resources.getString(R.string.act2_cadena_error)
        var error = esNombreValido()
        if (error) {
            cadError += resources.getString(R.string.act2_nombre_texto) + "\n"
        }
        error = esCorreoValido()
        if (error) {
            cadError += resources.getString(R.string.act2_correo_texto) + "\n"
        }
        error = esAsuntoValido()
        if (error) {
            cadError += resources.getString(R.string.act2_asunto_texto) + "\n"
        }
        error = esMensajeValido()
        if (error) {
            cadError += resources.getString(R.string.act2_mensaje_texto) + "\n"
        }
        // Si hay errores se muestra diálogo
        if (cadError !== resources.getString(R.string.act2_cadena_error)) {
            val builder = AlertDialog.Builder(this@MainActivity)
            builder.setMessage(cadError)
                .setTitle(resources.getString(R.string.act2_dialogo_tit))
                .setIcon(android.R.drawable.stat_sys_warning)
                .setPositiveButton(resources.getString(R.string.btn_positivo_texto),
                    DialogInterface.OnClickListener { dialog, which -> })
            val dialogo = builder.create()
            dialogo.show()
        } else {
            Snackbar.make(v, resources.getString(R.string.act2_envform_texto), Snackbar.LENGTH_LONG)
                .show()
        }
    }

    // Validar campo Nombre
    private fun esNombreValido(): Boolean {
        if (campoNombre.editText!!.text.toString().isEmpty()) {
            campoNombre.error = resources.getString(R.string.error_nombre)
            return true
        } else {
            campoNombre.error = null
        }
        return false
    }

    // Validar campo Correo
    private fun esCorreoValido(): Boolean {
        // Patrón para validar el correo electrónico
        if (!Patterns.EMAIL_ADDRESS.matcher(campoCorreo.editText!!.text.toString()).matches()) {
            campoCorreo.error = resources.getString(R.string.error_correo)
            return true
        } else {
            campoCorreo.error = null
        }
        return false
    }

    // Validar campo Asunto
    private fun esAsuntoValido(): Boolean {
        if (campoAsunto.editText!!.text.toString().isEmpty()) {
            campoAsunto.error = resources.getString(R.string.error_asunto)
            return true
        } else {
            campoAsunto.error = null
        }
        return false
    }

    // Validar campo Mensaje
    private fun esMensajeValido(): Boolean {
        if (campoMensaje.editText!!.text.toString().isEmpty()) {
            campoMensaje.error = resources.getString(R.string.error_mensaje)
            return true
        } else {
            campoMensaje.error = null
        }
        return false
    }
}