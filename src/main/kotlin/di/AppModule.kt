package com.fathersprophets.backend.di

import com.fathersprophets.backend.database.dao.AttendanceDao
import com.fathersprophets.backend.database.dao.ClassDao
import com.fathersprophets.backend.database.dao.ClassMemberDao
import com.fathersprophets.backend.database.dao.CommentDao
import com.fathersprophets.backend.database.dao.EventDao
import com.fathersprophets.backend.database.dao.EventMemberDao
import com.fathersprophets.backend.database.dao.SessionDao
import com.fathersprophets.backend.database.dao.UserDao
import com.fathersprophets.backend.database.dao.VersionDao
import com.fathersprophets.backend.database.repository.attendance.AttendanceRepository
import com.fathersprophets.backend.database.repository.attendance.IAttendanceRepository
import com.fathersprophets.backend.database.repository.auth.AuthRepository
import com.fathersprophets.backend.database.repository.auth.IAuthRepository
import com.fathersprophets.backend.database.repository.classes.ClassRepository
import com.fathersprophets.backend.database.repository.classes.IClassRepository
import com.fathersprophets.backend.database.repository.classmember.ClassMemberRepository
import com.fathersprophets.backend.database.repository.classmember.IClassMemberRepository
import com.fathersprophets.backend.database.repository.comments.CommentsRepository
import com.fathersprophets.backend.database.repository.comments.ICommentsRepository
import com.fathersprophets.backend.database.repository.eventmember.EventMemberRepository
import com.fathersprophets.backend.database.repository.eventmember.IEventMemberRepository
import com.fathersprophets.backend.database.repository.events.EventRepository
import com.fathersprophets.backend.database.repository.events.IEventRepository
import com.fathersprophets.backend.database.repository.sessions.ISessionRepository
import com.fathersprophets.backend.database.repository.sessions.SessionRepository
import com.fathersprophets.backend.database.repository.users.IUserRepository
import com.fathersprophets.backend.database.repository.users.UserRepository
import com.fathersprophets.backend.database.repository.version.IVersionRepository
import com.fathersprophets.backend.database.repository.version.VersionRepository
import com.fathersprophets.backend.services.attendance.AttendanceService
import com.fathersprophets.backend.services.attendance.IAttendanceService
import com.fathersprophets.backend.services.auth.AuthService
import com.fathersprophets.backend.services.auth.IAuthService
import com.fathersprophets.backend.services.classes.ClassService
import com.fathersprophets.backend.services.classes.IClassService
import com.fathersprophets.backend.services.classmember.ClassMemberService
import com.fathersprophets.backend.services.classmember.IClassMemberService
import com.fathersprophets.backend.services.comments.CommentsService
import com.fathersprophets.backend.services.comments.ICommentsService
import com.fathersprophets.backend.services.eventmember.EventMemberService
import com.fathersprophets.backend.services.eventmember.IEventMemberService
import com.fathersprophets.backend.services.events.EventService
import com.fathersprophets.backend.services.events.IEventService
import com.fathersprophets.backend.services.session.ISessionService
import com.fathersprophets.backend.services.session.SessionService
import com.fathersprophets.backend.services.users.IUserService
import com.fathersprophets.backend.services.users.UserService
import com.fathersprophets.backend.services.version.IVersionService
import com.fathersprophets.backend.services.version.VersionService
import org.koin.dsl.module

val appModule = module {
    single { UserDao() }
    single { ClassDao() }
    single { ClassMemberDao() }
    single { CommentDao() }
    single { VersionDao() }
    single { SessionDao() }
    single { AttendanceDao() }
    single { EventDao() }
    single { EventMemberDao() }

    single<IAuthRepository> {
        AuthRepository(
            get(),
        )
    }

    single<IAuthService> { AuthService(get()) }

    single<IUserRepository> {
        UserRepository(get())
    }

    single <IUserService>{
        UserService(get())
    }

    single <IClassRepository> {
        ClassRepository(get())
    }

    single<IClassService> {
        ClassService(get())
    }

    single<IClassMemberRepository> {
        ClassMemberRepository(get())
    }

    single<IClassMemberService> {
        ClassMemberService(get())
    }

    single<ICommentsRepository> {
        CommentsRepository(get())
    }

    single<ICommentsService> {
        CommentsService(get())
    }

    single<IVersionRepository> {
        VersionRepository(get())
    }

    single<IVersionService> {
        VersionService(get())
    }

    single<ISessionRepository> {
        SessionRepository(get())
    }

    single<ISessionService> {
        SessionService(get())
    }

    single<IAttendanceRepository>{
        AttendanceRepository(get())
    }

    single <IAttendanceService>{
        AttendanceService(get())
    }

    single<IEventRepository> {
        EventRepository(get())
    }

    single<IEventService> {
        EventService(get())
    }

    single<IEventMemberRepository> {
        EventMemberRepository(get())
    }

    single<IEventMemberService> {
        EventMemberService(get())
    }

}
