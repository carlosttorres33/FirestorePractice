package com.carlostorres.firestorage.data

import android.net.Uri
import android.util.Log
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageMetadata
import com.google.firebase.storage.UploadTask
import com.google.firebase.storage.ktx.storage
import com.google.firebase.storage.ktx.storageMetadata
import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class StorageService @Inject constructor(
    private val storage: FirebaseStorage
) {

    fun uploadBasicImage(uri: Uri) {
        val reference = storage.reference.child(uri.lastPathSegment.orEmpty())
        reference.putFile(uri)
    }

    suspend fun downloadBasicImage() : Uri {

        val reference = storage.reference.child("ejemplo/test.jpeg")

        reference.downloadUrl.addOnSuccessListener {

        }.addOnFailureListener {

        }

        return reference.downloadUrl.await()
    }

    suspend fun uploadAndDownloadImage(uri: Uri) : Uri {
        return suspendCancellableCoroutine<Uri> { cancellableContinuation ->

            val reference = storage.reference.child("/download/${uri.lastPathSegment}")

            reference.putFile(uri, createMetadata()).addOnSuccessListener {
                downloadImage(it, cancellableContinuation)
            }.addOnFailureListener {
                cancellableContinuation.resumeWithException(it)
            }
        }
    }

    private fun downloadImage(
        uploadTask: UploadTask.TaskSnapshot,
        cancellableContinuation: CancellableContinuation<Uri>
    ){
        uploadTask.storage.downloadUrl.addOnSuccessListener { uri ->
            cancellableContinuation.resume(uri)
        }.addOnFailureListener {
            cancellableContinuation.resumeWithException(it)
        }
    }

    private suspend fun readMetaDataBasic(){
        val reference = storage.reference.child("ejemplo/test.jpeg")
        val response = reference.metadata.await()
        val metaInfo = response.getCustomMetadata("aris")
        Log.i("Aristidev MetaInfo", metaInfo.orEmpty())
    }

    private suspend fun readMetaDataAdvanced(){
        val reference = storage.reference.child("ejemplo/test.jpeg")
        val response = reference.metadata.await()
        response.customMetadataKeys.forEach { key ->
            response.getCustomMetadata(key)?.let { value ->
                Log.i("Aristidev MetaInfo", "$key: $value")
            }
        }
    }

    private fun createMetadata() : StorageMetadata{
        val metadata = storageMetadata {
            contentType = "image/jpeg"
            setCustomMetadata("date", "12-01-1993")
            setCustomMetadata("aris", "super pro especial")
        }

        return metadata
    }

}