package com.project.ofcourse;

import android.graphics.CornerPathEffect;
import android.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Objects;

public class TimelineGenerator {

    private String Session;
    private ArrayList<String> pastCourses;
    private ArrayList<Course> wantedCourses;
    private ArrayList<Course> allCourses;


    // Helpers:
    public int findCourseByStringInAll(String code) {
        for (int i = 0; i < wantedCourses.size(); i++) {
            if (Objects.equals(allCourses.get(i).code, code)) {return i;}
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

    }

    private void prereqCheck() { //so fucking bad in terms of complexity
        boolean allSatisfied = false;
        String[] preReqs;
        boolean courseMissingPrereq = false;

        while (!allSatisfied) {

            courseMissingPrereq = false;

            for (int i = 0; i < wantedCourses.size(); i++) {

                preReqs = wantedCourses.get(i).prereq.split(" ");

                for (String preReq : preReqs) {          // Check Prerequisite Courses
                    if (!pastCourses.contains(preReq) && !wantedCourses.contains(allCourses.get(findCourseByStringInAll(preReq)))) {
                        courseMissingPrereq = true;
                        wantedCourses.add(0, allCourses.get(findCourseByStringInAll(preReq))); // Add the missing prerequisite to the list of courses to add
                    }
                }
            }
            allSatisfied = !courseMissingPrereq;
        }
    }

    //Generates the Timeline:
    public LinkedHashMap<String, String[]> generateTimeline() {

        LinkedHashMap<String, String[]> Timeline = new LinkedHashMap<>();

        String presentSession = Session;
        ArrayList<String> CoursesArray = new ArrayList<String>();
        int coursesInSession = 0;
        int index;

        this.prereqCheck(); // Handles all prerequisites nonsense first.
        // NOW WANTED COURSES HAS ALL COURSES NECESSARY INCLUDING PreREQS. UNORDERED


        presentSession = advanceSession(Session);
        while (!wantedCourses.isEmpty()){
            index = 0;
            if (coursesInSession >= 6 || !SessionSuitable(presentSession)) { // Max 6 courses per session
                Timeline.put(presentSession, (String[]) CoursesArray.toArray());
                presentSession = advanceSession(presentSession);
                coursesInSession = 0;
                CoursesArray.clear();
            }
            while (index < wantedCourses.size()) {
                if (suitableToAdd(presentSession, CoursesArray, index)) {
                    CoursesArray.add(wantedCourses.get(index).code); // Prereqs have been satisfied, add it to the session and move on
                    pastCourses.add(wantedCourses.get(index).code);
                    wantedCourses.remove(index);
                    coursesInSession++;
                    break;
                }

            }
        }
        if (CoursesArray.size() != 0) {
            Timeline.put(presentSession, (String[]) CoursesArray.toArray());
            presentSession = advanceSession(presentSession);
            coursesInSession = 0;
            CoursesArray.clear();
        }
        return Timeline;
    }

    private boolean SessionSuitable(String presentSession) {
        for (int i = 0; i < wantedCourses.size(); i++) {
            if (wantedCourses.get(i).code.charAt(0) == presentSession.charAt(0) && preReqsFulfilled(wantedCourses.get(i))) {
                return true;
            }
        }
        return false;
    }

    private boolean suitableToAdd(String presentSession, ArrayList<String>CoursesArray, int index) {
        boolean preReqInSession = false;
        String [] preReq = wantedCourses.get(index).prereq.split(" ");
        for (int i = 0; i < CoursesArray.size(); i++) {
            for (String s : preReq) {
                if (Objects.equals(CoursesArray.get(i), s)) {
                    preReqInSession = true;
                    break;
                }
            }
        }
        return (wantedCourses.get(index).session.charAt(0) == Session.charAt(0) && !preReqInSession && preReqsFulfilled(wantedCourses.get(index)));
    }

    private boolean preReqsFulfilled(Course course) {
        String[] preReqs = course.prereq.split(" ");
        for (String preReq : preReqs) {
            if (!pastCourses.contains(preReq)) {
                return false;
            }
        }
        return true;
    }

    private String advanceSession(String presentSession) {
        String[] keyArray = presentSession.split(" ");
        if (keyArray[0].equals("S")) {keyArray[0] = "F";}
        if (keyArray[0].equals("W")) {keyArray[0] = "S";}
        if (keyArray[0].equals("F")) {keyArray[0] = "W"; keyArray[1] = Integer.toString(Integer.parseInt(keyArray[1])+1);}
        return keyArray[0] + " " + keyArray[1];
    }
}
