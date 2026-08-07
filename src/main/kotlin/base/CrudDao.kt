package com.fathersprophets.backend.base

interface CrudDao<Dto : Any, CreateDto : Any, UpdateDto : Any> {
    fun getAll(): List<Dto>
    fun getById(id: Int): Dto?
    fun create(dto: CreateDto): Dto?
    fun update(id: Int, dto: UpdateDto): Dto?
    fun delete(id: Int): Boolean
}