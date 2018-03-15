package by.akatsnelson.teachlang.model

import by.akatsnelson.teachlang.MainActivity
import by.akatsnelson.teachlang.MainActivity.Companion.ctx
import by.akatsnelson.teachlang.R

/**
 * Created by AKatsnelson on 06.03.2018.
 */
interface NodeInterface {
    var grammemes: ArrayList<Grammeme>
    fun addGrammemers(grammemers: List<List<String>>) {
        for (grammeme in grammemers) {
            grammemes.add(addGrammeme(grammeme))
        }
    }


    fun getAnswer(): String {
        return if (this.grammemes.size == 1) {
            getGrammemeString(this.grammemes[0])
        } else {
            var answer = ""
            for (i in 0 until this.grammemes.size) {
                answer += ctx.getString(R.string.parsing) + " " +  (i + 1)  + ": " + getGrammemeString(this.grammemes[i]) + "; "
            }

            answer
        }
    }

    private fun getGrammemeString(grammemes: Grammeme): String {
        var answer = ""
        val declaredClasses = grammemes::class.java.declaredClasses
        for (fieldNodeNumber in 0 until declaredClasses.size) { // список переменый нода
            val nodeName = declaredClasses[fieldNodeNumber].simpleName.toLowerCase()
            if (nodeName == "companion") continue
            val fieldNode =  grammemes::class.java.declaredFields.find { el -> nodeName == el.name }!!
            fieldNode.isAccessible = true
            if (fieldNode.get(grammemes) != null) {  // test grammemes
                val declaredFields = R.string::class.java.declaredFields // lang string
                for (langGrammemeNumber in 0 until declaredFields.size) { // перебор
                    val langGrammeme = declaredFields[langGrammemeNumber]
                    langGrammeme.isAccessible = true
                    if (langGrammeme.name == fieldNode.get(grammemes).toString()) {
                        answer += MainActivity.ctx.resources.getString(langGrammeme.getInt(R.string())) + ", "
                    }
                }
            }
        }
        if (answer.length > 2) {
            answer = answer.substring(0, answer.length - 2)
        }
        return answer
    }

    companion object {
        fun addGrammeme(grammemersStr: List<String>): Grammeme {
            var gram = Grammeme()
            for (grammemeStr in grammemersStr) {
                val grammemeClasses = Grammeme::class.java.classes
                for (grammeme in grammemeClasses) {
                    if(grammeme.simpleName != "Companion")
                    (0 until grammeme.enumConstants.size).forEach { valGrammeme ->
                        if (grammeme.enumConstants[valGrammeme].toString() == grammemeStr) {
                            (0 until gram::class.java.declaredFields.size - 2).forEach { grammemeInNode ->
                                val field = gram::class.java.declaredFields[grammemeInNode]
                                if (field.name == grammeme.simpleName.toLowerCase()) {
                                    field.isAccessible = true
                                    field.set(gram, grammeme.enumConstants[valGrammeme])
                                }
                            }
                        }
                    }
                }
            }
            return gram
        }
    }

}