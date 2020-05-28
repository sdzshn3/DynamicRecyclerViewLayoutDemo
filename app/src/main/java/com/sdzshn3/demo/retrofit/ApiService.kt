package com.sdzshn3.demo.retrofit

import retrofit2.Response
import retrofit2.http.GET
import com.sdzshn3.demo.Employee
import com.sdzshn3.demo.Student

interface ApiService {
    @GET("5cdf27653000002b00430d14")
    suspend fun getEmployees(): Response<List<Employee>>

    @GET("5cdf2f353000004600430d29")
    suspend fun getStudents(): Response<List<Student>>
}