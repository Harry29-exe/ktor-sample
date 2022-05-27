package com.example

import org.ktorm.database.Database
import org.ktorm.dsl.*
import org.ktorm.entity.Entity
import org.ktorm.entity.add
import org.ktorm.entity.sequenceOf
import org.ktorm.schema.ColumnDeclaring
import org.ktorm.schema.Table
import org.ktorm.schema.long
import org.ktorm.schema.varchar

object Users : Table<User>("users") {
    val id = long("id")
        .bindTo { it.id }
    val username = varchar("username")
        .bindTo { it.username }

}

interface User : Entity<User> {
    companion object : Entity.Factory<User>()
    val id: Long
    var username: String
    var passwordHash: String

    suspend fun changePassword(password: String, hasher: PasswordHasher) {
        this.passwordHash = hasher.hash(password)
    }

}

interface PasswordHasher {

    fun hash(password: String): String

    fun match(password: String, hashedPassword: String): Boolean

}

val database = Database.connect("jdbc:postgresql://localhost:5432/", user = "postgres", password = "123")
val Database.users get() = this.sequenceOf(Users)

fun main() {


    database.useTransaction {

        for (user in database.users) {
            println(user.username)
        }

        val newUser = User {
            username = "bob2"
            passwordHash = "123"
        }

        val bob2Exist = database.from(Users)
            .select(count(Users.id) greater 0)
            .where{Users.username eq "bob2"}
            .map { it }


        val row1 = bob2Exist[0]
        val exist = row1.getBoolean(0)
        if (!exist) {
            val addedCount = database.users.add(newUser)
            if (addedCount != 1) {
                println("failed to add user")
            } else {
                println("added new user")
            }
        } else {
            println("bob2 already exist")
        }

        for (user in database.users) {
            println(user.username)
        }
    }

}