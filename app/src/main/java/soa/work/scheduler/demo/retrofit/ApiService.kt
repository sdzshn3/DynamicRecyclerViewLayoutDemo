package soa.work.scheduler.demo.retrofit

import retrofit2.Call
import retrofit2.http.GET
import soa.work.scheduler.demo.Employee
import soa.work.scheduler.demo.Student

interface ApiService {
    @GET("5cdf27653000002b00430d14")
    fun getEmployees(): Call<List<Employee>>

    @GET("5cdf2f353000004600430d29")
    fun getStudents(): Call<List<Student>>
}