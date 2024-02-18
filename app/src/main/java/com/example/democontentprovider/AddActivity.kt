package com.example.democontentprovider

import android.content.ContentValues
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.democontentprovider.ContentProvider.MyContentProvider
import com.example.democontentprovider.Model.TableSample
import com.example.democontentprovider.databinding.ActivityAddBinding
import java.text.SimpleDateFormat
import java.util.*

class AddActivity : AppCompatActivity() {
    lateinit var binding: ActivityAddBinding
    lateinit var uri: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)
        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        uri = Uri.parse("content://com.example.demo.content.provider/work")

        val smf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

        binding.datePicker.setOnDateChangedListener { _, year, monthOfYear, dayOfMonth ->

            val calendar = Calendar.getInstance()
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, monthOfYear)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            val date = smf.format(calendar.time)
            binding.txtDatePicker.text = date
        }

        binding.btnAdd.setOnClickListener {
            if(binding.inputAddContent.text.isEmpty() || binding.txtDatePicker.text.isEmpty())
            {
                Toast.makeText(this, "Input your work and choose date", Toast.LENGTH_SHORT).show()
            } else {
                var currentCalendar = Calendar.getInstance()
                var date = smf.parse(binding.txtDatePicker.text.toString())
                var pickedCalendar = Calendar.getInstance()
                pickedCalendar.time = date
//                Toast.makeText(this, "day: ${smf.format(pickedCalendar.time)}", Toast.LENGTH_SHORT).show()
                var result = pickedCalendar.compareTo(currentCalendar)

                when {
                    result >= 0 -> {
                        addWork(binding.inputAddContent.text.toString(), smf.format(pickedCalendar.time))
                    }
                    else -> Toast.makeText(this, "This day has passed", Toast.LENGTH_SHORT).show()

                }
            }
        }
    }

    private fun addWork(content: String, date: String) {
//        Toast.makeText(this, "In day $date have $content", Toast.LENGTH_SHORT).show()
        var contentValue = ContentValues()
        contentValue.put(TableSample.COL_CONTENT, content)
        contentValue.put(TableSample.COL_DATE, date)
        contentResolver.insert(uri, contentValue)
        Toast.makeText(this, "Add Successfully", Toast.LENGTH_SHORT).show()
        finish()
    }
}