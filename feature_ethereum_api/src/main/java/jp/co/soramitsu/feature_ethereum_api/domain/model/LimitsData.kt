/**
* Copyright Soramitsu Co., Ltd. All Rights Reserved.
* SPDX-License-Identifier: GPL-3.0
*/

package jp.co.soramitsu.feature_ethereum_api.domain.model

import java.math.BigDecimal

data class LimitsData(
    val remainingLimit: BigDecimal,
    val totalLimit: BigDecimal,
    val timestamp: Long
)