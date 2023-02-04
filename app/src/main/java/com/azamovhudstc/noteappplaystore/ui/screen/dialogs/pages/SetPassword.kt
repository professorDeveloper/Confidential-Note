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
import kotlinx.android.synthetic.main.fragment_set_password.*
import kotlinx.android.synthetic.main.layout_set_password_note.view.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SetPassword : Fragment(R.layout.fragment_set_password) {
    lateinit var block: (password: String) -> Unit
    fun setOnClickListener(blocks: (password: String) -> Unit) {
        block = blocks
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progressDialog(root, false, progressDialog)
        noextPassword.setOnClickListener {
            if (password.text?.trim().toString().isEmpty() || confirm_password.text?.trim()
                    .toString()
                    .isEmpty() || password.text.toString().length <= 3 || password.text.toString()
                    .trim().length > 15 || confirm_password.text.toString().length <= 3 ||
                confirm_password.text.toString().length > 15
            ) {
                showSnackbar("Maydonlarda xatolik bor !",requireView())
            } else {
                if (confirm_password.text.toString() == password.text.toString()) {
                    lifecycleScope.launch {
                        progressDialog(root, true, progressDialog)
                        delay(400)
                        progressDialog(root, false, progressDialog)
                        block.invoke(password.text.toString())
                        println("SalomSalomSalom")
                    }
                } else {
                    showSnackbar("O`zaro teng emas", requireView())
                }
            }

        }

    }
}