package Model;

import java.util.List;

public class Lecture extends Course{




    @Override
    public String getLecture_ID() {
        return super.getLecture_ID();
    }

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public String getTeacher() {
        return super.getTeacher();
    }

    @Override
    public String getSemester() {
        return super.getSemester();
    }

    @Override
    public List<String> getCourseMeetings() {
        return super.getCourseMeetings();
    }

    @Override
    public boolean isJoined() {
        return super.isJoined();
    }
}
