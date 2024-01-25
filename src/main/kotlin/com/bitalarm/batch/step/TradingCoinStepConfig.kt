package com.bitalarm.batch.step

import com.bitalarm.domain.BuyPlan
import com.bitalarm.dto.upbit.BalanceResponse
import com.bitalarm.dto.upbit.OrderResponse
import com.bitalarm.dto.upbit.TickerResponse
import com.bitalarm.service.BuyPlanService
import com.bitalarm.service.SlackService
import com.bitalarm.service.UpbitService
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.batch.item.support.ListItemReader
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.PlatformTransactionManager

@Configuration
class TradingCoinStepConfig(
    private val upbitService: UpbitService,
    private val slackService: SlackService,
    private val buyPlanService: BuyPlanService
) {

    @Bean("buyCoinStep")
    fun buyCoinStep(
        jobRepository: JobRepository,
        transactionManager: PlatformTransactionManager
    ): Step {
        return StepBuilder("buyCoinStep", jobRepository)
            .chunk<BuyPlan, OrderResponse?>(10, transactionManager)
            .reader(buyPlanReader())
            .processor {
                upbitService.postBuyOrder(
                    it.market,
                    "10000"
                )
            }
            .writer {
                it.map { orderResponse ->
                    slackService.sendMessage("매수 완료: ${orderResponse.market}")
                }
            }
            .exceptionHandler { _, _ ->
                slackService.sendMessage("매수 실패")
            }
            .build()
    }

    @Bean("sellCoinStep")
    fun sellCoinStep(
        jobRepository: JobRepository,
        transactionManager: PlatformTransactionManager
    ): Step {
        return StepBuilder("sellCoinStep", jobRepository)
            .chunk<BalanceResponse, OrderResponse?>(10, transactionManager)
            .reader(balanceReader())
            .processor {
                upbitService.postSellOrder(
                    "KRW-${it.currency}",
                    it.balance
                )
            }
            .writer {
                it.map { orderResponse ->
                    slackService.sendMessage("매도 완료: ${orderResponse.market}")
                }
            }
            .build()
    }

    @Bean("checkBalanceStep")
    fun checkBalanceStep(
        jobRepository: JobRepository,
        transactionManager: PlatformTransactionManager
    ): Step {
        return StepBuilder("checkBalanceStep", jobRepository)
            .tasklet(
                { _, _ ->
                    upbitService.getAccounts()?.filter {
                        it.avgBuyPriceModified || (it.balance.toDouble() * it.avgBuyPrice.toDouble() > 10)
                    }?.map {
                        Pair(
                            it,
                            if (!it.avgBuyPriceModified)
                                upbitService.getTicker("KRW-${it.currency}")
                            else null
                        )
                    }?.joinToString("-----------------------------------------\n") {
                        "자산명: ${it.first.currency}\n" +
                                createBalanceMessage(it)
                    }?.let {
                        slackService.sendMessage(it)
                    }
                    RepeatStatus.FINISHED
                }, transactionManager
            ).build()
    }

    private fun createBalanceMessage(it: Pair<BalanceResponse, List<TickerResponse>?>): String {
        val balance = it.first.balance.toDouble()
        val avgBuyPrice = it.first.avgBuyPrice.toDouble()
        val tradePrice = it.second?.getOrNull(0)?.tradePrice ?: 0f.toDouble()
        val price = balance * avgBuyPrice

        return if (it.first.avgBuyPriceModified) {
            "가격: ${balance.toRoundFormat()}\n"
        } else {
            """
            매입금액: ${price.toRoundFormat()}
            평가금액: ${(balance * tradePrice).toRoundFormat()}
            평균매수가: ${avgBuyPrice.toRoundFormat()}
            현재가: ${tradePrice.toRoundFormat()} (${calculatePercentageString(avgBuyPrice, tradePrice)})
            개수: ${balance.toRoundFormat()}
            """.trimIndent()
        }
    }

    private fun Double.toRoundFormat() =  String.format("%.2f", this)

    private fun calculatePercentageString(
        current: Double,
        tradePrice: Double
    ) = String.format(
        "%s%.2f%%",
        if ((1 - (current / tradePrice)) * 100 > 0) "+" else "",
        (1 - (current / tradePrice)) * 100
    )

    @Bean("buyPlanReader")
    @StepScope
    fun buyPlanReader(): ListItemReader<BuyPlan> {
        return ListItemReader(buyPlanService.getBuyPlan())
    }

    @Bean("balanceReader")
    @StepScope
    fun balanceReader(): ListItemReader<BalanceResponse> {
        return ListItemReader(
            upbitService.getAccounts()?.filter {
                !it.avgBuyPriceModified && (it.balance.toDouble() * it.avgBuyPrice.toDouble() > 5000)
            } ?: listOf()
        )
    }
}