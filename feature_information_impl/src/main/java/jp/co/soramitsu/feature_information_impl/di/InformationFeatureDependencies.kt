/**
* Copyright Soramitsu Co., Ltd. All Rights Reserved.
* SPDX-License-Identifier: GPL-3.0
*/

package jp.co.soramitsu.feature_information_impl.di

import android.content.Context

import jp.co.soramitsu.common.util.PrefsUtil
import jp.co.soramitsu.core_network_api.NetworkApiCreator
import jp.co.soramitsu.core_network_api.data.auth.AuthHolder

interface InformationFeatureDependencies {

    fun context(): Context

    fun prefsUtil(): PrefsUtil

    fun authHolder(): AuthHolder

    fun networkApiCreator(): NetworkApiCreator
}