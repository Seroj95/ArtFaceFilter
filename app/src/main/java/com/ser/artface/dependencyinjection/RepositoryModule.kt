package com.ser.artface.dependencyinjection

import com.ser.artface.repositories.EditImageRepository
import com.ser.artface.repositories.EditImageRepositoryImpl
import com.ser.artface.repositories.SavedImagesRepository
import com.ser.artface.repositories.SavedImagesRepositoryImp
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val repositoryModule= module {
    factory <EditImageRepository>{  EditImageRepositoryImpl(androidContext())}
factory<SavedImagesRepository> {
SavedImagesRepositoryImp(androidContext())
}
}