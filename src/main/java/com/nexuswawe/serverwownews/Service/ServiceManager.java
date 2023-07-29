package com.nexuswawe.serverwownews.Service;

import com.nexuswawe.serverwownews.Core.IsendDataToRtDb;

import java.io.IOException;

public class ServiceManager implements AutoCloseable
{

    private  IsendDataToRtDb _sendDataToRtDb;

    //Injection Database Ref
    public  ServiceManager(IsendDataToRtDb sendDataToRtDb)  {
        _sendDataToRtDb = sendDataToRtDb;
    }

    public void SendData() throws IOException, InterruptedException {
        _sendDataToRtDb.SendDataToRtDatabase();

    }

    @Override
    public void close() throws Exception {

    }
}
