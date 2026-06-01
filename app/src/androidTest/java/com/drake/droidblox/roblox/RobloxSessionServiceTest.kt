package com.drake.droidblox.roblox

//import android.content.ComponentName
//import android.content.Context
//import android.content.Intent
//import android.content.ServiceConnection
//import android.os.IBinder
//import androidx.test.core.app.ApplicationProvider
//import androidx.test.ext.junit.runners.AndroidJUnit4
//import com.drake.IRobloxSession
//import com.drake.logger.AndroidLogger
//import com.drake.logger.Logger
//import org.junit.Test
//import org.junit.runner.RunWith
//
//@RunWith(AndroidJUnit4::class)
//class RobloxSessionServiceTest {
//    companion object {
//        private const val TAG = "RobloxSessionServiceTest"
//    }
//
//    private val logger: Logger = AndroidLogger
//    private val context: Context = ApplicationProvider.getApplicationContext()
//    private var mService: IRobloxSession? = null
//
//    @Test fun testAll() {
//        logger.i(TAG, "Binding into service")
//        context.bindService(
//            Intent(context, RobloxSessionService::class.java),
//            object : ServiceConnection {
//                override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
//                    logger.d(TAG, "onServiceConnected()")
//                    mService = IRobloxSession.Stub.asInterface(service)
//                }
//
//                override fun onServiceDisconnected(name: ComponentName?) {
//                    logger.d(TAG, "onServiceDisconnected()")
//                    mService = null
//                }
//            },
//            Context.BIND_AUTO_CREATE
//        )
//
//        logger.i(TAG, "Testing game joining")
//        mService?.gameJoiningEntry(142823291, "return-is-a-furry")
//
//        logger.i(TAG, "Testing universe joining")
//        mService?.universeJoiningEntry(1, 66654135)
//
//        logger.i(TAG, "Testing udmux entry")
//        mService?.udmuxEntry("128.116.97.33")
//
//        logger.i(TAG, "Testing BloxstrapRPC 1")
//        mService?.gameMessageEntry("{\"command\": \"SetRichPresence\", \"data\": {\"details\": \"meowwers!!\", \"state\": \"testing bloxstraprpc\", \"timeStart\": 1774337668136, \"largeImage\": {\"assetId\": 11176073582, \"hoverText\": \"meowers\", \"clear\": false, \"reset\": false}}}")
//
//        logger.i(TAG, "Testing Bloxstrap 2")
//        mService?.gameMessageEntry("{\"command\": \"SetLaunchData\", \"data\": \"returnisafurry\"}")
//
//        while (true) {
//
//        }
//
//    }
//}