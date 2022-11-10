package com.demo.bitcointradingsystem.service.joblog

import com.demo.bitcointradingsystem.entity.system.JobStatus
import com.demo.bitcointradingsystem.repository.LogRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import javax.annotation.PostConstruct
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@SpringBootTest
@Transactional
class LogServiceImplTest {

    @Autowired
    private lateinit var logService: LogService

    @Autowired
    private lateinit var logRepository: LogRepository

    @PersistenceContext
    private lateinit var em: EntityManager

    @PostConstruct
    fun init() {
        logRepository.deleteAll()
    }

    @Test
    fun createLogTest() {
        // given
        val msg = "로그생성"

        // when
        val createLog = logService.createLog(msg)

        em.flush()
        em.clear()

        // then
        assertThat(createLog.startDateTime).isNotNull
        assertThat(createLog.jobId).isNotEmpty
        assertThat(createLog.endDateTime).isNull()
        assertThat(createLog.msg).isEqualTo(msg)
        assertThat(createLog.status).isEqualTo(JobStatus.PROGRESS)
    }

    @Test
    fun successLogTest() {
        // given
        val msg = "로그생성"
        val successMsg = "작업성공"
        val createLog = logService.createLog(msg)

        // when
        logService.successLog(createLog.id!!, successMsg)

        em.flush()
        em.clear()

        val findLog = logRepository.findById(createLog.id!!)
                .orElseThrow { Exception() }


        // then
        assertThat(findLog.jobId).isNotEmpty
        assertThat(findLog.startDateTime).isNotNull
        assertThat(findLog.endDateTime).isNotNull
        assertThat(findLog.status).isEqualTo(JobStatus.SUCCESS)
        assertThat(findLog.msg).isEqualTo("$msg\n$successMsg")
    }

    @Test
    fun failLogTest() {
        // given
        val msg = "로그생성"
        val successMsg = "작업실패"
        val createLog = logService.createLog(msg)

        // when
        logService.failLog(createLog.id!!, successMsg)

        em.flush()
        em.clear()

        val findLog = logRepository.findById(createLog.id!!)
                .orElseThrow { Exception() }


        // then
        assertThat(findLog.jobId).isNotEmpty
        assertThat(findLog.startDateTime).isNotNull
        assertThat(findLog.endDateTime).isNotNull
        assertThat(findLog.status).isEqualTo(JobStatus.FAIL)
        assertThat(findLog.msg).isEqualTo("$msg\n$successMsg")
    }
}