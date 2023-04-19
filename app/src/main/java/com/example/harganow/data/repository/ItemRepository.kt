import android.util.Log
import com.example.harganow.data.source.Firestore
import com.example.harganow.domain.model.DataOrException
import com.example.harganow.domain.model.Item
import com.google.firebase.firestore.FirebaseFirestoreException
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

// REF: https://medium.com/firebase-developers/how-to-display-data-from-firestore-using-jetpack-compose-49ee736dc07d
@Singleton
class ItemRepository {
    val TAG = "ItemRepository"

    val collectionName = "item"

    fun DocRef(id: Int) = Firestore.getCollection(collectionName).document(id.toString())
    fun ColRef() = Firestore.getCollection(collectionName)

    fun getAllItem(): MutableList<Item>? {
        var result: MutableList<Item>? = null
        ColRef().get().addOnSuccessListener { document ->
            result = document.toObjects(Item::class.java)
            return@addOnSuccessListener
        }.addOnFailureListener { exception ->
            Log.d(TAG, "Error getting documents: ", exception)
            return@addOnFailureListener
        }
        return result
    }

    fun getItemWithCategory(category: String): MutableList<Item>? {
        var result: MutableList<Item>? = null
        ColRef().whereEqualTo("item_category", category).get().addOnSuccessListener { document ->
            result = document.toObjects(Item::class.java)
            return@addOnSuccessListener
        }.addOnFailureListener { exception ->
            Log.d(TAG, "Error getting documents: ", exception)
            return@addOnFailureListener
        }
        return result
    }

    fun getItemWithGroup(group: String): MutableList<Item>? {
        var result: MutableList<Item>? = null
        ColRef().whereEqualTo("item_group", group).get().addOnSuccessListener { document ->
            result = document.toObjects(Item::class.java)
            return@addOnSuccessListener
        }.addOnFailureListener { exception ->
            Log.d(TAG, "Error getting documents: ", exception)
            return@addOnFailureListener
        }
        return result
    }

    suspend fun getItemWithId(id: Int): DataOrException<Item, Exception> {
        val dataOrException = DataOrException<Item, Exception>()
        try {
            Log.d(TAG, "Item ${id} is found")
            var data = DocRef(id).get().await().toObject(Item::class.java)
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