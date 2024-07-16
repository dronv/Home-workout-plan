package com.example.homeworkoutplan

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button: Button = findViewById(R.id.btResult)
        val resultTV: TextView = findViewById(R.id.textResult)
        val spinnerVal: Spinner = findViewById(R.id.spSelect)
        val descText: TextView = findViewById(R.id.desc_text)
        val options = arrayOf("Cardio", "Body Weight", "Core")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, options)

        descText.text = ""
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerVal.adapter = adapter

        spinnerVal.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedOption = options[position]
                resultTV.text = "$selectedOption"
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                resultTV.text = "No workout selected"
            }
        }

        button.setOnClickListener {
            val selectedOption = spinnerVal.selectedItem.toString()
            val workoutDescription = getWorkoutDescription(selectedOption)
            resultTV.text = selectedOption
            descText.text = workoutDescription
        }

        val calculateBMIButton: Button = findViewById(R.id.btCalculateBMI)
        val heightEditText: EditText = findViewById(R.id.etHeight)
        val weightEditText: EditText = findViewById(R.id.etWeight)
        val bmiResultTextView: TextView = findViewById(R.id.tvBMIResult)

        calculateBMIButton.setOnClickListener {
            val heightStr = heightEditText.text.toString().trim()
            val weightStr = weightEditText.text.toString().trim()

            if (heightStr.isEmpty() || weightStr.isEmpty()) {
                bmiResultTextView.text = "Please enter valid height and weight."
                return@setOnClickListener
            }

            val height = heightStr.toDouble() / 100
            val weight = weightStr.toDouble()

            val bmi = calculateBMI(height, weight)
            val bmiCategory = getBMICategory(bmi)

            bmiResultTextView.text = "Your BMI: $bmi\nCategory: $bmiCategory"
        }
    }

    private fun getWorkoutDescription(workoutType: String): String {
        return if (workoutType == "Cardio") {
            "Cardio workouts focus on improving cardiovascular health."
        } else if (workoutType == "Body Weight") {
            "Body weight workouts use your own body for resistance."
        } else if (workoutType == "Core") {
            "Core workouts strengthen muscles around your abdomen and lower back."
        } else {
            "Unknown workout type"
        }
    }

    private fun calculateBMI(height: Double, weight: Double): Double {
        return weight / (height * height)
    }

    private fun getBMICategory(bmi: Double): String {
        return when {
            bmi < 18.5 -> "Underweight"
            bmi < 24.9 -> "Normal Weight"
            bmi < 29.9 -> "Overweight"
            else -> "Obese"
        }
    }
}
