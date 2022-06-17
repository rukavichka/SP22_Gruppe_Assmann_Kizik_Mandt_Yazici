package com.example.Model;

import com.example.SoapAPI.SOAP;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class VerificationProcess {

    public String sid;
    public int userId;

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
        });

        Boolean loggedOut = foo.get();
        System.out.println(loggedOut); // Will wait until the value is complete
        executor.shutdown();
        return loggedOut;
    }

    /*
    get user id
    */
    public Integer lookupUser(String sid, String username) throws ExecutionException, InterruptedException {
        SOAP soap = new SOAP();
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<Integer> foo = executor.submit(() -> {
            return soap.lookupUser(sid, username);
        });

        Integer userId = foo.get();
        this.userId = userId;
        System.out.println(userId); // Will wait until the value is complete
        executor.shutdown();
        return userId;
    }

    /*
    check if is assigned to course,
    return 0 => not assigned, 1 => course admin, 2 => course member or 3 => course tutor
    */
    public Integer isAssignedToCourse(String sid, int course_id, int user_id) throws ExecutionException, InterruptedException {
        SOAP soap = new SOAP();
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<Integer> foo = executor.submit(() -> {
            return soap.isAssignedToCourse(sid, course_id, user_id);
        });

        Integer role = foo.get();
        executor.shutdown();
        return role;
    }

    /*
    assign a user to course, possible roles from {"Admin", "Tutor", "Member"}
    */
    public Boolean assignCourseMember(String sid, int course_id, int user_id, String role) throws ExecutionException, InterruptedException {
        SOAP soap = new SOAP();
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<Boolean> foo = executor.submit(() -> {
            return soap.assignCourseMember(sid, course_id, user_id,role);
        });

        Boolean assigned = foo.get();
        executor.shutdown();
        return assigned;
    }

    /*
    exclude course member
    */
    public Boolean excludeCourseMember(String sid, int course_id, int user_id) throws ExecutionException, InterruptedException {
        SOAP soap = new SOAP();
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<Boolean> foo = executor.submit(() -> {
            return soap.excludeCourseMember(sid, course_id, user_id);
        });

        Boolean excluded = foo.get();
        executor.shutdown();
        return excluded;
    }

    public Integer getUserId() {
        return userId;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

}
