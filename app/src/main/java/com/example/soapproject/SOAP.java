package com.example.soapproject;

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
            //e.printStackTrace();
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
            System.out.println("executed, user id: " + user_id);
        } catch (Exception e) {
            System.out.println("not executed, user id");
            e.printStackTrace();
        }
        return user_id;
    }

    /**
     * check if the user is assigned to course
     * @param sid, course_id, user_id
     * @return 0 => not assigned, 1 => course admin, 2 => course member or 3 => course tutor
     * @throws InterruptedException
     */
    public Integer isAssignedToCourse(String sid, int course_id, int user_id) throws InterruptedException {
        Thread.sleep(2000);
        Integer role = null;
        try {
            role = service.isAssignedToCourse(sid, course_id, user_id);
            System.out.println("executed, is assigned to course: " + role);
        } catch (Exception e) {
            System.out.println("not executed, is assigned to course");
            e.printStackTrace();
        }
        return role;
    }

    /**
     * assignCourseMember
     * @param sid, course_id, user_id, type from {"Admin", "Tutor", "Member"}
     * @return
     * @throws InterruptedException
     */
    public boolean assignCourseMember(String sid, int course_id, int user_id, String type) throws InterruptedException {
        Thread.sleep(2000);
        boolean assigned = false;
        try {
            assigned = service.assignCourseMember(sid, course_id, user_id, type);
            System.out.println("executed, assign course member: " + assigned);
        } catch (Exception e) {
            System.out.println("not executed, assign course member");
            e.printStackTrace();
        }
        return assigned;
    }

    /**
     * excludeCourseMember
     * @param sid, course_id, user_id,
     * @return
     * @throws InterruptedException
     */
    public boolean excludeCourseMember(String sid, int course_id, int user_id) throws InterruptedException {
        Thread.sleep(2000);
        boolean excluded = false;
        try {
            excluded = service.excludeCourseMember(sid, course_id, user_id);
            System.out.println("executed, exclude course member: " + excluded);
        } catch (Exception e) {
            System.out.println("not executed, exclude course membe");
            e.printStackTrace();
        }
        return excluded;
    }

    /**
     *
     * @param sid
     * @return
     * @throws InterruptedException
     */
    public String getObjectsByTitle(String sid, String title, Integer user_id) throws InterruptedException {
        Thread.sleep(2000);
        String xml = null;
        try {
            xml = service.getObjectsByTitle(sid, title, user_id);
            System.out.println("executed, getObjectsByTitle: " + xml);
        } catch (Exception e) {
            System.out.println("not executed, getObjectsByTitle");
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
        String user_id = null;
        try {
            user_id = service.getUserIdBySid(sid);
            System.out.println("executed, user id: " + user_id);
        } catch (Exception e) {
            System.out.println("not executed, user id");
            e.printStackTrace();
        }
        return user_id;
    }

}
