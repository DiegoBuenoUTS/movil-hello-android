package com.bueno.helloandroid.repository

import com.bueno.helloandroid.model.User

class UserRepository {

    // Simulación de base de datos local
    private val users = mutableListOf(
        User(1, "Juan García", "juan@example.com", 25),
        User(2, "María López", "maria@example.com", 30),
        User(3, "Carlos Rodríguez", "carlos@example.com", 28),
        User(4, "Ana Martínez", "ana@example.com", 22),
        User(5, "Pedro Sánchez", "pedro@example.com", 35)
    )

    // Obtener todos los usuarios
    fun getAllUsers(): List<User> {
        return users.toList() // Retorna copia inmutable
    }

    // Obtener usuario por ID
    fun getUserById(id: Int): User? {
        return users.find { it.id == id }
    }

    // Agregar nuevo usuario
    fun addUser(user: User) {
        users.add(user)
    }

    // Actualizar usuario existente
    fun updateUser(user: User) {
        val index = users.indexOfFirst { it.id == user.id }
        if (index != -1) {
            users[index] = user
        }
    }

    // Eliminar usuario
    fun deleteUser(userId: Int) {
        users.removeAll { it.id == userId }
    }
}
