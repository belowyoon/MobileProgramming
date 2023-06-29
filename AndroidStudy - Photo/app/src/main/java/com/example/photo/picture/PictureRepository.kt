package com.example.photo.picture

import android.content.ContentResolver
import android.content.ContentUris
import android.database.Cursor
import android.net.Uri
import android.nfc.Tag
import android.provider.MediaStore
import android.util.Log
import com.example.photo.GlobalApplication
import com.example.photo.picture.model.ImageData

class PictureRepository {

    companion object {
        const val TAG = "PictureRepository"
    }
    private var contentResolver: ContentResolver = GlobalApplication.getInstance().contentResolver

    fun getImageList(): ArrayList<ImageData>? {
        var imageList: ArrayList<ImageData>? = null
        var cursor: Cursor? = null
        if (getImageCursor()?.also { Log.d(TAG, "getImageList: ${it.count}")
                cursor = it}?.count!!>=0){
            imageList = ArrayList()
            while (cursor!!.moveToNext()){
                imageList.add(getImageProperty(cursor!!))
            }
        }else {
            Log.d(TAG, "getImageList: no cursor !!")
        }

        return imageList
    }

    fun getImageCursor(): Cursor? {
        var queryUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val mProjection = arrayOf(MediaStore.Images.Media._ID,
            MediaStore.Images.Media.TITLE,
            MediaStore.Images.Media.DATE_TAKEN,
        )
        return contentResolver.query(queryUri,mProjection,null,null,null)
    }

    fun getImageProperty(cursor: Cursor): ImageData {
        val idNum = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
        val titleNum = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.TITLE)
        val dateTakenNum = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_TAKEN)

        val id = cursor.getLong(idNum)
        val title = cursor.getString(titleNum)
        val dateTaken = cursor.getLong(dateTakenNum)
        val imageUri : Uri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id)
        Log.d(TAG, "getImageProperty : $id $title $dateTaken $imageUri")
        return ImageData(id,title,dateTaken,imageUri)
    }
}