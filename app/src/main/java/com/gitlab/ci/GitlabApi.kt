package com.gitlab.ci

import com.gitlab.ci.pipelines.Pipeline
import retrofit2.http.GET
import retrofit2.http.Path

interface GitlabApi {
    @GET("/api/v4/projects/{id}/pipelines")
    suspend fun pipelines(@Path("id") projectId: String): List<Pipeline>
}