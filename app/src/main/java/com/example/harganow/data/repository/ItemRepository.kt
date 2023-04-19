import android.util.Log
import com.example.harganow.data.source.Firestore
import com.example.harganow.domain.model.Item

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

    fun getItemWithId(id: Int): Item? {
        var result: Item? = null
        DocRef(id).get().addOnSuccessListener { document ->
            if (document != null) {
                result = document.toObject(Item::class.java)
                return@addOnSuccessListener
            }
        }
        return result
    }


}