package com.hahow.androidRecruitProject.modules

import com.hahow.androidRecruitProject.data.loader.AssetCourseDataLoader
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
class DataLoaderModule {

    @Single
    fun provideAssetCourseDataLoader(): AssetCourseDataLoader =
        AssetCourseDataLoader("courses.json")
}