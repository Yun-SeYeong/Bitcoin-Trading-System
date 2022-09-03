package com.demo.bitcointradingsystem.entity

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.EntityListeners
import javax.persistence.Id

@Entity
@EntityListeners(AuditingEntityListener::class)
class MarketCode(
        market: String,
        koreanName: String,
        englishName: String,
        marketWarning: String?
) {
        @Id
        var market: String = market
                protected set
        var koreanName: String = koreanName
                protected set
        var englishName: String = englishName
                protected set
        var marketWarning: String? = marketWarning
                protected set

        @CreatedDate
        var createdDate: LocalDateTime? = null

        @LastModifiedDate
        var lastModifiedDate: LocalDateTime? = null

        override fun equals(other: Any?): Boolean {
                if (this === other) return true
                if (javaClass != other?.javaClass) return false

                other as MarketCode

                if (market != other.market) return false

                return true
        }

        override fun hashCode(): Int {
                return market.hashCode()
        }

        override fun toString(): String {
                return "MarketCode(market='$market', koreanName='$koreanName', englishName='$englishName', marketWarning=$marketWarning)"
        }
}
