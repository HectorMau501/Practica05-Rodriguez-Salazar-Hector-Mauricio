package com.example.practica05rodriguezsalazarhectormauricio

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Switch
import android.widget.Toast
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

class MainActivity : AppCompatActivity() {

//Tamaño del arreglo
    val tamanio = 15;
    val ropaDeportiva = Array<RopaDeportiva?>(tamanio) {null}

    //Intancias para componentes graficos
    //Cajas de texto
    private lateinit var seach: Button
    private lateinit var editseach: EditText
    private lateinit var code: EditText
    private lateinit var model: EditText
    private lateinit var option: ChipGroup
    private lateinit var nike: Chip
    private lateinit var adidas: Chip
    private lateinit var puma: Chip

    private lateinit var optionGroup: RadioGroup
    private lateinit var small: RadioButton
    private lateinit var medium: RadioButton
    private lateinit var big: RadioButton

    private lateinit var black: CheckBox
    private lateinit var white: CheckBox
    private lateinit var blue: CheckBox
    private lateinit var red: CheckBox
    private lateinit var orange: CheckBox

    private lateinit var cost: EditText

    //Este mapa se utiliza comúnmente para almacenar datos asociados con un identificador único.
    private val ropa = mutableMapOf<Int, RopaDeportiva>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        seach = findViewById(R.id.btnBuscar)
        editseach = findViewById(R.id.editBuscar)
        code = findViewById(R.id.editCodigo)
        option = findViewById(R.id.gpoOpciones)
        nike = findViewById(R.id.chNike)
        adidas = findViewById(R.id.chAdidas)
        optionGroup = findViewById(R.id.rgpOpciones)
        puma = findViewById(R.id.chPuma)
        small = findViewById(R.id.rbtChica)
        medium = findViewById(R.id.rbtMediana)
        big = findViewById(R.id.rbtGrande)
        model = findViewById(R.id.editModelo)
        black = findViewById(R.id.chbNegro)
        white = findViewById(R.id.chbBlanco)
        blue = findViewById(R.id.chbAzul)
        red = findViewById(R.id.chbRojo)
        orange = findViewById(R.id.chbNaranja)


