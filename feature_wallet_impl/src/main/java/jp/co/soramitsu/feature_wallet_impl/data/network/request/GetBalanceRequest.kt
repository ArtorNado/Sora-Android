/**
* Copyright Soramitsu Co., Ltd. All Rights Reserved.
* SPDX-License-Identifier: GPL-3.0
*/

package jp.co.soramitsu.feature_wallet_impl.data.network.request

import com.google.gson.annotations.SerializedName

data class GetBalanceRequest(
    @SerializedName("assets") val assets: Array<String>,
    @SerializedName("query") val query: String
)