package com.example.number.numbers.domain

abstract class DomainException : IllegalStateException()

class NoInternetConnectException: DomainException()

class ServiceUnavailableException : DomainException()
