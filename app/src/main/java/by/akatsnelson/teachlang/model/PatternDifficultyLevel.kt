package by.akatsnelson.teachlang.model

import by.akatsnelson.teachlang.MainActivity

/**
 * Created by AKatsnelson on 11.03.2018.
 */
class PatternDifficultyLevel {
    var name: String? = null
    var form: Boolean? = null
    var mood: Boolean? = null
    var tnase: Boolean? = null
    var number: Boolean? = null
    var preson: Boolean? = null
    var gender: Boolean? = null
    var aspect: Boolean? = null
    var voice: Boolean? = null
    var transitiviness: Boolean? = null
    var conjugntion: Boolean? = null
    var brevity: Boolean? = null
    var reflexiveness: Boolean? = null
    var case: Boolean? = null
    var grammeme: Grammeme? = null

    fun isCorrectForPattern(node: Node): Boolean {
        for (fieldP in this::class.java.declaredFields) {
            fieldP.isAccessible = true
            if (fieldP.get(this) != null) {
                val grammemes = node.grammemes
                for (fieldN in grammemes::class.java.declaredFields) {
                    fieldN.isAccessible = true
                    if (fieldP.type == Boolean::class.java) {
                        val haveInThePattern = fieldN.get(grammemes) != null && (fieldP.get(this) as Boolean)
                        val notHaveInTheTemplate = fieldN.get(grammemes) == null && !(fieldP.get(this) as Boolean)
                        if (notHaveInTheTemplate || haveInThePattern) {
                            if (haveInThePattern && MainActivity.patternNow!!.grammeme != null) {
                                val findInGrammeme = Grammeme.findInGrammeme(MainActivity.patternNow!!.grammeme!!, fieldN.name.toLowerCase())
                                if (findInGrammeme != "null") {
                                    if (findInGrammeme != fieldN.get(grammemes)) {
                                        return false
                                    }
                                }
                            }
                        } else {
                            return false
                        }
                    }
                }
            }
        }
        return true
    }


}