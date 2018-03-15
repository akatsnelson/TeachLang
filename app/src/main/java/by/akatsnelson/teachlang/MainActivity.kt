package by.akatsnelson.teachlang

import android.Manifest
import android.annotation.SuppressLint
import android.app.FragmentTransaction
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import by.akatsnelson.teachlang.dao.XmlReader
import by.akatsnelson.teachlang.fragment.*
import by.akatsnelson.teachlang.model.PatternDifficultyLevel
import by.akatsnelson.teachlang.service.LanguageService
import by.akatsnelson.teachlang.service.game.BallsService
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import ru.yandex.speechkit.SpeechKit

/**
 * Created by AKatsnelson on 05.03.2018.
 */

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private var fragmentTransaction: FragmentTransaction? = null
    @SuppressLint("CommitTransaction")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
        MainActivity.ctx = this
        MainActivity.languageService = LanguageService()
        MainActivity.patterns = XmlReader.readPattern(ctx.resources.openRawResource(R.raw.patterns))
        MainActivity.ballsService = BallsService()

        print(patterns)

        fMain = MainFragment()
        fBuildWordGame = BuildWordGameFragment()
        fParse = ParseFragment()
        fSettings = SettingsFragment()
        fInfo = InfoFragment()


        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_WIFI_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_WIFI_STATE)) {

            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    ActivityCompat.requestPermissions(this,
                            arrayOf(Manifest.permission.ACCESS_WIFI_STATE),
                            1)
                }
            }
        }
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.RECORD_AUDIO)) {
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    ActivityCompat.requestPermissions(this,
                            arrayOf(Manifest.permission.RECORD_AUDIO),
                            1)
                }
            }
        }

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction!!.replace(R.id.content_main, fMain)
        fragmentTransaction!!.commit()

        nav_view.setNavigationItemSelectedListener(this)
        SpeechKit.getInstance().configure(applicationContext, "3d335ed8-d48b-4f77-86b6-eefce924e2f9")
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    @SuppressLint("ResourceType")
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        fragmentTransaction = fragmentManager.beginTransaction()
        when (item.itemId) {
            R.id.nav_main -> {
                fragmentTransaction!!.replace(R.id.content_main, fMain)
            }
            R.id.nav_buildWordGame -> {
                fragmentTransaction!!.replace(R.id.content_main, fBuildWordGame)
            }
            R.id.nav_parse -> {
                fragmentTransaction!!.replace(R.id.content_main, fParse)
            }
            R.id.nav_settings -> {
                fragmentTransaction!!.replace(R.id.content_main, fSettings)
            }
            R.id.nav_about_program -> {
                fragmentTransaction!!.replace(R.id.content_main, fInfo)
            }
        }
        fragmentTransaction!!.commit()

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var ctx: AppCompatActivity
        lateinit var languageService: LanguageService
        lateinit var fMain: MainFragment
        lateinit var fBuildWordGame: BuildWordGameFragment
        @SuppressLint("StaticFieldLeak")
        lateinit var fParse: ParseFragment
        lateinit var fSettings: SettingsFragment
        lateinit var fInfo: InfoFragment
        lateinit var ballsService: BallsService
        lateinit var patterns: List<PatternDifficultyLevel>
        var patternNow: PatternDifficultyLevel? = null
    }
}
