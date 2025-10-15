package id.antasari.p4appnavigation_230104040214.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import id.antasari.p4appnavigation_230104040214.data.FormPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FormViewModel(application: Application) : AndroidViewModel(application) {

    private val formPreferences = FormPreferences(application)

    private val _name = MutableStateFlow("")
    val name: StateFlow<String> = _name.asStateFlow()

    private val _nim = MutableStateFlow("")
    val nim: StateFlow<String> = _nim.asStateFlow()

    init {
        // Load saved data when ViewModel is created
        viewModelScope.launch {
            formPreferences.nameFlow.collect { savedName ->
                _name.value = savedName
            }
        }

        viewModelScope.launch {
            formPreferences.nimFlow.collect { savedNim ->
                _nim.value = savedNim
            }
        }
    }

    fun updateName(newName: String) {
        _name.value = newName
    }

    fun updateNim(newNim: String) {
        _nim.value = newNim
    }

    fun saveFormData(name: String, nim: String) {
        viewModelScope.launch {
            formPreferences.saveFormData(name, nim)
        }
    }

    fun clearFormData() {
        viewModelScope.launch {
            formPreferences.clearFormData()
            _name.value = ""
            _nim.value = ""
        }
    }
}