package com.fathersprophets.backend.database.repository.anonymouschatmessage

import com.fathersprophets.backend.database.dao.AnonymousChatDao
import com.fathersprophets.backend.database.dao.AnonymousChatMessageDao
import com.fathersprophets.backend.database.dao.UserDao
import com.fathersprophets.backend.database.tables.UserRole
import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.anonymouschatmessage.AnonymousChatMessageResponse
import com.fathersprophets.backend.models.anonymouschatmessage.CreateAnonymousChatMessageRequest
import com.fathersprophets.backend.models.anonymouschatmessage.UpdateAnonymousChatMessageRequest
import com.fathersprophets.backend.models.dto.AnonymousChatMessageDto
import com.fathersprophets.backend.models.dto.UserDto
import com.fathersprophets.backend.services.notification.IFirebaseMessagingService
import com.fathersprophets.backend.utils.Localization

class AnonymousChatMessageRepository(
    private val messageDao: AnonymousChatMessageDao,
    private val chatDao: AnonymousChatDao,
    private val userDao: UserDao,
    private val firebaseMessagingService: IFirebaseMessagingService
) : IAnonymousChatMessageRepository {

    override fun getAllMessages(lang: String): ApiResponse<List<AnonymousChatMessageResponse>> {
        val messages = messageDao.findAll()
        return ApiResponse(
            success = true,
            data = messages.map { it.convertToResponse() },
            message = Localization.get("anonymous_chat_messages_retrieved_successfully", lang)
        )
    }

    override fun getMessageById(id: Int, lang: String): ApiResponse<AnonymousChatMessageResponse> {
        val message = messageDao.findById(id)
        return ApiResponse(
            success = true,
            data = message?.convertToResponse(),
            message = Localization.get("anonymous_chat_message_retrieved_successfully", lang)
        )
    }

    override fun getMessagesByChatId(chatId: Int, lang: String): ApiResponse<List<AnonymousChatMessageResponse>> {
        val messages = messageDao.findByChatId(chatId)
        return ApiResponse(
            success = true,
            data = messages.map { it.convertToResponse() },
            message = Localization.get("anonymous_chat_messages_retrieved_successfully", lang)
        )
    }

    override fun createMessage(request: CreateAnonymousChatMessageRequest, lang: String): ApiResponse<AnonymousChatMessageResponse> {
        chatDao.findById(request.chatId ?: 0)
            ?: throw IllegalArgumentException(Localization.get("anonymous_chat_not_found", lang))

        val id = messageDao.create(request.toAnonymousChatMessageDto())
        val created = messageDao.findById(id)
        val member = userDao.findById(idToUserDto(request.memberId ?: 0))?: throw IllegalArgumentException(Localization.get("member_not_found", lang))
        val servant = userDao.findById(idToUserDto(request.servantId ?: 0))?: throw IllegalArgumentException(Localization.get("servant_not_found", lang))

        if (created != null) {
            val tokens = listOfNotNull(
                userDao.findFcmTokenById(created.memberId),
                userDao.findFcmTokenById(created.servantId)
            )
            val data = mapOf(
                "type" to "chat_message",
                "id" to created.id.toString(),
                "chatId" to created.chatId.toString(),
                "memberId" to created.memberId.toString(),
                "servantId" to created.servantId.toString(),
                "message" to created.message,
                "memberName" to member.name,
                "servantName" to servant.name,
                "isRead" to created.isRead.toString(),
                "createdAt" to created.createdAt
            )
            firebaseMessagingService.sendToTokens(tokens, "New message", created.message, data)
        }

        return ApiResponse(
            success = true,
            data = created?.convertToResponse(),
            message = Localization.get("anonymous_chat_message_created_successfully", lang)
        )
    }

    override fun updateMessage(id: Int, request: UpdateAnonymousChatMessageRequest, lang: String): ApiResponse<AnonymousChatMessageResponse> {
        messageDao.update(request.toAnonymousChatMessageDto(id))
        val updated = messageDao.findById(id)
        return ApiResponse(
            success = true,
            data = updated?.convertToResponse(),
            message = Localization.get("anonymous_chat_message_updated_successfully", lang)
        )
    }

    override fun deleteMessage(id: Int, lang: String): ApiResponse<Nothing> {
        messageDao.delete(idToDto(id))
        return ApiResponse(
            success = true,
            data = null,
            message = Localization.get("anonymous_chat_message_deleted_successfully", lang)
        )
    }

    private fun idToDto(id: Int) = AnonymousChatMessageDto(
        id = id,
        chatId = 0,
        memberId = 0,
        servantId = 0,
        message = "",
        isRead = false,
        createdAt = ""
    )

    private fun idToUserDto(id : Int) = UserDto(
        id = id,
        name = "",
        username = "",
        passwordHash = "",
        role = UserRole.member
    )
}