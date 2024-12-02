package com.example.pocketconversor


import android.R
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.pocketconversor.databinding.ActivityCalculatorBinding


class CalculatorActivity : AppCompatActivity() {

        private lateinit var binding: ActivityCalculatorBinding
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            binding = ActivityCalculatorBinding.inflate(layoutInflater)
            setContentView(binding.root)

// Arrays com as opções de unidades
            val weightUnits = arrayOf("kg", "lb")
            val distanceUnits = arrayOf("m", "km", "mile")
            val volumeUnits = arrayOf("L", "m3", "gallon")

            // Configuração dos Spinners
            val weightFromSpinner = binding.spWeightLeft
            val weightToSpinner = binding.spWeightRight

            val distanceFromSpinner = binding.spDistanceLeft
            val distanceToSpinner = binding.spDistanceRight

            val volumeFromSpinner = binding.spVolumeLeft
            val volumeToSpinner = binding.spVolumeRight

            // Adaptadores para os Spinners
            val weightAdapter = ArrayAdapter(this, R.layout.simple_spinner_item, weightUnits)
            weightAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            weightFromSpinner.adapter = weightAdapter
            weightToSpinner.adapter = weightAdapter

            val distanceAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, distanceUnits)
            distanceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            distanceFromSpinner.adapter = distanceAdapter
            distanceToSpinner.adapter = distanceAdapter

            val volumeAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, volumeUnits)
            volumeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            volumeFromSpinner.adapter = volumeAdapter
            volumeToSpinner.adapter = volumeAdapter

            // Função para limpar os campos quando um campo recebe foco
            val weightField = binding.tieWeight
            val distanceField = binding.tieDistance
            val volumeField = binding.tieVolume

            weightField.setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    distanceField.setText("")
                    volumeField.setText("")
                }
            }

            distanceField.setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    weightField.setText("")
                    volumeField.setText("")
                }
            }

            volumeField.setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    weightField.setText("")
                    distanceField.setText("")
                }
            }

            // Botão de calcular
            val calculateButton = binding.btnCalculate

            calculateButton.setOnClickListener {
                val weight = weightField.text.toString().toDoubleOrNull()
                val distance = distanceField.text.toString().toDoubleOrNull()
                val volume = volumeField.text.toString().toDoubleOrNull()

                // Recuperar unidades selecionadas
                val weightFromUnit = weightFromSpinner.selectedItem.toString()
                val weightToUnit = weightToSpinner.selectedItem.toString()

                val distanceFromUnit = distanceFromSpinner.selectedItem.toString()
                val distanceToUnit = distanceToSpinner.selectedItem.toString()

                val volumeFromUnit = volumeFromSpinner.selectedItem.toString()
                val volumeToUnit = volumeToSpinner.selectedItem.toString()

                var result = ""

                // Realizar a conversão de peso
                if (weight != null) {
                    val convertedWeight = convertWeight(weight, weightFromUnit, weightToUnit)
                    val formatedWeight = "%.2f".format(convertedWeight)
                    result = "$formatedWeight $weightToUnit"
                }

                // Realizar a conversão de distância
                if (distance != null) {
                    val convertedDistance = convertDistance(distance, distanceFromUnit, distanceToUnit)

                    val formatedDistance = "%.2f".format(convertedDistance)
                    result = "$formatedDistance $distanceToUnit"

                }

                // Realizar a conversão de volume
                if (volume != null) {
                    val convertedVolume = convertVolume(volume, volumeFromUnit, volumeToUnit)

                    val formatedVolume = "%.2f".format(convertedVolume)
                    result = "$formatedVolume $volumeToUnit"

                }

                binding.tvTotal.setText(result)
            }

            val clearButton = binding.btnClear

            clearButton.setOnClickListener {
                distanceField.setText("")
                volumeField.setText("")
                weightField.setText("")
            }
        }

    private fun convertWeight(value: Double, fromUnit: String, toUnit: String): Double {
        // Base: 1 kg = 1000 g
        val kgValue = when (fromUnit) {
            "kg" -> value
            "lb" -> value / 2.20462 // lb para kg
            else -> value
        }

        // Converte o valor em kg para a unidade de destino
        return when (toUnit) {
            "kg" -> kgValue
            "lb" -> kgValue * 2.20462 // kg para lb
            else -> kgValue
        }
    }

    private fun convertDistance(value: Double, fromUnit: String, toUnit: String): Double {
        // Base: 1 metro
        val meterValue = when (fromUnit) {
            "m" -> value
            "km" -> value * 1000 // km para metros
            "mile" -> value * 1609.34 // milhas para metros
            else -> value
        }

        // Converte o valor em metros para a unidade de destino
        return when (toUnit) {
            "m" -> meterValue
            "km" -> meterValue / 1000 // metros para km
            "mile" -> meterValue / 1609.34 // metros para milhas
            else -> meterValue
        }

    }

    private fun convertVolume(value: Double, fromUnit: String, toUnit: String): Double {
        // Base: 1 litro
        val literValue = when (fromUnit) {
            "L" -> value
            "m3" -> value * 1000 // m³ para litros
            "gallon" -> value * 3.78541 // galões para litros
            else -> value
        }

        // Converte o valor em litros para a unidade de destino
        return when (toUnit) {
            "L" -> literValue
            "m3" -> literValue / 1000 // litros para m³
            "gallon" -> literValue / 3.78541 // litros para galões
            else -> literValue
        }
    }

}