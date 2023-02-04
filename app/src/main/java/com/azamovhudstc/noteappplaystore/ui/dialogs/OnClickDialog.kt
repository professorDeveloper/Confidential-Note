package com.azamovhudstc.noteappplaystore.ui.dialogs

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.azamovhudstc.noteappplaystore.R
import com.azamovhudstc.noteappplaystore.data.local.sharedpref.AppReference
import com.azamovhudstc.noteappplaystore.utils.showSnackbar
import kotlinx.android.synthetic.main.fragment_on_click_dialog.*

class OnClickDialog : DialogFragment(R.layout.fragment_on_click_dialog) {
    fun openNote(blocks :()->Unit){
        block=blocks
    }
    private lateinit var  block: ()->Unit

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog!!.window?.setBackgroundDrawable(ColorDrawable(0))
        openNote.setOnClickListener {
            var appReference=AppReference(requireContext())
            if (appReference.getToken().toString()==oldPassword.text.toString()){
                block.invoke()

            }
            else{
                showSnackbar("Parol xato ! ",requireView())
            }
        }

    }
}