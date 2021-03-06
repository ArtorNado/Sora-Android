/**
* Copyright Soramitsu Co., Ltd. All Rights Reserved.
* SPDX-License-Identifier: GPL-3.0
*/

package jp.co.soramitsu.feature_ethereum_impl.util

import jp.co.soramitsu.feature_ethereum_api.domain.model.EthereumCredentials
import jp.co.soramitsu.feature_ethereum_impl.data.mappers.EthereumCredentialsMapper
import jp.co.soramitsu.feature_ethereum_impl.data.network.ERC20ContractApi
import jp.co.soramitsu.feature_ethereum_impl.data.network.SmartContractApi
import org.web3j.protocol.Web3j
import java.math.BigInteger
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ContractsApiProvider @Inject constructor(
    private val web3j: Web3j,
    private val ethereumCredentialsMapper: EthereumCredentialsMapper
) {

    companion object {
        const val DEFAULT_GAS_LIMIT_TRANSFER = 200000
        const val DEFAULT_GAS_LIMIT_WITHDRAW = 350000
    }

    private var smartContractApi: SmartContractApi? = null
    private var erc20ContractApi: ERC20ContractApi? = null
    private val gasProvider = GasProvider(BigInteger.ZERO, BigInteger.ZERO)

    fun getSmartContractApi(ethCredentials: EthereumCredentials): SmartContractApi {
        if (smartContractApi == null) {
            val credentials = ethereumCredentialsMapper.getCredentials(ethCredentials.privateKey)
            smartContractApi = SmartContractApi(web3j, credentials, gasProvider)
        }
        return smartContractApi!!
    }

    fun getErc20ContractApi(ethCredentials: EthereumCredentials, xorTokenAddress: String): ERC20ContractApi {
        if (erc20ContractApi == null) {
            val credentials = ethereumCredentialsMapper.getCredentials(ethCredentials.privateKey)
            erc20ContractApi = ERC20ContractApi(web3j, credentials, gasProvider, xorTokenAddress)
        }
        return erc20ContractApi!!
    }

    fun fetchGasPrice(): BigInteger {
        val gasPrice = web3j.ethGasPrice().send().gasPrice
        gasProvider.price = gasPrice
        return gasPrice
    }

    fun getGasLimit(): BigInteger {
        return gasProvider.estimatedGas
    }

    fun getGasPrice(): BigInteger {
        return gasProvider.price
    }

    fun setGasLimit(gasLimit: BigInteger) {
        gasProvider.estimatedGas = gasLimit
    }

    fun setGasPrice(gasPrice: BigInteger) {
        gasProvider.price = gasPrice
    }
}