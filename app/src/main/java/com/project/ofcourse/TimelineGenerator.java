package com.project.ofcourse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Objects;

public class TimelineGenerator {

    private String Session;
    private ArrayList<String> pastCourses;
    private ArrayList<Course> wantedCourses;
    private ArrayList<Course> allCourses;


    // Helpers:
    public int findCourseByStringInAll(String code) {
        for (int i = 0; i < wantedCourses.size(); i++) {
            if (Objects.equals(allCourses.get(i).code, code)) {
                return i;
            }
        }
        return -1;
    }


    /*Will be called in Timeline Generation Activity which consists of:
        - Past Courses Fragment (Creates field for given student in Firebase)
        - Wanted Courses Fragment (Gets stored as an array to be passed here)
        - Generating fragment (Calls this class's main function)
        Which then creates a collection as a field in the student's Firebase from the above info
     */
    public TimelineGenerator(String Session, ArrayList<String> pastCourses, ArrayList<Course> wantedCourses, ArrayList<Course> allCourses) {
        this.Session = Session;
        this.pastCourses = pastCourses;
        this.wantedCourses = wantedCourses;
        this.allCourses = allCourses;
        System.out.println("Session:" + Session);
        System.out.println("Past Courses" + pastCourses);
        System.out.println("Wanted Courses" + wantedCourses);
        System.out.println("All Courses" + allCourses);
    }

    public boolean courseInWanted(String code) {
        for (int i = 0; i < wantedCourses.size(); i++) {
            if (wantedCourses.get(i).code.equals(code)) {return true;}
        }
        return false;
    }


    private void addPrereqs() { //so fucking bad in terms of complexity
        boolean allSatisfied = false;
        String[] preReqs;
        boolean courseMissingPrereq = false;

        System.out.println("Started addPrereq");

        while (!allSatisfied) {

            courseMissingPrereq = false;

            for (int i = 0; i < wantedCourses.size(); i++) {

                if(wantedCourses.get(i).prereq.equals("") || prereqsFulfilled(wantedCourses.get(i))) {
                    continue;
                }

                System.out.println("Out of the for in addPrereq");

                preReqs = getPrereqs(wantedCourses.get(i));

                for (String preReq : preReqs) {          // Check Prerequisite Courses
                    if (!pastCourses.contains(preReq) && !courseInWanted(preReq)) {
                        courseMissingPrereq = true;
                        wantedCourses.add(0, getCourseinAll(preReq)); // Add the missing prerequisite to the list of courses to add
                    }
                }
            }
            allSatisfied = !courseMissingPrereq;
            System.out.println("exited the big While once");
        }
    }

    public Course getCourseinAll(String code) {
        for (int i = 0; i < allCourses.size(); i++) {
            if (allCourses.get(i).code.equals(code)) {
                return allCourses.get(i);
            }
        }
        return null;
    }



    public ArrayList<String> getOfferingSession(Course course) {
        String[] ret = course.session.split(" ");
        ArrayList<String> needed = new ArrayList<>();
        Collections.addAll(needed, ret);
        return needed;
    }

    public String[] getPrereqs(Course course) {
        return course.prereq.split(" ");
    }

    public String newSession(String Session) {
        String[] keyArray = Session.split(" ");
        if (keyArray[0].equals("S")) {
            keyArray[0] = "F";
        }
        if (keyArray[0].equals("W")) {
            keyArray[0] = "S";
        }
        if (keyArray[0].equals("F")) {
            keyArray[0] = "W";
            keyArray[1] = Integer.toString(Integer.parseInt(keyArray[1]) + 1);
        }
        return keyArray[0] + " " + keyArray[1];
    }

    public boolean prereqsFulfilled(Course course) {
        if(course.prereq.equals("")) {return true;}
        String[] prereqs = getPrereqs(course);
        for (int i = 0; i < prereqs.length; i++) {
            if (!pastCourses.contains(prereqs[i])) {
                return false;
            }
        }
        return true;
    }

    public boolean suitableSession(Course course, String session) {
        String sessionType = String.valueOf(session.charAt(0));
//        ArrayList<String> check;
//        check = getOfferingSession(course);
        return sessionType.equals(course.session);
    }

    private boolean suitableToAdd(Course course, String presentSession) {
        return (suitableSession(course, presentSession) && prereqsFulfilled(course));
    }

    public boolean anyForSession(ArrayList<Course> wantedCourses, String session) {
        String sessionType = String.valueOf(session.charAt(0));
        ArrayList<String> check;
        for (int i = 0; i < wantedCourses.size(); i++) {
            check = getOfferingSession(wantedCourses.get(i));
            if (check.contains(sessionType) && prereqsFulfilled(wantedCourses.get(i))) {
                return true;
            }
        }
        return false;
    }

    public void removeCourses(ArrayList<Course> toRemove) {
        for (int i = 0; i < toRemove.size(); i++) {
            wantedCourses.remove(toRemove.get(i));
            pastCourses.add(toRemove.get(i).code);
        }
    }

    public int getOneNotOther(ArrayList<Course> added, ArrayList<Course> wanted) {
        for (int i = 0; i < wantedCourses.size(); i++) {
            if (!added.contains(wanted.get(i))) {
                return i;
            }
        }
        return -1;
    }

