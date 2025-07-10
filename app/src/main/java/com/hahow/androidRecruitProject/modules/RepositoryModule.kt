package com.hahow.androidRecruitProject.modules

import com.hahow.androidRecruitProject.data.loader.AssetCourseDataLoader
import com.hahow.androidRecruitProject.data.loader.CourseDataLoader
import com.hahow.androidRecruitProject.data.repository.DefaultCourseRepository
import org.koin.core.annotation.Module
import org.koin.core.annotation.Named
import org.koin.core.annotation.Single

@Module(includes = [DataLoaderModule::class])
class RepositoryModule {

    @Single
    fun provideDefaultCourseRepository(
        assetCourseDataLoader: AssetCourseDataLoader
    ): DefaultCourseRepository = DefaultCourseRepository(assetCourseDataLoader)
}