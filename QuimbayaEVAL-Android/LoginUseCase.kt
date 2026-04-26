package com.sena.quimbayaeval.domain.usecases

import com.sena.quimbayaeval.domain.models.Usuario
import com.sena.quimbayaeval.domain.repository.IUsuarioRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val usuarioRepository: IUsuarioRepository
) {
    suspend fun execute(email: String, password: String): Result<Usuario> {
        return try {
            // Validaciones básicas
            if (email.isBlank() || password.isBlank()) {
                return Result.failure(IllegalArgumentException("Email y contraseña requeridos"))
            }

            // Llamar al repositorio
            val usuario = usuarioRepository.login(email, password)
            
            // Validar respuesta
            if (usuario != null) {
                Result.success(usuario)
            } else {
                Result.failure(Exception("Credenciales inválidas"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
