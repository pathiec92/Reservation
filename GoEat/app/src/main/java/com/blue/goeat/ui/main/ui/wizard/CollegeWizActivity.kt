package com.blue.goeat.ui.main.ui.wizard

import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import com.blue.goeat.R
import com.blue.goeat.data.entity.College
import com.blue.goeat.data.entity.EntityInfo
import com.blue.goeat.data.entity.Hotel
import com.blue.goeat.databinding.ActivityCollegeWizBinding
import com.blue.goeat.extentions.showToast
import com.blue.goeat.di.InjectorUtils
import com.google.android.material.chip.Chip


class CollegeWizActivity : DialogFragment() {
   /* companion object {
        fun navigate(activity: Context) {
            val intent = Intent(activity, CollegeWizActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            activity.startActivity(intent)
        }
    }*/

    private val collegeList: MutableList<College> = ArrayList()
    private val hotelList: MutableList<Hotel> = ArrayList()
    private lateinit var binding: ActivityCollegeWizBinding
    private var collegeChip: Chip? = null
    private var hotelChip: Chip? = null
    private val collegeViewModel: CollegeWizViewModel by viewModels {
        InjectorUtils.provideCollegeWizViewModelFactory()
    }



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =  ActivityCollegeWizBinding.inflate(inflater,container,false)
        done()
        collegeChips()
        hotelChips()
        return binding.root
    }

    private fun done() {
        binding.done.setOnClickListener {

        }
    }

    private fun collegeChips() {
        collegeViewModel.colleges.observe(this) {
            binding.hotelGroup.visibility = View.VISIBLE
            collegeList.addAll(it)
            it.forEachIndexed { index, college ->
                val chip = chip(index, college.entityInfo)
                chip.setOnClickListener { binding.collegeChips.check(index) }
                binding.collegeChips.addView(chip)
            }
        }
        binding.collegeChips.setOnCheckedChangeListener { chipView, chipId ->
            collegeChip?.let{it.isSelected= false}
            chipView.findViewById<Chip>(chipId).apply {
                collegeChip = this
                isSelected = true
            }
            activity?.showToast(collegeList[chipId].entityInfo.name!!)
        }
    }

    private fun hotelChips() {
        collegeViewModel.hotels.observe(this) {
            binding.hotelGroup.visibility = View.VISIBLE
            hotelList.addAll(it)
            it.forEachIndexed { index, hotel ->
                val chip = chip(index, hotel.entityInfo)
                chip.setOnClickListener { binding.hotelChips.check(index) }
                binding.hotelChips.addView(chip)
            }
        }
        binding.hotelChips.setOnCheckedChangeListener { chipView, chipId ->
            hotelChip?.let{it.isSelected = false}
            chipView.findViewById<Chip>(chipId).apply {
                hotelChip = this
                isSelected = true
            }
            activity?.showToast(hotelList[chipId].entityInfo.name!!)
        }
    }

    private fun chip(
        index: Int,
        entityInfo: EntityInfo
    ): Chip {
        val chip = Chip(context)
        chip.id = index
        val paddingDp = getDp()
        chip.setPadding(0, paddingDp, paddingDp, paddingDp)
        chip.text = entityInfo.name
        chip.setChipIconResource(R.drawable.ic_location_color_control_normal_24dp)
        chip.setCloseIconResource(R.drawable.avatar)
        return chip
    }

    private fun getDp() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP, 10f,
        resources.displayMetrics
    ).toInt()

}
