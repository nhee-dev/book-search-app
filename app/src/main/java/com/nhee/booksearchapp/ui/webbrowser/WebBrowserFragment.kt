package com.nhee.booksearchapp.ui.webbrowser

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.navigation.fragment.navArgs
import com.nhee.booksearchapp.R
import com.nhee.booksearchapp.databinding.FragmentWebBrowserBinding

class WebBrowserFragment : Fragment() {

    private lateinit var viewDataBinding : FragmentWebBrowserBinding

    val args: WebBrowserFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = FragmentWebBrowserBinding.inflate(inflater, container, false)
        return viewDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewDataBinding.wvBookInfo.apply {
            webViewClient = WebViewClient()

            settings.apply {
                javaScriptEnabled = true
                domStorageEnabled = true
            }
            loadUrl(args.link)
        }
    }
}