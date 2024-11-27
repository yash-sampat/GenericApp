package com.generic.login.utils

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class PrimaryApi

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class PhotoApi