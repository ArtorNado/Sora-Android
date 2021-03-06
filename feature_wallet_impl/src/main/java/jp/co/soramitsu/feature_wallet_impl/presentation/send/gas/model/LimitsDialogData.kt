/**
* Copyright Soramitsu Co., Ltd. All Rights Reserved.
* SPDX-License-Identifier: GPL-3.0
*/

package jp.co.soramitsu.feature_wallet_impl.presentation.send.gas.model

import java.math.BigDecimal

data class LimitsDialogData(
    val availableLimit: BigDecimal,
    val resetTimeFormatted: String,
    val totalLimit: BigDecimal
)