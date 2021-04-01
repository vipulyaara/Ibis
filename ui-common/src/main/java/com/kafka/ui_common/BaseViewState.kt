package com.kafka.ui_common

interface BaseViewState {
    companion object {
        val Empty
            get() = object : BaseViewState {}
    }
}
