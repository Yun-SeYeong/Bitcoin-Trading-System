package com.demo.bitcointradingsystem.entity.system

import java.time.LocalDateTime
import javax.persistence.*

@Entity
class Log(
    jobId: String,
    startDateTime: LocalDateTime,
    endDateTime: LocalDateTime?,
    status: JobStatus,
    msg: String
){
    @Id
    @GeneratedValue
    @Column(name = "log_id")
    var id: Long? = null
        protected set
    var jobId: String = jobId
        protected set
    var startDateTime: LocalDateTime = startDateTime
        protected set
    var endDateTime: LocalDateTime? = endDateTime
        protected set
    @Enumerated(EnumType.STRING)
    var status: JobStatus = status
        protected set
    var msg: String = msg
        protected set

    fun success(msg: String) {
        this.endDateTime = LocalDateTime.now()
        this.status = JobStatus.SUCCESS
        this.msg += "${if (this.msg.isNotEmpty()) "\n" else ""}$msg"
    }

    fun fail(msg: String) {
        this.endDateTime = LocalDateTime.now()
        this.status = JobStatus.FAIL
        this.msg += "${if (this.msg.isNotEmpty()) "\n" else ""}$msg"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Log

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }

    override fun toString(): String {
        return "Log(id=$id, jobId='$jobId', startDateTime=$startDateTime, endDateTime=$endDateTime, status=$status, msg='$msg')"
    }


}