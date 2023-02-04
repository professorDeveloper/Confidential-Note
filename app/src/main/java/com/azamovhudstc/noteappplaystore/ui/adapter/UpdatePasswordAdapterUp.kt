package com.azamovhudstc.noteappplaystore.ui.adapter

import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.azamovhudstc.noteappplaystore.ui.screen.dialogs.pages.BackUpPassword
import com.azamovhudstc.noteappplaystore.ui.screen.dialogs.pages.EnterBackUp
import com.azamovhudstc.noteappplaystore.ui.screen.dialogs.pages.EnterPassword
import com.azamovhudstc.noteappplaystore.ui.screen.dialogs.pages.SetPassword

class UpdatePasswordAdapterUp(fragment: DialogFragment) : FragmentStateAdapter(fragment) {

    private lateinit var nextUpdateEnterPassword: () -> Unit
    private lateinit var nextBackupScreenBlock: () -> Unit

    fun nextBackupScreen(blocks: () -> Unit) {
        nextBackupScreenBlock = blocks
    }

    fun nextUpdatePasswordInEnterPassword(blocks: () -> Unit) {
        nextUpdateEnterPassword = blocks
    }

    private lateinit var saveBlock: (String) -> Unit

    fun saveBlockListener(blocks: (String) -> Unit) {
        saveBlock = blocks
    }

    private lateinit var saveBackUpBlock: (String) -> Unit

    fun saveBackUpBlockListener(blocks: (String) -> Unit) {
        saveBackUpBlock = blocks
    }
    override fun getItemCount(): Int {
        return 4
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                val enterPassword = EnterPassword()
                enterPassword.setBackupNextClickListener {
                    nextBackupScreenBlock.invoke()
                }
                enterPassword.setNextClickListener {
                    nextUpdateEnterPassword.invoke()
                }
                return enterPassword
            }
            1 -> {
                val enterBackUp = EnterBackUp()
                enterBackUp.setNextUpdateListener {
                    nextUpdateEnterPassword.invoke()
                }
                return enterBackUp
            }
            2 ->{
                val setPassword = SetPassword()

                setPassword.setOnClickListener {
                    saveBlock.invoke(it)
                    println("Adapter Savvvvvvvvvvvvv")
                }

                return setPassword
            }
            else -> {
                val backUpPassword = BackUpPassword()

                backUpPassword.setOnClickListener {
                    saveBackUpBlock.invoke(it)
                }

                return backUpPassword
            }
        }
    }
}