package com.frogobox.admobsample.base

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.frogobox.admobsample.R
import com.frogobox.admob.ui.FrogoAdmobActivity
import com.google.gson.Gson

/**
 * Created by Faisal Amir
 * FrogoBox Inc License
 * =========================================
 * ImplementationAdmob
 * Copyright (C) 27/11/2019.
 * All rights reserved
 * -----------------------------------------
 * Name     : Muhammad Faisal Amir
 * E-mail   : faisalamircs@gmail.com
 * Github   : github.com/amirisback
 * LinkedIn : linkedin.com/in/faisalamircs
 * -----------------------------------------
 * FrogoBox Software Industries
 * com.frogobox.admobhelper.base
 *
 */
abstract class BaseActivity<VB : ViewBinding> : FrogoAdmobActivity() {

    protected lateinit var binding: VB

    abstract fun setupViewBinding() : VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = setupViewBinding()
        setContentView(binding.root)
        setupAdmob()
    }

    private fun setupAdmob(){
        setPublisher()
        setBanner()
        setInterstitial()
        setRewarded()
        setRewardedInterstitial()
    }

    private fun setPublisher() {
        setupAdsPublisher(getString(R.string.admob_publisher_id))
    }

    private fun setBanner() {
        setupAdsBanner(getString(R.string.admob_banner))
    }

    private fun setInterstitial() {
        setupAdsInterstitial(getString(R.string.admob_interstitial))
    }

    private fun setRewarded() {
        setupAdsRewarded(getString(R.string.admob_rewarded))
    }

    private fun setRewardedInterstitial() {
        setupAdsRewardedInterstitial(getString(R.string.admob_rewarded_interstitial))
    }

    protected fun setupCustomTitleToolbar(title: Int) {
        supportActionBar?.setTitle(title)
    }

    protected fun setupNoLimitStatBar() {
        val windows = window // in Activity's onCreate() for instance
        windows.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
    }

    protected fun setupChildFragment(frameId: Int, fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(frameId, fragment)
            commit()
        }
    }

    protected inline fun <reified ClassActivity> baseStartActivity() {
        this.startActivity(Intent(this, ClassActivity::class.java))
    }

    protected inline fun <reified ClassActivity, Model> baseStartActivity(
        extraKey: String,
        data: Model
    ) {
        val intent = Intent(this, ClassActivity::class.java)
        val extraData = Gson().toJson(data)
        intent.putExtra(extraKey, extraData)
        this.startActivity(intent)
    }

    protected inline fun <reified Model> baseGetExtraData(extraKey: String): Model {
        val extraIntent = intent.getStringExtra(extraKey)
        val extraData = Gson().fromJson(extraIntent, Model::class.java)
        return extraData
    }

    protected fun checkExtra(extraKey: String): Boolean {
        return intent?.hasExtra(extraKey)!!
    }

    protected fun <Model, VB: ViewBinding> baseFragmentNewInstance(
        fragment: BaseFragment<VB>,
        argumentKey: String,
        extraDataResult: Model
    ) {
        fragment.baseNewInstance(argumentKey, extraDataResult)
    }


    protected fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    protected fun setupDetailActivity(title: String) {
        setTitle(title)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    protected fun setupEventEmptyView(view: View, isEmpty: Boolean) {
        if (isEmpty) {
            view.visibility = View.VISIBLE
        } else {
            view.visibility = View.GONE
        }
    }

    protected fun setupEventProgressView(view: View, progress: Boolean) {
        if (progress) {
            view.visibility = View.VISIBLE
        } else {
            view.visibility = View.GONE
        }
    }

}