package com.example.kotlinunittest.utils

import com.google.common.truth.Truth.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ValidatorTest {

    @Test
    fun whenUserNameNotValid() {
        val validator = Validator()
        val result = validator.userNameValidator("123456789")

        assertThat(result).isFalse()
    }

    @Test
    fun whenUserNameValid() {
        val validator = Validator()
        val result = validator.userNameValidator("1234567890")

        assertThat(result).isTrue()
    }
}