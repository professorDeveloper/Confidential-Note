package com.azamovhudstc.noteappplaystore.ui.screen

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.speech.RecognizerIntent
import android.speech.tts.TextToSpeech
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.azamovhudstc.noteappplaystore.BuildConfig
import com.azamovhudstc.noteappplaystore.R
import com.azamovhudstc.noteappplaystore.data.local.models.NoteEntity
import com.azamovhudstc.noteappplaystore.data.local.sharedpref.AppReference
import com.azamovhudstc.noteappplaystore.ui.screen.dialogs.SetPasswordDialog
import com.azamovhudstc.noteappplaystore.utils.gone
import com.azamovhudstc.noteappplaystore.utils.initBottomSheet
import com.azamovhudstc.noteappplaystore.utils.showSnackbar
import com.azamovhudstc.noteappplaystore.utils.visible
import com.azamovhudstc.noteappplaystore.viewmodel.add.AddNoteViewModel
import com.azamovhudstc.noteappplaystore.viewmodel.add.AddNoteViewModelImpl
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_create_note.*
import kotlinx.android.synthetic.main.fragment_home_screen.*
import kotlinx.android.synthetic.main.fragment_set_password.*
import kotlinx.android.synthetic.main.layout_add_url.*
import kotlinx.android.synthetic.main.layout_miscellaneous.*
import kotlinx.android.synthetic.main.layout_miscellaneous.view.*
import kotlinx.android.synthetic.main.layout_reading_note.*
import kotlinx.android.synthetic.main.layout_reading_note.view.*
import net.yslibrary.android.keyboardvisibilityevent.util.UIUtil
import java.text.SimpleDateFormat
import java.util.*
import java.util.Objects.*

@AndroidEntryPoint
class AddScreen : Fragment(R.layout.activity_create_note) {
    private var textToSpeech: TextToSpeech? = null
    private var dialogReadingNote: AlertDialog? = null
    private var dialogAddURL: androidx.appcompat.app.AlertDialog? = null
    private val REQUEST_CODE_SPEECH_INPUT = 1

