package by.akatsnelson.teachlang.service.game

import android.annotation.SuppressLint
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import by.akatsnelson.teachlang.MainActivity.Companion.ctx


/**
 * Created by inark on 14.03.2018.
 */
class BallsService {
    var WORD_BUILD_GAME_SERVICE: Int = 0

    init {
        val gameBallsFields = BallsService::class.java.declaredFields.filter { el -> el.type == Int::class.java }
        for (field in gameBallsFields) {
            val sPref: SharedPreferences = ctx.getPreferences(MODE_PRIVATE)
            val ed = sPref.getInt(field.name, 0)
            field.set(this, ed)
        }
    }

    @SuppressLint("ApplySharedPref")
    fun addBalls(ballsToAdd: Int, game: BallsService.Games): Pair<Int, Boolean> {
        val gameBallsField = BallsService::class.java.declaredFields.find { el -> el.name == game.name }
        gameBallsField!!.isAccessible = true
        var balls = gameBallsField.getInt(this)
        balls += ballsToAdd
        gameBallsField.setInt(this, balls)
        val gameOver: Boolean
        gameOver = if (balls <= 0) {
            gameBallsField.setInt(this, 0)
            balls = gameBallsField.getInt(this)
            true
        } else false
        val sPref: SharedPreferences = ctx.getPreferences(MODE_PRIVATE)
        val ed = sPref.edit()
        ed.putInt(game.name, balls)
        ed.commit()
        return Pair(balls, gameOver)
    }

    enum class Games {
        WORD_BUILD_GAME_SERVICE
    }
}