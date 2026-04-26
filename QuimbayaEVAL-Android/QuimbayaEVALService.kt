package com.sena.quimbayaeval.data.remote.api

import com.sena.quimbayaeval.data.remote.dto.LoginRequest
import com.sena.quimbayaeval.data.remote.dto.LoginResponse
import com.sena.quimbayaeval.data.remote.dto.EvaluacionDTO
import com.sena.quimbayaeval.data.remote.dto.PQRSDto
import retrofit2.Response
import retrofit2.http.*

interface QuimbayaEVALService {

    // ============= AUTENTICACION =============
    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @POST("auth/logout")
    suspend fun logout(): Response<Unit>

    // ============= EVALUACIONES =============
    @GET("evaluaciones")
    suspend fun getEvaluaciones(
        @Query("estudianteId") estudianteId: Long
    ): Response<List<EvaluacionDTO>>

    @GET("evaluaciones/{id}")
    suspend fun getEvaluacion(@Path("id") id: Long): Response<EvaluacionDTO>

    @POST("evaluaciones/{id}/respuestas")
    suspend fun submitEvaluacion(
        @Path("id") evaluacionId: Long,
        @Body respuestas: Map<String, String>
    ): Response<Map<String, Any>>

    // ============= PQRS =============
    @GET("pqrs")
    suspend fun getPQRS(
        @Query("usuarioId") usuarioId: Long
    ): Response<List<PQRSDto>>

    @POST("pqrs")
    suspend fun createPQRS(@Body pqrs: PQRSDto): Response<PQRSDto>

    @PUT("pqrs/{id}/responder")
    suspend fun respondPQRS(
        @Path("id") id: Long,
        @Body response: Map<String, String>
    ): Response<PQRSDto>

    // ============= CURSOS =============
    @GET("cursos")
    suspend fun getCursos(): Response<List<Map<String, Any>>>

    @GET("cursos/{id}")
    suspend fun getCurso(@Path("id") id: Long): Response<Map<String, Any>>
}
