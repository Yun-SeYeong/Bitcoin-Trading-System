package com.bitalarm.batch

import org.springframework.batch.core.Job
import org.springframework.batch.core.JobParameter
import org.springframework.batch.core.JobParameters
import org.springframework.batch.core.configuration.JobRegistry
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class BatchScheduler(
    private val jobLauncher: JobLauncher,
    private val jobRegistry: JobRegistry
) {

    @Scheduled(cron = "0 0 9 * * *")
    fun analysisAndBuyBatch() {
        jobLauncher.run(jobRegistry.getJob("analysisAndBuyJob"), getTimeJobParameter())
    }

    @Scheduled(cron = "0 59 8 * * *")
    fun sellBatch() {
        jobLauncher.run(jobRegistry.getJob("sellJob"), getTimeJobParameter())
    }

    @Scheduled(cron = "0 0 * * * *")
    fun checkBalanceBatch() {
        jobLauncher.run(jobRegistry.getJob("checkBalanceJob"), getTimeJobParameter())
    }

    private fun getTimeJobParameter() = JobParameters(
        mutableMapOf(
            "time" to JobParameter(LocalDateTime.now().toString(), String::class.java)
        ) as Map<String, JobParameter<String>>
    )
}