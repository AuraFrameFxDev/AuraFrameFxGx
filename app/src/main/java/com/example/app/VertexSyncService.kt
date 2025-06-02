package com.example.app

import android.app.Service
import android.content.Intent
import android.os.IBinder

class VertexSyncService : Service() {
    override fun onBind(intent: Intent?): IBinder? {
        return null // TODO: Implement if binding is needed
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // TODO: Implement service logic
        return START_NOT_STICKY
    }
}
