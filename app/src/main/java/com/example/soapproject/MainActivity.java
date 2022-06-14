package com.example.soapproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import org.ksoap2.HeaderProperty;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MainActivity extends AppCompatActivity {

    Firebase fb;
    SOAP soap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // a SOAP request will be executed in a separate thread

        // check login

        String sid = null;
        try {
            sid = loginSoap("", "", "");
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("sid: " + sid);

        // check logout
        /**
        Boolean loggedOut = null;
        try {
            loggedOut = logoutSoap("sid");
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("loggedOut: " + loggedOut);
         */


        // check lookupUser (returns user_id by sid und username)
        /**
        Integer user_id = null;
        try {
            user_id = lookupUser("", "");
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("xml: " + user_id);
        */
        // check getCoursesForUser
        /**
        String xml = null;
        try {
            xml = getCoursesForUserSoap("", "");
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("xml: " + xml);
         */

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //fb = new Firebase();
        //System.out.println("list data: " + fb.showCourses());

    }
    public void onClickRead(View view) {
        Intent i = new Intent(MainActivity.this, ReadActivity.class);
        startActivity(i);
    }

    /*
     * login implementation
     */
    public String loginSoap(String client_id, String username, String password) throws ExecutionException, InterruptedException {
        soap = new SOAP();

        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<String> foo = executor.submit(() -> {
            return soap.login(client_id, username, password);
            //return soap.login(client_id, username, password);
        });

        String sid = foo.get();
        System.out.println(sid); // Will wait until the value is complete
        executor.shutdown();
        return sid;
    }


    /*
    logout implementation
     */
    public Boolean logoutSoap(String sid) throws ExecutionException, InterruptedException {
        soap = new SOAP();
        //String sid = new Thread(() -> soap.login(client_id, username, password)).start();

        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<Boolean> foo = executor.submit(() -> {
            return soap.logout(sid);
            //return soap.login(client_id, username, password);
        });

        Boolean loggedOut = foo.get();
        System.out.println(loggedOut); // Will wait until the value is complete
        executor.shutdown();
        return loggedOut ;
    }

    public String getCoursesForUserSoap(String sid, String parameters) throws ExecutionException, InterruptedException {
        soap = new SOAP();

        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<String> foo = executor.submit(() -> {
            return soap.getCoursesForUser(sid, parameters);
            //return soap.login(client_id, username, password);
        });

        String xml = foo.get();
        System.out.println(xml); // Will wait until the value is complete
        executor.shutdown();
        return xml;
    }

    /*
    getCoursesForUserSoap implementation
    */
    public Integer lookupUser(String sid, String username) throws ExecutionException, InterruptedException {
        soap = new SOAP();

        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<Integer> foo = executor.submit(() -> {
            return soap.lookupUser(sid, username);
        });

        Integer user_id = foo.get();
        System.out.println(user_id); // Will wait until the value is complete
        executor.shutdown();
        return user_id;
    }

}