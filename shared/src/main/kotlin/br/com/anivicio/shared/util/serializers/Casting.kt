package br.com.anivicio.shared.util.serializers

import java.lang.reflect.Type

/**
 * Check is standard type
 *
 * Check if a type is a standard type
 *
 * @param type Type
 * @return Boolean
 */
fun checkIsStandardType(type: Type): Boolean {
    return type == String::class.java ||
            type == Int::class.java ||
            type == Long::class.java ||
            type == Double::class.java ||
            type == Float::class.java ||
            type == Boolean::class.java
}
