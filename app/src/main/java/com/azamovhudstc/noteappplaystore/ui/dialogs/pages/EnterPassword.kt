package com.azamovhudstc.noteappplaystore.ui.dialogs.pages

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.azamovhudstc.noteappplaystore.R
import com.azamovhudstc.noteappplaystore.data.local.sharedpref.AppReference
import com.azamovhudstc.noteappplaystore.utils.progressDialog
import com.azamovhudstc.noteappplaystore.utils.showSnackbar
import kotlinx.android.synthetic.main.fragment_enter_password.*
import kotlinx.android.synthetic.main.fragment_enter_password.view.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class EnterPassword : Fragment(R.layout.fragment_enter_password) {
    private lateinit var nextUnitBlock: () -> Unit
    private lateinit var nextBackupBlock: () -> Unit
    fun setNextClickListener(block: () -> Unit) {
        nextUnitBlock = block
    }

    fun setBackupNextClickListener(block: () -> Unit) {
        nextBackupBlock = block
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progressDialog(root, false, enterPasswordDialog)
        backupPasswordnext.setOnClickListener {
            progressDialog(root, true, enterPasswordDialog)
            lifecycleScope.launch {
                delay(400)
                progressDialog(root, false, enterPasswordDialog)
                nextBackupBlock.invoke()
            }
        }
        nextUpdatePassword.setOnClickListener {
            val appReference = AppReference(requireContext())
            if (appReference.getToken() == oldPassword.text.toString().trim()) {
                lifecycleScope.launch {
                    progressDialog(root, true, view.enterPasswordDialog)
                    delay(400)
                    progressDialog(root, false, view.enterPasswordDialog)
                    nextUnitBlock.invoke()
                }
            } else {
                progressDialog(root, false, view.enterPasswordDialog)
                showSnackbar("Parol notug`ri !", root)
            }
        }
    }
}