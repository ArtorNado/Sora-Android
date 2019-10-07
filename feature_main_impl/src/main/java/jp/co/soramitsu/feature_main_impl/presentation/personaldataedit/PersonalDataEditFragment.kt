/**
* Copyright Soramitsu Co., Ltd. All Rights Reserved.
* SPDX-License-Identifier: GPL-3.0
*/

package jp.co.soramitsu.feature_main_impl.presentation.personaldataedit

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.InputFilter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import jp.co.soramitsu.common.base.BaseFragment
import jp.co.soramitsu.common.base.SoraProgressDialog
import jp.co.soramitsu.common.presentation.view.hideSoftKeyboard
import jp.co.soramitsu.common.util.Const
import jp.co.soramitsu.common.util.EventObserver
import jp.co.soramitsu.common.util.KeyboardHelper
import jp.co.soramitsu.common.util.ext.isValidNameChar
import jp.co.soramitsu.core_di.holder.FeatureUtils
import jp.co.soramitsu.feature_main_api.di.MainFeatureApi
import jp.co.soramitsu.feature_main_impl.R
import jp.co.soramitsu.feature_main_impl.di.MainFeatureComponent
import jp.co.soramitsu.feature_main_impl.presentation.MainActivity
import jp.co.soramitsu.feature_main_impl.presentation.MainRouter
import kotlinx.android.synthetic.main.fragment_personal_data_edit.firstNameEt
import kotlinx.android.synthetic.main.fragment_personal_data_edit.lastNameEt
import kotlinx.android.synthetic.main.fragment_personal_data_edit.nextBtn
import kotlinx.android.synthetic.main.fragment_personal_data_edit.phoneNumberEt
import kotlinx.android.synthetic.main.fragment_personal_data_edit.toolbar

@SuppressLint("CheckResult")
class PersonalDataEditFragment : BaseFragment<PersonalDataEditViewModel>() {

    private lateinit var progressDialog: SoraProgressDialog

    private var keyboardHelper: KeyboardHelper? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_personal_data_edit, container, false)
    }

    override fun inject() {
        FeatureUtils.getFeature<MainFeatureComponent>(context!!, MainFeatureApi::class.java)
            .personalComponentBuilder()
            .withFragment(this)
            .withRouter(activity as MainRouter)
            .build()
            .inject(this)
    }

    override fun initViews() {
        toolbar.setTitle(getString(R.string.personal_info_title))
        toolbar.setHomeButtonListener { viewModel.backPressed() }

        (activity as MainActivity).hideBottomView()

        nextBtn.setText(R.string.save)

        nextBtn.setOnClickListener {
            viewModel.saveData(firstNameEt.text!!.toString(), lastNameEt.text!!.toString())
        }

        val inputFilter = InputFilter { source, start, end, _, _, _ ->

            for (i in start until end) {
                if (!source[i].isValidNameChar()) {
                    return@InputFilter source.substring(0, i)
                }
            }
            null
        }

        firstNameEt.filters = arrayOf(inputFilter, InputFilter.LengthFilter(Const.NAME_MAX_LENGTH))
        lastNameEt.filters = arrayOf(inputFilter, InputFilter.LengthFilter(Const.NAME_MAX_LENGTH))

        progressDialog = SoraProgressDialog(activity!!)
    }

    override fun subscribe(viewModel: PersonalDataEditViewModel) {
        observe(viewModel.userLiveData, Observer { user ->
            firstNameEt.setText(user.firstName)
            lastNameEt.setText(user.lastName)
            phoneNumberEt.setText(user.phone)
        })

        observe(viewModel.emptyFirstNameLiveData, EventObserver {
            Toast.makeText(activity!!, R.string.first_name_is_empty, Toast.LENGTH_SHORT).show()
        })

        observe(viewModel.incorrectFirstNameLiveData, EventObserver {
            Toast.makeText(activity!!, R.string.first_name_hyphen_error, Toast.LENGTH_SHORT).show()
        })

        observe(viewModel.emptyLastNameLiveData, EventObserver {
            Toast.makeText(activity!!, R.string.last_name_is_empty, Toast.LENGTH_SHORT).show()
        })

        observe(viewModel.incorrectLastNameLiveData, EventObserver {
            Toast.makeText(activity!!, R.string.last_name_hyphen_error, Toast.LENGTH_SHORT).show()
        })

        observe(viewModel.getProgressVisibility(), Observer {
            if (it) progressDialog.show() else progressDialog.dismiss()
        })

        viewModel.getUserData(false)
    }

    override fun onResume() {
        super.onResume()
        keyboardHelper = KeyboardHelper(view!!)
    }

    override fun onPause() {
        super.onPause()
        if (keyboardHelper != null && keyboardHelper!!.isKeyboardShowing) {
            hideSoftKeyboard(activity)
        }
        keyboardHelper?.release()
    }
}