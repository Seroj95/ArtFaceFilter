package com.ser.artface.dependencyinjection

import com.ser.artface.viewmodels.EditImageViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule= module {
    viewModel { EditImageViewModel(editImageRepository = get()) }

}

