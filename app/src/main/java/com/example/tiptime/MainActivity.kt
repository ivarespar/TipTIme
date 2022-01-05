package com.example.tiptime

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tiptime.databinding.ActivityMainBinding
import java.text.NumberFormat

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configurar objeto listener cuando se le da CLICK al botón
        // Esto ejecuta la función calculateTip()
        binding.calculateButton.setOnClickListener { calculateTip() }
    }

    // Función/Metodo para cálculo de propina
    private fun calculateTip() {
        val stringInTextField = binding.costOfService.text.toString()           // Asigno valor en el EditText a la variable
        val cost = stringInTextField.toDoubleOrNull()                           // Convierto variable a tipo DOBLE (decimal), o a null si está vacio el campo
        if (cost == null || cost == 0.0) {                                      // Si el costo es null o 0.0
            displayTip(0.0)                                                // se limpia el resultado
            return                                                              // se detiene la ejecución
        }
        val tipPercentage = when (binding.tipOption.checkedRadioButtonId){      // Asigno el porcentaje evaluando cual es el ID del RadioButton seleccionado
            R.id.option_twenty_percent -> 0.20
            R.id.option_eighteen_percent -> 0.18
            else -> 0.15
        }
        var tip = tipPercentage * cost                                          // Asigno el producto del porcentaje y el costo a la variable, se una VAR porque puede redondearse, es decir se puede alterar su valor
        if (binding.roundUpSwitch.isChecked) {                                  // Verifico si el estado del switch es TRUE
            tip = kotlin.math.ceil(tip)                                         // Invoco libreria, kotlin.math para usar la funcion ceil()
        }
        displayTip(tip)                                                         // Muestro pripina formateada


    }

    // Función para formatear el resultado, con un parámetro tipo DOUBLE
    private fun displayTip(tip : Double){
        val formattedTip = NumberFormat.getCurrencyInstance().format(tip)       // Llamo a esta libreria para formatear moneda de acuerdo al pais
        binding.tipResult.text = getString(R.string.result_text,formattedTip)   // Asigno el resultado con formato moneda, al objeto tipResult, teniendo en cuenta la referencia 'R.string.result_text'
                                                                                // ubicada en app/values/string.xml -> result_text
                                                                                // al hacer esto se obvia [android:text="@string/result_text"] en
                                                                                // activity_main.xml, porque @string/result_text se usa como una plantilla
    }


}