package com.example.Model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.SoapAPI.SOAP;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.lang.reflect.Field;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

@RunWith(AndroidJUnit4.class)
public class VerificationProcessTest {

    SOAP soap = new SOAP();

    /**
     * Test for Login with the existing ilias username and password
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    public void LoginCorrectDataTest() throws ExecutionException, InterruptedException {

        VerificationProcess vp = VerificationProcess.getInstance();
        /**
        try {
            Field field = VerificationProcess.class.getDeclaredField("instance");
            field.setAccessible(true);
            field.set(vp, VerificationProcess.getInstance());
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        */

        String client_id = "mriliastest";
        String username = "Kizik";
        String password = "Bogdasik19";
        /**
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<String> foo = executor.submit(() -> {
            return soap.login(client_id, username, password);
        });
        String sid = foo.get();
        executor.shutdown();
         */
        String sid = vp.login(client_id, username, password);
        assertNotNull(sid);

    }


    /**
     * Test for Login with the not existing ilias username and password
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    public void LoginIncorrectDataTest() throws ExecutionException, InterruptedException {
        VerificationProcess vp = VerificationProcess.getInstance();
        String client_id = "mriliastest";
        String username = "abc";
        String password = "abc";

        /**
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<String> foo = executor.submit(() -> {
            return soap.login(client_id, username, password);
        });
        String sid = foo.get();
        executor.shutdown();
        */
        String sid = vp.login(client_id, username, password);
        Assert.assertNull(sid);
    }

    /**
     * Test for Logout for the correctly loggedIn user
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    public void LogoutLoginedUserTest() throws ExecutionException, InterruptedException {
        VerificationProcess vp = VerificationProcess.getInstance();
        // login part with the correct login data
        String client_id = "mriliastest";
        String username = "Kizik";
        String password = "Bogdasik19";
        /**
        ExecutorService executorLogin = Executors.newSingleThreadExecutor();
        Future<String> fooLogin = executorLogin.submit(() -> {
            return soap.login(client_id, username, password);
        });
        String sid = fooLogin.get();
        executorLogin.shutdown();
        */
        String sid = vp.login(client_id, username, password);

        // logout part with the correct login data
        /**
        ExecutorService executorLogout = Executors.newSingleThreadExecutor();
        Future<Boolean> fooLogout = executorLogout.submit(() -> {
            return soap.logout(sid);
        });
        Boolean loggedOut = fooLogout.get();
        executorLogout.shutdown();
         */
        Boolean loggedOut = vp.logout(sid);
        assertTrue(loggedOut);
    }

    /**
     * Test for Logout for the not loggedIn(null) user
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    public void LogoutNullUserTest() throws ExecutionException, InterruptedException {

        VerificationProcess vp = VerificationProcess.getInstance();
        /**
        ExecutorService executorLogout = Executors.newSingleThreadExecutor();
        Future<Boolean> fooLogout = executorLogout.submit(() -> {
            return soap.logout(null);
        });
        Boolean loggedOut = fooLogout.get();
        executorLogout.shutdown();
         */
        Boolean loggedOut = vp.logout(null);
        assertFalse(loggedOut);
    }

}
