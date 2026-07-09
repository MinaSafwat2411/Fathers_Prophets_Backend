package com.fathersprophets.backend.di

import com.fathersprophets.backend.database.dao.chat.AnonymousChatDao
import com.fathersprophets.backend.database.dao.chat.AnonymousChatMessageDao
import com.fathersprophets.backend.database.dao.attendance.AttendanceDao
import com.fathersprophets.backend.database.dao.classes.ClassDao
import com.fathersprophets.backend.database.dao.classes.ClassMemberDao
import com.fathersprophets.backend.database.dao.CommentDao
import com.fathersprophets.backend.database.dao.EscapeEgyptAnswerDao
import com.fathersprophets.backend.database.dao.EscapeEgyptDao
import com.fathersprophets.backend.database.dao.EscapeEgyptQuestionDao
import com.fathersprophets.backend.database.dao.EventDao
import com.fathersprophets.backend.database.dao.EventMemberDao
import com.fathersprophets.backend.database.dao.NotificationDao
import com.fathersprophets.backend.database.dao.PersonAnswerDao
import com.fathersprophets.backend.database.dao.PersonDao
import com.fathersprophets.backend.database.dao.GuessPersonAnswerDao
import com.fathersprophets.backend.database.dao.GuessPersonQuestionDao
import com.fathersprophets.backend.database.dao.MatchingPairAnswerDao
import com.fathersprophets.backend.database.dao.MatchingPairDao
import com.fathersprophets.backend.database.dao.PersonMcqAnswerDao
import com.fathersprophets.backend.database.dao.PersonMcqDao
import com.fathersprophets.backend.database.dao.PersonOfDayDao
import com.fathersprophets.backend.database.dao.PersonQuestionDao
import com.fathersprophets.backend.database.dao.PersonStoryDao
import com.fathersprophets.backend.database.dao.PersonStoryQuestionDao
import com.fathersprophets.backend.database.dao.QuizAnswerDao
import com.fathersprophets.backend.database.dao.QuizDao
import com.fathersprophets.backend.database.dao.QuizDayDao
import com.fathersprophets.backend.database.dao.QuizDayQuestionDao
import com.fathersprophets.backend.database.dao.attendance.SessionDao
import com.fathersprophets.backend.database.dao.SuperEventDao
import com.fathersprophets.backend.database.dao.SuperEventBookingDao
import com.fathersprophets.backend.database.dao.UserProgressQuizDao
import com.fathersprophets.backend.database.dao.TimelineAnswerDao
import com.fathersprophets.backend.database.dao.TimelineDao
import com.fathersprophets.backend.database.dao.UserDao
import com.fathersprophets.backend.database.dao.VersionDao
import com.fathersprophets.backend.database.repository.anonymouschat.AnonymousChatRepository
import com.fathersprophets.backend.database.repository.anonymouschat.IAnonymousChatRepository
import com.fathersprophets.backend.database.repository.anonymouschatmessage.AnonymousChatMessageRepository
import com.fathersprophets.backend.database.repository.anonymouschatmessage.IAnonymousChatMessageRepository
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
import com.fathersprophets.backend.database.repository.escapeegypt.EscapeEgyptRepository
import com.fathersprophets.backend.database.repository.escapeegypt.IEscapeEgyptRepository
import com.fathersprophets.backend.database.repository.escapeegyptanswer.EscapeEgyptAnswerRepository
import com.fathersprophets.backend.database.repository.escapeegyptanswer.IEscapeEgyptAnswerRepository
import com.fathersprophets.backend.database.repository.escapeegyptquestion.EscapeEgyptQuestionRepository
import com.fathersprophets.backend.database.repository.escapeegyptquestion.IEscapeEgyptQuestionRepository
import com.fathersprophets.backend.database.repository.eventmember.EventMemberRepository
import com.fathersprophets.backend.database.repository.eventmember.IEventMemberRepository
import com.fathersprophets.backend.database.repository.events.EventRepository
import com.fathersprophets.backend.database.repository.events.IEventRepository
import com.fathersprophets.backend.database.repository.notification.INotificationRepository
import com.fathersprophets.backend.database.repository.notification.NotificationRepository
import com.fathersprophets.backend.database.repository.person.IPersonRepository
import com.fathersprophets.backend.database.repository.person.PersonRepository
import com.fathersprophets.backend.database.repository.personanswer.IPersonAnswerRepository
import com.fathersprophets.backend.database.repository.personanswer.PersonAnswerRepository
import com.fathersprophets.backend.database.repository.guessperson.GuessPersonQuestionRepository
import com.fathersprophets.backend.database.repository.guessperson.IGuessPersonQuestionRepository
import com.fathersprophets.backend.database.repository.guesspersonanswer.GuessPersonAnswerRepository
import com.fathersprophets.backend.database.repository.guesspersonanswer.IGuessPersonAnswerRepository
import com.fathersprophets.backend.database.repository.matchingpair.IMatchingPairRepository
import com.fathersprophets.backend.database.repository.matchingpair.MatchingPairRepository
import com.fathersprophets.backend.database.repository.matchingpairanswer.IMatchingPairAnswerRepository
import com.fathersprophets.backend.database.repository.matchingpairanswer.MatchingPairAnswerRepository
import com.fathersprophets.backend.database.repository.personmcq.IPersonMcqRepository
import com.fathersprophets.backend.database.repository.personmcq.PersonMcqRepository
import com.fathersprophets.backend.database.repository.personmcqanswer.IPersonMcqAnswerRepository
import com.fathersprophets.backend.database.repository.personmcqanswer.PersonMcqAnswerRepository
import com.fathersprophets.backend.database.repository.personofday.IPersonOfDayRepository
import com.fathersprophets.backend.database.repository.personofday.PersonOfDayRepository
import com.fathersprophets.backend.database.repository.personstory.IPersonStoryRepository
import com.fathersprophets.backend.database.repository.personstory.PersonStoryRepository
import com.fathersprophets.backend.database.repository.personstoryquestion.IPersonStoryQuestionRepository
import com.fathersprophets.backend.database.repository.personstoryquestion.PersonStoryQuestionRepository
import com.fathersprophets.backend.database.repository.personquestion.IPersonQuestionRepository
import com.fathersprophets.backend.database.repository.personquestion.PersonQuestionRepository
import com.fathersprophets.backend.database.repository.quiz.IQuizRepository
import com.fathersprophets.backend.database.repository.quiz.QuizRepository
import com.fathersprophets.backend.database.repository.quizanswer.IQuizAnswerRepository
import com.fathersprophets.backend.database.repository.quizanswer.QuizAnswerRepository
import com.fathersprophets.backend.database.repository.quizday.IQuizDayRepository
import com.fathersprophets.backend.database.repository.quizday.QuizDayRepository
import com.fathersprophets.backend.database.repository.quizdayquestion.IQuizDayQuestionRepository
import com.fathersprophets.backend.database.repository.quizdayquestion.QuizDayQuestionRepository
import com.fathersprophets.backend.database.repository.sessions.ISessionRepository
import com.fathersprophets.backend.database.repository.sessions.SessionRepository
import com.fathersprophets.backend.database.repository.superevent.ISuperEventRepository
import com.fathersprophets.backend.database.repository.superevent.SuperEventRepository
import com.fathersprophets.backend.database.repository.supereventbooking.ISuperEventBookingRepository
import com.fathersprophets.backend.database.repository.supereventbooking.SuperEventBookingRepository
import com.fathersprophets.backend.database.repository.userprogressquiz.IUserProgressQuizRepository
import com.fathersprophets.backend.database.repository.userprogressquiz.UserProgressQuizRepository
import com.fathersprophets.backend.database.repository.timeline.ITimelineRepository
import com.fathersprophets.backend.database.repository.timeline.TimelineRepository
import com.fathersprophets.backend.database.repository.timelineanswer.ITimelineAnswerRepository
import com.fathersprophets.backend.database.repository.timelineanswer.TimelineAnswerRepository
import com.fathersprophets.backend.database.repository.users.IUserRepository
import com.fathersprophets.backend.database.repository.users.UserRepository
import com.fathersprophets.backend.database.repository.version.IVersionRepository
import com.fathersprophets.backend.database.repository.version.VersionRepository
import com.fathersprophets.backend.services.anonymouschat.AnonymousChatService
import com.fathersprophets.backend.services.anonymouschat.IAnonymousChatService
import com.fathersprophets.backend.services.anonymouschatmessage.AnonymousChatMessageService
import com.fathersprophets.backend.services.anonymouschatmessage.IAnonymousChatMessageService
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
import com.fathersprophets.backend.services.escapeegypt.EscapeEgyptService
import com.fathersprophets.backend.services.escapeegypt.IEscapeEgyptService
import com.fathersprophets.backend.services.escapeegyptanswer.EscapeEgyptAnswerService
import com.fathersprophets.backend.services.escapeegyptanswer.IEscapeEgyptAnswerService
import com.fathersprophets.backend.services.escapeegyptquestion.EscapeEgyptQuestionService
import com.fathersprophets.backend.services.escapeegyptquestion.IEscapeEgyptQuestionService
import com.fathersprophets.backend.services.eventmember.EventMemberService
import com.fathersprophets.backend.services.eventmember.IEventMemberService
import com.fathersprophets.backend.services.events.EventService
import com.fathersprophets.backend.services.events.IEventService
import com.fathersprophets.backend.services.notification.BirthdayReminderScheduler
import com.fathersprophets.backend.services.notification.FirebaseMessagingService
import com.fathersprophets.backend.services.notification.IFirebaseMessagingService
import com.fathersprophets.backend.services.notification.INotificationService
import com.fathersprophets.backend.services.notification.NotificationService
import com.fathersprophets.backend.services.person.IPersonService
import com.fathersprophets.backend.services.person.PersonService
import com.fathersprophets.backend.services.personanswer.IPersonAnswerService
import com.fathersprophets.backend.services.personanswer.PersonAnswerService
import com.fathersprophets.backend.services.guessperson.GuessPersonQuestionService
import com.fathersprophets.backend.services.guessperson.IGuessPersonQuestionService
import com.fathersprophets.backend.services.guesspersonanswer.GuessPersonAnswerService
import com.fathersprophets.backend.services.guesspersonanswer.IGuessPersonAnswerService
import com.fathersprophets.backend.services.matchingpair.IMatchingPairService
import com.fathersprophets.backend.services.matchingpair.MatchingPairService
import com.fathersprophets.backend.services.matchingpairanswer.IMatchingPairAnswerService
import com.fathersprophets.backend.services.matchingpairanswer.MatchingPairAnswerService
import com.fathersprophets.backend.services.personmcq.IPersonMcqService
import com.fathersprophets.backend.services.personmcq.PersonMcqService
import com.fathersprophets.backend.services.personmcqanswer.IPersonMcqAnswerService
import com.fathersprophets.backend.services.personmcqanswer.PersonMcqAnswerService
import com.fathersprophets.backend.services.personofday.IPersonOfDayService
import com.fathersprophets.backend.services.personofday.PersonOfDayService
import com.fathersprophets.backend.services.personstory.IPersonStoryService
import com.fathersprophets.backend.services.personstory.PersonStoryService
import com.fathersprophets.backend.services.personstoryquestion.IPersonStoryQuestionService
import com.fathersprophets.backend.services.personstoryquestion.PersonStoryQuestionService
import com.fathersprophets.backend.services.personquestion.IPersonQuestionService
import com.fathersprophets.backend.services.personquestion.PersonQuestionService
import com.fathersprophets.backend.services.quiz.IQuizService
import com.fathersprophets.backend.services.quiz.QuizService
import com.fathersprophets.backend.services.quizanswer.IQuizAnswerService
import com.fathersprophets.backend.services.quizanswer.QuizAnswerService
import com.fathersprophets.backend.services.quizday.IQuizDayService
import com.fathersprophets.backend.services.quizday.QuizDayService
import com.fathersprophets.backend.services.quizdayquestion.IQuizDayQuestionService
import com.fathersprophets.backend.services.quizdayquestion.QuizDayQuestionService
import com.fathersprophets.backend.services.session.ISessionService
import com.fathersprophets.backend.services.session.SessionService
import com.fathersprophets.backend.services.superevent.ISuperEventService
import com.fathersprophets.backend.services.superevent.SuperEventService
import com.fathersprophets.backend.services.supereventbooking.ISuperEventBookingService
import com.fathersprophets.backend.services.supereventbooking.SuperEventBookingService
import com.fathersprophets.backend.services.userprogressquiz.IUserProgressQuizService
import com.fathersprophets.backend.services.userprogressquiz.UserProgressQuizService
import com.fathersprophets.backend.services.timeline.ITimelineService
import com.fathersprophets.backend.services.timeline.TimelineService
import com.fathersprophets.backend.services.timelineanswer.ITimelineAnswerService
import com.fathersprophets.backend.services.timelineanswer.TimelineAnswerService
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
    single { NotificationDao() }
    single { PersonDao() }
    single { PersonOfDayDao() }
    single { PersonStoryDao() }
    single { PersonStoryQuestionDao() }
    single { PersonQuestionDao() }
    single { PersonMcqDao() }
    single { PersonAnswerDao() }
    single { GuessPersonQuestionDao() }
    single { GuessPersonAnswerDao() }
    single { PersonMcqAnswerDao() }
    single { MatchingPairDao() }
    single { MatchingPairAnswerDao() }
    single { EscapeEgyptDao() }
    single { EscapeEgyptQuestionDao() }
    single { EscapeEgyptAnswerDao() }
    single { TimelineDao() }
    single { TimelineAnswerDao() }
    single { QuizDao() }
    single { QuizAnswerDao() }
    single { UserProgressQuizDao() }
    single { QuizDayDao() }
    single { QuizDayQuestionDao() }
    single { AnonymousChatDao() }
    single { AnonymousChatMessageDao() }
    single { SuperEventDao() }
    single { SuperEventBookingDao() }

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
        EventRepository(get(), get())
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

    single<IFirebaseMessagingService> { FirebaseMessagingService() }

    single<INotificationRepository> {
        NotificationRepository(get(), get(), get(), get())
    }

    single<INotificationService> {
        NotificationService(get())
    }

    single { BirthdayReminderScheduler(get(), get(), get()) }

    single<IPersonRepository> {
        PersonRepository(get())
    }

    single<IPersonService> {
        PersonService(get())
    }

    single<IPersonQuestionRepository> {
        PersonQuestionRepository(get())
    }

    single<IPersonQuestionService> {
        PersonQuestionService(get())
    }

    single<IPersonMcqRepository> {
        PersonMcqRepository(get())
    }

    single<IPersonMcqService> {
        PersonMcqService(get())
    }

    single<IPersonAnswerRepository> {
        PersonAnswerRepository(get())
    }

    single<IPersonAnswerService> {
        PersonAnswerService(get())
    }

    single<IPersonMcqAnswerRepository> {
        PersonMcqAnswerRepository(get(), get())
    }

    single<IPersonMcqAnswerService> {
        PersonMcqAnswerService(get())
    }

    single<IPersonOfDayRepository> {
        PersonOfDayRepository(get())
    }

    single<IPersonOfDayService> {
        PersonOfDayService(get())
    }

    single<IPersonStoryRepository> {
        PersonStoryRepository(get())
    }

    single<IPersonStoryService> {
        PersonStoryService(get())
    }

    single<IPersonStoryQuestionRepository> {
        PersonStoryQuestionRepository(get())
    }

    single<IPersonStoryQuestionService> {
        PersonStoryQuestionService(get())
    }

    single<IGuessPersonQuestionRepository> {
        GuessPersonQuestionRepository(get())
    }

    single<IGuessPersonQuestionService> {
        GuessPersonQuestionService(get())
    }

    single<IGuessPersonAnswerRepository> {
        GuessPersonAnswerRepository(get(), get())
    }

    single<IGuessPersonAnswerService> {
        GuessPersonAnswerService(get())
    }

    single<IMatchingPairRepository> {
        MatchingPairRepository(get())
    }

    single<IMatchingPairService> {
        MatchingPairService(get())
    }

    single<IMatchingPairAnswerRepository> {
        MatchingPairAnswerRepository(get(), get())
    }

    single<IMatchingPairAnswerService> {
        MatchingPairAnswerService(get())
    }

    single<IEscapeEgyptRepository> {
        EscapeEgyptRepository(get())
    }

    single<IEscapeEgyptService> {
        EscapeEgyptService(get())
    }

    single<IEscapeEgyptQuestionRepository> {
        EscapeEgyptQuestionRepository(get())
    }

    single<IEscapeEgyptQuestionService> {
        EscapeEgyptQuestionService(get())
    }

    single<IEscapeEgyptAnswerRepository> {
        EscapeEgyptAnswerRepository(get(), get())
    }

    single<IEscapeEgyptAnswerService> {
        EscapeEgyptAnswerService(get())
    }

    single<ITimelineRepository> {
        TimelineRepository(get())
    }

    single<ITimelineService> {
        TimelineService(get())
    }

    single<ITimelineAnswerRepository> {
        TimelineAnswerRepository(get(), get())
    }

    single<ITimelineAnswerService> {
        TimelineAnswerService(get())
    }

    single<IQuizRepository> {
        QuizRepository(get())
    }

    single<IQuizService> {
        QuizService(get())
    }

    single<IQuizAnswerRepository> {
        QuizAnswerRepository(get(), get(), get())
    }

    single<IQuizAnswerService> {
        QuizAnswerService(get())
    }

    single<IUserProgressQuizRepository> {
        UserProgressQuizRepository(get())
    }

    single<IUserProgressQuizService> {
        UserProgressQuizService(get())
    }

    single<IQuizDayRepository> {
        QuizDayRepository(get())
    }

    single<IQuizDayService> {
        QuizDayService(get())
    }

    single<IQuizDayQuestionRepository> {
        QuizDayQuestionRepository(get())
    }

    single<IQuizDayQuestionService> {
        QuizDayQuestionService(get())
    }

    single<IAnonymousChatRepository> {
        AnonymousChatRepository(get())
    }

    single<IAnonymousChatService> {
        AnonymousChatService(get())
    }

    single<IAnonymousChatMessageRepository> {
        AnonymousChatMessageRepository(get(), get(), get(), get())
    }

    single<IAnonymousChatMessageService> {
        AnonymousChatMessageService(get())
    }

    single<ISuperEventRepository> {
        SuperEventRepository(get(), get())
    }

    single<ISuperEventService> {
        SuperEventService(get())
    }

    single<ISuperEventBookingRepository> {
        SuperEventBookingRepository(get(), get(), get())
    }

    single<ISuperEventBookingService> {
        SuperEventBookingService(get())
    }

}
