package com.drake.droidblox.datastores.fastflags

class EditFFlagScope(
    val fflagsToEdit: MutableMap<String, String>
) {
    fun set(key: String, value: String) {
        fflagsToEdit[key] = value
    }
    fun delete(key: String) {
        fflagsToEdit.remove(key)
    }
}