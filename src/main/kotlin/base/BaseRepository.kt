package com.fathersprophets.backend.base

open class BaseRepository<Dto : Any, CreateDto : Any, UpdateDto : Any, Dao : CrudDao<Dto, CreateDto, UpdateDto>>(
    protected val dao: Dao,
    vararg extraDaos: Any
) {
    protected val extraDaos: List<Any> = extraDaos.toList()

    open fun getAll(): List<Dto> = dao.getAll()
    open fun getById(id: Int): Dto? = dao.getById(id)
    open fun create(dto: CreateDto): Dto? = dao.create(dto)
    open fun update(id: Int, dto: UpdateDto): Dto? = dao.update(id, dto)
    open fun delete(id: Int): Boolean = dao.delete(id)
}