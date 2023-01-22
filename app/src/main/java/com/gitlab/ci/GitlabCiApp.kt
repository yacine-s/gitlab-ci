package com.gitlab.ci

import android.app.Application
import com.gitlab.ci.pipelines.PipelineListViewModel
import com.gitlab.ci.pipelines.PipelineMapper
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import retrofit2.Retrofit


class GitlabCiApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(modules)
        }
    }
}

val modules = module {
    single {
        val contentType = "application/json".toMediaType()
        Retrofit.Builder()
            .baseUrl("https://gitlab.com")
            .addConverterFactory(Json.asConverterFactory(contentType))
            .build()
    }
    factory {
        val retrofit: Retrofit = get()
        retrofit.create(GitlabApi::class.java)
    }
    factory { PipelineMapper() }
    viewModel {
        PipelineListViewModel(get(), get())
    }
}