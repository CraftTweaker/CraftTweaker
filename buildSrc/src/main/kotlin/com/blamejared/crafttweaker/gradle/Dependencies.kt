package com.blamejared.crafttweaker.gradle

object Dependencies {

    val ZENCODE = sequenceOf(
            ":CodeFormatter",
            ":CodeFormatterShared",
            ":JavaIntegration",
            ":JavaAnnotations",
            ":JavaBytecodeCompiler",
            ":JavaShared",
            ":Validator",
            ":Parser",
            ":CodeModel",
            ":Shared"
    )
    val ZENCODE_TEST = sequenceOf(":ScriptingExample")
}