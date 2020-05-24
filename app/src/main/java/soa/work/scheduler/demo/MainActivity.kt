package soa.work.scheduler.demo

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.internal.ContextUtils.getActivity
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import soa.work.scheduler.demo.retrofit.ApiClient


class MainActivity : AppCompatActivity() {

    private val commonList = ArrayList<Any>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val myAdapter = Adapter(commonList)
        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = myAdapter
        }
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                this,
                DividerItemDecoration.VERTICAL
            )
        )

        val apiService = ApiClient.getApiService()
        val getEmployeesCall = apiService.getEmployees()
        //Getting Employees list
        getEmployeesCall.enqueue(object : Callback<List<Employee>> {
            override fun onFailure(call: Call<List<Employee>>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Something went wrong " + t.localizedMessage, Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<List<Employee>>, employeesResponse: Response<List<Employee>>) {
                if (employeesResponse.isSuccessful) {

                    //Getting Students list
                    val getStudentsCall = apiService.getStudents()
                    getStudentsCall.enqueue(object : Callback<List<Student>> {
                        override fun onFailure(call: Call<List<Student>>, t: Throwable) {
                            Toast.makeText(this@MainActivity, "Something went wrong", Toast.LENGTH_SHORT).show()
                        }

                        override fun onResponse(call: Call<List<Student>>, studentsResponse: Response<List<Student>>) {
                            if (studentsResponse.isSuccessful) {
                                var lastPositionOfStudent = 0
                                for (i in 0 until employeesResponse.body()?.size!!) {
                                    lastPositionOfStudent = i
                                    commonList.add(employeesResponse.body()!![i])

                                    // If students are less than employees, below try catch will catch the exception
                                    try {
                                        commonList.add(studentsResponse.body()!![i])
                                    } catch (e: IndexOutOfBoundsException) {}
                                }

                                // IF employees are less than students, we add remaining remaining students here
                                if (employeesResponse.body()?.size!! < studentsResponse.body()?.size!!) {
                                    for (i in lastPositionOfStudent until studentsResponse.body()?.size!!) {
                                        commonList.add(studentsResponse.body()!![i])
                                    }
                                }

                                myAdapter.notifyDataSetChanged()
                            } else {
                                Toast.makeText(this@MainActivity, "Something went wrong", Toast.LENGTH_SHORT).show()
                            }
                        }

                    })
                } else {
                    Toast.makeText(this@MainActivity, "Something went wrong", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}
