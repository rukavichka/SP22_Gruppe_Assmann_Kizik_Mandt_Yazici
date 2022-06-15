package com.example.Model;

import com.example.soapproject.SOAP;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class VerificationProcess {

    public String sid;

    public static final VerificationProcess instance = new VerificationProcess();

    private VerificationProcess(){};

    public static  VerificationProcess getInstance(){
        return instance;
    }

    /*
     * login implementation with SOAP
     */
    public String login(String client_id, String username, String password) throws ExecutionException, InterruptedException {
        SOAP soap = new SOAP();

        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<String> foo = executor.submit(() -> {
            return soap.login(client_id, username, password);
            //return soap.login(client_id, username, password);
        });

        sid = foo.get();
        System.out.println("SID" + sid); // Will wait until the value is complete
        executor.shutdown();
        return sid;
    }

    /*
    logout implementation with SOAP
     */
    public Boolean logout(String s) throws ExecutionException, InterruptedException {
        SOAP soap = new SOAP();
        //String sid = new Thread(() -> soap.login(client_id, username, password)).start();
        System.out.println("SID logout: " + s);
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<Boolean> foo = executor.submit(() -> {
            return soap.logout(s);
            //return soap.login(client_id, username, password);
        });

        Boolean loggedOut = foo.get();
        System.out.println(loggedOut); // Will wait until the value is complete
        executor.shutdown();
        return loggedOut;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

}
