package com.blue.goeat.ui.main.ui.wizard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.*
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.blue.goeat.data.entity.College
import com.blue.goeat.data.entity.Hotel
import com.blue.goeat.databinding.CollegeWizBinding
import com.blue.goeat.databinding.PastScheduledItemBinding
import com.blue.goeat.extentions.loadImageUrl
import com.blue.goeat.extentions.showTimePicker
import com.blue.goeat.di.InjectorUtils
import java.text.SimpleDateFormat

class CollegePagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return CollegeWizFragment()
    }


}

class CollegeWizFragment : Fragment() {
    private lateinit var binding: CollegeWizBinding
    private val collegeViewModel: CollegeWizViewModel by viewModels {
        InjectorUtils.provideCollegeWizViewModelFactory()
    }
    private val collegeWizAdapter = CollegeWizAdapter()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = CollegeWizBinding.inflate(inflater, container, false)
        binding.collegeList.apply {
            adapter = collegeWizAdapter
        }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        collegeViewModel.colleges.observe(viewLifecycleOwner) {
            collegeWizAdapter.submitList(it)
        }
    }
}

class CollegeWizViewModel : ViewModel() {
    val colleges: LiveData<List<College>> =
        InjectorUtils.provideAppDb().getCollegeDao().getAllColleges()

    val hotels: LiveData<List<Hotel>> =
        InjectorUtils.provideAppDb().getHotelDao().getAllHotels()

}


class CollegeWizAdapter : ListAdapter<College, RecyclerView.ViewHolder>(CollegeWizDiffCallBack()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return CollegeWizViewHolder(
            PastScheduledItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as CollegeWizViewHolder).bind(getItem(position))
    }

}

class CollegeWizViewHolder(private val binding: PastScheduledItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    init {
        binding.dateChip.setOnClickListener {
            binding.dateChip.context?.showTimePicker {
                val sdf = SimpleDateFormat("E, dd MMM, hh:mm a")
                println("time = ${sdf.format(it.time)}")
                binding.dateChip.text = sdf.format(it.time)
            }
        }
    }

    fun bind(college: College) {
        binding.hotelName.text = college.entityInfo.name
       // binding.hotelDesc.text = college.entityInfo.description
        binding.hotelImg.loadImageUrl(college.entityInfo.uri ?: "")
    }
}


private class CollegeWizDiffCallBack : DiffUtil.ItemCallback<College>() {
    override fun areItemsTheSame(oldItem: College, newItem: College): Boolean {
        return oldItem.collegeId == newItem.collegeId
    }

    override fun areContentsTheSame(oldItem: College, newItem: College): Boolean {
        return oldItem == newItem
    }
}

class CollegeWizViewModelFactory : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CollegeWizViewModel() as T
    }
}

