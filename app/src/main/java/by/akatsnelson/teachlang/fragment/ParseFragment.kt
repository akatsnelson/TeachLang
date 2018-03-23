package by.akatsnelson.teachlang.fragment

import android.annotation.SuppressLint
import android.app.Fragment
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import by.akatsnelson.teachlang.MainActivity
import by.akatsnelson.teachlang.R
import by.akatsnelson.teachlang.service.ParseService
import by.akatsnelson.teachlang.service.TLRecognizerListener
import ru.yandex.speechkit.Recognizer
import java.util.*

/**
 * Created by AKatsnelson on 11.03.2018.
 */
class ParseFragment : Fragment() {

    lateinit var btn: Button
    lateinit var text: TextView
    lateinit var textField: EditText
    private lateinit var micOn: ImageButton
    private lateinit var micOff: ImageButton

    @SuppressLint("SetTextI18n", "MissingPermission")
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val recognizer = arrayOfNulls<Recognizer>(1)
        val v = inflater!!.inflate(R.layout.fragment_parse, container, false)
        btn = v.findViewById(R.id.button)
        text = v.findViewById(R.id.textView2)
        textField = v.findViewById(R.id.editText)
        micOn = v.findViewById(R.id.micOn)
        micOff = v.findViewById(R.id.micOff)
        btn!!.setOnClickListener({ view ->
            if (textField.text.toString().trim({ it <= ' ' }) == "") {
                /*Toast toast = Toast.makeText(v.getApplicationContext(), "Введите глагол", Toast.LENGTH_LONG);
                    //toast.show();*/
                Snackbar.make(view, resources.getString(R.string.enter_name), Snackbar.LENGTH_SHORT).setAction("Action", null).show()
                text.text = resources.getString(R.string.answer)
            } else {
                val entWord = textField.text.toString().trim({ it <= ' ' })
                val parse = ParseService().parse(entWord, MainActivity.languageService.nodes, MainActivity.languageService.nodeExclusion)
                var answer = if(parse == null) "404" else parse!!.getAnswer()
                text.text = resources.getString(R.string.answer) + " " + answer
            }
        })
        if (!MainActivity.languageService.getYandexLanguages().contains(Locale.getDefault().language)) {
            micOff.visibility = View.INVISIBLE
            micOn.visibility = View.INVISIBLE
        }
        micOff.setOnClickListener({
            recognizer[0] = Recognizer.create(Locale.getDefault().language, "general", TLRecognizerListener())
            recognizer[0]!!.start()
            tootleMicrophone()
        })
        micOn.setOnClickListener({
            recognizer[0]!!.cancel()
            TLRecognizerListener.createVocalizer.cancel()
            tootleMicrophone()
        })
        return v
    }

    fun tootleMicrophone() {
        if (micOff.visibility == View.INVISIBLE) {
            micOff.visibility = View.VISIBLE
            micOn.visibility = View.INVISIBLE
        } else {
            micOff.visibility = View.INVISIBLE
            micOn.visibility = View.VISIBLE
        }
    }
}