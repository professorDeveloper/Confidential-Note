package com.azamovhudstc.noteappplaystore.ui.screen.dialogs

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import com.azamovhudstc.noteappplaystore.R
import com.azamovhudstc.noteappplaystore.data.local.sharedpref.AppReference
import com.azamovhudstc.noteappplaystore.ui.adapter.SetPasswordAdapter
import com.azamovhudstc.noteappplaystore.utils.progressDialog
import kotlinx.android.synthetic.main.fragment_set_password.*
import kotlinx.android.synthetic.main.layout_set_password_note.*
import kotlinx.android.synthetic.main.layout_set_password_note.view.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SetPasswordDialog : DialogFragment(R.layout.layout_set_password_note) {
    private lateinit var  block: ()->Unit
    private var password=""
    fun savePassword(blocks :()->Unit){
        block=blocks
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = SetPasswordAdapter(this)
        setPassword.adapter = adapter
        setPassword.isUserInputEnabled = false
        setPassword.setOnTouchListener(null);
        dialog!!.window?.setBackgroundDrawable(ColorDrawable(0))
        adapter.nextBtnClickListener{
            password=it

            setPassword.currentItem=1
        }
        adapter.saveBlockListener {
            AppReference(requireContext()).setToken(password)
            AppReference(requireContext()).setPasswordConfirm(it)
            block.invoke()

        }

        setPassword.currentItem
    }

}