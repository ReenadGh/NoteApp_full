package com.example.noteapp

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.item_row.view.*

class MainActivity : AppCompatActivity() {
    lateinit var noteText: EditText
    lateinit var addNoteB: ImageView
    lateinit var myRV: RecyclerView
    lateinit var MyAdabter: RecyclerViewAdapter

    @SuppressLint("Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        myRV = findViewById(R.id.rvMain)
        var dbhr = DBHolder(applicationContext)
        noteText = findViewById(R.id.noteE)
        addNoteB = findViewById(R.id.addB)


        MyAdabter = RecyclerViewAdapter(dbhr, dbhr.getNotesArray())
        myRV.adapter = MyAdabter
        myRV.layoutManager = LinearLayoutManager(this)
        SwiptoDelete()


        addNoteB.setOnClickListener {

            if (noteText.text.isNotEmpty()) {
                var status = dbhr.addNote(noteText.text.toString())
                if (status > 0) {


                    Toast.makeText(this, "note added !", Toast.LENGTH_SHORT).show()
                    MyAdabter = RecyclerViewAdapter(dbhr, dbhr.getNotesArray())
                    myRV.adapter = MyAdabter
                    myRV.layoutManager = LinearLayoutManager(this)


                } else {
                    Toast.makeText(this, "error : note not added !", Toast.LENGTH_SHORT).show()
                }
            }

        }

    }


    private fun SwiptoDelete() {

        val swipToDelete = object : SwipGu(this@MainActivity) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                when (direction) {

                    ItemTouchHelper.LEFT -> {

                        MyAdabter.deleteNoteFromRec(viewHolder.adapterPosition)

                    }
                }
            }

        }

        val touchHelper = ItemTouchHelper(swipToDelete)
        touchHelper.attachToRecyclerView(myRV)

    }
}