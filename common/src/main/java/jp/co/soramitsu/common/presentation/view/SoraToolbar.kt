/**
* Copyright Soramitsu Co., Ltd. All Rights Reserved.
* SPDX-License-Identifier: GPL-3.0
*/

package jp.co.soramitsu.common.presentation.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.Toolbar
import jp.co.soramitsu.common.R
import jp.co.soramitsu.common.util.ext.gone
import jp.co.soramitsu.common.util.ext.show
import kotlinx.android.synthetic.main.tool_bar.view.backImg
import kotlinx.android.synthetic.main.tool_bar.view.titleTv
import kotlinx.android.synthetic.main.tool_bar.view.votesTv
import kotlinx.android.synthetic.main.tool_bar.view.shareImg

class SoraToolbar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : Toolbar(context, attrs, defStyleAttr) {

    init {
        View.inflate(context, R.layout.tool_bar, this)
    }

    fun setHomeButtonListener(listener: (View) -> Unit) {
        backImg.setOnClickListener(listener)
    }

    fun setShareButtonListener(listener: (View) -> Unit) {
        shareImg.setOnClickListener(listener)
    }

    fun setVotes(votes: String) {
        votesTv.text = votes
    }

    fun showVotes() {
        votesTv.show()
    }

    fun setTitle(title: String) {
        titleTv.text = title
    }

    fun showHomeButton() {
        backImg.show()
    }

    fun hideHomeButton() {
        backImg.gone()
    }

    fun showShareButton() {
        shareImg.show()
    }

    fun hideShareButton() {
        shareImg.gone()
    }
}