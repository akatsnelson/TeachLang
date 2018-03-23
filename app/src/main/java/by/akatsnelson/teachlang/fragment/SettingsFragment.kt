package by.akatsnelson.teachlang.fragment

import android.app.Fragment
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import by.akatsnelson.teachlang.MainActivity
import by.akatsnelson.teachlang.MainActivity.Companion.ctx
import by.akatsnelson.teachlang.R


/**
 * Created by AKatsnelson on 09.03.2018.
 */
class SettingsFragment : Fragment() {
    val patterNow = "PATTERN_NOW"
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val v = inflater!!.inflate(R.layout.fragment_settings, container, false)
        val patterns = v.findViewById<Spinner>(R.id.list_patterns)
        val patternsLists = MainActivity.languageService.patterns
        val patternsNames = arrayListOf<String>()
        val sPref: SharedPreferences = ctx.getPreferences(Context.MODE_PRIVATE)
        patternsLists.forEach({ el -> patternsNames.add(el.name!!) })
        val adapter = ArrayAdapter(ctx, android.R.layout.simple_spinner_dropdown_item, patternsNames)
        patterns.adapter = adapter
        if (MainActivity.patternNow == null) {
            patterns.setSelection(0)
        } else for (pos in 0 until patternsLists.size) {
            if (patternsLists[pos].name == MainActivity.patternNow!!.name) {
                patterns.setSelection(pos)
            }
        }
        patterns.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val ed = sPref.edit()
                if (position == 0) {
                    MainActivity.patternNow = null
                    ed.putInt(patterNow, 0)
                } else {
                    ed.putInt(patterNow, position)
                    MainActivity.patternNow = patternsLists[position]
                }
                ed.commit()
            }

        }
        return v
    }
}