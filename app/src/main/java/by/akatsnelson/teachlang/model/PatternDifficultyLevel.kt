package by.akatsnelson.teachlang.model

import by.akatsnelson.teachlang.MainActivity
import java.lang.reflect.Field

/**
 * Created by AKatsnelson on 11.03.2018.
 */
class PatternDifficultyLevel {
    var name: String? = null
    var form: Boolean? = null
    var mood: Boolean? = null
    var tense: Boolean? = null
    var number: Boolean? = null
    var person: Boolean? = null
    var gender: Boolean? = null
    var aspect: Boolean? = null
    var voice: Boolean? = null
    var transitiviness: Boolean? = null
    var conjugntion: Boolean? = null
    var brevity: Boolean? = null
    var reflexiveness: Boolean? = null
    var case: Boolean? = null
    var comment: Boolean? = null
    var grammeme: Grammeme? = null

    fun isCorrectForPattern(node: Node): Boolean {
        for (fieldP in this::class.java.declaredFields) {
            fieldP.isAccessible = true
            if (fieldP.get(this) != null) {
                val grammemes = node.grammemes
                var currentForThisGrammeme = false
                val fieldN = Grammeme::class.java.declaredFields.find {
                    it.name == fieldP.name
                }
                if (fieldN != null) {
                    fieldN.isAccessible = true
                    for (gram in grammemes) {
                        if (isCurrentForThisGrammeme(fieldP, fieldN, gram)) currentForThisGrammeme = true
                    }
                    if (!currentForThisGrammeme) return false
                }
            }
        }
        return true
    }

    private fun isCurrentForThisGrammeme(fieldP: Field, fieldN: Field, gram: Grammeme): Boolean {
        if (fieldP.type.simpleName.toLowerCase() == Boolean::class.java.simpleName) {
            val filed = fieldN.get(gram)
            val haveInThePattern = filed != null && (fieldP.get(this) as Boolean)
            val notHaveInTheTemplate = filed == null && !(fieldP.get(this) as Boolean)
            if (notHaveInTheTemplate || haveInThePattern) {
                if (haveInThePattern && MainActivity.patternNow!!.grammeme != null) {
                    val findInGrammeme = Grammeme.findInGrammeme(MainActivity.patternNow!!.grammeme!!, fieldN.name.toLowerCase())
                    if (findInGrammeme != "null") {
                        if (findInGrammeme != filed.toString()) {
                            return false
                        }
                    }
                }
            } else {
                return false
            }
        }
        return true
    }


}