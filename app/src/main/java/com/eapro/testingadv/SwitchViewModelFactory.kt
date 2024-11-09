package com.eapro.testingadv

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


//class SwitchViewModelFactory(private val repository: SwitchRepository) : ViewModelProvider.Factory {
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        return modelClass.getConstructor(SwitchRepository::class.java).newInstance(repository)
//    }
//}

class ChangeSwitchViewModelFactory(private val repository: ChangeSwitchRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(ChangeSwitchRepository::class.java).newInstance(repository)
    }
}


//class LowSwitchViewModelFactory(private val repository: LowSwitchRepository) : ViewModelProvider.Factory {
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        return modelClass.getConstructor(LowSwitchRepository::class.java).newInstance(repository)
//    }
//}
