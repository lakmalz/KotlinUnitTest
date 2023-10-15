package com.example.kotlinunittest.utils

class Validator {

    fun userNameValidator(userName: String): Boolean{
        return userName.length >= 10
    }

    fun telephoneNumberValidator(telephone: String):Boolean{
        return telephone.length >=10
    }


}