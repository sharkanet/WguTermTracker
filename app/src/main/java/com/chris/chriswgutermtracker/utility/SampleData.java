package com.chris.chriswgutermtracker.utility;


import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class SampleData {
    private static final String SAMPLE_TERM_TITLE = "SAMPLE TERM";
    private static final Date SAMPLE_DATE = getDate(0);
    private static Date getDate(int diff){
        GregorianCalendar cal = new GregorianCalendar();
        cal.add(Calendar.MILLISECOND, diff);
        return cal.getTime();
    }

    private static final String SAMPLE_COURSE_TITLE = "SAMPLE COURSE";
    private static final CourseStatus SAMPLE_COURSE_STATUS = CourseStatus.IN_PROGRESS;
    private static final String SAMPLE_COURSE_MENTOR_PHONE = "123-123-1234";
    private static final String SAMPLE_COURSE_MENTOR_NAME =  "SAMPLE MENTOR";
    private static final String SAMPLE_COURSE_MENTOR_EMAIL = "SAMPLE EMAIL";

// do this later

}
