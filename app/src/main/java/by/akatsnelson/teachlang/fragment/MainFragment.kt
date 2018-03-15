package by.akatsnelson.teachlang.fragment

import android.app.Fragment
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import by.akatsnelson.teachlang.R


/**
* Created by AKatsnelson on 09.03.2018.
*/
class MainFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_main,container,false)
    }
}// Required empty public constructor
