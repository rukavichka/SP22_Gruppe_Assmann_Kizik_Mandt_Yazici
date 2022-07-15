package com.example.Model;

import androidx.annotation.NonNull;

import com.example.SoapAPI.SOAP;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class VerificationProcess {

    public String sid;
    public int userId;
    private String iliasUsername;
    private String name;

    public void setName(String name) {
        this.name = name;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setIliasUsername(String iliasUsername) {
        this.iliasUsername = iliasUsername;
    }

    private static final VerificationProcess instance = new VerificationProcess();

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

        this.sid = foo.get();
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
        System.out.println("Lookup user sid: "+ sid);
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

    public void setUserData(String iliasUsername){
        Query databaseReferenceUsers = FirebaseDatabase.getInstance().getReference("users")
                .orderByChild("iliasUsername").equalTo(iliasUsername);
        databaseReferenceUsers.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot sn : snapshot.getChildren()){
                    for(DataSnapshot sn1 : sn.getChildren()){
                        if(sn1.getKey().equals("user_id")){
                            setUserId(Integer.parseInt(sn1.getValue().toString()));
                        }
                        else if(sn1.getKey().equals("name")){
                            setName(sn1.getValue().toString());
                        }
                        else if(sn1.getKey().equals("iliasUsername")){
                            setIliasUsername(sn1.getValue().toString());
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public String getIliasUsername() {
        return iliasUsername;
    }

    public String getName() {
        return name;
    }


}
