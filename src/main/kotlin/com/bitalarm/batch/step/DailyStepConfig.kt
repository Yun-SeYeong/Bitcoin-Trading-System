package com.bitalarm.batch.step

import com.bitalarm.domain.MarketCode
import com.bitalarm.dto.slack.SlackMessage
import com.bitalarm.dto.request.BuyPlanRequest
import com.bitalarm.service.BuyPlanService
import com.bitalarm.service.MarketCodeService
import com.bitalarm.service.SlackService
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.batch.item.support.ListItemReader
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.PlatformTransactionManager
import java.time.LocalDate
import java.time.ZoneId

@Configuration
class DailyStepConfig(
    private val marketCodeService: MarketCodeService,
    private val buyPlanService: BuyPlanService,
    private val slackService: SlackService
) {

    @Bean("checkDayConditionStep")
    fun checkDayConditionStep(
        jobRepository: JobRepository,
        transactionManager: PlatformTransactionManager
    ): Step {
        return StepBuilder("checkDayConditionStep", jobRepository)
            .chunk<MarketCode, BuyPlanRequest?>(10, transactionManager)
            .reader(listItemReader())
            .processor {
                Thread.sleep(100)
                buyPlanService.analysis(it)
            }
            .writer { chunk ->
                chunk.map {
                    if (it.isBuy) {
                        slackService.sendMessage("Daily 매수 추천: ${it.market}")
                        buyPlanService.createBuyPlan(it)
                    }
                }
            }
            .build()
    }



    @Bean
    @StepScope
    fun listItemReader(): ListItemReader<MarketCode> {
        return ListItemReader(marketCodeService.getMarketCodeAll())
    }
}