package com.demo.bitcointradingsystem.entity

import javax.persistence.*

@Entity
class Scenario(
        name: String,
        description: String
) {
    @Id
    @GeneratedValue
    @Column(name = "scenario_id")
    var id: Long? = null
        protected set

    var name: String = name
        protected set

    var description: String = description
        protected set

    @OneToMany(mappedBy = "scenario", cascade = [CascadeType.ALL])
    var rules: MutableList<Rule> = mutableListOf()
        protected set

    companion object {
        fun createScenario(name: String, description: String, rules: Array<Rule>) : Scenario {
            val scenario = Scenario(name, description)
            scenario.rules.addAll(rules)
            return scenario
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Scenario

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }

    override fun toString(): String {
        return "Scenario(id=$id, name='$name', description='$description')"
    }

}