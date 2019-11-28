package com.radh.krish

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.charts.LineChart
import helpers.ChartHelper
import helpers.MqttHelper
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended
import org.eclipse.paho.client.mqttv3.MqttMessage


class MainActivity : AppCompatActivity() {
    var mqttHelper: MqttHelper? = null
    var mChart: ChartHelper? = null
   lateinit var setActivity:SettingActivity
    /*
* Smart cast to 'Type' is impossible, because 'variable'
* is a mutable property that could have been changed by this time
* */

    lateinit var chart: LineChart
    //var chart: LineChart? = null
    var dataReceived: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        dataReceived = findViewById(R.id.dataReceived)
        chart = findViewById(R.id.chart)
        mChart = ChartHelper(chart)

       // setActivity=SettingActivity()

        startMqtt()
    }

    private fun startMqtt() {

        val brokerInfo = BrokerInfo(this)
        mqttHelper = MqttHelper(applicationContext,brokerInfo)


        //Log.d("setASDValue", "Value: "+brokerInfo.serverUi)


        mqttHelper!!.mqttAndroidClient.setCallback(object : MqttCallbackExtended {
            override fun connectComplete(b: Boolean, s: String) {
                Log.w("Debug", "Connected")
            }

            override fun connectionLost(throwable: Throwable) {}
            @Throws(Exception::class)
            override fun messageArrived(topic: String, mqttMessage: MqttMessage) {
                Log.w("Debug", mqttMessage.toString())
                dataReceived!!.text = mqttMessage.toString()
                mChart!!.addEntry(java.lang.Float.valueOf(mqttMessage.toString()))
            }

            override fun deliveryComplete(iMqttDeliveryToken: IMqttDeliveryToken) {}
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.setting_menu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.toolMqtt -> {
               // newGame()
                val i = Intent(this, SettingActivity::class.java)
                startActivity(i)
                true
            }
            R.id.help -> {
               // showHelp()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


}