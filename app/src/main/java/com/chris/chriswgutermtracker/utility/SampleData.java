package com.chris.chriswgutermtracker.utility;


import com.chris.chriswgutermtracker.database.Assessment;
import com.chris.chriswgutermtracker.database.Course;
import com.chris.chriswgutermtracker.database.Note;
import com.chris.chriswgutermtracker.database.Term;
import com.chris.chriswgutermtracker.database.WGUAppRepository;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class SampleData {

    private static final String SAMPLE_TERM_TITLE = "SAMPLE TERM";
    private static final String SAMPLE_TERM_TITLE_2 = "SAMPLE TERM 2";
    private static final String SAMPLE_TERM_TITLE_3 = "SAMPLE TERM 3";
    private static final Date SAMPLE_DATE = getDate(0);
    private static Date getDate(int diff){
        GregorianCalendar cal = new GregorianCalendar();
        cal.add(Calendar.MILLISECOND, diff);
        return cal.getTime();
    }

    private static final String SAMPLE_COURSE_TITLE = "SAMPLE COURSE";
    private static final String SAMPLE_COURSE_STATUS = "Sample";
    private static final String SAMPLE_COURSE_MENTOR_PHONE = "123-123-1234";
    private static final String SAMPLE_COURSE_MENTOR_NAME =  "SAMPLE MENTOR";
    private static final String SAMPLE_COURSE_MENTOR_EMAIL = "SAMPLE EMAIL";

// do this later

    public static Term term1 = new Term(1, SAMPLE_TERM_TITLE, getDate(-1) , getDate(1));
    public static Term term2 = new Term(2, SAMPLE_TERM_TITLE_2, getDate(10) , getDate(100000));
    public static Term term3 = new Term(3, SAMPLE_TERM_TITLE_3, getDate(200000) , getDate(500000));
    public static Course course1 = new Course(1, SAMPLE_COURSE_TITLE, getDate(0),getDate(0),SAMPLE_COURSE_STATUS, SAMPLE_COURSE_MENTOR_NAME,SAMPLE_COURSE_MENTOR_PHONE,SAMPLE_COURSE_MENTOR_EMAIL, 1 );
    public static Assessment assessment1 = new Assessment(1, "Sample Assessment", getDate(0), "Sample Info", "Sample Alert", getDate(500), 1);
    public static Note note1 = new Note(1, "Sample note text", 1);

}
