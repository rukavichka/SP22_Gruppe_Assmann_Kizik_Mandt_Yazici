package com.example.SoapAPI;

public class SOAP {

    EPSILIASSoapWebserviceBinding service;
    //String sid;

    public SOAP(){
        service = new EPSILIASSoapWebserviceBinding("https://ilias-test.hrz.uni-marburg.de:443/webservice/soap/server.php");
    }

    /**
     * login user
     * @param client_id
     * @param username
     * @param password
     * @return
     * @throws InterruptedException
     */
    public String login(String client_id, String username, String password) throws InterruptedException {
        Thread.sleep(2000);
        String sid = null;
        try {
            sid = service.login(client_id, username, password);
            System.out.println("executed");
        } catch (Exception e) {
            System.out.println("not executed");
            e.printStackTrace();
        }
        return sid;
    }

    /**
     * logout user by sid
     * @param sid
     * @return
     * @throws InterruptedException
     */
    public boolean logout(String sid) throws InterruptedException {
        Thread.sleep(2000);
        boolean loggedOut = false;
        try {
            loggedOut = service.logout(sid);
            System.out.println("executed");
        } catch (Exception e) {
            System.out.println("not executed");
            e.printStackTrace();
        }
        return loggedOut;
    }

    /**
     * logout user by sid
     * @param sid
     * @return
     * @throws InterruptedException
     */
    public Integer lookupUser(String sid, String username) throws InterruptedException {
        Thread.sleep(2000);
        Integer user_id = null;
        try {
            user_id = service.lookupUser(sid, username);
            System.out.println("executed");
        } catch (Exception e) {
            System.out.println("not executed");
            e.printStackTrace();
        }
        return user_id;
    }

    /**
     * logout user by sid
     * @param sid
     * @return
     * @throws InterruptedException
     */
    public String getCoursesForUser(String sid, String parameters) throws InterruptedException {
        Thread.sleep(2000);
        String xml = null;
        try {
            xml = service.getCoursesForUser(sid, parameters);
            System.out.println("executed");
        } catch (Exception e) {
            System.out.println("not executed");
            e.printStackTrace();
        }
        return xml;
    }

    /**
     * not implemented, needed Premium Account
     * @param sid
     * @return
     * @throws InterruptedException
     */
    public String getUserIdBySid(String sid) throws InterruptedException {
        Thread.sleep(2000);
        String user = null;
        try {
            sid = service.getUserIdBySid(sid);
            System.out.println("executed");
        } catch (Exception e) {
            System.out.println("not executed");
            e.printStackTrace();
        }
        return user;
    }

}
