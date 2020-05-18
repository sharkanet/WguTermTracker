package com.chris.chriswgutermtracker.database;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.Date;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "assessments",
        indices = {@Index("courseId_FK")},
        foreignKeys = @ForeignKey(
                entity = Course.class,
                parentColumns = "courseId",
                childColumns = "courseId_FK",
                onDelete = CASCADE
        )
)
public class Assessment {
    @PrimaryKey(autoGenerate = true)
    private int assessmentId;

    private String assessmentTitle;
    private Date assessmentDate;
    private String assessmentInfo;
    private String assessmentAlertTitle;
    private Date assessmentAlertDate;

    private int courseId_FK;
@Ignore
    public Assessment(String assessmentTitle, Date assessmentDate, String assessmentInfo, String assessmentAlertTitle, Date assessmentAlertDate, int courseId_FK) {
        this.assessmentTitle = assessmentTitle;
        this.assessmentDate = assessmentDate;
        this.assessmentInfo = assessmentInfo;
        this.assessmentAlertTitle = assessmentAlertTitle;
        this.assessmentAlertDate = assessmentAlertDate;
        this.courseId_FK = courseId_FK;
    }
@Ignore
    public Assessment() {
    }

    public Assessment(int assessmentId, String assessmentTitle, Date assessmentDate, String assessmentInfo, String assessmentAlertTitle, Date assessmentAlertDate, int courseId_FK) {
        this.assessmentId = assessmentId;
        this.assessmentTitle = assessmentTitle;
        this.assessmentDate = assessmentDate;
        this.assessmentInfo = assessmentInfo;
        this.assessmentAlertTitle = assessmentAlertTitle;
        this.assessmentAlertDate = assessmentAlertDate;
        this.courseId_FK = courseId_FK;
    }

    public int getAssessmentId() {
        return assessmentId;
    }

    public void setAssessmentId(int assessmentId) {
        this.assessmentId = assessmentId;
    }

    public String getAssessmentTitle() {
        return assessmentTitle;
    }

    public void setAssessmentTitle(String assessmentTitle) {
        this.assessmentTitle = assessmentTitle;
    }

    public Date getAssessmentDate() {
        return assessmentDate;
    }

    public void setAssessmentDate(Date assessmentDate) {
        this.assessmentDate = assessmentDate;
    }

    public String getAssessmentInfo() {
        return assessmentInfo;
    }

    public void setAssessmentInfo(String assessmentInfo) {
        this.assessmentInfo = assessmentInfo;
    }

    public String getAssessmentAlertTitle() {
        return assessmentAlertTitle;
    }

    public void setAssessmentAlertTitle(String assessmentAlertTitle) {
        this.assessmentAlertTitle = assessmentAlertTitle;
    }

    public Date getAssessmentAlertDate() {
        return assessmentAlertDate;
    }

    public void setAssessmentAlertDate(Date assessmentAlertDate) {
        this.assessmentAlertDate = assessmentAlertDate;
    }

    public int getCourseId_FK() {
        return courseId_FK;
    }

    public void setCourseId_FK(int courseId_FK) {
        this.courseId_FK = courseId_FK;
    }

    @Override
    public String toString() {
        return "Assessment{" +
                "assessmentId=" + assessmentId +
                ", assessmentTitle='" + assessmentTitle + '\'' +
                ", assessmentDate=" + assessmentDate +
                ", assessmentInfo='" + assessmentInfo + '\'' +
                ", assessmentAlertTitle='" + assessmentAlertTitle + '\'' +
                ", assessmentAlertDate='" + assessmentAlertDate + '\'' +
                ", courseId_FK=" + courseId_FK +
                '}';
    }
}
