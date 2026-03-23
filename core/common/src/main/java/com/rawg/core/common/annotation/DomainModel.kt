package com.rawg.core.common.annotation

/**
 * Marker annotation for Domain Model classes.
 *
 * Domain models represent the core business entities of the application.
 * They are independent of any framework or data layer concerns and contain
 * only data relevant to the business domain.
 *
 * Domain models are typically created by mapping DTOs through [com.rawg.core.common.mapper.Mapper].
 */
@MustBeDocumented
@Target(AnnotationTarget.CLASS)
annotation class DomainModel
