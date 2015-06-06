package com.coherentlogic.coherent.datafeed.client;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import com.coherentlogic.coherent.datafeed.adapters.FrameworkEventListenerAdapter;
import com.reuters.rfa.common.Handle;

/**
 * @deprecated This class is no longer necessary.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class ClientBootstrap
    implements ClientBootstrapSpecification {

    private final ExecutorService executorService;

    private final Client client;

    private ClientBootstrap(
        ExecutorService executorService,
        Client client,
        FrameworkEventListenerAdapter frameworkEventListenerAdapter
    ) {
        super();
        this.executorService = executorService;
        this.client = client;
    }

    public Client getClient() {
        return client;
    }

    @Override
    public Handle login(String dacsId) {

        LoginTask loginTask = new LoginTask (client, dacsId);

        Future<?> result = executorService.submit(loginTask);
        
//        executorService.
//        result.
        
        
        return null;
    }
}

class LoginTask implements Runnable {

    private final Client client;

    private final String dacsId;

    public LoginTask(Client client, String dacsId) {
        super();
        this.client = client;
        this.dacsId = dacsId;
    }

    @Override
    public void run() {
        client.login(dacsId);
    }
}