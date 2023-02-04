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
import kotlinx.android.synthetic.main.enter_old_backup.*
import kotlinx.android.synthetic.main.enter_old_backup.view.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class EnterBackUp : Fragment(R.layout.enter_old_backup) {
    private lateinit var nextBackupBlock: () -> Unit
    fun setNextUpdateListener(block: () -> Unit) {
        nextBackupBlock = block
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        nextUpdatePasswordInBackup.setOnClickListener {
            var appReference=AppReference(requireContext())
            if (oldBackUpPass.text.toString()==appReference.getPasswordConfirm().toString()){
                progressDialog(root, true, enterbackupProgress)
                lifecycleScope.launch {
                    delay(400)
                    progressDialog(root, false, enterbackupProgress)
                    nextBackupBlock.invoke()
                }
            }
        }
    }
}