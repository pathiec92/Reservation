package com.college.smartcertificate.ui.dashboard

import android.app.DialogFragment
import android.content.Context
import android.nfc.FormatException
import android.nfc.tech.Ndef
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import com.college.smartcertificate.MainActivity
import com.college.smartcertificate.R
import com.college.smartcertificate.data.Encryption
import com.college.smartcertificate.data.Signer.decrypt
import com.college.smartcertificate.data.StudentData.refreshData
import com.college.smartcertificate.data.StudentData.studentEntity
import com.college.smartcertificate.data.StudentEntity
import com.college.smartcertificate.ui.Listener
import kotlinx.android.synthetic.main.fragment_home.*

import java.io.IOException
import java.lang.Exception
import kotlin.contracts.contract

class NFCReadFragment : DialogFragment() {

    private var mListener: Listener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_read, container, false)
        return view
    }



    override fun onAttach(context: Context) {
        super.onAttach(context)
        mListener = context as MainActivity
        mListener!!.onDialogDisplayed()
    }

    override fun onDetach() {
        super.onDetach()
        mListener!!.onDialogDismissed()
    }

    fun onNfcDetected(ndef: Ndef) {

        readFromNFC(ndef)
    }

    private fun readFromNFC(ndef: Ndef) {

        try {
            ndef.connect()
            val ndefMessage = ndef.ndefMessage
            val message = String(ndefMessage.records[0].payload)
            val decrypted = decrypt(message)
            Log.d(TAG, "readFromNFC: $decrypted"  )
            ndef.close()
            when{
                decrypted!=null ->  refreshData(decrypted)
                else -> Toast.makeText(activity,"Data is compromised!!!. Certification Verification failed", Toast.LENGTH_LONG).show()
            }
            mListener!!.onDialogDismissed()

            activity.fragmentManager.beginTransaction().remove(this).commit()

        } catch (e: IOException) {
            e.printStackTrace()

        } catch (e: FormatException) {
            e.printStackTrace()
        }catch (e: Exception) {
            e.printStackTrace()
        }

    }

    companion object {

        val TAG = NFCReadFragment::class.java.simpleName

        fun newInstance(): NFCReadFragment {

            return NFCReadFragment()
        }
    }
}