    //Generates the Timeline:
    public LinkedHashMap<String, ArrayList<String>> generateTimeline() {

        LinkedHashMap<String, ArrayList<String>> Timeline = new LinkedHashMap<>();
        String presentSession = Session;
        Course currentCourse;
        String courseCode;
        ArrayList<Course> added = new ArrayList<>();
        addPrereqs();
        ArrayList<String> arrayList = new ArrayList<>();


        while (added.size() < wantedCourses.size()) {

            if (added.size() == wantedCourses.size() - 1) {
                arrayList.clear();
                arrayList.add(wantedCourses.get(getOneNotOther(added, wantedCourses)).code);
                presentSession = newSession(presentSession);
                Timeline.put(presentSession, arrayList);
                return Timeline;
            }

            for (int i = 0; i < wantedCourses.size(); i++) {

                currentCourse = wantedCourses.get(i);
                courseCode = currentCourse.code;

                if (prereqsFulfilled(currentCourse) && suitableSession(currentCourse, presentSession) && !added.contains(currentCourse)) {
                    arrayList.add(courseCode);
                    added.add(currentCourse);
                    pastCourses.add(courseCode);
                }
                if (arrayList.size() > 5) {
                    Timeline.put(presentSession, arrayList);
                    arrayList.clear();
                    presentSession = newSession(presentSession);
                }
            }
            Timeline.put(presentSession, arrayList);
            presentSession = newSession(presentSession);
        }

        return Timeline;
    }


//    private void prereqCheck() { //so fucking bad in terms of complexity
//        boolean allSatisfied = false;
//        String[] preReqs;
//        boolean courseMissingPrereq = false;
//
//        while (!allSatisfied) {
//
//            courseMissingPrereq = false;
//
//            for (int i = 0; i < wantedCourses.size(); i++) {
//
//                preReqs = wantedCourses.get(i).prereq.split(" ");
//
//                for (String preReq : preReqs) {          // Check Prerequisite Courses
//                    if (!pastCourses.contains(preReq) && !wantedCourses.contains(allCourses.get(findCourseByStringInAll(preReq)))) {
//                        courseMissingPrereq = true;
//                        wantedCourses.add(0, allCourses.get(findCourseByStringInAll(preReq))); // Add the missing prerequisite to the list of courses to add
//                    }
//                }
//            }
//            allSatisfied = !courseMissingPrereq;
//        }
//    }
//
//    //Generates the Timeline:
//    public LinkedHashMap<String, ArrayList<String>> generateTimeline() {
//
//        LinkedHashMap<String, ArrayList<String>> Timeline = new LinkedHashMap<>();
//
//        String presentSession = Session;
//        ArrayList<String> CoursesArray = new ArrayList<String>();
//        int coursesInSession = 0;
//        int index;
//
//        this.prereqCheck(); // Handles all prerequisites nonsense first.
//        // NOW WANTED COURSES HAS ALL COURSES NECESSARY INCLUDING PreREQS. UNORDERED
//
//
//        presentSession = advanceSession(Session);
//        while (!wantedCourses.isEmpty()){
//            index = 0;
//            if (coursesInSession >= 6 || !SessionSuitable(presentSession)) { // Max 6 courses per session
//                Timeline.put(presentSession, CoursesArray);
//                presentSession = advanceSession(presentSession);
//                coursesInSession = 0;
//                CoursesArray.clear();
//            }
//            while (index < wantedCourses.size()) {
//                if (suitableToAdd(presentSession, CoursesArray, index)) {
//                    CoursesArray.add(wantedCourses.get(index).code); // Prereqs have been satisfied, add it to the session and move on
//                    pastCourses.add(wantedCourses.get(index).code);
//                    wantedCourses.remove(index);
//                    coursesInSession++;
//                    break;
//                }
//
//            }
//        }
//        if (CoursesArray.size() != 0) {
//            Timeline.put(presentSession, CoursesArray);
//            presentSession = advanceSession(presentSession);
//            coursesInSession = 0;
//            CoursesArray.clear();
//        }
//        return Timeline;
//    }
//
//    private boolean SessionSuitable(String presentSession) {
//        for (int i = 0; i < wantedCourses.size(); i++) {
//            if (wantedCourses.get(i).code.charAt(0) == presentSession.charAt(0) && preReqsFulfilled(wantedCourses.get(i))) {
//                return true;
//            }
//        }
//        return false;
//    }
//
//    private boolean suitableToAdd(String presentSession, ArrayList<String>CoursesArray, int index) {
//        boolean preReqInSession = false;
//        String [] preReq = wantedCourses.get(index).prereq.split(" ");
//        for (int i = 0; i < CoursesArray.size(); i++) {
//            for (String s : preReq) {
//                if (Objects.equals(CoursesArray.get(i), s)) {
//                    preReqInSession = true;
//                    break;
//                }
//            }
//        }
//        return (wantedCourses.get(index).session.charAt(0) == Session.charAt(0) && !preReqInSession && preReqsFulfilled(wantedCourses.get(index)));
//    }
//
//    private boolean preReqsFulfilled(Course course) {
//        String[] preReqs = course.prereq.split(" ");
//        for (String preReq : preReqs) {
//            if (!pastCourses.contains(preReq)) {
//                return false;
//            }
//        }
//        return true;
//    }
//
//    private String advanceSession(String presentSession) {
//        String[] keyArray = presentSession.split(" ");
//        if (keyArray[0].equals("S")) {keyArray[0] = "F";}
//        if (keyArray[0].equals("W")) {keyArray[0] = "S";}
//        if (keyArray[0].equals("F")) {keyArray[0] = "W"; keyArray[1] = Integer.toString(Integer.parseInt(keyArray[1])+1);}
//        return keyArray[0] + " " + keyArray[1];
//    }
}
