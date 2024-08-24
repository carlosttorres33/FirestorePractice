package com.carlostorres.firestorage.data

import android.net.Uri
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import javax.inject.Inject

class StorageService @Inject constructor(
    private val storage: FirebaseStorage
) {

    fun basicExample(){

        val reference = Firebase.storage.reference.child("ejemplo/test.jepg")

        reference.name //test.jepg
        reference.path //ejemplo/test.jepg
        reference.bucket //gs://firestorage-8f68b.appspot.com/ ejemplo/test.jepg

    }

    fun uploadBasicImage(uri: Uri) {
        val reference = storage.reference.child(uri.lastPathSegment.orEmpty())
        reference.putFile(uri)
    }

}