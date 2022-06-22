package com.example.fitness_feip.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.fitness_feip.ui.main.screens.activity.MyActivityScreenFragment
import com.example.fitness_feip.ui.main.screens.activity.UsersActivityScreenFragment

class ViewPagerAdapter(fragment: Fragment) :
    FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                MyActivityScreenFragment()
            }
            1 -> {
                UsersActivityScreenFragment()
            }
            else -> {
                Fragment()
            }
        }
    }

}
