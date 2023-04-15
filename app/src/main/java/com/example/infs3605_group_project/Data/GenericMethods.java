package com.example.infs3605_group_project.Data;

import android.util.Log;

import com.example.infs3605_group_project.Activity.Activity;
import com.example.infs3605_group_project.Activity.ActivityDatabase;

import java.util.List;
import java.util.concurrent.Executors;

public class GenericMethods {
    // Generic method for inserting dummy data
    public static void insertDummyData(ActivityDatabase mDb) {
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                mDb.activityDao().deleteAll();
                Activity activity;
                activity = new Activity(1, "z1234568", "MAHE Academic exchange MoU signed", "H Vinod Bhat", "Education Exchange", "India", "Online", "29/06/2007", "\"The University of New South Wales has strengthened its teaching and research ties in India, signing a Memorandum of Understanding (MoU) with Manipal University last weekend (24 June).\"");
                mDb.activityDao().safeInsertActivity(activity);
                activity = new Activity(2, "z1234567", "CATTS MOU signed", "Shri Sanjeev Kumar", "Centre (int/domestic)", "India", "\"New Dheli, India\"", "17/07/2017", " a memorandum of understanding (MoU) was signed between UNSW and IAHE");
                mDb.activityDao().safeInsertActivity(activity);
                activity = new Activity(3, "z1234560", "new centre in India", "Harinder Sidhu", "Centre (int/domestic)", "India", "\"New Dheli, India\"", "19/07/2018", "UNSW Sydney's new India Centre in New Delhi is established");
                mDb.activityDao().safeInsertActivity(activity);
                activity = new Activity(4, "z1234561", "CTET centre opened", "Zhongping Zhou", "Centre (int/domestic)", "China", "\"Shanghai, China\"", "21/11/2018", "first overseas research centre in china opened");
                mDb.activityDao().safeInsertActivity(activity);
                activity = new Activity(5, "z12345682", "CTET agreement signed", "David Waite", "Centre (int/domestic)", "China", "\"Shanghai, China\"", "21/06/2019", "UNSW signed two agreements");
                mDb.activityDao().safeInsertActivity(activity);
                activity = new Activity(6, "z1234567", "CATTS Indian delegations go to UNSW", "Shri Sanjeev Kumar", "Centre (int/domestic)", "India", "\"Sydney, Australia\"", "18/07/2019", "\"a high-level delegation from the Government of India to UNSW, as well as conversations as follow-up to the MoU and to further discuss the creation of the centre of excellence as indicated in the MoU.\"");
                mDb.activityDao().safeInsertActivity(activity);
                activity = new Activity(7, "z12345684", "MAHE Academic exchange program set up", "H Vinod Bhat", "Education Exchange", "India", "Online", "5/08/2019", "partnership seeks to promote academic and educational exchange between UNSW and Manipal Academy of Higher Education and establishes a joint seed fund for research collaborations.");
                mDb.activityDao().safeInsertActivity(activity);
                activity = new Activity(8, "z1234567", "CATTS Final draft agreement approved", "Shri Sanjeev Kumar", "Centre (int/domestic)", "India", "Online", "19/07/2020", "Final draft is agreed upon");
                mDb.activityDao().safeInsertActivity(activity);
                activity = new Activity(9, "z1234567", "CATTS funding given", "Shri Sanjeev Kumar", "Centre (int/domestic)", "India", "Online", "16/07/2021", "New ");
                mDb.activityDao().safeInsertActivity(activity);
                activity = new Activity(10, "z12345687", "INFS UNSW exhange program proposition", "UNSW business school", "Education Exchange", "China", "\"Shanghai, China\"", "27/01/2023", "30 students will go to china for the infs course and will gain international industry experience once the program is approved");
                mDb.activityDao().safeInsertActivity(activity);
                activity = new Activity(11, "z12345688", "INFS UNSW exhange program start", "UNSW business school", "Education Exchange", "China", "\"Shanghai, China\"", "5/05/2023", "30 students applied and meet the criteria for the program");
                mDb.activityDao().safeInsertActivity(activity);
                activity = new Activity(12, "z12345689", "INFS UNSW exhange program great wall visit", "UNSW business school", "Education Exchange", "China", "\"Shanghai, China\"", "12/05/2023", "Students went to the great wall and saw how the business operates and worked with process managers to understand the efficencies and bottlenecks that have naturally come with a  company of that size");
                mDb.activityDao().safeInsertActivity(activity);
                activity = new Activity(13, "z12345690", "INFS UNSW exhange program end", "UNSW business school", "Education Exchange", "China", "\"Shanghai, China\"", "22/05/2023", "Students return to UNSW as the program comes to an end");
                mDb.activityDao().safeInsertActivity(activity);
                activity = new Activity(14, "z12345691", "UNSW Japan exhange program", "Keiichi Tsuchiya", "Education Exchange", "Japan", "\"Tokyo, Japan\"", "20/05/2023", "30 UNSW student learning Japanese went on an exchange program and will be living in japan");
                mDb.activityDao().safeInsertActivity(activity);
                activity = new Activity(15, "z12345692", "UNSW Japan exhange program", "Keiichi Tsuchiya", "Education Exchange", "Japan", "\"Tokyo, Japan\"", "25/05/2023", "Students visit Toyota and understand the role of translaters in meetings with individuals of high value");
                mDb.activityDao().safeInsertActivity(activity);
                activity = new Activity(16, "z12345693", "UNSW Japan exhange program", "Keiichi Tsuchiya", "Education Exchange", "Japan", "\"Tokyo, Japan\"", "28/05/2023", "Students return to UNSW and the program ends for the term");
                mDb.activityDao().safeInsertActivity(activity);
                activity = new Activity(17, "z12345694", "CATTS state inspected", "Shri Sanjeev Kumar", "Centre (int/domestic)", "India", "\"Sydney, Australia\"", "7/04/2024", "\"To see how what progress is being made with the CATTs centre, an inspection was organised to see how it would operate in normal circumstances\"");
                mDb.activityDao().safeInsertActivity(activity);
                activity = new Activity(18, "z12345695", "MAHE international partners event", "H Vinod Bhat", "Relations event", "India", "\"New Dheli, India\"", "21/03/2023", "A regular social event was attended by our deligates to network with MAHE and other universities in India");
                mDb.activityDao().safeInsertActivity(activity);
            }
        });
    }

    // Display the data in the database under the debug logs
    public static void debugData(ActivityDatabase mDb) {
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                List<Activity> activityList = mDb.activityDao().getActivities();
                for(Activity temp: activityList){
                    Log.i("Activity",String.valueOf(temp.getId())+" "+temp.getEventName());
                }
            }
        });
    }

    public static boolean isDate(String date){
        String[] tempStr = date.split("/");
        if(date.length()==0){
            return false;
        } else if(tempStr.length != 3){
            return false;
        } else if(tempStr[0].length() != 2){
            return false;
        } else if(tempStr[1].length() != 2){
            return false;
        } else if(tempStr[2].length() != 4){
            return false;
        } else if(!isInteger(tempStr[0])){
            return false;
        } else if(!isInteger(tempStr[1])){
            return false;
        } else if(!isInteger(tempStr[2])){
            return false;
        }
        return true;
    }
    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch(Exception e) {
            Log.d("Error notInt","Nothing entered");
            return false;
        }
        // only got here if we didn't return false
        return true;
    }
}
