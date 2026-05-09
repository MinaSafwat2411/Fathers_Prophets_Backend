package com.fathersprophets.backend.di

import com.fathersprophets.backend.database.dao.AttendanceDao
import com.fathersprophets.backend.database.dao.ClassDao
import com.fathersprophets.backend.database.dao.ClassMemberDao
import com.fathersprophets.backend.database.dao.CommentDao
import com.fathersprophets.backend.database.dao.SessionDao
import com.fathersprophets.backend.database.dao.UserDao
import com.fathersprophets.backend.database.dao.VersionDao
import com.fathersprophets.backend.database.repository.auth.AuthRepository
import com.fathersprophets.backend.database.repository.auth.IAuthRepository
import com.fathersprophets.backend.database.repository.classes.ClassRepository
import com.fathersprophets.backend.database.repository.classes.IClassRepository
import com.fathersprophets.backend.database.repository.classmember.ClassMemberRepository
import com.fathersprophets.backend.database.repository.classmember.IClassMemberRepository
import com.fathersprophets.backend.database.repository.comments.CommentsRepository
import com.fathersprophets.backend.database.repository.comments.ICommentsRepository
import com.fathersprophets.backend.database.repository.users.IUserRepository
import com.fathersprophets.backend.database.repository.users.UserRepository
import com.fathersprophets.backend.database.repository.version.IVersionRepository
import com.fathersprophets.backend.database.repository.version.VersionRepository
import com.fathersprophets.backend.services.auth.AuthService
import com.fathersprophets.backend.services.auth.IAuthService
import com.fathersprophets.backend.services.classes.ClassService
import com.fathersprophets.backend.services.classes.IClassService
import com.fathersprophets.backend.services.classmember.ClassMemberService
import com.fathersprophets.backend.services.classmember.IClassMemberService
import com.fathersprophets.backend.services.comments.CommentsService
import com.fathersprophets.backend.services.comments.ICommentsService
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

}
