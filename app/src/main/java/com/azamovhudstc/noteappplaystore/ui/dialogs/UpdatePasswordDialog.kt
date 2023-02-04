package com.azamovhudstc.noteappplaystore.ui.dialogs

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import com.azamovhudstc.noteappplaystore.R
import com.azamovhudstc.noteappplaystore.data.local.sharedpref.AppReference
import com.azamovhudstc.noteappplaystore.ui.adapter.UpdatePasswordAdapterUp
import com.azamovhudstc.noteappplaystore.utils.progressDialog
import com.azamovhudstc.noteappplaystore.utils.showSnackbar
import kotlinx.android.synthetic.main.fragment_set_password.*
import kotlinx.android.synthetic.main.layout_set_password_note.*
import kotlinx.android.synthetic.main.layout_set_password_note.view.*
import kotlinx.android.synthetic.main.lock_update_dialog.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class UpdatePasswordDialog : DialogFragment(R.layout.lock_update_dialog) {
    private lateinit var  block: ()->Unit
    private var password=""
    fun savePassword(blocks :()->Unit){
        block=blocks
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = UpdatePasswordAdapterUp(this)

        setUpdatePassword.adapter = adapter
        setUpdatePassword.isUserInputEnabled = false
        setUpdatePassword.setOnTouchListener(null);
        dialog!!.window?.setBackgroundDrawable(ColorDrawable(0))
        adapter.nextUpdatePasswordInEnterPassword {
            setUpdatePassword.currentItem=2
        }
        adapter.nextBackupScreen {
            setUpdatePassword.currentItem=1
        }
        adapter.saveBlockListener {
            setUpdatePassword.currentItem=3
            password=it
        }
        adapter.saveBackUpBlockListener {
            AppReference(requireContext()).setToken(password)
            AppReference(requireContext()).setPasswordConfirm(it)
            block.invoke()
            dialog!!.dismiss()
        }

    }

}