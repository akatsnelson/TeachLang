package by.akatsnelson.teachlang.fragment

import android.app.AlertDialog
import android.app.Fragment
import android.content.DialogInterface
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import by.akatsnelson.teachlang.MainActivity
import by.akatsnelson.teachlang.MainActivity.Companion.ctx
import by.akatsnelson.teachlang.R
import by.akatsnelson.teachlang.service.ParseService
import by.akatsnelson.teachlang.service.game.BallsService
import by.akatsnelson.teachlang.service.game.WordBuildingGameService


/**
 * Created by AKatsnelson on 12.03.2018.
 */
class BuildWordGameFragment : Fragment() {
    lateinit var enterAnswerInBuildWordGame: Button
    lateinit var questionInBuildWordGame: TextView
    lateinit var ballsInBuildWordGame: TextView
    lateinit var answerInBuildWordGame: EditText
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater!!.inflate(R.layout.fragment_build_word_game, container, false)
        enterAnswerInBuildWordGame = v.findViewById(R.id.enterAnswerInBuildWordGame)
        questionInBuildWordGame = v.findViewById(R.id.questionInBuildWordGame)
        ballsInBuildWordGame = v.findViewById(R.id.ballsInBuildWordGame)
        answerInBuildWordGame = v.findViewById(R.id.answerInBuildWordGame)
        ballsInBuildWordGame.text = MainActivity.ballsService.WORD_BUILD_GAME_SERVICE.toString()
        val wordBuildingGameService = WordBuildingGameService()
        var question = wordBuildingGameService.getRandomQuestion()
        questionInBuildWordGame.text = question.getAnswer()
        enterAnswerInBuildWordGame.setOnClickListener({ view ->
            val word = answerInBuildWordGame.text.toString().trim({ it <= ' ' })
            if (word == "") {
                Snackbar.make(view, resources.getString(R.string.enterGlagol_name), Snackbar.LENGTH_SHORT).setAction("Action", null).show()
            } else {
                val node = ParseService().parse(word, MainActivity.languageService.nodes, MainActivity.languageService.nodeExclusion)
                var balls: Int? = null
                var gameOver: Boolean? = null
                if (node != null) {
                    for (it in node.grammemes
                            .filter { question.grammemes[0] == it }) {
                        val pair = MainActivity.ballsService.addBalls(10, BallsService.Games.WORD_BUILD_GAME_SERVICE)
                        balls = pair.first
                        gameOver = pair.second
                    }
                }


                if (balls == null) {
                    val pair = MainActivity.ballsService.addBalls(-5, BallsService.Games.WORD_BUILD_GAME_SERVICE)
                    balls = pair.first
                    gameOver = pair.second
                    val builder = AlertDialog.Builder(MainActivity.ctx)
                    val answer = node?.getAnswer() ?: "404"
                    builder.setTitle(MainActivity.ctx.resources.getString(R.string.wrongTitle))
                            .setMessage(ctx.resources.getString(R.string.wrong) + " " + question.getAnswer() + System.getProperty("line.separator")
                                    + System.getProperty("line.separator") + ctx.resources.getString(R.string.yourAnswer) + " " + answer + System.getProperty("line.separator")
                                    + System.getProperty("line.separator") + ctx.resources.getString(R.string.yourWordIs) + " " + word)
                            .setIcon(R.drawable.ic_error_black_24dp)
                            .setCancelable(false)
                            .setNegativeButton(ctx.resources.getString(R.string.i_realized),
                                    DialogInterface.OnClickListener { dialog, id -> dialog.cancel() })
                    val alert = builder.create()
                    alert.show()
                }
                if (gameOver!!) {
                    Snackbar.make(view, resources.getString(R.string.game_over), Snackbar.LENGTH_SHORT).setAction("Action", null).show()
                }
                ballsInBuildWordGame.text = balls.toString()
                question = wordBuildingGameService.getRandomQuestion()
                questionInBuildWordGame.text = question.getAnswer()
                answerInBuildWordGame.setText("")
            }
        })
        return v
    }

}