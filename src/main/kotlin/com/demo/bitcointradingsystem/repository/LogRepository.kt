package com.demo.bitcointradingsystem.repository

import com.demo.bitcointradingsystem.entity.Log
import org.springframework.data.jpa.repository.JpaRepository

interface LogRepository : JpaRepository<Log, Long> {

}