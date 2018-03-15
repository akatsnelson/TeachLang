package by.akatsnelson.teachlang.model


/**
 *
 * Created by AKatsnelson on 04.03.2018.
 */


class Node : NodeInterface {
    var auxiliaryVerb: List<String>? = null
    var prefix: List<String>? = null
    var ending: List<String>
    override var grammemes: ArrayList<Grammeme> = arrayListOf()

    constructor(ending: List<String>, prefix: List<String>, auxiliaryVerb: List<String>, grammemers: List<List<String>>) {
        this.ending = ending
        this.prefix = prefix
        this.auxiliaryVerb = auxiliaryVerb
        addGrammemers(grammemers)
    }

    constructor(prefix: List<String>?, ending: List<String>, grammemers: List<List<String>>) {
        this.prefix = prefix
        this.ending = ending
        addGrammemers(grammemers)
    }

    constructor(ending: List<String>, grammemers: List<List<String>>) {
        this.ending = ending
        addGrammemers(grammemers)
    }


    /*var form
    if(grammemes.form != null) {
        = when () {
            Grammeme.Form.V -> ctx.resources.getString(R.string.V)
            Grammeme.Form.ger -> ctx.resources.getString(R.string.ger)
            Grammeme.Form.inf -> ctx.resources.getString(R.string.inf)
            Grammeme.Form.partcp -> ctx.resources.getString(R.string.partcp)
        }
    }*/
}