    private val viewModel: AddNoteViewModel by viewModels<AddNoteViewModelImpl>()
    private var selectedNoteColor = "#FFC845"
    private var bottomSheetMiscellaneous: BottomSheetBehavior<ConstraintLayout>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.addNoteViewModel.observe(this) { findNavController().popBackStack() }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        eventClicks()
        initBottomSheet(view)
        initView()
        initBottomSheetEventsAndClick()


    }

    @SuppressLint("UseRequireInsteadOfGet")
    private fun initView() {
        var data = arguments?.getBoolean("isurl", false)
        var dataVoice = arguments?.getString("dataVoice", "").toString()
        input_note.setText(dataVoice)
        var url = arguments?.getString("url", "").toString()
        if (data!!) {
            if (url.isNotEmpty()) {
                text_url.text = url.toString()
                text_url.visible()
                remove_url_btn.visible()
            }
        }

        text_date_time.setText(
            String.format(
                "Added  %s",
                SimpleDateFormat("EEEE, dd MMMM yyyy HH:mm a", Locale.getDefault())
                    .format(Date())
            )
        )
    }

    private fun eventClicks() {
        var isPassword = false
        create_note_back_btn.setOnClickListener { findNavController().popBackStack() }
        create_note_set_password.setOnClickListener {
            if (AppReference(requireContext()).getToken().toString().isEmpty()) {
                val addPasswordDialog = SetPasswordDialog()
                addPasswordDialog.show(parentFragmentManager, "salom")
                addPasswordDialog.savePassword {
                    if (!isPassword) {
                        create_note_set_password.setImageResource(R.drawable.lock)
                        isPassword = true
                    } else {
                        isPassword = false
                        create_note_set_password.setImageResource(R.drawable.unlock)
                    }
                    showSnackbar(
                        "Parol o`rnatildi endi barcha notelarni quluuflash imkoyiatiga ekasiz !",
                        requireView()
                    )
                    addPasswordDialog.dismiss()
                }
            } else {
                if (!isPassword) {
                    create_note_set_password.setImageResource(R.drawable.lock)
                    isPassword = true
                } else {
                    isPassword = false
                    create_note_set_password.setImageResource(R.drawable.unlock)
                }
            }
        }
        create_note_save_btn.setOnClickListener {
            if (input_note_title.text.toString().isEmpty() || input_note_subtitle.text.toString()
                    .isEmpty() || input_note.text.toString().isEmpty()
            ) {
                showSnackbar("Maydon Bo`sh !", requireView())
            } else {
                viewModel.addNote(
                    NoteEntity(
                        password = isPassword,
                        title = input_note_title.text.toString(),
                        subTitle = input_note_subtitle.text.toString(),
                        color = selectedNoteColor,
                        date = text_date_time.text.toString(),
                        note = input_note.text.toString(),
                        webLink = text_url.text.toString()
                    )
                )
            }
        }
        remove_url_btn.setOnClickListener {
            text_url.setText(null);
            text_url.gone();
            remove_url_btn.gone()
        }

    }

    private fun showAddURLDialog() {
        if (dialogAddURL == null) {
            val builder = androidx.appcompat.app.AlertDialog.Builder(requireContext())
            val view = LayoutInflater.from(requireContext()).inflate(
                R.layout.layout_add_url,
                layout_add_url_container
            )
            builder.setView(view)
            dialogAddURL = builder.create()
            if (dialogAddURL!!.window != null) {
                dialogAddURL!!.window!!.setBackgroundDrawable(ColorDrawable(0))
            }
            val inputURL = view.findViewById<EditText>(R.id.input_url)
            inputURL.requestFocus()
            view.findViewById<View>(R.id.dialog_add_btn).setOnClickListener { v: View? ->
                if (inputURL.text.toString().trim { it <= ' ' }
                        .isEmpty()) {
                    showSnackbar("Enter Url", view)
                } else if (!Patterns.WEB_URL.matcher(
                        inputURL.text.toString().trim { it <= ' ' }).matches()
                ) {
                    showSnackbar("Enter Url", view)
                } else {
                    UIUtil.hideKeyboard(view.context, inputURL)
                    text_url.setText(inputURL.text.toString().trim { it <= ' ' })
                    text_url.visible()
                    remove_url_btn.visible()
                    dialogAddURL!!.dismiss()
                }
            }
            view.findViewById<View>(R.id.dialog_cancel_btn).setOnClickListener { v: View? ->
                UIUtil.hideKeyboard(view.context, inputURL)
                dialogAddURL!!.dismiss()
            }
        }
        dialogAddURL!!.setCancelable(false)
        dialogAddURL!!.show()
    }

    private fun initBottomSheet(view: View) {
        initBottomSheet(view, selectedNoteColor) {
            val gradientDrawable = view_indicator_subtitle
            selectedNoteColor = it
            gradientDrawable.setBackgroundColor(Color.parseColor(it))

        }
    }

    private fun initBottomSheetEventsAndClick() {
        val layoutMiscellaneous: ConstraintLayout = layout_miscellaneous
        bottomSheetMiscellaneous = BottomSheetBehavior.from(layoutMiscellaneous)
        layoutMiscellaneous.layout_read_note.setOnClickListener {
            if (input_note_title.getText().toString().trim { it <= ' ' }.isEmpty() &&
                input_note_subtitle.getText().toString().trim { it <= ' ' }.isEmpty() &&
                input_note.getText().toString().trim { it <= ' ' }.isEmpty()
            ) {
                UIUtil.hideKeyboard(requireActivity())
                bottomSheetMiscellaneous!!.state = BottomSheetBehavior.STATE_COLLAPSED
                showSnackbar("Maydon Bo`sh !", requireView())
            } else {
                textToSpeech = TextToSpeech(
                    requireContext()
                ) { status: Int ->
                    if (status == TextToSpeech.SUCCESS) {
                        val result =
                            textToSpeech!!.setLanguage(Locale.ENGLISH)
                        if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                            bottomSheetMiscellaneous!!.state = BottomSheetBehavior.STATE_COLLAPSED
                            Toast.makeText(
                                requireContext(),
                                "Sorry, language not supported!",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            UIUtil.hideKeyboard(requireActivity())
                            bottomSheetMiscellaneous!!.state = BottomSheetBehavior.STATE_COLLAPSED
                            showDialogReadingNote()
                        }
                    } else {
                        bottomSheetMiscellaneous!!.state = BottomSheetBehavior.STATE_COLLAPSED
                        Toast.makeText(
                            requireContext(),
                            "Initialization Failed!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }

        }
        layoutMiscellaneous.layout_add_url.setOnClickListener {
            showAddURLDialog()
        }
        layoutMiscellaneous.add_voice.setOnClickListener {
            val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
            intent.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )

            intent.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE,
                Locale.getDefault()
            )

            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak to text")
            try {
                startActivityForResult(intent, REQUEST_CODE_SPEECH_INPUT)
            } catch (e: Exception) {
                Log.d("!@#", "eventClicks: ${e.message}")
            }
        }
        layoutMiscellaneous.layout_share_note.setOnClickListener {
            if (!(input_note_title.text.toString().isEmpty() || input_note_subtitle.text.toString()
                    .isEmpty())){
                val sharingIntent = Intent(Intent.ACTION_SEND)
                sharingIntent.type = "text/plain"
                val shareBody = input_note_subtitle.text.toString()
                val shareSubject =
                    "${input_note_title.text}"
                sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody)
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, shareSubject)
                startActivity(Intent.createChooser(sharingIntent, "Share using"))

            }else{
                showSnackbar("Maydon Bo`sh !",requireView())
            }
        }

    }

    private fun showDialogReadingNote() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(requireActivity())
        val view: View = LayoutInflater.from(requireContext()).inflate(
            com.azamovhudstc.noteappplaystore.R.layout.layout_reading_note,
            layout_reading_note_container
        )
        builder.setView(view)
        dialogReadingNote = builder.create()
        if (dialogReadingNote!!.getWindow() != null) {
            dialogReadingNote!!.window!!.setBackgroundDrawable(ColorDrawable(0))
        }

        val textToRead1: String = input_note_title.getText().toString().trim()
        val textToRead2: String = input_note_subtitle.getText().toString().trim()
        val textToRead3: String = input_note.getText().toString().trim()
        view.findViewById<View>(R.id.start_reading).setOnClickListener { v: View? ->
            view.text_reading_text.setText("O`QILMOQDA...")
            textToSpeech!!.speak(textToRead1, TextToSpeech.QUEUE_ADD, null, "")
            textToSpeech!!.speak(textToRead2, TextToSpeech.QUEUE_ADD, null, "")
            textToSpeech!!.speak(textToRead3, TextToSpeech.QUEUE_ADD, null, "")
        }
        view.findViewById<View>(R.id.stop_reading).setOnClickListener { v: View? ->
            view.text_reading_text.setText("Es?")
            if (textToSpeech != null) {
                textToSpeech!!.stop()
            }
            dialogReadingNote!!.dismiss()
        }
        dialogReadingNote!!.setCancelable(false)
        dialogReadingNote!!.show()
    }

    override fun onPause() {
        super.onPause()
        if (textToSpeech != null) {
            textToSpeech!!.stop()
            dialogReadingNote!!.dismiss()
        }
    }

    override fun onStop() {
        super.onStop()
        if (textToSpeech != null) {
            textToSpeech!!.stop()
            textToSpeech!!.shutdown()
            dialogReadingNote!!.dismiss()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (textToSpeech != null) {
            textToSpeech!!.stop()
            textToSpeech!!.shutdown()
            dialogReadingNote!!.dismiss()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_SPEECH_INPUT) {
            // on below line we are checking if result code is ok
            if (resultCode == Activity.RESULT_OK && data != null) {

                // in that case we are extracting the
                // data from our array list
                val res: ArrayList<String> =
                    data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS) as ArrayList<String>

                val dater = "${input_note.text.toString()} ${requireNonNull(res)[0]}"
                input_note.setText(dater)
            }
        }
    }

}