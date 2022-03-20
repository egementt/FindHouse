package com.example.findhouse.service

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.media.Image
import android.net.Uri
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.core.graphics.drawable.toBitmap
import androidx.core.net.toUri
import androidx.lifecycle.MutableLiveData
import com.example.findhouse.model.Current
import com.example.findhouse.util.FirebaseResponse
import com.google.android.material.animation.DrawableAlphaProperty
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.InputStream

class StorageService {

    private val storage = FirebaseStorage.getInstance()
    private val uploadResponse: MutableLiveData<FirebaseResponse> =
        MutableLiveData(FirebaseResponse.Loading())
    private val uriResponse: MutableLiveData<FirebaseResponse> = MutableLiveData(FirebaseResponse.Loading())

    companion object {
        private var count = 0
    }


    fun uploadPhotos(listID: String): MutableLiveData<FirebaseResponse> {
        uploadResponse.value = FirebaseResponse.Loading()
        count = 0
        Current.photoList.forEach {
            val file = it.toUri()
            val ref = storage.reference.child("images/").child(listID + "/${file.lastPathSegment}")
            val task = ref.putFile(file)

            task.addOnFailureListener { e ->
                uploadResponse.value = FirebaseResponse.Failed(e.message.toString())
                throw e
            }.addOnSuccessListener { taskSnapshot ->
                count += 1
                if (count == Current.photoList.size){
                   uploadResponse.value = FirebaseResponse.Success(count)
                }
            }.addOnProgressListener {
                FirebaseResponse.Loading()
            }
        }


        return uploadResponse
    }

    fun getPhotosByListingID(listID: String): MutableLiveData<FirebaseResponse> {
        uriResponse.value = FirebaseResponse.Loading()
        var numberOfReceivedUri = 0
        storage.reference.child("images/").child(listID).listAll().addOnSuccessListener {
            it.items.forEach { ref ->
                ref.downloadUrl.addOnCompleteListener { task ->
                    Log.d("STORAGE", task.result.toString())
                    Current.houseListing.photos.add(task.result.toString())
                    numberOfReceivedUri += 1
                    Log.d("STORAGE_LIST", Current.houseListing.photos.toString())

                    if (it.items.size == numberOfReceivedUri){
                        uriResponse.value = FirebaseResponse.Success(numberOfReceivedUri)
                    }
                }.addOnFailureListener { e ->
                    uriResponse.value = FirebaseResponse.Failed(e.message.toString())
                }
            }

        }
            .addOnFailureListener { e ->
                throw e
            }
        return uriResponse
    }

/*    private fun compressImage(path: String): ByteArray {
        //todo
    }*/

}



