package com.fathersprophets.backend.database.repository.chat.anonymouschatmessage

import com.fathersprophets.backend.database.dao.chat.AnonymousChatMessageDao
import com.fathersprophets.backend.database.dao.users.UserDao
import com.fathersprophets.backend.models.ApiResponse
import com.fathersprophets.backend.models.anonymouschatmessage.AnonymousChatMessageResponse
import com.fathersprophets.backend.models.anonymouschatmessage.CreateAnonymousChatMessageRequest
import com.fathersprophets.backend.models.anonymouschatmessage.UpdateAnonymousChatMessageRequest
import com.fathersprophets.backend.services.notification.IFirebaseMessagingService
import com.fathersprophets.backend.utils.Localization
import java.time.LocalDateTime

class AnonymousChatMessageRepository(
    private val messageDao: AnonymousChatMessageDao,
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

    override fun getMessagesByChatId(chatId: Int, userId : Int, lang: String): ApiResponse<List<AnonymousChatMessageResponse>> {
        val messages = messageDao.findByChatId(chatId,userId)
        return ApiResponse(
            success = true,
            data = messages.map { it.convertToResponse() },
            message = Localization.get("anonymous_chat_messages_retrieved_successfully", lang)
        )
    }

    override fun createMessage(
        request: CreateAnonymousChatMessageRequest,
        lang: String
    ): ApiResponse<AnonymousChatMessageResponse> {

        val created = messageDao.create(request.toAnonymousChatMessageDto())
            ?: throw IllegalStateException(Localization.get("anonymous_chat_message_creation_failed", lang))

        val member = userDao.findById(request.memberId ?: 0)
            ?: throw IllegalArgumentException(Localization.get("member_not_found", lang))
        val servant = userDao.findById(request.servantId ?: 0)
            ?: throw IllegalArgumentException(Localization.get("servant_not_found", lang))

        val tokens = listOfNotNull(
            userDao.findFcmTokenById(request.memberId ?: 0),
            userDao.findFcmTokenById(request.servantId ?: 0)
        )

        val data = mapOf(
            "type" to "chat_message",
            "id" to created.toString(),
            "chatId" to request.chatId.toString(),
            "memberId" to request.memberId.toString(),
            "servantId" to request.servantId.toString(),
            "message" to request.message.toString(),
            "memberName" to member.name,
            "servantName" to servant.name,
            "isRead" to false.toString(),
            "createdAt" to LocalDateTime.now().toString(),
        )

        firebaseMessagingService.sendToTokens(tokens, "New message", request.message ?: "", data)

        return ApiResponse(
            success = true,
            data = created.convertToResponse(),
            message = Localization.get("anonymous_chat_message_created_successfully", lang)
        )
    }

    override fun updateMessage(
        id: Int,
        request: UpdateAnonymousChatMessageRequest,
        lang: String
    ): ApiResponse<AnonymousChatMessageResponse> {
        val updated = messageDao.update(request.toAnonymousChatMessageDto(id))
            ?: throw IllegalStateException(Localization.get("anonymous_chat_message_update_failed", lang))

        return ApiResponse(
            success = true,
            data = updated.convertToResponse(),
            message = Localization.get("anonymous_chat_message_updated_successfully", lang)
        )
    }

    override fun deleteMessage(id: Int, lang: String): ApiResponse<Nothing> {

        val deleted = messageDao.delete(id)

        if (!deleted) throw IllegalStateException(Localization.get("anonymous_chat_message_deletion_failed", lang))

        return ApiResponse(
            success = true,
            data = null,
            message = Localization.get("anonymous_chat_message_deleted_successfully", lang)
        )
    }

}