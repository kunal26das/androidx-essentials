package androidx.essentials.firebase.database

import com.google.firebase.database.*
import com.google.firebase.database.Query

object Query {

    fun Query.getValue(
        onDataChange: ((snapshot: DataSnapshot) -> Unit),
        onCancelled: ((error: DatabaseError) -> Unit)? = null
    ) {
        addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                onDataChange.invoke(snapshot)
            }

            override fun onCancelled(error: DatabaseError) {
                onCancelled?.invoke(error)
            }
        })
    }

    fun Query.observeChildren(
        onChildAdded: ((snapshot: DataSnapshot, previousChildName: String?) -> Unit)? = null,
        onChildMoved: ((snapshot: DataSnapshot, previousChildName: String?) -> Unit)? = null,
        onChildChanged: ((snapshot: DataSnapshot, previousChildName: String?) -> Unit)? = null,
        onChildRemoved: ((snapshot: DataSnapshot) -> Unit)? = null,
        onCancelled: ((error: DatabaseError) -> Unit)? = null
    ) {
        addChildEventListener(object : ChildEventListener {
            override fun onCancelled(error: DatabaseError) {
                onCancelled(error)
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                onChildMoved(snapshot, previousChildName)
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                onChildChanged(snapshot, previousChildName)
            }

            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                onChildAdded(snapshot, previousChildName)
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                onChildRemoved(snapshot)
            }
        })
    }

    fun Query.observeValue(
        onDataChange: ((snapshot: DataSnapshot) -> Unit),
        onCancelled: ((error: DatabaseError) -> Unit)? = null
    ) {
        addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                onDataChange.invoke(snapshot)
            }

            override fun onCancelled(error: DatabaseError) {
                onCancelled?.invoke(error)
            }
        })
    }
}