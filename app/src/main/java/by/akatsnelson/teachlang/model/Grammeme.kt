package by.akatsnelson.teachlang.model

/**
 * Created by AKatsnelson on 04.03.2018.
 */
class Grammeme {
    var form: Form? = null
    var mood: Mood? = null
    var tnase: Tnase? = null
    var number: Number? = null
    var preson: Preson? = null
    var gender: Gender? = null
    var aspect: Aspect? = null
    var voice: Voice? = null
    var transitiviness: Transitiviness? = null
    var conjugntion: Conjugntion? = null
    var brevity: Brevity? = null
    var reflexiveness: Reflexiveness? = null
    var case: Case? = null
    var comment: Comment? = null

    enum class Form {
        V, ger, inf, partcp
    }

    enum class Mood {
        indic, imper, cond
    }

    enum class Tnase {
        praet, praes, fut
    }

    enum class Number {
        sg, pl
    }

    enum class Preson {
        p1, p2, p3
    }

    enum class Gender {
        m, f, n, mn, mf, fn
    }

    enum class Aspect {
        ipf, pf
    }

    enum class Voice {
        act, pass
    }

    enum class Transitiviness {
        tran, intr
    }

    enum class Conjugntion {
        conjugI, conjugII
    }

    enum class Brevity {
        brev, plen
    }

    enum class Reflexiveness {
        refl, nonRefl
    }

    enum class Case {
        nom, gen, dat, acc, ins, abl
    }

    enum class Comment {
        noPref
    }


    companion object {
        fun findInGrammeme(grammeme: Grammeme, fieldName: String): String {
            val declaredFields = grammeme::class.java.declaredFields
            for (field in declaredFields) {
                if (field.name == fieldName) {
                    field.isAccessible = true
                    return field.get(grammeme).toString()
                }
            }
            return "null"
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Grammeme

        if (form != other.form) return false
        if (mood != other.mood) return false
        if (tnase != other.tnase) return false
        if (number != other.number) return false
        if (preson != other.preson) return false
        if (gender != other.gender) return false
        if (aspect != other.aspect) return false
        if (voice != other.voice) return false
        if (transitiviness != other.transitiviness) return false
        if (conjugntion != other.conjugntion) return false
        if (brevity != other.brevity) return false
        if (reflexiveness != other.reflexiveness) return false
        if (case != other.case) return false

        return true
    }

    override fun hashCode(): Int {
        var result = form?.hashCode() ?: 0
        result = 31 * result + (mood?.hashCode() ?: 0)
        result = 31 * result + (tnase?.hashCode() ?: 0)
        result = 31 * result + (number?.hashCode() ?: 0)
        result = 31 * result + (preson?.hashCode() ?: 0)
        result = 31 * result + (gender?.hashCode() ?: 0)
        result = 31 * result + (aspect?.hashCode() ?: 0)
        result = 31 * result + (voice?.hashCode() ?: 0)
        result = 31 * result + (transitiviness?.hashCode() ?: 0)
        result = 31 * result + (conjugntion?.hashCode() ?: 0)
        result = 31 * result + (brevity?.hashCode() ?: 0)
        result = 31 * result + (reflexiveness?.hashCode() ?: 0)
        result = 31 * result + (case?.hashCode() ?: 0)
        return result
    }

}