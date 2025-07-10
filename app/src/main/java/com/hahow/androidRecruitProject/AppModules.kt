package com.hahow.androidRecruitProject

import com.hahow.androidRecruitProject.modules.RepositoryModule
import com.hahow.androidRecruitProject.ui.course.CourseViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import org.koin.ksp.generated.module

val appModules = module {
    includes(RepositoryModule().module)

    viewModel { CourseViewModel(get()) }
}