package org.rekhta.data.entities

interface RekhtaEntity

interface ContentEntity : RekhtaEntity {
}

interface Entry : RekhtaEntity {
}

interface PaginatedEntry : Entry {
    val page: Int
}
