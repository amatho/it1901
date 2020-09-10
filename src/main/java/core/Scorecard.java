package core;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Scorecard {

    private Course course;
   // private List<User> user;
    //private List<List<Integer>> scorecard;
    private HashMap<User, Array[]> scorecard;


    public Scorecard(Course course, User ... users){
        if(users.length > 4){
            throw new IllegalArgumentException("Cannot have more than four users.");
        }
        this.course = course;
        Array[] score = {null};
        Arrays.fill(score, course.getCourseLength());
        for(User u : users){
            scorecard.put(u, score);
        }
    }




}
