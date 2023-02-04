package com.azamovhudstc.noteappplaystore.ui.adapter

import android.graphics.BitmapFactory
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.azamovhudstc.noteappplaystore.R
import com.azamovhudstc.noteappplaystore.data.local.models.NoteEntity
import com.azamovhudstc.noteappplaystore.utils.gone
import com.azamovhudstc.noteappplaystore.utils.visible
import kotlinx.android.synthetic.main.item_container_note.view.*
import kotlin.Boolean
import kotlin.Int

class NoteAdapter: RecyclerView.Adapter<NoteAdapter.Wh>() {
    lateinit var block: ((NoteEntity) -> Unit)
    private val list:ArrayList<NoteEntity> = ArrayList()
    fun setOnItemClickListener(block:(NoteEntity)->Unit){
        this.block =block
    }

    inner class Wh(view: View) : RecyclerView.ViewHolder(view) {
        fun onBind(data:NoteEntity) {
            itemView.item_note_title.text=data.title
            if (data.password){
                itemView.lock.visibility=View.VISIBLE
            }
            else{
                itemView.lock.visibility=View.GONE

            }
            if (data.subTitle.trim().isEmpty()) {
                itemView.item_note_subtitle.gone()
            } else {
                itemView.item_note_subtitle.visible()
                itemView.item_note_subtitle.text=data.subTitle
            }
            itemView.setOnClickListener{
                block.invoke(data)
            }
            itemView.item_note_date_time.text=data.date
            if (data.imagePath.isNotEmpty()) {
                itemView.item_note_image.visible()
                itemView.item_note_image.setImageBitmap(BitmapFactory.decodeFile(data.imagePath))
            } else {
                itemView.item_note_image.gone()
            }


            if (data.color.isNotEmpty()) {
                itemView.layout_note.setCardBackgroundColor(Color.parseColor(data.color))
            } else {
                itemView.layout_note.setCardBackgroundColor(Color.WHITE)
            }

        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Wh {
        return  Wh(LayoutInflater.from(parent.context).inflate(R.layout.item_container_note,parent,false))
    }
    override fun onBindViewHolder(holder: Wh, position: Int) {
        holder.onBind(list[position])
    }
    fun submitList(list2: List<NoteEntity>){
        list.clear()
        list.addAll(list2)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
      return  list.size
    }
}