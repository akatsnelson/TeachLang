package by.akatsnelson.teachlang.model

/**
 * Created by AKatsnelson on 06.03.2018.
 */
class NodeExclusion(word: List<String>, grammemers: List<List<String>>) : NodeInterface {
    var words:  List<String> = word
    override var grammemes: ArrayList<Grammeme> = arrayListOf()

    init {
        addGrammemers(grammemers)
    }

}