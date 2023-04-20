import android.util.Log
import com.example.harganow.data.source.Firestore
import com.example.harganow.data.source.Firestore.Companion.ColRef
import com.example.harganow.data.source.Firestore.Companion.ColRefFilter
import com.example.harganow.data.source.Firestore.Companion.DocRef
import com.example.harganow.domain.model.DataOrException
import com.example.harganow.domain.model.Item
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

// REF: https://medium.com/firebase-developers/how-to-display-data-from-firestore-using-jetpack-compose-49ee736dc07d
@Singleton
class ItemRepository {
    val TAG = "ItemRepository"
    val collectionName = "item"

    suspend fun getAllItem(): DataOrException<List<Item>, Exception> {
        val dataOrException = DataOrException<List<Item>, Exception>()
        try {
            dataOrException.data = ColRef(collectionName).get().await().map { document ->
                document.toObject(Item::class.java)
            }
        } catch (e: FirebaseFirestoreException) {
            Log.e(TAG, "Error getting documents: ", e)
            dataOrException.exception = e
        }
        return dataOrException
//        var result: MutableList<Item>? = null
//        ColRef().get().addOnSuccessListener { document ->
//            result = document.toObjects(Item::class.java)
//            return@addOnSuccessListener
//        }.addOnFailureListener { exception ->
//            Log.d(TAG, "Error getting documents: ", exception)
//            return@addOnFailureListener
//        }
//        return result
    }

    suspend fun getItemWithCategory(category: String): DataOrException<List<Item>, Exception> {
        val dataOrException = DataOrException<List<Item>, Exception>()
        val query: Query = ColRefFilter(collectionName, "item_category", category)
        try {
            dataOrException.data = query.get().await().map { document ->
                document.toObject(Item::class.java)
            }
        } catch (e: FirebaseFirestoreException) {
            Log.e(TAG, "Error getting documents: ", e)
            dataOrException.exception = e
        }
        return dataOrException
//        var result: MutableList<Item>? = null
//        ColRef().whereEqualTo("item_category", category).get().addOnSuccessListener { document ->
//            result = document.toObjects(Item::class.java)
//            return@addOnSuccessListener
//        }.addOnFailureListener { exception ->
//            Log.d(TAG, "Error getting documents: ", exception)
//            return@addOnFailureListener
//        }
//        return result
    }

    suspend fun getItemWithGroup(group: String): DataOrException<List<Item>, Exception> {
        val dataOrException = DataOrException<List<Item>, Exception>()
        val query: Query = ColRefFilter(collectionName, "item_group", group)
        try {
            dataOrException.data = query.get().await().map { document ->
                document.toObject(Item::class.java)
            }
        } catch (e: FirebaseFirestoreException) {
            Log.e(TAG, "Error getting documents: ", e)
            dataOrException.exception = e
        }
        return dataOrException
//        var result: Item? = null
//
//        var result: MutableList<Item>? = null
//        ColRef().whereEqualTo("item_group", group).get().addOnSuccessListener { document ->
//            result = document.toObjects(Item::class.java)
//            return@addOnSuccessListener
//        }.addOnFailureListener { exception ->
//            Log.d(TAG, "Error getting documents: ", exception)
//            return@addOnFailureListener
//        }
//        return result
    }

    suspend fun getItemWithId(id: String): DataOrException<Item, Exception> {
        val dataOrException = DataOrException<Item, Exception>()
        try {
            Log.d(TAG, "Item ${id} is found")
            var data = DocRef(collectionName, id).get().await().toObject(Item::class.java)
            Log.d(TAG, "Item ${id} is ${data}")
            dataOrException.data = data
        } catch (e: FirebaseFirestoreException) {
            Log.e(TAG, "Error getting documents: ", e)
            dataOrException.exception = e
        }
        return dataOrException
        var result: Item? = null
//        DocRef(id).get().addOnSuccessListener { document ->
//            if (document != null) {
//                Log.d(TAG, "Item ${id} is found")
//                result = document.toObject(Item::class.java)
//                Log.d(TAG, "Item ${id} is ${result?.item}")
//                return@addOnSuccessListener
//            } else {
//                Log.d(TAG, "Item ${id} is not found")
//            }
//        }
//        return result
//    }


    }
}