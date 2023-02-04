package com.azamovhudstc.noteappplaystore.utils

import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.azamovhudstc.noteappplaystore.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_create_note.*
import kotlinx.android.synthetic.main.activity_create_note.view.*
import kotlinx.android.synthetic.main.fragment_home_screen.view.*
import kotlinx.android.synthetic.main.layout_miscellaneous.*
import kotlinx.android.synthetic.main.layout_miscellaneous.view.*
import net.yslibrary.android.keyboardvisibilityevent.util.UIUtil


fun View.inVisible() {
    this.visibility = View.INVISIBLE
}

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

fun Fragment.placeHolder(view: View,list: List<Any>) {
    if (list.isEmpty()) {
        view!!.notes_recycler_view.inVisible()
        view!!.image_empty.visible()
        view!!.text_empty.visible()
    } else {
        view!!.notes_recycler_view.visible()
        view!!.image_empty.inVisible()
        view!!.text_empty.inVisible()
    }

}

fun Fragment.initBottomSheet(
    view: View,
    selectedNoteColor: String,
    block: (String) -> Unit
): String {
    var selectedNoteColors = selectedNoteColor
    var bottomSheetDialog = BottomSheetBehavior.from(view.layout_miscellaneous)
    view.layout_miscellaneous.setOnClickListener {

        if (bottomSheetDialog.getState() != BottomSheetBehavior.STATE_EXPANDED) {
            bottomSheetDialog.setState(BottomSheetBehavior.STATE_EXPANDED)
        } else {
            bottomSheetDialog.setState(BottomSheetBehavior.STATE_COLLAPSED)
        }
    }
    when (selectedNoteColor) {
        "#FFB400" -> {
            view.layout_miscellaneous.findViewById<View>(R.id.view_color2).performClick()
            view.check_color2.setImageResource(R.drawable.ic_check)
            view.check_color1.setImageResource(0)
            view.check_color3.setImageResource(0)
            view.check_color4.setImageResource(0)
            view.check_color5.setImageResource(0)
            view.check_color6.setImageResource(0)
            view.check_color7.setImageResource(0)
            view.check_color8.setImageResource(0)

        }
        "#3B81FF" -> {
            view.layout_miscellaneous.findViewById<View>(R.id.view_color3).performClick()
            view.check_color3.setImageResource(R.drawable.ic_check)
            view.check_color1.setImageResource(0)
            view.check_color2.setImageResource(0)
            view.check_color4.setImageResource(0)
            view.check_color5.setImageResource(0)
            view.check_color6.setImageResource(0)
            view.check_color7.setImageResource(0)
            view.check_color8.setImageResource(0)
        }
        "#FF4E4E" -> {
            view.layout_miscellaneous.findViewById<View>(R.id.view_color4).performClick()
            view.check_color4.setImageResource(R.drawable.ic_check)
            view.check_color1.setImageResource(0)
            view.check_color3.setImageResource(0)
            view.check_color2.setImageResource(0)
            view.check_color5.setImageResource(0)
            view.check_color6.setImageResource(0)
            view.check_color7.setImageResource(0)
            view.check_color8.setImageResource(0)     }
        "#13A662" -> {
            view.layout_miscellaneous.findViewById<View>(R.id.view_color5).performClick()
            view.check_color5.setImageResource(R.drawable.ic_check)
            view.check_color1.setImageResource(0)
            view.check_color3.setImageResource(0)
            view.check_color4.setImageResource(0)
            view.check_color2.setImageResource(0)
            view.check_color6.setImageResource(0)
            view.check_color7.setImageResource(0)
            view.check_color8.setImageResource(0) }
        "#FF388E" -> {
            view.layout_miscellaneous.findViewById<View>(R.id.view_color6).performClick()
            view.check_color6.setImageResource(R.drawable.ic_check)
            view.check_color1.setImageResource(0)
            view.check_color3.setImageResource(0)
            view.check_color4.setImageResource(0)
            view.check_color5.setImageResource(0)
            view.check_color2.setImageResource(0)
            view.check_color7.setImageResource(0)
            view.check_color8.setImageResource(0)     }
        "#118E9C" -> {
            view.layout_miscellaneous.findViewById<View>(R.id.view_color7).performClick()
            view.check_color7.setImageResource(R.drawable.ic_check)
            view.check_color1.setImageResource(0)
            view.check_color3.setImageResource(0)
            view.check_color4.setImageResource(0)
            view.check_color5.setImageResource(0)
            view.check_color6.setImageResource(0)
            view.check_color2.setImageResource(0)
            view.check_color8.setImageResource(0)}
        "#FF822E" -> {
            view.layout_miscellaneous.findViewById<View>(R.id.view_color8).performClick()
            view.check_color8.setImageResource(R.drawable.ic_check)
            view.check_color1.setImageResource(0)
            view.check_color3.setImageResource(0)
            view.check_color4.setImageResource(0)
            view.check_color5.setImageResource(0)
            view.check_color6.setImageResource(0)
            view.check_color7.setImageResource(0)
            view.check_color2.setImageResource(0)
        }
        else->{
            view.check_color1.setImageResource(R.drawable.ic_check)
            view.check_color2.setImageResource(0)
            view.check_color3.setImageResource(0)
            view.check_color4.setImageResource(0)
            view.check_color5.setImageResource(0)
            view.check_color6.setImageResource(0)
            view.check_color7.setImageResource(0)
            view.check_color8.setImageResource(0)
        }
    }

    val checkColor1: ImageView =
        view.layout_miscellaneous.findViewById<ImageView>(R.id.check_color1)
    val checkColor2: ImageView =
        view.layout_miscellaneous.findViewById<ImageView>(R.id.check_color2)
    val checkColor3: ImageView =
        view.layout_miscellaneous.findViewById<ImageView>(R.id.check_color3)
    val checkColor4: ImageView =
        view.layout_miscellaneous.findViewById<ImageView>(R.id.check_color4)
    val checkColor5: ImageView =
        view.layout_miscellaneous.findViewById<ImageView>(R.id.check_color5)
    val checkColor6: ImageView =
        view.layout_miscellaneous.findViewById<ImageView>(R.id.check_color6)
    val checkColor7: ImageView =
        view.layout_miscellaneous.findViewById<ImageView>(R.id.check_color7)
    val checkColor8: ImageView =
        view.layout_miscellaneous.findViewById<ImageView>(R.id.check_color8)
    view.layout_miscellaneous.findViewById<View>(R.id.view_color1).setOnClickListener(
        View.OnClickListener { v: View? ->
            UIUtil.hideKeyboard(requireActivity())
            selectedNoteColors = "#" + Integer.toHexString(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.colorPrimary
                ) and 0x00ffffff
            )
            checkColor1.setImageResource(R.drawable.ic_check)
            checkColor2.setImageResource(0)
            checkColor3.setImageResource(0)
            checkColor4.setImageResource(0)
            checkColor5.setImageResource(0)
            checkColor6.setImageResource(0)
            checkColor7.setImageResource(0)
            checkColor8.setImageResource(0)
            block.invoke(selectedNoteColors)
        })

    view.layout_miscellaneous.findViewById<View>(R.id.view_color2).setOnClickListener(
        View.OnClickListener { v: View? ->
            UIUtil.hideKeyboard(requireActivity())
            selectedNoteColors = "#FFB400"
            checkColor1.setImageResource(0)
            checkColor2.setImageResource(R.drawable.ic_check)
            checkColor3.setImageResource(0)
            checkColor4.setImageResource(0)
            checkColor5.setImageResource(0)
            checkColor6.setImageResource(0)
            checkColor7.setImageResource(0)
            checkColor8.setImageResource(0)
            block.invoke(selectedNoteColors)
        })

    view.layout_miscellaneous.findViewById<View>(R.id.view_color3).setOnClickListener(
        View.OnClickListener { v: View? ->
            UIUtil.hideKeyboard(requireActivity())
            selectedNoteColors = "#3B81FF"
            checkColor1.setImageResource(0)
            checkColor2.setImageResource(0)
            checkColor3.setImageResource(R.drawable.ic_check)
            checkColor4.setImageResource(0)
            checkColor5.setImageResource(0)
            checkColor6.setImageResource(0)
            checkColor7.setImageResource(0)
            checkColor8.setImageResource(0)
            block.invoke(selectedNoteColors)
        })

    view.layout_miscellaneous.findViewById<View>(R.id.view_color4).setOnClickListener(
        View.OnClickListener { v: View? ->
            UIUtil.hideKeyboard(requireActivity())
            selectedNoteColors = "#FF4E4E"
            checkColor1.setImageResource(0)
            checkColor2.setImageResource(0)
            checkColor3.setImageResource(0)
            checkColor4.setImageResource(R.drawable.ic_check)
            checkColor5.setImageResource(0)
            checkColor6.setImageResource(0)
            checkColor7.setImageResource(0)
            checkColor8.setImageResource(0)
            block.invoke(selectedNoteColors)
        })

    view.layout_miscellaneous.findViewById<View>(R.id.view_color5).setOnClickListener(
        View.OnClickListener { v: View? ->
            UIUtil.hideKeyboard(requireActivity())
            selectedNoteColors = "#13A662"
            checkColor1.setImageResource(0)
            checkColor2.setImageResource(0)
            checkColor3.setImageResource(0)
            checkColor4.setImageResource(0)
            checkColor5.setImageResource(R.drawable.ic_check)
            checkColor6.setImageResource(0)
            checkColor7.setImageResource(0)
            checkColor8.setImageResource(0)
            block.invoke(selectedNoteColors)
        })

    view.layout_miscellaneous.findViewById<View>(R.id.view_color6).setOnClickListener(
        View.OnClickListener { v: View? ->
            UIUtil.hideKeyboard(requireActivity())
            selectedNoteColors = "#FF388E"
            checkColor1.setImageResource(0)
            checkColor2.setImageResource(0)
            checkColor3.setImageResource(0)
            checkColor4.setImageResource(0)
            checkColor5.setImageResource(0)
            checkColor6.setImageResource(R.drawable.ic_check)
            checkColor7.setImageResource(0)
            checkColor8.setImageResource(0)
            block.invoke(selectedNoteColors)
        })

    view.layout_miscellaneous.findViewById<View>(R.id.view_color7).setOnClickListener(
        View.OnClickListener { v: View? ->
            UIUtil.hideKeyboard(requireActivity())
            selectedNoteColors = "#118E9C"
            checkColor1.setImageResource(0)
            checkColor2.setImageResource(0)
            checkColor3.setImageResource(0)
            checkColor4.setImageResource(0)
            checkColor5.setImageResource(0)
            checkColor6.setImageResource(0)
            checkColor7.setImageResource(R.drawable.ic_check)
            checkColor8.setImageResource(0)
            block.invoke(selectedNoteColors)
        })

    view.layout_miscellaneous.findViewById<View>(R.id.view_color8).setOnClickListener(
        View.OnClickListener { v: View? ->
            UIUtil.hideKeyboard(requireActivity())
            selectedNoteColors = "#FF822E"
            checkColor1.setImageResource(0)
            checkColor2.setImageResource(0)
            checkColor3.setImageResource(0)
            checkColor4.setImageResource(0)
            checkColor5.setImageResource(0)
            checkColor6.setImageResource(0)
            checkColor7.setImageResource(0)
            checkColor8.setImageResource(R.drawable.ic_check)
            block.invoke(selectedNoteColors)
        })

    return selectedNoteColors
}

fun Fragment.showSnackbar(text: String, view: View) {
    Snackbar.make(view, text, Toast.LENGTH_SHORT).show()
}

fun Fragment.progress(view:View,state:Boolean) {
    if (state
    ) {
        view.notes_recycler_view.inVisible()
        view.progress.visible()
    }
    else {
        view.progress.inVisible()
        view.notes_recycler_view.visible()
    }
}
fun Fragment.progressDialog(view:View,state:Boolean,progressBar: ProgressBar) {
    if (state) {
        view.inVisible()
        progressBar.visible()
    } else {
        progressBar.inVisible()
        view.visible()
    }
}
