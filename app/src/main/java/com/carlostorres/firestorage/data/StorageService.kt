package com.carlostorres.firestorage.data

import android.net.Uri
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import com.google.firebase.storage.ktx.storage
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
            reference.putFile(uri).addOnSuccessListener {
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

}