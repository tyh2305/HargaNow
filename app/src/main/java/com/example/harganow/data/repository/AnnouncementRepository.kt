package com.example.harganow.data.repository

import android.util.Log
import com.example.harganow.data.source.Firestore.Companion.ColRef
import com.example.harganow.domain.model.Announcement
import com.example.harganow.domain.model.DataOrException
import com.google.firebase.firestore.FirebaseFirestoreException
import kotlinx.coroutines.tasks.await

class AnnouncementRepository {
    val TAG = "AnnouncementRepository"
    val collectionName = "announcement"

    suspend fun getAllAnnouncement(): DataOrException<List<Announcement>, Exception> {
        val dataOrException = DataOrException<List<Announcement>, Exception>()
        try {
            dataOrException.data = ColRef(collectionName).get().await().map { document ->
                document.toObject(Announcement::class.java)
            }
        } catch (e: FirebaseFirestoreException) {
            Log.e(TAG, "Error getting documents: ", e)
            dataOrException.exception = e
        }
        return dataOrException
    }
}