package com.azamovhudstc.noteappplaystore.ui.screen

import android.app.Activity.RESULT_OK
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.speech.RecognizerIntent
import android.speech.tts.TextToSpeech
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.GravityCompat
import androidx.core.widget.addTextChangedListener
import androidx.drawerlayout.widget.DrawerLayout.SimpleDrawerListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.azamovhudstc.noteappplaystore.BuildConfig
import com.azamovhudstc.noteappplaystore.R
import com.azamovhudstc.noteappplaystore.data.local.models.NoteEntity
import com.azamovhudstc.noteappplaystore.data.local.sharedpref.AppReference
import com.azamovhudstc.noteappplaystore.ui.adapter.NoteAdapter
import com.azamovhudstc.noteappplaystore.ui.screen.dialogs.OnClickDialog
import com.azamovhudstc.noteappplaystore.ui.screen.dialogs.SetPasswordDialog
import com.azamovhudstc.noteappplaystore.ui.screen.dialogs.UpdatePasswordDialog
import com.azamovhudstc.noteappplaystore.utils.Constants.END_SCALE
import com.azamovhudstc.noteappplaystore.utils.placeHolder
import com.azamovhudstc.noteappplaystore.utils.progress
import com.azamovhudstc.noteappplaystore.utils.showSnackbar
import com.azamovhudstc.noteappplaystore.utils.visible
import com.azamovhudstc.noteappplaystore.viewmodel.home.HomeScreenViewModel
import com.azamovhudstc.noteappplaystore.viewmodel.home.HomeScreenViewModelImpl
import dagger.hilt.android.AndroidEntryPoint
import hotchemi.android.rate.AppRate
import kotlinx.android.synthetic.main.activity_create_note.*
import kotlinx.android.synthetic.main.fragment_edit_screen.*
import kotlinx.android.synthetic.main.fragment_home_screen.*
import kotlinx.android.synthetic.main.layout_about_note.*
import kotlinx.android.synthetic.main.layout_about_note.view.*
import kotlinx.android.synthetic.main.layout_add_url.*
import kotlinx.android.synthetic.main.layout_reading_note.*
import kotlinx.android.synthetic.main.layout_reading_note.view.*
import net.yslibrary.android.keyboardvisibilityevent.util.UIUtil
import java.util.*
import java.util.Locale.filter
import kotlin.collections.ArrayList

@AndroidEntryPoint
class HomeScreen : Fragment(R.layout.fragment_home_screen) {
    private val adapter = NoteAdapter()
    private var dialogAddURL: AlertDialog? = null
    private var dialogReadingNote: Dialog? = null
    private val REQUEST_CODE_SPEECH_INPUT = 1
    lateinit var handler: Handler
    var query: String = ""
    lateinit var views: View
    lateinit var list: ArrayList<NoteEntity>
    private val viewModel: HomeScreenViewModel by viewModels<HomeScreenViewModelImpl>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        list = ArrayList()
        viewModel.openAddNoteScreen.observe(this, openAddNoteScreenObserver)
        viewModel.openAddNoteScreen.observe(this, openAddNoteScreenObserver)
        viewModel.getAllNote.observe(this, getAllNoteObserver)
        viewModel.progressState.observe(this) {
            progress(views, it)
        }
        viewModel.openUrlDialog.observe(this, openAddUrl)
        appRate()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        views=view
        setUpNavigationDrawer()
        eventClicks()


        viewModel.getAllNote()

