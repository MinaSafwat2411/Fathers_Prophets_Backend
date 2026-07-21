package com.fathersprophets.backend.exceptions

class UnauthorizedException(message: String) : RuntimeException(message)

class ConflictException(message: String) : RuntimeException(message)

class BadRequestException(message: String) : RuntimeException(message)

class NotFoundException(message: String) : RuntimeException(message)

class ForbiddenException(message: String) : RuntimeException(message)

class TooManyRequestsException(message: String) : RuntimeException(message)