        cost = findViewById(R.id.editCosto)
    }//override

    fun OnClick(v: View?){
        when(v?.id){
            R.id.btnBuscar -> {
                btnbuscar()
            }
            R.id.fabAgregar ->{
                btnAgregar()
            }
            R.id.fabEliminar -> {
                btnEliminar()
            }
            R.id.fabLimpiar -> {
                btnLimpiar()
            }
        }
    }

    private fun btnbuscar() {
        val idBuscar = editseach.text.toString()

        if (idBuscar.isNotEmpty()) {
            val id = idBuscar.toInt()
            val ropaEncontrada = ropa[id]

            if (ropaEncontrada != null) {

                val mostrarCodigo = findViewById<EditText>(R.id.editCodigo)
                mostrarCodigo.setText(ropaEncontrada.codigo.toString())

                val mostrarModelo = findViewById<EditText>(R.id.editModelo)
                mostrarModelo.setText(ropaEncontrada.modelo)

                //findViewById<EditText>(R.id.editCodigo).text = "Código: ${ropaEncontrada.codigo}"
                /* when(ropaEncontrada.marca){
                    "Nike" -> findViewById<Chip>(R.id.chNike).isChecked = true
                    "Adidas" -> findViewById<Chip>(R.id.chAdidas).isChecked = true
                }*/

                //Marca
                val marcaEncontrados = ropaEncontrada.marca

                val chNike = findViewById<Chip>(R.id.chNike)
                chNike.isChecked = marcaEncontrados.contains("Nike")
                val chAdidas = findViewById<Chip>(R.id.chAdidas)
                chAdidas.isChecked = marcaEncontrados.contains("Adidas")
                val chPuma = findViewById<Chip>(R.id.chPuma)
                chPuma.isChecked = marcaEncontrados.contains("Puma")

                //Colores
                val coloresSeleccionados = ropaEncontrada.colores

                val chbNegro = findViewById<CheckBox>(R.id.chbNegro)
                chbNegro.isChecked = coloresSeleccionados.contains("Negro")
                val chbRojo = findViewById<CheckBox>(R.id.chbRojo)
                chbRojo.isChecked = coloresSeleccionados.contains("Rojo")
                val chbBlanco = findViewById<CheckBox>(R.id.chbBlanco)
                chbBlanco.isChecked = coloresSeleccionados.contains("Blanco")
                val chbAzul = findViewById<CheckBox>(R.id.chbAzul)
                chbAzul.isChecked = coloresSeleccionados.contains("Azul")
                val chbNaranja = findViewById<CheckBox>(R.id.chbNaranja)
                chbNaranja.isChecked =coloresSeleccionados.contains("Naranja")

                when(ropaEncontrada.talla){
                    "Chica" -> findViewById<RadioButton>(R.id.rbtChica).isChecked = true
                    "Mediana" -> findViewById<RadioButton>(R.id.rbtMediana).isChecked = true
                    "Grande" -> findViewById<RadioButton>(R.id.rbtGrande).isChecked = true
                }

                val mostrarCosto = findViewById<EditText>(R.id.editCosto)
                mostrarCosto.setText(ropaEncontrada.costo.toString())

                Toast.makeText(
                    this, "Búsqueda realizada",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                Toast.makeText(
                    this, "Codigo no encontrado.",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    fun btnEliminar() {
        val idEliminar = editseach.text.toString().toIntOrNull()

        if (idEliminar != null) {
            recorrerElemento(idEliminar)
        } else {
            // Manejar con estos datos
            Toast.makeText(this, "No busco",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun recorrerElemento(id: Int) {
        if (ropa.containsKey(id)) {
            ropa.remove(id) // Eliminar el elemento

            // Crear un nuevo mapa para almacenar los elementos restantes
            val nuevoMapa = mutableMapOf<Int, RopaDeportiva>()

            // Transferir los elementos restantes al nuevo mapa
            for ((key, value) in ropa) {
                if (key > id) {
                    nuevoMapa[key - 1] = value // Reducir en 1 la clave de elementos a la derecha
                } else {
                    nuevoMapa[key] = value
                }
            }

            ropa.clear() // Borrar el mapa original
            ropa.putAll(nuevoMapa) // Copiar los elementos al mapa original

            Toast.makeText(
                this, "Eliminado exitosamente",
                Toast.LENGTH_LONG
            ).show()
            btnLimpiar()
        } else {
            Toast.makeText(
                this, "No se pudo encontrar el elemento con ID $id",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun btnLimpiar() {
        code.text.clear()
        editseach.text.clear()
        nike.isChecked = false
        adidas.isChecked = false
        puma.isChecked = false
        model.text.clear()
        option.clearCheck()
        white.isChecked = false
        blue.isChecked = false
        red.isChecked = false
        black.isChecked = false
        orange.isChecked = false
        optionGroup.clearCheck()
        big.isChecked = false
        cost.text.clear()
        code.requestFocus()
    }

    private fun btnAgregar() {
        if (code.text.isNotEmpty()) {
            val id = code.text.toString().toInt()
            if (!ropa.containsKey(id)) {

                val ropa1 = RopaDeportiva()

                ropa1.codigo = code.text.toString().toInt()
                ropa1.modelo = model.text.toString()

                // Asigna la talla según el RadioButton seleccionado
                when (optionGroup.checkedRadioButtonId) {
                    R.id.rbtChica -> ropa1.talla = "Chica"
                    R.id.rbtMediana -> ropa1.talla = "Mediana"
                    R.id.rbtGrande -> ropa1.talla = "Grande"
                }

                if(nike.isChecked){
                    ropa1.marca += "Nike"
                }
                if(adidas.isChecked){
                    ropa1.marca += "Adidas"
                }
                if(puma.isChecked){
                    ropa1.marca += "Puma"
                }

                // Asigna la marca según el Chip seleccionado
//                when (option.checkedChipId) {
//                    R.id.chNike -> ropa1.marca = "Nike"
//                    R.id.chAdidas -> ropa1.marca = "Adidas"
//                    R.id.chPuma -> ropa1.marca = "Puma"
//                }

                if(black.isChecked){
                    ropa1.colores += "Negro"
                }

                if(blue.isChecked){
                    ropa1.colores += "Azul"
                }

                if(orange.isChecked){
                    ropa1.colores += "Naranja"
                }

                if(white.isChecked){
                    ropa1.colores += "Blanco"
                }

                if(red.isChecked){
                    ropa1.colores += "Rojo"
                }

                ropa1.costo = cost.text.toString().toDouble()

                ropa[id] = ropa1
                Toast.makeText(
                    this, "Registro Correctamente",
                    Toast.LENGTH_SHORT
                ).show()
                btnLimpiar()
            } else {
                Toast.makeText(
                    this, "Esta lleno el arreglo",
                    Toast.LENGTH_LONG
                ).show()
            }
        } else {
            Toast.makeText(
                this, "No se pudo registrar.",
                Toast.LENGTH_LONG
            ).show()
        }
    }
}