        val appReference = AppReference(requireContext())
        println("ppassword :" + appReference.getToken())
        println("getPasswordConfirm :" + appReference.getPasswordConfirm())
        handler = Handler(Looper.getMainLooper())
    }

    private fun filter(text: String) {
       if (text.isNotEmpty()){
           val filteredlist = ArrayList<NoteEntity>()
           for (item in list) {
               if (item.title.toLowerCase().trim()
                       .contains(text.lowercase(Locale.getDefault())) || item.subTitle.toLowerCase()
                       .trim()
                       .contains(text.lowercase(Locale.getDefault())) || item.title.toLowerCase()
                       .trim()
                       .contains(text.lowercase(Locale.getDefault()))
               ) {
                   filteredlist.add(item)
               }
           }
           if (filteredlist.isEmpty()) {
               placeHolder(views,filteredlist)
           } else {
               adapter.submitList(filteredlist)
               placeHolder(views,filteredlist)
           }
       }else{
           placeHolder(views,list)
           adapter.submitList(list)
       }
    }

    private val openAddNoteScreenObserver = Observer<Unit> {
        var bundle = Bundle()
        bundle.putBoolean("isurl", false)

        findNavController().navigate(R.id.action_homeScreen_to_addScreen, bundle)
    }
    private val getAllNoteObserver = Observer<List<NoteEntity>> { it ->
        placeHolder(views,it)
        list.clear()
        list.addAll(it)
        adapter.submitList(it)
        notes_recycler_view.adapter = adapter
        adapter.setOnItemClickListener { note ->
            if (!note.password) {
                var bundle = Bundle()
                bundle.putSerializable("data", note)
                findNavController().navigate(R.id.action_homeScreen_to_editScreen, bundle)
            } else {
                var dialog = OnClickDialog()
                dialog.show(requireFragmentManager(), "")
                dialog.openNote {
                    dialog.dismiss()
                    var bundle = Bundle()
                    bundle.putSerializable("data", note)
                    findNavController().navigate(R.id.action_homeScreen_to_editScreen, bundle)
                }
            }
        }
    }

    private val openAddUrl = Observer<Unit> {
        if (dialogAddURL == null) {
            val builder = AlertDialog.Builder(requireContext())
            val view: View = LayoutInflater.from(requireContext()).inflate(
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
                    showSnackbar("Enter Valid Url", view)
                } else {
                    dialogAddURL!!.dismiss()
                    dialogAddURL=null
                    UIUtil.hideKeyboard(view.context, inputURL)
                    val bundle = Bundle()
                    bundle.putString("url", inputURL.text.toString().trim { it <= ' ' })
                    bundle.putBoolean("isurl", true)
                    findNavController().navigate(R.id.action_homeScreen_to_addScreen, bundle)
                }
            }
            view.findViewById<View>(R.id.dialog_cancel_btn).setOnClickListener { v: View? ->
                UIUtil.hideKeyboard(view.context, inputURL)
                dialogAddURL!!.dismiss()
                dialogAddURL=null
            }
        }
        dialogAddURL!!.setCancelable(false)
        dialogAddURL!!.show()
    }


    private fun eventClicks() {
        floating_action_add_notes_btn.setOnClickListener {
            viewModel.openAddNoteScreen()
        }
        input_search.addTextChangedListener {
            query = it.toString().lowercase()
            handler.postDelayed({
                query?.let {
                        filter(query)
                }
            }, 1300)
        }

        main_bottom_app_bar.setOnMenuItemClickListener {
            when (it.itemId) {

                R.id.menu_web_link -> {
                    viewModel.addUrl()
                }
                R.id.menu_voice -> {
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
            }
            true
        }
    }

    private fun setUpNavigationDrawer() {
        UIUtil.hideKeyboard(requireActivity())
        main_navigation_menu.bringToFront()
        main_navigation.setOnClickListener {
            UIUtil.hideKeyboard(requireActivity())
            if (main_drawer_layout.isDrawerVisible(GravityCompat.END))
                main_drawer_layout.closeDrawer(
                    GravityCompat.END
                ) else main_drawer_layout.openDrawer(GravityCompat.END)
        }
        main_navigation_menu.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.menu_add_note -> {
                    viewModel.openAddNoteScreen()
                    main_drawer_layout.closeDrawer(GravityCompat.END)
                }

                R.id.menu_lock_Set -> {
                    val appReference = AppReference(requireContext())
                    if (appReference.getToken().toString().trim().isEmpty()) {
                        val setPasswordDialog = SetPasswordDialog()
                        setPasswordDialog.show(requireFragmentManager(), "")
                        setPasswordDialog.savePassword {
                            setPasswordDialog.dismiss()
                            showSnackbar("Parol o`rnatildi !", requireView())
                        }
                        main_drawer_layout.closeDrawer(GravityCompat.END)

                    } else {
                        val setPasswordDialog = UpdatePasswordDialog()
                        setPasswordDialog.show(requireFragmentManager(), "")
                        setPasswordDialog.savePassword {
                            setPasswordDialog.dismiss()
                            showSnackbar("Parol o`rnatildi !", requireView())
                        }
                        main_drawer_layout.closeDrawer(GravityCompat.END)

                    }
                }
                R.id.menu_info_app -> {
                    val builder: Dialog =
                       Dialog(requireActivity())
                    val view: View = LayoutInflater.from(requireContext()).inflate(
                        com.azamovhudstc.noteappplaystore.R.layout.layout_about_note,
                        null
                    )
                    builder.setContentView(view)
                    if (builder!!.window != null) {
                        builder!!.window!!.setBackgroundDrawable(ColorDrawable(0))
                    }

                    view.start_delete.setOnClickListener {
                        builder?.cancel()
                        builder?.dismiss()
                    }

                    builder!!.setCancelable(false)
                    builder!!.show()
                    main_drawer_layout.closeDrawer(GravityCompat.END)
                }
                R.id.share_up -> {
                    val sharingIntent = Intent(Intent.ACTION_SEND)

                    sharingIntent.type = "text/plain"
                    val shareBody = "4 pic 1 word Game Sharing With"
                    val shareSubject =
                        "https://play.google.com/store/apps/details?id=${BuildConfig.APPLICATION_ID.toString()}"
                    sharingIntent.putExtra(Intent.EXTRA_TEXT, shareSubject)
                    sharingIntent.putExtra(Intent.EXTRA_SUBJECT, shareSubject)
                    startActivity(Intent.createChooser(sharingIntent, "Share using"))
                    main_drawer_layout.closeDrawer(GravityCompat.END)

                }
                R.id.rate_us->{
                    val url =
                        "https://play.google.com/store/apps/details?id=${BuildConfig.APPLICATION_ID.toString()}"
                    val i = Intent(Intent.ACTION_VIEW)
                    i.data = Uri.parse(url)
                    startActivity(i)
                    main_drawer_layout.closeDrawer(GravityCompat.END)
                }
                R.id.menu_app_theme->{
                    main_drawer_layout.closeDrawer(GravityCompat.END)
                    val dialog = Dialog(requireContext())
                    val view: View = LayoutInflater.from(requireContext())
                        .inflate(R.layout.exit_dialog, null, false)
                    dialog.setContentView(view)

                    view.findViewById<View>(R.id.button_exit_no).setOnClickListener { view1: View? ->
                        dialog.cancel()
                        dialog.dismiss()
                    }
                    view.findViewById<View>(R.id.button_exit_yes)
                        .setOnClickListener { view2: View? ->
                            dialog.cancel()
                            dialog.dismiss()
                            val a = Intent(Intent.ACTION_MAIN)
                            a.addCategory(Intent.CATEGORY_HOME)
                            a.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                            startActivity(a)
                        }
                    dialog.show()

                    dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                }
            }

            true
        }

        animateNavigationDrawer()

    }

    override fun onResume() {
        super.onResume()
        input_search.setText("")
    }

    private fun appRate() {
        AppRate.with(requireActivity())
            .setInstallDays(1)
            .setLaunchTimes(3)
            .setRemindInterval(1)
            .setShowLaterButton(true)
            .setShowNeverButton(false)
            .monitor()

        AppRate.showRateDialogIfMeetsConditions(requireActivity())
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_SPEECH_INPUT) {
            // on below line we are checking if result code is ok
            if (resultCode == RESULT_OK && data != null) {

                // in that case we are extracting the
                // data from our array list
                val res: ArrayList<String> =
                    data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS) as ArrayList<String>

                // on below line we are setting data
                // to our output text view.
                var bundle = Bundle()
                bundle.putString("dataVoice", Objects.requireNonNull(res)[0].toString())
                findNavController().navigate(R.id.action_homeScreen_to_addScreen, bundle)
            }
        }
    }

    private fun animateNavigationDrawer() {
        main_drawer_layout.addDrawerListener(object : SimpleDrawerListener() {
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
                val diffScaledOffset: Float = slideOffset * (1 - END_SCALE)
                val offsetScale = 1 - diffScaledOffset
                content_view.scaleX = offsetScale
                content_view.scaleY = offsetScale
                val xOffset = drawerView.width * slideOffset
                val xOffsetDiff: Float = content_view.width * diffScaledOffset / 2
                val xTranslation = xOffsetDiff - xOffset
                content_view.translationX = xTranslation
            }
        })
    }

}