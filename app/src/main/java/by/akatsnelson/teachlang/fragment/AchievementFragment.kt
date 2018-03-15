package by.akatsnelson.teachlang.fragment

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import by.akatsnelson.teachlang.R

/**
 * Created by AKatsnelson on 15.03.2018.
 */
class AchievementFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        /* Games.getAchievementsClient(this, GoogleSignIn.getLastSignedInAccount(this))
                 .unlock(getString(R.string.my_achievement_id));*/
         return inflater!!.inflate(R.layout.fragment_achievement, container, false)
    }
}