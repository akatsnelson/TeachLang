package by.akatsnelson.teachlang.service

import android.content.Context.MODE_PRIVATE
import android.content.res.Configuration
import android.os.Build
import by.akatsnelson.teachlang.MainActivity.Companion.ctx
import by.akatsnelson.teachlang.R
import by.akatsnelson.teachlang.dao.XmlReader
import by.akatsnelson.teachlang.model.Node
import by.akatsnelson.teachlang.model.NodeExclusion
import by.akatsnelson.teachlang.model.PatternDifficultyLevel
import java.util.*


/**
 * Created by AKatsnelson on 09.03.2018.
 */


class LanguageService {
    lateinit var nodes: List<ArrayList<Node>>
    lateinit var nodeExclusion: List<NodeExclusion>
    lateinit var patterns: List<PatternDifficultyLevel>

    init {
        val langNow = Locale.getDefault().language.toLowerCase()
        if (getAllLanguages().contains(langNow)) {

            updateNodes(langNow)

            updateNodesExclusions(langNow)

            updatePatterns()
        } else {
            replaceLanguage("ru")
        }
    }


    public fun replaceLanguage(lang: String) {
        val mNewLocale = Locale(lang)
        Locale.setDefault(mNewLocale)
        val config = Configuration()
        config.locale = mNewLocale
        ctx.resources.updateConfiguration(config, ctx.resources.displayMetrics)
        val resources = ctx.resources
        val sharedPreferences = ctx.getSharedPreferences("localePref", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("localekey", lang)
        editor.apply()

        val configuration = resources.configuration
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            configuration.setLocale(mNewLocale)
        }
        ctx.resources.updateConfiguration(configuration,
                ctx.resources.displayMetrics)
        ctx.recreate()

        updateNodes(lang)

        updateNodesExclusions(lang)

        updatePatterns()
        //navigationView.menu.getItem(2).isChecked = true
    }

    private fun getAllLanguages(): List<String> {
        val languagesAndFiles = R.raw::class.java.declaredFields
        val languagesFields = languagesAndFiles.filter { el ->
            el.name.contains("nodes_")
        }
        return languagesFields.map { el ->
            el.name.replace("nodes_", "")
        }
    }

    fun getYandexLanguages(): ArrayList<String> {
        return arrayListOf("en", "ru", "uk", "tr")
    }

    private fun updatePatterns() {
        patterns = XmlReader.readPattern(ctx.resources.openRawResource(R.raw.patterns))
        val newPatterns: ArrayList<PatternDifficultyLevel> = arrayListOf()
        for (pattern in patterns) {
            val declaredFields = R.string::class.java.declaredFields
            for (field in declaredFields) {
                if (field.name == pattern.name) {
                    field.isAccessible = true
                    pattern.name = ctx.resources.getString(field.getInt(R.string()))
                    newPatterns.add(pattern)
                }
            }
        }
        patterns = newPatterns
    }

    private fun updateNodesExclusions(langNow: String) {
        val fieldNodesExclusions = R.raw::class.java.declaredFields.find { el -> el.name == "nodes_exclusions_$langNow" }
        fieldNodesExclusions!!.isAccessible = true
        nodeExclusion = XmlReader.readExlusions(ctx.resources.openRawResource(fieldNodesExclusions.getInt(R.raw())))
    }

    private fun updateNodes(langNow: String) {
        val fieldNodes = R.raw::class.java.declaredFields.find { el -> el.name == "nodes_$langNow" }
        fieldNodes!!.isAccessible = true
        nodes = XmlReader.readNodes(ctx.resources.openRawResource(fieldNodes.getInt(R.raw())))
    }
}

