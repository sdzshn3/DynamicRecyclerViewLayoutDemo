package soa.work.scheduler.demo.retrofit

import retrofit2.Response
import retrofit2.http.GET
import soa.work.scheduler.demo.Employee
import soa.work.scheduler.demo.Student

interface ApiService {
    @GET("5cdf27653000002b00430d14")
    suspend fun getEmployees(): Response<List<Employee>>

    @GET("5cdf2f353000004600430d29")
    suspend fun getStudents(): Response<List<Student>>
}