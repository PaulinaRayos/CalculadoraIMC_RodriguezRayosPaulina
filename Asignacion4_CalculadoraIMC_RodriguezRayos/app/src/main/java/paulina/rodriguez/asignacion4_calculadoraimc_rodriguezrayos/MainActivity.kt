package paulina.rodriguez.asignacion4_calculadoraimc_rodriguezrayos

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.Locale

class MainActivity : AppCompatActivity() {
    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val etPeso = findViewById<EditText>(R.id.etPeso)
        val etAltura = findViewById<EditText>(R.id.etEstatura)
        val btnCalcular = findViewById<Button>(R.id.btnCalcular)
        val tvIMC = findViewById<TextView>(R.id.tvIMC)
        val tvRange= findViewById<TextView>(R.id.tvRange)

        // Listener para calcular el IMC
        btnCalcular.setOnClickListener {
            val pesoStr = etPeso.text.toString()
            val alturaStr = etAltura.text.toString()

            //validar campos vacíos
            if (pesoStr.isBlank() || alturaStr.isBlank()) {
                tvIMC.text = "Por favor, ingresa peso y altura"
                tvRange.text = ""
                return@setOnClickListener
            }

            val peso = pesoStr.toFloat()
            val altura = alturaStr.toFloat()

            //validar que no sea cero el valor
            if (peso == null || altura == null || peso <= 0 || altura <= 0) {
                tvIMC.text = "Datos inválidos"
                tvRange.text = ""
                return@setOnClickListener
            }

            val imc = peso / (altura * altura)

            tvIMC.text = String.format(Locale.US, "Tu IMC es %.2f", imc)

            val mensaje = when {
                imc < 18.5 -> {
                    tvRange.setBackgroundResource(R.color.yellow)
                    "Bajo peso"
                }
                imc < 24.9 -> {
                    tvRange.setBackgroundResource(R.color.green)
                    "Peso normal"
                }
                imc < 29.9 -> {
                    tvRange.setBackgroundResource(R.color.yellow)
                    "Sobrepeso"
                }
                imc < 34.9 -> {
                    tvRange.setBackgroundResource(R.color.orange)
                    "Obesidad grado 1"
                }
                imc < 39.9 -> {
                    tvRange.setBackgroundResource(R.color.orange)
                    "Obesidad grado 2"
                }
                else -> {
                    tvRange.setBackgroundResource(R.color.red)
                    "Obesidad grado 3"
                }
            }

            tvRange.text = mensaje
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}