package com.example.noteapp

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_row.view.*
import kotlin.random.Random
import androidx.core.view.isVisible
import androidx.recyclerview.widget.ItemTouchHelper


class RecyclerViewAdapter(val dbhr: DBHolder, private val notes: ArrayList<Note> ): RecyclerView.Adapter<RecyclerViewAdapter.ItemViewHolder>(){
    class ItemViewHolder (itemView : View) : RecyclerView.ViewHolder(itemView)



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_row,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val myNote = notes[position].noteDes
        val myNoteId = notes[position].id

        holder.itemView.apply {
            notebox.text = myNote
            cardViewNote.setOnClickListener {
                cardViewNote.isVisible = false
                cardEditNote.isVisible = true
                editNote.setText(myNote)

                addEdit.setOnClickListener {

                    var myUpdatedNote = Note( editNote.text.toString(), myNoteId)
                    dbhr.updateNote(myUpdatedNote)
                    cardViewNote.isVisible = true
                    cardEditNote.isVisible = false
                    notebox.text = editNote.text.toString()
                }

            }






        }


    }

    fun deleteNoteFromRec(position : Int ){
        val myDeletedNoteId = notes[position].id
        notes.removeAt(position)
        dbhr.deleteNote(myDeletedNoteId)
        notifyDataSetChanged()
    }



    override fun getItemCount() = notes.size



}
