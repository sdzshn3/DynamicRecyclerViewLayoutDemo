package com.sdzshn3.demo

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_layout_employee.view.*
import kotlinx.android.synthetic.main.item_layout_student.view.*

@Suppress("PrivatePropertyName")
class Adapter(private val commonList: List<Any>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    //private val STUDENT_TYPE = 1
    //private val EMPLOYEE_TYPE = 2

    override fun getItemViewType(position: Int): Int {
        val item = commonList[position]
        return if (item is Student) {
            LayoutType.STUDENT.type
        } else {
            LayoutType.EMPLOYEE.type
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == LayoutType.STUDENT.type) {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout_student, parent,false)
            StudentViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout_employee, parent,false)
            EmployeeViewHolder(view)
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            LayoutType.STUDENT.type -> {
                val studentViewHolder = holder as StudentViewHolder
                val currentStudent = commonList[position] as Student

                studentViewHolder.studentRollTextView.text = "Roll: ${currentStudent.roll}"
                studentViewHolder.studentNameTextView.text = "Name: ${currentStudent.name}"
                studentViewHolder.studentLocationTextView.text = "Location: ${currentStudent.location}"
                studentViewHolder.studentTagLineTextView.text = "Tag Line: ${currentStudent.tagLine}"
            }
            LayoutType.EMPLOYEE.type -> {
                val employeeViewHolder = holder as EmployeeViewHolder
                val currentEmployee = commonList[position] as Employee

                employeeViewHolder.employeeNameTextView.text = currentEmployee.employeeName
                employeeViewHolder.employeeSalaryTextView.text = currentEmployee.employeeSalary
                employeeViewHolder.employeeAgeTextView.text = currentEmployee.employeeAge
                if (currentEmployee.profileImage?.contains("https")!!) {
                    Picasso.get().load(currentEmployee.profileImage)
                        .into(employeeViewHolder.profileImageView)
                } else {
                    Picasso.get().load(currentEmployee.profileImage?.replace("http", "https"))
                        .into(employeeViewHolder.profileImageView)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return commonList.size
    }

    class StudentViewHolder(v: View): RecyclerView.ViewHolder(v) {
        val studentRollTextView: TextView = v.studentRollTextView
        val studentNameTextView: TextView = v.studentNameTextView
        val studentLocationTextView: TextView = v.studentLocationTextView
        val studentTagLineTextView: TextView = v.studentTagLineTextView
    }

    class EmployeeViewHolder(v: View): RecyclerView.ViewHolder(v) {
        val profileImageView: ImageView = v.profileImageView
        val employeeNameTextView: TextView = v.employeeNameTextView
        val employeeSalaryTextView : TextView= v.employeeSalaryTextView
        val employeeAgeTextView: TextView = v.employeeAgeTextView
    }
}