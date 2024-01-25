package com.bitalarm.service

import com.bitalarm.domain.MarketCode
import com.bitalarm.repository.MarketCodeRepository
import com.bitalarm.dto.upbit.MarketCodeResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class MarketCodeService(
    private val marketCodeRepository: MarketCodeRepository
) {

    fun getMarketCodeAll(): List<MarketCode> {
        return marketCodeRepository.findAll().toList()
    }

    fun createMarketCode(marketCodeResponse: MarketCodeResponse) {
        marketCodeRepository.save(MarketCode(
            market = marketCodeResponse.market,
            koreanName = marketCodeResponse.koreanName,
            englishName = marketCodeResponse.englishName,
            marketWarning = marketCodeResponse.marketWarning
        ))
    }

    fun createMarketCodeAll(marketCodeList: List<MarketCodeResponse>) {
        marketCodeList.map {
            createMarketCode(it)
        }
    }

    fun deleteAllMarketCode() {
        marketCodeRepository.deleteAll()
    }
}