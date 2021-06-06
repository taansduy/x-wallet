package com.example.mywallet.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.mywallet.util.addToolbar
import com.example.mywallet.util.setAutoHideKeyboard

abstract class BaseFragment<ViewBinding: ViewDataBinding> :Fragment(){
    lateinit var viewBinding: ViewBinding
    lateinit var navController: NavController

    @get:LayoutRes
    abstract val layoutId: Int
    @get:LayoutRes
    open val toolbarLayout: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindViewModelOnce()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()
        viewBinding.apply {
            lifecycleOwner = viewLifecycleOwner
            executePendingBindings()
        }
        view.setAutoHideKeyboard(requireActivity())
        setUpToolbar()
        bindView()
        bindViewModel()
    }

    private fun setUpToolbar() {
        if (toolbarLayout == -1) return
        (activity as? BaseActivity<*>)?.addToolbar(
            toolbarLayoutId = toolbarLayout,
            viewGroup = (activity as? BaseActivity<*>)?.viewBinding?.root as? ViewGroup,
            toolbarCallBack = { curActivity, toolbar ->
                toolbarFunc(curActivity, toolbar)
            })
    }
    open fun toolbarFunc(curActivity: AppCompatActivity?, toolbar: Toolbar?) {}
    open fun bindViewModel() {}
    open fun bindView() {}
    open fun bindViewModelOnce() {}
}