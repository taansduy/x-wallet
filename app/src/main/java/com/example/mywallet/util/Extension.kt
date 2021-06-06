package com.example.mywallet.util

import android.app.Activity
import android.text.SpannableString
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import com.bumptech.glide.Glide
import com.example.mywallet.R
import com.example.mywallet.model.Result
import com.google.android.material.appbar.AppBarLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

fun <T:Any> CoroutineScope.perform(source: MutableLiveData<Result<T>>, action: suspend () -> T){
    source.postValue(Result.loading())
    launch(Dispatchers.IO) {
        val result: Result<T> = try {
            Result.success(action())
        } catch (e: Exception) {
            Log.e(javaClass.simpleName, "Exception during perform $action", e)
            Result.error(e)
        }
        source.postValue(result)
    }
}


fun ImageView.loadAvatar(url: String?) {
    Glide.with(this)
        .load(url)
        .optionalFitCenter()
        .circleCrop()
        .placeholder(R.drawable.ic_currency_placeholder)
        .error(R.drawable.ic_currency_placeholder)
        .into(this)
}

fun View.setAutoHideKeyboard(activity: Activity) {
    // Set up touch listener for non-text box views to hide keyboard.
    if (this !is EditText) {
        setOnTouchListener { _, _ ->
            activity.hideSoftKeyboard()
            false
        }
    }

    //If a layout container, iterate over children and seed recursion.
    if (this is ViewGroup) {
        for (i in 0 until childCount) {
            val innerView = getChildAt(i)
            innerView.setAutoHideKeyboard(activity)
        }
    }
}

fun Activity.hideSoftKeyboard() {
    val inputMethodManager =
        getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as? InputMethodManager

    inputMethodManager?.hideSoftInputFromWindow(
        currentFocus?.windowToken, 0
    )
}

fun Activity.showToast(message: String? = "") {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

//fun Activity.showErrorToast(message: String? = "") {
//    runOnUiThread {
//        val viewToast =
//            layoutInflater.inflate(R.layout.layout_toast, findViewById(R.id.clContentToast))
//
//        viewToast.findViewById<TextView>(R.id.tvMessage).text = message
//
//        val appBarLayout = findViewById<AppBarLayout>(R.id.appBarLayout)
//
//        val toast = Toast(this).apply {
//            duration = Toast.LENGTH_LONG
//            view = viewToast
//            setGravity(Gravity.TOP, xOffset, appBarLayout?.measuredHeight ?: (yOffset - 100))
//        }
//
//        toast.show()
//    }
//}

fun Activity.handleError(
    error: Result<Any>?,
    callback: (() -> Unit) = {}
) {
//    if (error == null) return
//    AppEvent.notifyClosePopUp()
//    val token = MainApplication.instance.currentUser?.token
//    when {
//        !error.message.toLowerCase(Locale.US).contains("oauth token invalid")
//                && (error.code == 1016 || (error.code == 1003 && !token.isNullOrEmpty())) -> {
//
//            (this as? BaseActivity<*>)?.blockPopup = true
//            (this as? BaseActivity<*>)?.showPopup<PopupForceLogoutBinding>(
//                PopUp(
//                    popupId = R.layout.popup_force_logout,
//                    isCancelable = false
//                ) { viewBinding, _, dialog ->
//
//                    if (error.code == 1016) {
//                        viewBinding?.tvTitle?.text = getString(R.string.user_blocked)
//                        viewBinding?.tvDescription?.text = getString(R.string.email_contact)
//                    }
//
//                    viewBinding?.btnOk?.setOnClickListener {
//                        dialog?.dismiss()
//                        blockPopup = false
//                        if (MainApplication.instance.currentUser?.token.isNullOrEmpty()) return@setOnClickListener
//                        showPopup<ViewDataBinding>(PopUp())
//                        authViewModel.signOut()
//                    }
//                })
//        }
//
//        else -> {
//            showErrorToast(error.message)
//            callback.invoke()
//        }
//    }
}