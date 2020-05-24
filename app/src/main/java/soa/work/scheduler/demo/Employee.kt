package soa.work.scheduler.demo

import com.google.gson.annotations.SerializedName

data class Employee(
    @SerializedName("id") var id: String? = null,
    @SerializedName("employee_name") var employeeName: String? = null,
    @SerializedName("employee_salary") var employeeSalary: String? = null,
    @SerializedName("employee_age") var employeeAge: String? = null,
    @SerializedName("profile_image") var profileImage: String? = null
)