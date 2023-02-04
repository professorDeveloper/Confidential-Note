package com.azamovhudstc.noteappplaystore.ui.adapter

import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.azamovhudstc.noteappplaystore.ui.screen.dialogs.pages.BackUpPassword
import com.azamovhudstc.noteappplaystore.ui.screen.dialogs.pages.SetPassword

class SetPasswordAdapter(fragment: DialogFragment):FragmentStateAdapter(fragment) {

    private lateinit var  block: (String)->Unit

    fun nextBtnClickListener(blocks :(String)->Unit){
        block=blocks
    }
    private lateinit var  saveBlock: (String)->Unit

    fun saveBlockListener(blocks :(String)->Unit){
        saveBlock=blocks
    }

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0-> {
               val setPassword= SetPassword()
                setPassword.setOnClickListener {
                    block.invoke(it)
                }

                return setPassword
            }
            else-> {


               val backUpPassword= BackUpPassword()

                backUpPassword.setOnClickListener {
                    saveBlock.invoke(it)

                }



                return backUpPassword
            }
        }
    }
}