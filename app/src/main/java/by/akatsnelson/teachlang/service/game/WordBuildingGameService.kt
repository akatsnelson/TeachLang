package by.akatsnelson.teachlang.service.game

import by.akatsnelson.teachlang.MainActivity
import by.akatsnelson.teachlang.model.Node
import by.akatsnelson.teachlang.model.PatternDifficultyLevel
import java.util.*

/**
 * Created by AKatsnelson on 11.03.2018.
 */
class WordBuildingGameService {
    var nodes: List<ArrayList<Node>>? = null
    var allNodes: ArrayList<Node>? = null

    init {
        nodes = MainActivity.languageService.nodes
        allNodes = arrayListOf()
        nodes!!.forEach { node -> allNodes!!.addAll(node) }
    }

    fun getRandomQuestion(): Node {
        var random = Random()
        var count = 0
        while (true) {
            val i = random.nextInt(allNodes!!.size)
            val node = allNodes!![i]
            allNodes!!.remove(allNodes!![i])
            if (count >= 2) {
                throw RuntimeException("Incorrect pattern")
            }
            if (allNodes!!.size == 0) {
                nodes!!.forEach { node -> allNodes!!.addAll(node) }
                count++
            }
            if (MainActivity.patternNow != null) {
                if (MainActivity.patternNow!!.isCorrectForPattern(allNodes!![i])) {
                    return node
                }
            } else {
                return node
            }
        }
    }

}