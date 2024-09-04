package com.example.curd

import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.databinding.PropertyChangeRegistry
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.curd.db.Subscriber
import com.example.curd.db.SubscriberRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class SubscriberViewModel(private val repository: SubscriberRepository) : ViewModel(), Observable {

    // LiveData for the list of subscribers
    val subscribers = repository.subscribers

    // Observable fields for data binding
    @Bindable
    val inputName = MutableLiveData<String?>()

    @Bindable
    val inputEmail = MutableLiveData<String?>()

    @Bindable
    val saveOrUpdateButtonText = MutableLiveData<String>()

    @Bindable
    val clearAllOrDeleteButtonText = MutableLiveData<String>()

    // PropertyChangeRegistry to manage observables
    private val callbacks: PropertyChangeRegistry = PropertyChangeRegistry()

    init {
        saveOrUpdateButtonText.value = "Save"
        clearAllOrDeleteButtonText.value = "Clear All"
    }

    // Notify observers of property changes
    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback) {
        callbacks.add(callback)
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback) {
        callbacks.remove(callback)
    }

    // Notify change for specific property
    fun notifyPropertyChanged(fieldId: Int) {
        callbacks.notifyCallbacks(this, fieldId, null)
    }

    // Notify change for all properties
    fun notifyChange() {
        callbacks.notifyCallbacks(this, 0, null)
    }

    fun saveOrUpdate() {
        val name = inputName.value
        val email = inputEmail.value

        if (!name.isNullOrEmpty() && !email.isNullOrEmpty()) {
            insert(Subscriber(0, name, email))
            inputName.value = null
            inputEmail.value = null
            notifyChange()  // Notify that properties have changed
        }
    }

    fun clearAllOrDelete() {
        clearAll()
    }

    fun insert(subscriber: Subscriber): Job = viewModelScope.launch {
        repository.insert(subscriber)
    }

    fun update(subscriber: Subscriber): Job = viewModelScope.launch {
        repository.update(subscriber)
    }

    fun delete(subscriber: Subscriber): Job = viewModelScope.launch {
        repository.delete(subscriber)
    }

    fun clearAll(): Job = viewModelScope.launch {
        repository.deleteAll()
    }
}
