package com.college.smartcertificate

import android.Manifest
import android.app.PendingIntent
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.nfc.tech.Ndef
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.college.smartcertificate.data.StudentData.isRestarted
import com.college.smartcertificate.data.StudentData.nfcId
import com.college.smartcertificate.data.StudentData.refreshed
import com.college.smartcertificate.ui.Listener
import com.college.smartcertificate.ui.dashboard.NFCReadFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), Listener {
    companion object {
        private const val RC_PERMISSION_REQUEST = 1
    }

    private var isDialogDisplayed = false
    private var mNfcAdapter: NfcAdapter? = null
    private var nfcReadFragment: NFCReadFragment = NFCReadFragment.newInstance()
    private val refreshCallback: (Boolean) -> Int = {
        if (it) {
            println("Restarting the activity")
            finish()
            startActivity(intent)
        }
        1
    }
    private var isInit = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        mNfcAdapter = NfcAdapter.getDefaultAdapter(this)

        if (savedInstanceState == null && !isRestarted) {
            nfcReadFragment.show(fragmentManager, NFCReadFragment.TAG)
            //nfcReadFragment.isCancelable = false
        } else {
            isRestarted = false
            initViews()
        }


    }

    override fun onDestroy() {
        super.onDestroy()
        refreshed = null
    }

    override fun invalidCardDetected() {
        println("Invalid card detected, please use valid card")
        Toast.makeText(this, "Invalid card detected, please use valid card", Toast.LENGTH_LONG)
            .show()
        nfcReadFragment.show(fragmentManager, NFCReadFragment.TAG)
        //nfcReadFragment.isCancelable = false
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        this.fragmentManager.beginTransaction().remove(nfcReadFragment).commit()
        val tag = intent?.getParcelableExtra<Tag>(NfcAdapter.EXTRA_TAG)

        Log.d("NFC", "onNewIntent: " + intent?.getAction())

        if (tag != null) {
            Toast.makeText(this, getString(R.string.message_tag_detected), Toast.LENGTH_SHORT)
                .show()
            val ndef = Ndef.get(tag)
            nfcId= Base64.encodeToString(tag.id,0)

            //if (isDialogDisplayed) {
            /*nfcReadFragment =
                fragmentManager.findFragmentByTag(NFCReadFragment.TAG) as NFCReadFragment*/
            nfcReadFragment.onNfcDetected(ndef)
            if (!isInit) {
                isInit = true
                initViews()
            }
            //}
        }
    }

    private fun initViews() {
        container.visibility = View.VISIBLE
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    var messageToWrite: String = ""

    override fun onResume() {
        super.onResume()
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                RC_PERMISSION_REQUEST
            )
        } else {
            println("Permission wr approved")
        }
        val tagDetected = IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED)
        val ndefDetected = IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED)
        val techDetected = IntentFilter(NfcAdapter.ACTION_TECH_DISCOVERED)
        val nfcIntentFilter = arrayOf(techDetected, tagDetected, ndefDetected)

        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            Intent(this, MainActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP),
            0
        )
        mNfcAdapter?.enableForegroundDispatch(this, pendingIntent, nfcIntentFilter, null)

    }

    override fun onPause() {
        super.onPause()
        refreshed = refreshCallback
        mNfcAdapter?.disableForegroundDispatch(this)
    }

    override fun onDialogDisplayed() {
        isDialogDisplayed = true
    }

    override fun onDialogDismissed() {
        isDialogDisplayed = false
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // Ensure we update the availability of our storage provider
            println("Permission wr approved")
            Toast.makeText(this, "Glad you have approved the permission", Toast.LENGTH_SHORT).show()

        } else {
            Toast.makeText(
                this,
                "You must give the Storage permission to use this app",
                Toast.LENGTH_LONG
            ).show()
        }
    }
}
