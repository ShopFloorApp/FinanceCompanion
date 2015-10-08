package com.fincomp.mobile.utility;

import com.fincomp.mobile.dc.lov.SyncPreferencesDC;


public class BackgroundProcess implements Runnable {
    public BackgroundProcess() {
        super();
    }

    @Override
    public void run() {
        // TODO Implement this method
        SyncPreferencesDC sync=new SyncPreferencesDC();
        sync.getSyncLovs();
        sync.syncAll();
    }
}
