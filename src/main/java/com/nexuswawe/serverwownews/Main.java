package com.nexuswawe.serverwownews;



import com.nexuswawe.serverwownews.Core.InitialFirebase;
import com.nexuswawe.serverwownews.Service.SendPosts;
import com.nexuswawe.serverwownews.Service.IsPostUpdatedThenSendNotification;
import com.nexuswawe.serverwownews.Service.SendTokens;
import com.nexuswawe.serverwownews.Service.ServiceManager;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {

        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

       executor.scheduleAtFixedRate(new Runnable() {
            public void run() {



                try (ServiceManager serviceManager = new ServiceManager(new SendPosts())){
                    serviceManager.SendData();
                }catch (Exception e){run();}

                try(ServiceManager serviceManager = new ServiceManager(new SendTokens())){
                    serviceManager.SendData();
                }catch (Exception e){run();}

                try(IsPostUpdatedThenSendNotification isPostUpdatedThenSendNotification = new IsPostUpdatedThenSendNotification(new InitialFirebase())){
                    isPostUpdatedThenSendNotification.NotificationAndPostManager();
                } catch (Exception e) {run();}


                System.out.println("********************************************************");
                System.out.println("Cycle is over...");
                System.out.println("********************************************************");


            }
        }, 0, 15, TimeUnit.MINUTES);
    }
}




