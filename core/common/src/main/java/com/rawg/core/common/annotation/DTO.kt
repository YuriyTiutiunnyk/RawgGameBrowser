package com.rawg.core.common.annotation

/**
 * Marker annotation for Data Transfer Objects (DTOs).
 *
 * DTOs are data layer classes that directly map to API responses or database entities.
 * They should not contain business logic and are transformed to domain models via [com.rawg.core.common.mapper.Mapper].
 */
@MustBeDocumented
@Target(AnnotationTarget.CLASS)
annotation class DTO
