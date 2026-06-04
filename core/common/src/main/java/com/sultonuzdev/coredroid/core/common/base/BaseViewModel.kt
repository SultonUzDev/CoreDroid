package com.sultonuzdev.coredroid.core.common.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel<State : Any, Intent : Any, Effect : Any>
    (initialState: State) : ViewModel() {
    private val _state = MutableStateFlow(initialState)
    val state: StateFlow<State> = _state

    private val _effect = MutableSharedFlow<Effect>()
    val effect: SharedFlow<Effect> = _effect

    abstract fun handleIntent(intent: Intent)

    protected fun setState(newState: State) {
        _state.value = newState
    }

    protected fun sendEffect(newEffect: Effect) {
        viewModelScope.launch {
            _effect.emit(newEffect)
        }
    }

}