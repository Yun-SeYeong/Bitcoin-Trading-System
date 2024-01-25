package com.bitalarm.domain

import com.blog.domain.base.PrimaryKeyEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EntityListeners
import org.springframework.data.jpa.domain.support.AuditingEntityListener

@Entity
class MarketCode(
    market: String,
    koreanName: String,
    englishName: String,
    marketWarning: String?
) : PrimaryKeyEntity() {
    @Column(unique = true)
    var market: String = market
        protected set
    @Column(nullable = false)
    var koreanName: String = koreanName
        protected set
    @Column(nullable = false)
    var englishName: String = englishName
        protected set
    var marketWarning: String? = marketWarning
        protected set
}
