package com.demo.bitcointradingsystem.service.joblog

import com.demo.bitcointradingsystem.entity.system.Log
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface LogService {
    fun createLog(msg: String): Log

    fun successLog(id: Long, msg: String): Log

    fun failLog(id: Long, msg: String): Log

    fun getLog(pageable: Pageable): Page<Log>
}