package com.fathersprophets.backend.di

import com.fathersprophets.backend.database.dao.chat.AnonymousChatDao
import com.fathersprophets.backend.database.dao.chat.AnonymousChatMessageDao
import com.fathersprophets.backend.database.dao.attendance.AttendanceDao
import com.fathersprophets.backend.database.dao.classes.ClassDao
import com.fathersprophets.backend.database.dao.classes.ClassMemberDao
import com.fathersprophets.backend.database.dao.users.CommentDao
import com.fathersprophets.backend.database.dao.activity.escapeegypt.EscapeEgyptAnswerDao
import com.fathersprophets.backend.database.dao.activity.escapeegypt.EscapeEgyptDao
import com.fathersprophets.backend.database.dao.activity.escapeegypt.EscapeEgyptQuestionDao
import com.fathersprophets.backend.database.dao.event.EventDao
import com.fathersprophets.backend.database.dao.event.EventMemberDao
import com.fathersprophets.backend.database.dao.notification.NotificationDao
import com.fathersprophets.backend.database.dao.person.complete.PersonAnswerDao
import com.fathersprophets.backend.database.dao.person.PersonDao
import com.fathersprophets.backend.database.dao.person.guessperson.GuessPersonAnswerDao
import com.fathersprophets.backend.database.dao.person.guessperson.GuessPersonQuestionDao
import com.fathersprophets.backend.database.dao.activity.matchpaor.MatchingPairAnswerDao
import com.fathersprophets.backend.database.dao.activity.matchpair.MatchingPairDao
import com.fathersprophets.backend.database.dao.person.mcq.PersonMcqAnswerDao
import com.fathersprophets.backend.database.dao.person.mcq.PersonMcqDao
import com.fathersprophets.backend.database.dao.person.personofday.PersonOfDayDao
import com.fathersprophets.backend.database.dao.person.complete.PersonQuestionDao
import com.fathersprophets.backend.database.dao.person.story.PersonStoryDao
import com.fathersprophets.backend.database.dao.person.story.PersonStoryQuestionDao
import com.fathersprophets.backend.database.dao.quiz.QuizAnswerDao
import com.fathersprophets.backend.database.dao.quiz.QuizDao
import com.fathersprophets.backend.database.dao.quiz.QuizDayDao
import com.fathersprophets.backend.database.dao.quiz.QuizDayQuestionDao
import com.fathersprophets.backend.database.dao.attendance.SessionDao
import com.fathersprophets.backend.database.dao.superevent.SuperEventDao
import com.fathersprophets.backend.database.dao.superevent.SuperEventBookingDao
import com.fathersprophets.backend.database.dao.users.UserProgressQuizDao
import com.fathersprophets.backend.database.dao.activity.timeline.TimelineAnswerDao
import com.fathersprophets.backend.database.dao.activity.timeline.TimelineDao
import com.fathersprophets.backend.database.dao.users.UserDao
import com.fathersprophets.backend.database.dao.version.VersionDao
import com.fathersprophets.backend.database.repository.anonymouschat.AnonymousChatRepository
import com.fathersprophets.backend.database.repository.anonymouschat.IAnonymousChatRepository
import com.fathersprophets.backend.database.repository.anonymouschatmessage.AnonymousChatMessageRepository
import com.fathersprophets.backend.database.repository.anonymouschatmessage.IAnonymousChatMessageRepository
import com.fathersprophets.backend.database.repository.attendance.attendance.AttendanceRepository
import com.fathersprophets.backend.database.repository.attendance.attendance.IAttendanceRepository
import com.fathersprophets.backend.database.repository.auth.AuthRepository
import com.fathersprophets.backend.database.repository.auth.IAuthRepository
import com.fathersprophets.backend.database.repository.classes.classes.ClassRepository
import com.fathersprophets.backend.database.repository.classes.classes.IClassRepository
import com.fathersprophets.backend.database.repository.classes.classmember.ClassMemberRepository
import com.fathersprophets.backend.database.repository.classes.classmember.IClassMemberRepository
import com.fathersprophets.backend.database.repository.users.comments.CommentsRepository
import com.fathersprophets.backend.database.repository.users.comments.ICommentsRepository
import com.fathersprophets.backend.database.repository.activity.escapeegypt.EscapeEgyptRepository
import com.fathersprophets.backend.database.repository.activity.escapeegypt.IEscapeEgyptRepository
import com.fathersprophets.backend.database.repository.activity.escapeegypt.escapeegyptanswer.EscapeEgyptAnswerRepository
import com.fathersprophets.backend.database.repository.activity.escapeegypt.escapeegyptanswer.IEscapeEgyptAnswerRepository
import com.fathersprophets.backend.database.repository.activity.escapeegypt.escapeegyptquestion.EscapeEgyptQuestionRepository
import com.fathersprophets.backend.database.repository.activity.escapeegypt.escapeegyptquestion.IEscapeEgyptQuestionRepository
import com.fathersprophets.backend.database.repository.events.eventmember.EventMemberRepository
import com.fathersprophets.backend.database.repository.events.eventmember.IEventMemberRepository
import com.fathersprophets.backend.database.repository.events.EventRepository
import com.fathersprophets.backend.database.repository.events.IEventRepository
import com.fathersprophets.backend.database.repository.notification.INotificationRepository
import com.fathersprophets.backend.database.repository.notification.NotificationRepository
import com.fathersprophets.backend.database.repository.person.IPersonRepository
import com.fathersprophets.backend.database.repository.person.PersonRepository
import com.fathersprophets.backend.database.repository.personanswer.IPersonAnswerRepository
import com.fathersprophets.backend.database.repository.personanswer.PersonAnswerRepository
import com.fathersprophets.backend.database.repository.person.guessperson.GuessPersonQuestionRepository
import com.fathersprophets.backend.database.repository.person.guessperson.IGuessPersonQuestionRepository
import com.fathersprophets.backend.database.repository.person.guessperson.guesspersonanswer.GuessPersonAnswerRepository
import com.fathersprophets.backend.database.repository.person.guessperson.guesspersonanswer.IGuessPersonAnswerRepository
import com.fathersprophets.backend.database.repository.activity.matchingpair.IMatchingPairRepository
import com.fathersprophets.backend.database.repository.activity.matchingpair.MatchingPairRepository
import com.fathersprophets.backend.database.repository.activity.matchingpair.matchingpairanswer.IMatchingPairAnswerRepository
import com.fathersprophets.backend.database.repository.activity.matchingpair.matchingpairanswer.MatchingPairAnswerRepository
import com.fathersprophets.backend.database.repository.person.personmcq.IPersonMcqRepository
import com.fathersprophets.backend.database.repository.person.personmcq.PersonMcqRepository
import com.fathersprophets.backend.database.repository.person.personmcqanswer.IPersonMcqAnswerRepository
import com.fathersprophets.backend.database.repository.person.personmcqanswer.PersonMcqAnswerRepository
import com.fathersprophets.backend.database.repository.person.personofday.IPersonOfDayRepository
import com.fathersprophets.backend.database.repository.person.personofday.PersonOfDayRepository
import com.fathersprophets.backend.database.repository.person.personstory.IPersonStoryRepository
import com.fathersprophets.backend.database.repository.personstory.PersonStoryRepository
import com.fathersprophets.backend.database.repository.person.personstoryquestion.IPersonStoryQuestionRepository
import com.fathersprophets.backend.database.repository.person.personstoryquestion.PersonStoryQuestionRepository
import com.fathersprophets.backend.database.repository.person.personquestion.IPersonQuestionRepository
import com.fathersprophets.backend.database.repository.person.personquestion.PersonQuestionRepository
import com.fathersprophets.backend.database.repository.quiz.IQuizRepository
import com.fathersprophets.backend.database.repository.quiz.QuizRepository
import com.fathersprophets.backend.database.repository.quiz.quizanswer.IQuizAnswerRepository
import com.fathersprophets.backend.database.repository.quiz.quizanswer.QuizAnswerRepository
import com.fathersprophets.backend.database.repository.quiz.quizday.IQuizDayRepository
import com.fathersprophets.backend.database.repository.quiz.quizday.QuizDayRepository
import com.fathersprophets.backend.database.repository.quiz.quizdayquestion.IQuizDayQuestionRepository
import com.fathersprophets.backend.database.repository.quiz.quizdayquestion.QuizDayQuestionRepository
import com.fathersprophets.backend.database.repository.attendance.sessions.ISessionRepository
import com.fathersprophets.backend.database.repository.attendance.sessions.SessionRepository
import com.fathersprophets.backend.database.repository.superevent.ISuperEventRepository
import com.fathersprophets.backend.database.repository.superevent.SuperEventRepository
import com.fathersprophets.backend.database.repository.superevent.supereventbooking.ISuperEventBookingRepository
import com.fathersprophets.backend.database.repository.supereventbooking.SuperEventBookingRepository
import com.fathersprophets.backend.database.repository.userprogressquiz.IUserProgressQuizRepository
import com.fathersprophets.backend.database.repository.userprogressquiz.UserProgressQuizRepository
import com.fathersprophets.backend.database.repository.activity.timeline.ITimelineRepository
import com.fathersprophets.backend.database.repository.activity.timeline.TimelineRepository
import com.fathersprophets.backend.database.repository.activity.timeline.timelineanswer.ITimelineAnswerRepository
import com.fathersprophets.backend.database.repository.activity.timeline.timelineanswer.TimelineAnswerRepository
import com.fathersprophets.backend.database.repository.users.IUserRepository
import com.fathersprophets.backend.database.repository.users.UserRepository
import com.fathersprophets.backend.database.repository.version.IVersionRepository
import com.fathersprophets.backend.database.repository.version.VersionRepository
import com.fathersprophets.backend.services.chat.anonymouschat.AnonymousChatService
import com.fathersprophets.backend.services.chat.anonymouschat.IAnonymousChatService
import com.fathersprophets.backend.services.chat.anonymouschatmessage.AnonymousChatMessageService
import com.fathersprophets.backend.services.chat.anonymouschatmessage.IAnonymousChatMessageService
import com.fathersprophets.backend.services.attendance.AttendanceService
import com.fathersprophets.backend.services.attendance.IAttendanceService
import com.fathersprophets.backend.services.auth.AuthService
import com.fathersprophets.backend.services.auth.IAuthService
import com.fathersprophets.backend.services.classes.ClassService
import com.fathersprophets.backend.services.classes.IClassService
import com.fathersprophets.backend.services.classes.classmember.ClassMemberService
import com.fathersprophets.backend.services.classes.classmember.IClassMemberService
import com.fathersprophets.backend.services.comments.CommentsService
import com.fathersprophets.backend.services.comments.ICommentsService
import com.fathersprophets.backend.services.activity.escapeegypt.EscapeEgyptService
import com.fathersprophets.backend.services.activity.escapeegypt.IEscapeEgyptService
import com.fathersprophets.backend.services.escapeegyptanswer.EscapeEgyptAnswerService
import com.fathersprophets.backend.services.activity.escapeegypt.escapeegyptanswer.IEscapeEgyptAnswerService
import com.fathersprophets.backend.services.activity.escapeegypt.escapeegyptquestion.EscapeEgyptQuestionService
import com.fathersprophets.backend.services.activity.escapeegypt.escapeegyptquestion.IEscapeEgyptQuestionService
import com.fathersprophets.backend.services.events.eventmember.EventMemberService
import com.fathersprophets.backend.services.events.eventmember.IEventMemberService
import com.fathersprophets.backend.services.events.EventService
import com.fathersprophets.backend.services.events.IEventService
import com.fathersprophets.backend.services.notification.BirthdayReminderScheduler
import com.fathersprophets.backend.services.notification.FirebaseMessagingService
import com.fathersprophets.backend.services.notification.IFirebaseMessagingService
import com.fathersprophets.backend.services.notification.INotificationService
import com.fathersprophets.backend.services.notification.NotificationService
import com.fathersprophets.backend.services.person.IPersonService
import com.fathersprophets.backend.services.person.PersonService
import com.fathersprophets.backend.services.person.complete.personanswer.IPersonAnswerService
import com.fathersprophets.backend.services.person.complete.personanswer.PersonAnswerService
import com.fathersprophets.backend.services.person.guessperson.GuessPersonQuestionService
import com.fathersprophets.backend.services.person.guessperson.IGuessPersonQuestionService
import com.fathersprophets.backend.services.person.guesspersonanswer.GuessPersonAnswerService
import com.fathersprophets.backend.services.guesspersonanswer.IGuessPersonAnswerService
import com.fathersprophets.backend.services.activity.matchingpair.IMatchingPairService
import com.fathersprophets.backend.services.activity.matchingpair.MatchingPairService
import com.fathersprophets.backend.services.activity.matchingpair.matchingpairanswer.IMatchingPairAnswerService
import com.fathersprophets.backend.services.activity.matchingpair.matchingpairanswer.MatchingPairAnswerService
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
import com.fathersprophets.backend.services.attendance.session.ISessionService
import com.fathersprophets.backend.services.attendance.session.SessionService
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
