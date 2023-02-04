package com.azamovhudstc.noteappplaystore.data.local.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.azamovhudstc.noteappplaystore.R
import java.io.Serializable

@Entity
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var title: String,
    var note: String,
    var subTitle: String,
    var date: String,
    var color: String,
    var isDeleteType: Int=0,
    var webLink: String="",
    var imagePath:String="",
    var password:Boolean=false,



    ) : Serializable {
    override fun toString(): String {
        return "NoteEntity(id=$id, title='$title', content='$subTitle', date='$date', color=$color, isDeleteType=$isDeleteType)"
    }
}