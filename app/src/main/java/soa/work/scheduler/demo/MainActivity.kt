package soa.work.scheduler.demo

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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

        CoroutineScope(Dispatchers.IO).launch {
            val employeesResponse = apiService.getEmployees()
            val studentsResponse = apiService.getStudents()

            withContext(Dispatchers.Main) {
                try {
                    if (employeesResponse.isSuccessful && studentsResponse.isSuccessful) {
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
                } catch (e: Throwable) {
                    Toast.makeText(this@MainActivity, "Something went wrong", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
