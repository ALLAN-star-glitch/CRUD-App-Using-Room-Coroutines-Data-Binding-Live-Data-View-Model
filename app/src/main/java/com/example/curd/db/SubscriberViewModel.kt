package com.example.curd.db

import androidx.databinding.Bindable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class SubscriberViewModel(private val repository: SubscriberRepository) : ViewModel() {

    val subscribers = repository.subscribers

    @Bindable
    val inputName = MutableLiveData<String?>()

    @Bindable
    val inputEmail = MutableLiveData<String?>()

    @Bindable
    val saveorUpdateButtonText = MutableLiveData<String>()

    @Bindable
    val clearAllorDeleteButtonText = MutableLiveData<String>()

    init {
        saveorUpdateButtonText.value = "Save"
        clearAllorDeleteButtonText.value = "Clear All"
    }

    fun saveOrUpdate(){
        val name: String = inputName.value!!
        val email: String = inputEmail.value!!
        insert(Subscriber(0, name, email))
        inputEmail.value = null
        inputName.value = null

    }

    fun clearAllOrDelete(){
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