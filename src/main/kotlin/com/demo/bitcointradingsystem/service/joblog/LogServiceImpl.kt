package com.demo.bitcointradingsystem.service.joblog

import com.demo.bitcointradingsystem.entity.JobStatus
import com.demo.bitcointradingsystem.entity.Log
import com.demo.bitcointradingsystem.repository.LogRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.util.*
import javax.persistence.EntityManager

@Service
@Transactional
class LogServiceImpl(
        private val logRepository: LogRepository,
        private val em: EntityManager
) : LogService {

    override fun createLog(msg: String): Log {
        val save = logRepository.save(Log(UUID.randomUUID().toString(), LocalDateTime.now(), null, JobStatus.PROGRESS, msg))
        em.flush()
        return save
    }

    override fun successLog(id: Long, msg: String): Log {
        val apply = logRepository.findById(id)
                .orElseThrow { Exception("Not exist") }
                .apply { success(msg) }
        em.flush()
        return apply
    }

    override fun failLog(id: Long, msg: String): Log {
        val apply = logRepository.findById(id)
                .orElseThrow { Exception("Not exist") }
                .apply { fail(msg) }
        em.flush()
        return apply
    }

    override fun getLog(pageable: Pageable): Page<Log> {
        return logRepository.findAll(pageable)
    }
}