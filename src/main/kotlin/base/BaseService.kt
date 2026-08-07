package com.fathersprophets.backend.base

open class BaseService<Dto : Any, CreateDto : Any, UpdateDto : Any, Repo : BaseRepository<Dto, CreateDto, UpdateDto, *>>(
    protected val repository: Repo
) {
    protected open fun validateCreate(dto: CreateDto) {}
    protected open fun validateUpdate(id: Int, dto: UpdateDto) {}

    open fun getAll(): List<Dto> = repository.getAll()
    open fun getById(id: Int): Dto? = repository.getById(id)

    open fun create(dto: CreateDto): Dto? {
        validateCreate(dto)
        return repository.create(dto)
    }

    open fun update(id: Int, dto: UpdateDto): Dto? {
        validateUpdate(id, dto)
        return repository.update(id, dto)
    }

    open fun delete(id: Int): Boolean = repository.delete(id)
}