/**
* Copyright Soramitsu Co., Ltd. All Rights Reserved.
* SPDX-License-Identifier: GPL-3.0
*/

package jp.co.soramitsu.feature_votable_api.di

import jp.co.soramitsu.feature_votable_api.domain.interfaces.ProjectRepository
import jp.co.soramitsu.feature_votable_api.domain.interfaces.ReferendumRepository
import jp.co.soramitsu.feature_votable_api.domain.interfaces.VotesDataSource

interface VotableFeatureApi {
    fun projectRepository(): ProjectRepository

    fun referendumRepository(): ReferendumRepository

    fun votesDataSource(): VotesDataSource
}