package com.example.assignment3.roomDB

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "musicals")
data class Musical(
    @PrimaryKey var pId:Int,
    @ColumnInfo(name = "mName") var name:String,
    @ColumnInfo(name = "date") var date:String,
    @ColumnInfo(name = "place") var place:String,
    @ColumnInfo(name = "url") var url:String)
