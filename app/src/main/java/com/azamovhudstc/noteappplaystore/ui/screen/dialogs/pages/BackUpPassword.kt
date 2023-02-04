package com.azamovhudstc.noteappplaystore.ui.screen.dialogs.pages

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.azamovhudstc.noteappplaystore.R
import com.azamovhudstc.noteappplaystore.utils.progressDialog
import com.azamovhudstc.noteappplaystore.utils.showSnackbar
import kotlinx.android.synthetic.main.fragment_back_up_password.*
import kotlinx.android.synthetic.main.fragment_back_up_password.progressDialog
import kotlinx.android.synthetic.main.fragment_set_password.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class BackUpPassword : Fragment(R.layout.fragment_back_up_password) {
    lateinit var block: (password: String) -> Unit
    fun setOnClickListener(blocks: (password: String) -> Unit) {
        block = blocks
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        saqlash.setOnClickListener {
            if (backupPassword.text.toString().trim().isEmpty() || backupConfirm.text.toString().trim()
                    .isEmpty() || backupPassword.text.toString().length <= 3 || backupPassword.text.toString()
                    .trim().length > 15 || backupConfirm.text.toString().length <= 3 ||
                backupConfirm.text.toString().length > 15
            ) {
                showSnackbar("Maydonlarda xatolik bor !",requireView())
            }else {
                if (backupPassword.text.toString().trim() == backupConfirm.text.toString()
                        .trim()
                ) {
                    lifecycleScope.launch {
                        progressDialog(containerbackup, true, progressDialog)
                        delay(400)
                        progressDialog(containerbackup, false, progressDialog)
                        block.invoke(backupPassword.text.toString())

                    }
                } else {
                    showSnackbar("Maydonlar noto`g`ri  to`ldirilgan", requireView())
                }

            }


        }
    }
}