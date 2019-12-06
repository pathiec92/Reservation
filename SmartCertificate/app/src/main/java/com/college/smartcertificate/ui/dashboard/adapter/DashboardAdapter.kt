package com.college.smartcertificate.ui.dashboard.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.college.smartcertificate.data.Sem
import com.college.smartcertificate.data.StudentData.studentEntity
import com.college.smartcertificate.data.StudentData.gradeData
import com.college.smartcertificate.data.StudentEntity
import com.college.smartcertificate.databinding.*
import androidx.recyclerview.widget.DividerItemDecoration



class DashboardAdapter :
    ListAdapter<StudentEntity, BaseViewHolder>(StudentDiffCallBack()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        println("onCreateViewHolder, viewType = $viewType")
        return when (viewType) {
            ViewType.HEADER_VIEW.ordinal -> HeaderViewHolder(
                HeaderItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            ViewType.YEAR_VIEW.ordinal -> YearViewHolder(
                YearItemBinding.inflate(
                    LayoutInflater.from(
                        parent.context
                    ), parent, false
                )
            )
            else -> SemViewHolder(
                SemItemBinding.inflate(
                    LayoutInflater.from(
                        parent.context
                    ), parent, false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return 16
    }

    override fun getItemViewType(position: Int): Int {
        return when {
            position == 0 -> ViewType.HEADER_VIEW
            position % 3 == 0 -> ViewType.YEAR_VIEW
            else -> ViewType.SEM_VIEW
        }.ordinal
    }

    enum class ViewType {
        HEADER_VIEW,
        SEM_VIEW,
        YEAR_VIEW
    }
}

abstract class BaseViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    abstract fun bind(position: Int)
}

class HeaderViewHolder(private val binding: HeaderItemBinding) :
    BaseViewHolder(binding.root) {
    //val collegeName: TextView = binding.collegeName
    val courseName: TextView = binding.courseName
    val studentName: TextView = binding.studentName
    val regNum: TextView = binding.regNum
    val rank: TextView = binding.rank
    override fun bind(position: Int) {
        //collegeName.text = studentEntity.colgname
        courseName.text = studentEntity.CourseLname
        studentName.text = studentEntity.name
        regNum.text = studentEntity.regno
        rank.text = studentEntity.rANK
    }

}


class YearViewHolder(private val binding: YearItemBinding) : BaseViewHolder(binding.root) {
    val ygpaValue: TextView = binding.ygpaValue
    val term :TextView =binding.term
    val year :TextView =binding.year

    override fun bind(position: Int) {
          when (position) {
            3 -> {
                ygpaValue.text = studentEntity.ygpa1
                term.text = "Year 1"
                year.text = studentEntity.year1
            }
            6 -> {
                ygpaValue.text = studentEntity.ygpa2
                term.text = "Year 2"
                year.text = studentEntity.year2

            }
            9 -> {
                ygpaValue.text = studentEntity.ygpa3
                term.text = "Year 3"
                year.text = studentEntity.year3

            }
            12 -> {
                ygpaValue.text = studentEntity.ygpa4
                term.text = "Year 4"
                year.text = studentEntity.year4

            }
            else -> {
                ygpaValue.text = studentEntity.ygpa5
                term.text = "Year 5"
                year.text = studentEntity.year5

            }
        }
    }
}

class SemViewHolder(private val binding: SemItemBinding) : BaseViewHolder(binding.root) {
    enum class ViewType {
        HEADER_VIEW,
        SEM_VIEW
    }

    val semister: TextView = binding.semister
    val year: TextView = binding.year
    val cgpaValue: TextView = binding.cgpaValue
    val gradeList: RecyclerView = binding.gradeList



    override fun bind(position: Int) {
        val semPosition = (position - (position / 3)) - 1
        if(semPosition>=10 || semPosition<0) return
        val yearWise = studentEntity.yearWise[semPosition]
        semister.text = yearWise.sem
        year.text = yearWise.year
        cgpaValue.text = yearWise.sgpa
        gradeList.addItemDecoration(
            DividerItemDecoration(
                binding.root.context,
                DividerItemDecoration.VERTICAL
            )
        )
        gradeList.adapter = GradeAdapter(when(semPosition){
            0-> gradeData.sem1
            1-> gradeData.sem1
            2-> gradeData.sem2
            3-> gradeData.sem3
            4-> gradeData.sem4
            5-> gradeData.sem5
            6-> gradeData.sem6
            7-> gradeData.sem7
            8-> gradeData.sem8
            9-> gradeData.sem9
            else-> gradeData.sem10
        })

    }

    class GradeAdapter(private val grades: List<Sem>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            return when {
                viewType == ViewType.HEADER_VIEW.ordinal -> GradeHeaderHolderView(
                    GradeHeaderItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
                else -> GradeHolderView(
                    GradeItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
        }

        override fun getItemCount(): Int {
            return grades.count()+1
        }

        override fun getItemViewType(position: Int): Int {
            return if (position == 0) ViewType.HEADER_VIEW.ordinal else ViewType.SEM_VIEW.ordinal
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            if(position == 0) return
            (holder as GradeHolderView).apply {
                courseCode.text = grades[position-1].cr
                grade.text = grades[position-1].grd
            }

        }
    }
    class GradeHeaderHolderView(private val binding: GradeHeaderItemBinding) : RecyclerView.ViewHolder(binding.root)
    class GradeHolderView(private val binding: GradeItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val courseCode = binding.courseCode
        val grade = binding.grade
        /* fun bind(sem:Int){

         }*/
    }
}

private class StudentDiffCallBack : DiffUtil.ItemCallback<StudentEntity>() {
    override fun areItemsTheSame(oldItem: StudentEntity, newItem: StudentEntity): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: StudentEntity, newItem: StudentEntity): Boolean {
        return oldItem == newItem
    }
}

