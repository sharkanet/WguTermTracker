package com.chris.chriswgutermtracker.database;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.chris.chriswgutermtracker.utility.CourseStatus;

import java.util.Date;

import static androidx.room.ForeignKey.CASCADE;

@Entity(
    tableName= "courses",
    indices = {@Index("termId_FK")},
    foreignKeys = @ForeignKey(
            entity = Term.class,
            parentColumns = "termId",
            childColumns = "termId_FK",
            onDelete = CASCADE
    )
)
public class Course {
    @PrimaryKey(autoGenerate = true)
    private int courseId;

    private String courseTitle;
    private Date courseStart;
    private Date courseEnd;
    private String courseStatus;
    private String mentorName;
    private String mentorPhone;
    private String mentorEmail;

    private int termId_FK;
  //  private Date courseAlertDate;

    public Course(int courseId, String courseTitle, Date courseStart, Date courseEnd, String courseStatus, String mentorName, String mentorPhone, String mentorEmail, int termId_FK) {
        this.courseId = courseId;
        this.courseTitle = courseTitle;
        this.courseStart = courseStart;
        this.courseEnd = courseEnd;
        this.courseStatus = courseStatus;
        this.mentorName = mentorName;
        this.mentorPhone = mentorPhone;
        this.mentorEmail = mentorEmail;
        this.termId_FK = termId_FK;
    }

@Ignore
    public Course(String courseTitle, Date courseStart, Date courseEnd, String courseStatus, String mentorName, String mentorPhone, String mentorEmail, int termId_FK) {
        this.courseTitle = courseTitle;
        this.courseStart = courseStart;
        this.courseEnd = courseEnd;
        this.courseStatus = courseStatus;
        this.mentorName = mentorName;
        this.mentorPhone = mentorPhone;
        this.mentorEmail = mentorEmail;
        this.termId_FK = termId_FK;
    }
@Ignore
    public Course() {
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public Date getCourseStart() {
        return courseStart;
    }

    public void setCourseStart(Date courseStart) {
        this.courseStart = courseStart;
    }

    public Date getCourseEnd() {
        return courseEnd;
    }

    public void setCourseEnd(Date courseEnd) {
        this.courseEnd = courseEnd;
    }

    public String getCourseStatus() {
        return courseStatus;
    }

    public void setCourseStatus(String courseStatus) {
        this.courseStatus = courseStatus;
    }

    public String getMentorName() {
        return mentorName;
    }

    public void setMentorName(String mentorName) {
        this.mentorName = mentorName;
    }

    public String getMentorPhone() {
        return mentorPhone;
    }

    public void setMentorPhone(String mentorPhone) {
        this.mentorPhone = mentorPhone;
    }

    public String getMentorEmail() {
        return mentorEmail;
    }

    public void setMentorEmail(String mentorEmail) {
        this.mentorEmail = mentorEmail;
    }

    public int getTermId_FK() {
        return termId_FK;
    }

    public void setTermId_FK(int termId_FK) {
        this.termId_FK = termId_FK;
    }

    @Override
    public String toString() {
        return "Course{" +
                "courseId=" + courseId +
                ", courseTitle='" + courseTitle + '\'' +
                ", courseStart=" + courseStart +
                ", courseEnd=" + courseEnd +
                ", courseStatus=" + courseStatus +
                ", mentorName='" + mentorName + '\'' +
                ", mentorPhone='" + mentorPhone + '\'' +
                ", mentorEmail='" + mentorEmail + '\'' +
                ", termId_FK=" + termId_FK +
                '}';
    }
}

