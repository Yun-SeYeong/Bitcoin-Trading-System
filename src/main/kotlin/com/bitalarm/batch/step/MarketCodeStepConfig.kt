package com.bitalarm.batch.step

import com.bitalarm.service.MarketCodeService
import com.bitalarm.service.UpbitService
import org.springframework.batch.core.Step
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.PlatformTransactionManager

@Configuration
class MarketCodeStepConfig(
    private val upbitService: UpbitService,
    private val marketCodeService: MarketCodeService
) {

    @Bean("saveMarketCodeStep")
    fun saveMarketCodeStep(
        jobRepository: JobRepository,
        transactionManager: PlatformTransactionManager
    ): Step {
        return StepBuilder("saveMarketCodeStep", jobRepository)
            .tasklet({ _, _ ->
                upbitService.getMarketAll(true)?.let {
                    marketCodeService.deleteAllMarketCode()
                    marketCodeService.createMarketCodeAll(
                        it.filter { marketCode -> marketCode.market.startsWith("KRW-") }
                    )
                }
                RepeatStatus.FINISHED
            }, transactionManager)
            .build()
    }
}