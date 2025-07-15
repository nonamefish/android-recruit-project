package com.hahow.androidRecruitProject.util

import androidx.annotation.StringRes
import com.hahow.androidRecruitProject.MyApplication

object ResourceUtil {

    fun getString(@StringRes resourceId: Int, vararg args: Any): String {
        return MyApplication.appContext.getString(resourceId, *args)
    }
}