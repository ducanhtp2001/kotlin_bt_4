package com.example.democontentprovider

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.democontentprovider.Adapter.WorkAdapter
import com.example.democontentprovider.Model.Work
import com.example.democontentprovider.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var adapter: WorkAdapter
    lateinit var listWork: ArrayList<Work>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        listWork = ArrayList()

        adapter = WorkAdapter(applicationContext, R.layout.work_item_layout, listWork)
        binding.listView.adapter = adapter

        binding.btnAdd.setOnClickListener {
            var intent = Intent(this, AddActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        listWork.clear()
        val cursor = contentResolver.query(Uri.parse("content://com.example.demo.content.provider/work"),
            null, null, null, null)
        cursor?.let {
            if(it.moveToFirst()) {
                do {
                    val id = it.getInt(0)
                    val content = it.getString(1)
                    val date = it.getString(2)
                    val work = Work(id, content, date)
                    listWork.add(work)
                } while (it.moveToNext())
            }
        }
        adapter.notifyDataSetChanged()
    }
}