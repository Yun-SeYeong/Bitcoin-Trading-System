package com.bitalarm.batch

import com.bitalarm.service.SlackService
import org.springframework.batch.core.Job
import org.springframework.batch.core.JobExecution
import org.springframework.batch.core.JobExecutionListener
import org.springframework.batch.core.Step
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.launch.support.RunIdIncrementer
import org.springframework.batch.core.repository.JobRepository
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.time.LocalDate
import java.time.ZoneId


@Configuration
class JobConfiguration(
    private val slackService: SlackService
) {

    @Bean("analysisAndBuyJob")
    fun analysisAndBuyJob(
        @Qualifier("saveMarketCodeStep") saveMarketCodeStep: Step,
        @Qualifier("checkDayConditionStep") checkDayConditionStep: Step,
        @Qualifier("buyCoinStep") buyCoinStep: Step,
        jobRepository: JobRepository
    ): Job {
        return JobBuilder("analysisAndBuyJob", jobRepository)
            .listener(printSlackMessageListener(
                " ${LocalDate.now(ZoneId.of("Asia/Seoul"))} 매수 분석 시작 ",
                " ${LocalDate.now(ZoneId.of("Asia/Seoul"))} 매수 분석 종료 "
            ))
            .start(saveMarketCodeStep)
            .next(checkDayConditionStep)
            .next(buyCoinStep)
            .incrementer(RunIdIncrementer())
            .build()
    }

    @Bean("sellJob")
    fun sellJob(
        @Qualifier("sellCoinStep") sellCoinStep: Step,
        jobRepository: JobRepository
    ): Job {
        return JobBuilder("sellJob", jobRepository)
            .start(sellCoinStep)
            .incrementer(RunIdIncrementer())
            .build()
    }

    @Bean("checkBalanceJob")
    fun checkBalanceJob(
        @Qualifier("checkBalanceStep") checkBalanceStep: Step,
        jobRepository: JobRepository
    ): Job {
        return JobBuilder("checkBalanceJob", jobRepository)
            .listener(printSlackMessageListener(
                " 자   산   조   회 ",
                "==========="
            ))
            .start(checkBalanceStep)
            .incrementer(RunIdIncrementer())
            .build()
    }

    private fun printSlackMessageListener(
        startMessage: String,
        endMessage: String
    ) = object : JobExecutionListener {
        override fun beforeJob(jobExecution: JobExecution) {
            super.beforeJob(jobExecution)
            slackService.sendMessage(" ========$startMessage======== ")
        }

        override fun afterJob(jobExecution: JobExecution) {
            super.afterJob(jobExecution)
            slackService.sendMessage(" ========$endMessage======== ")
        }
    }
}