package org.udelledo

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.module.kotlin.registerKotlinModule

val mapper = ObjectMapper().registerKotlinModule().apply {
    configure(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT, true)
    setSerializationInclusion(JsonInclude.Include.NON_NULL)
    registerModule(SimpleModule())

}
