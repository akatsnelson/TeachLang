package by.akatsnelson.teachlang.service

import by.akatsnelson.teachlang.model.Node
import by.akatsnelson.teachlang.model.NodeExclusion
import by.akatsnelson.teachlang.model.NodeInterface

/**
 * Created by AKatsnelson on 06.03.2018.
 */

class ParseService {
    fun parse(word: String, nodes: List<List<Node>>, nodesExclusions: List<NodeExclusion>): NodeInterface? {
        return searchWithExclusion(nodes, nodesExclusions, word)
    }

    private fun searchWithExclusion(nodes: List<List<Node>>, nodesExclusions: List<NodeExclusion>, word: String): NodeInterface? {
        var word = word.toLowerCase()
        for (nodeExclusion in nodesExclusions) {
            nodeExclusion.words
                    .filter { it == word }
                    .forEach { return nodeExclusion }
        }

        return search(nodes, word)
    }

    private fun search(nodes: List<List<Node>>, word: String): NodeInterface? {
        @Suppress("NAME_SHADOWING")
        var word: String = word
        //step one
        for (nodeWithAuxiliaryVerb in nodes[0]) {
            val wordParts = word.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            if (wordParts.size == 2) {
                for (auxiliaryVerb in nodeWithAuxiliaryVerb.auxiliaryVerb!!) {
                    if (wordParts[0] == auxiliaryVerb || wordParts[1] == auxiliaryVerb) {
                        word = if (wordParts[0] == auxiliaryVerb) {
                            wordParts[1]
                        } else {
                            wordParts[0]
                        }
                        if (prefixTest(nodeWithAuxiliaryVerb, word)) {
                            return nodeWithAuxiliaryVerb
                        }

                    }
                }
            }
        }

        //step two
        for (nodeWithPrefix in nodes[1]) {
            if (prefixTest(nodeWithPrefix, word))
                return nodeWithPrefix
        }

        //step three
        for (nodeWithEnding in nodes[2]) {
            if (endingTest(nodeWithEnding, word)) return nodeWithEnding
        }

        return null
    }

    private fun prefixTest(nodeWithPrefix: Node, word: String): Boolean {
        if (nodeWithPrefix.prefix!![0] != "") {
            if (nodeWithPrefix.prefix!!.any { (word.length > it.length) && (word.substring(0, it.length) == it) }) {
                if (endingTest(nodeWithPrefix, word)) {
                    return true
                }
            }
        }
        return false
    }

    private fun endingTest(nodeWithEnding: Node, word: String): Boolean {
        if (nodeWithEnding.ending.any { word.length > it.length && word.substring(word.length - it.length) == it }) {
            return true
        }
        return false
    }
}
