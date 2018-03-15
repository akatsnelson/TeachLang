package by.akatsnelson.teachlang.dao

import by.akatsnelson.teachlang.model.Node
import by.akatsnelson.teachlang.model.NodeExclusion
import by.akatsnelson.teachlang.model.NodeInterface
import by.akatsnelson.teachlang.model.PatternDifficultyLevel
import org.w3c.dom.Document
import java.io.InputStream
import java.lang.Boolean
import java.util.regex.Pattern
import javax.xml.parsers.DocumentBuilderFactory
import kotlin.String

/**
 * Created by AKatsnelson on 05.03.2018.
 */
class XmlReader {
    companion object {
        fun readNodes(inputStream: InputStream): ArrayList<ArrayList<Node>> {
            val xmlDoc: Document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(inputStream)
            xmlDoc.documentElement.normalize()
            val nodes = ArrayList<ArrayList<Node>>()
            val auxiliaryVerbElem = xmlDoc.getElementsByTagName("auxiliary_verb").item(0)
            val prefixElem = xmlDoc.getElementsByTagName("prefix").item(0)
            val endingElem = xmlDoc.getElementsByTagName("ending").item(0)
            nodes.add(getAllNodesWithAuxiliaryVerb(auxiliaryVerbElem))
            nodes.add(getAllNodesWithPrefix(prefixElem))
            nodes.add(getAllNodesWithEnding(endingElem))
            return nodes
        }

        fun readExlusions(inputStream: InputStream): ArrayList<NodeExclusion> {
            val xmlDoc: Document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(inputStream)
            xmlDoc.documentElement.normalize()
            val wordElem = xmlDoc.getElementsByTagName("nodes_exclusions").item(0)
            return getAllNodesExclusions(wordElem)
        }

        fun readPattern(inputStream: InputStream): List<PatternDifficultyLevel> {
            val patterns = arrayListOf<PatternDifficultyLevel>()
            val xmlDoc: Document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(inputStream)
            xmlDoc.documentElement.normalize()
            val patternsNode = xmlDoc.getElementsByTagName("patterns").item(0)
            val childNodes = patternsNode.childNodes
            for (elemNumber in 0 until childNodes.length) {
                if (childNodes.item(elemNumber).nodeName == "pattern") {
                    val pattern = PatternDifficultyLevel()
                    for (fieldNum in 0 until pattern::class.java.declaredFields.size - 2) {
                        val field = pattern::class.java.declaredFields[fieldNum]
                        field.isAccessible = true
                        val namedItem = childNodes.item(elemNumber).attributes.getNamedItem(field.name)
                        when {
                            field.name == "name" -> field.set(pattern, namedItem.nodeValue)
                            field.name == "grammeme" -> {
                                if (namedItem != null) {
                                    val grammeme = NodeInterface.addGrammeme(getGrammemes(namedItem.nodeValue)[0])
                                    field.set(pattern, grammeme)
                                }
                            }
                            else -> {
                                if (namedItem != null) {
                                    field.set(pattern, Boolean.valueOf(namedItem.nodeValue))
                                } else field.set(pattern, null)
                            }
                        }
                    }
                    patterns.add(pattern)
                }
            }
            return patterns
        }

        private fun getAllNodesWithEnding(nodesElem: org.w3c.dom.Node): ArrayList<Node> {
            val nodes = ArrayList<Node>()
            val nodesElements = nodesElem.childNodes
            for (elemNumber in 0 until nodesElements.length) {
                if (nodesElements.item(elemNumber).nodeName == "node") {
                    val ending = nodesElements.item(elemNumber).attributes.getNamedItem("ending")
                    val grammeme = nodesElements.item(elemNumber).attributes.getNamedItem("grammeme")
                    var endingR: List<String> = arrayListOf("")
                    if (ending != null) {
                        if (ending.nodeValue.replace(" ", "") != "") {
                            endingR = ending.nodeValue.split(", ")
                        }
                    }
                    nodes.add(Node(ending = endingR, grammemers = getGrammemes(grammeme.nodeValue)))
                }
            }
            return nodes
        }

        private fun getAllNodesWithPrefix(nodesElem: org.w3c.dom.Node): ArrayList<Node> {
            val nodes = ArrayList<Node>()
            val nodesElements = nodesElem.childNodes
            for (elemNumber in 0 until nodesElements.length) {
                if (nodesElements.item(elemNumber).nodeName == "node") {
                    val ending = nodesElements.item(elemNumber).attributes.getNamedItem("ending")
                    val prefix = nodesElements.item(elemNumber).attributes.getNamedItem("prefix")
                    val grammeme = nodesElements.item(elemNumber).attributes.getNamedItem("grammeme")
                    var prefixR: List<String> = arrayListOf("")
                    var endingR: List<String> = arrayListOf("")
                    if (prefix != null) {
                        if (prefix.nodeValue.replace(" ", "") != "") {
                            prefixR = prefix.nodeValue.split(", ")
                        }
                    }
                    if (ending != null) {
                        if (ending.nodeValue.replace(" ", "") != "") {
                            endingR = ending.nodeValue.split(", ")
                        }
                    }
                    nodes.add(Node(prefix = prefixR, ending = endingR, grammemers = getGrammemes(grammeme.nodeValue)))
                }
            }
            return nodes
        }

        private fun getAllNodesWithAuxiliaryVerb(nodesElem: org.w3c.dom.Node): ArrayList<Node> {
            val nodes = ArrayList<Node>()
            val nodesElements = nodesElem.childNodes
            for (elemNumber in 0 until nodesElements.length) {
                if (nodesElements.item(elemNumber).nodeName == "node") {
                    val ending = nodesElements.item(elemNumber).attributes.getNamedItem("ending")
                    val prefix = nodesElements.item(elemNumber).attributes.getNamedItem("prefix")
                    val auxiliaryVerb = nodesElements.item(elemNumber).attributes.getNamedItem("auxialiary_verb")
                    val grammeme = nodesElements.item(elemNumber).attributes.getNamedItem("grammeme")
                    var auxiliaryVerbR: List<String> = arrayListOf("")
                    var prefixR: List<String> = arrayListOf("")
                    var endingR: List<String> = arrayListOf("")
                    if (auxiliaryVerb != null) {
                        if (auxiliaryVerb.nodeValue.replace(" ", "") != "") {
                            auxiliaryVerbR = auxiliaryVerb.nodeValue.split(", ")
                        }
                    }
                    if (prefix != null) {
                        if (prefix.nodeValue.replace(" ", "") != "") {
                            prefixR = prefix.nodeValue.split(", ")
                        }
                    }
                    if (ending != null) {
                        if (ending.nodeValue.replace(" ", "") != "") {
                            endingR = ending.nodeValue.split(", ")
                        }
                    }
                    nodes.add(Node(auxiliaryVerb = auxiliaryVerbR, prefix = prefixR, ending = endingR, grammemers = getGrammemes(grammeme.nodeValue)))
                }
            }
            return nodes
        }

        private fun getAllNodesExclusions(nodesElem: org.w3c.dom.Node): ArrayList<NodeExclusion> {
            val nodesExclusions = ArrayList<NodeExclusion>()
            val nodesExclusionsElements = nodesElem.childNodes
            for (elemNumber in 0 until nodesExclusionsElements.length) {
                if (nodesExclusionsElements.item(elemNumber).nodeName == "node") {
                    val word = nodesExclusionsElements.item(elemNumber).attributes.getNamedItem("words")
                    val grammeme = nodesExclusionsElements.item(elemNumber).attributes.getNamedItem("grammeme")
                    nodesExclusions.add(NodeExclusion(word = word.nodeValue.split(", "), grammemers = getGrammemes(grammeme.nodeValue)))
                }
            }
            return nodesExclusions
        }

        private fun getGrammemes(gramemmes: String): List<List<String>> {
            val matcher = Pattern.compile("\\[.*?]").matcher(gramemmes)
            val grameemes = arrayListOf<List<String>>()
            while (matcher.find()) {
                var element = matcher.group(0).split(", ")
                element = element.map { elem ->
                    elem.replace("[", "")
                }
                element = element.map { elem ->
                    elem.replace("]", "")
                }
                grameemes.add(element)
            }
            return grameemes
        }
    }
}
