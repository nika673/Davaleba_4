package com.example.davaleba_4

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import java.util.HashSet

class MainActivity : AppCompatActivity() {
    private lateinit var noteEditText: EditText
    private lateinit var addButton: Button
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var recyclerview: RecyclerView
    private lateinit var data: ArrayList<ItemsViewModel>
    private lateinit var adapter: MainActivityAdapter
    private lateinit var info: String
    private lateinit var sets: Set<String>

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val intent = Intent(this, DetailActivity::class.java)


        init()
        registerListeners()
        sharedPreferences = getSharedPreferences("mySharedPreference", Context.MODE_PRIVATE)

//        val notes = sharedPreferences.getStringSet("notes", sets)
//        ვცადე StringSet ში ჩაწერა მაგრამ არ გამომივიდა, ასე კი ინახავს მხოლოდ ბოლო ჩანაწერს რომელისაც გავაკეთებ აპლიკაციაში
        val notes = sharedPreferences.getString("notes", "")
        info = notes.toString()




        data = ArrayList()
        data.add(ItemsViewModel(notes.toString()))
        adapter = MainActivityAdapter(data)
        recyclerview.adapter = adapter
        adapter.notifyDataSetChanged()
        recyclerview.layoutManager = LinearLayoutManager(this)



        adapter.onClicked = {
            intent.putExtra("text", it.text)
            startActivity(intent)
        }
//      მარჯვნივ თყ გაუსვამ თითს ნოუთს მაშინ ის წაიშლება, დეტალების ნახვა კი მასზე დაჭერითაა შესაძლებელი.

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val deletedCourse: ItemsViewModel = data.get(viewHolder.adapterPosition)
                val position = viewHolder.adapterPosition
                data.removeAt(viewHolder.adapterPosition)

                adapter.notifyItemRemoved(viewHolder.adapterPosition)

                Snackbar.make(recyclerview, "Deleted " + deletedCourse.text, Snackbar.LENGTH_LONG)
                    .setAction("Undo", View.OnClickListener {

                        data.add(position, deletedCourse)
                        adapter.notifyItemInserted(position)
                    }).show()
            }

        }).attachToRecyclerView(recyclerview)


    }

    private fun init() {
        noteEditText = findViewById(R.id.noteEditText)
        addButton = findViewById(R.id.addButton)
        recyclerview = findViewById(R.id.recyclerView)

    }

    private fun registerListeners() {
        addButton.setOnClickListener {
            info = noteEditText.text.toString()
            if (info.isEmpty()) {
                Toast.makeText(this, "You didn't write anything", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            noteEditText.text.clear()
//            sets.apply { info }


            data.add(ItemsViewModel(info))

            sharedPreferences.edit().putString("notes", info).apply()

        }
    }
}