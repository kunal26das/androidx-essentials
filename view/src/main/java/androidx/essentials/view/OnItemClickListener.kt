package androidx.essentials.view

interface OnItemClickListener<T> {
    var itemClickListener: ((T) -> Unit)?
    fun setOnItemClickListener(l: ((T) -> Unit)?) {
        itemClickListener = l
    }
}