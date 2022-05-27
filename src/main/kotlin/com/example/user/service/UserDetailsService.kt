package com.example.user.service

import com.example.user.dto.UserDetails

interface UserDetailsService {

    fun userByUsername(): UserDetails

    companion object {

        fun provider(): UserServiceImpl {
            return UserServiceImpl()
        }

    }

}