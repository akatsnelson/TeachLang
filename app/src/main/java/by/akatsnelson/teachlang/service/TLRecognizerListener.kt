package by.akatsnelson.teachlang.service

import android.annotation.SuppressLint
import android.location.Location
import by.akatsnelson.teachlang.MainActivity
import by.akatsnelson.teachlang.MainActivity.Companion.ctx
import by.akatsnelson.teachlang.R
import ru.yandex.speechkit.*
import java.util.*

/**
 * Created by boyst on 18.01.2018.
 */
class TLRecognizerListener : RecognizerListener {
    override fun onError(p0: Recognizer?, p1: Error?) {
        print("")
    }

    override fun onPartialResults(p0: Recognizer?, p1: Recognition?, p2: Boolean) {
    }

    override fun onRecordingDone(p0: Recognizer?) {
    }

    override fun onSoundDataRecorded(p0: Recognizer?, p1: ByteArray?) {
    }

    override fun onPowerUpdated(p0: Recognizer?, p1: Float) {
        // print("power: $p1")
    }

    override fun onRecordingBegin(p0: Recognizer?) {
        print("start Recording")
    }

    override fun onSpeechEnds(p0: Recognizer?) {
        print("end Recording")
    }

    override fun onSpeechDetected(p0: Recognizer?) {
        print("speech tyt")
    }

    @SuppressLint("SetTextI18n")
    override fun onRecognitionDone(p0: Recognizer?, p1: Recognition?) {
        val message = p1?.bestResultText
        var answer = ""

        if (message != null) {
            answer = ParseService().parse(message.trim(), MainActivity.languageService.nodes, MainActivity.languageService.nodeExclusion)!!.getAnswer()
            MainActivity.fParse.text.text = ctx.resources.getString(R.string.answer) + " " + answer
            MainActivity.fParse.textField.setText(message)
        }
        MainActivity.fParse.tootleMicrophone()
        answer = answer.replace("III", "третье")
        answer = answer.replace("3", "третье")
        answer = answer.replace("II", "второе")
        answer = answer.replace("2", "второе")
        answer = answer.replace("I", "первое")
        answer = answer.replace("1", "первое")
        answer = answer.replace("ср.р.", "средний род")
        answer = answer.replace("ср. р.", "средний род")
        answer = answer.replace("м. р.", "мужской род")
        answer = answer.replace("м.р.", "мужской род")
        answer = answer.replace("ж. р.", "женский род")
        answer = answer.replace("ж.р.", "женский род")
        answer = answer.replace("ед. ч.", "единственное число")
        answer = answer.replace("ед.ч.", "единственное число")
        answer = answer.replace("мн. ч.", "множественное число")
        answer = answer.replace("мн.ч.", "множественное число")
        answer = answer.replace("наст.вр.", "настоящее время")
        answer = answer.replace("наст. вр.", "настоящее время")
        answer = answer.replace("Им. п.", "именительный падеж")
        answer = answer.replace("Им.п.", "именительный падеж")
        answer = answer.replace("Р. п.", "родительный падеж")
        answer = answer.replace("Р.п.", "родительный падеж")
        answer = answer.replace("Д. п.", "дательный падеж")
        answer = answer.replace("Д.п.", "дательный падеж")
        answer = answer.replace("В. п.", "винительный падеж")
        answer = answer.replace("В.п.", "винительный падеж")
        answer = answer.replace("Т. п.", "творительный падеж")
        answer = answer.replace("Т.п.", "творительный падеж")
        answer = answer.replace("П. п.", "предложный падеж")
        answer = answer.replace("П.п.", "предложный падеж")
        createVocalizer = Vocalizer.createVocalizer(Locale.getDefault().language, answer, true)
        createVocalizer.start()
        createVocalizer.play()

    }

    companion object {
        var createVocalizer:Vocalizer = Vocalizer.createVocalizer(Locale.getDefault().language, "", false)
    }


}