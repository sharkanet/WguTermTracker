package com.chris.chriswgutermtracker.database;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "terms")
public class Term {
    @PrimaryKey (autoGenerate = true)
    private int termId;

    private String termTitle;
    private Date termStart;
    private Date termEnd;


    @Override
    public String toString() {
        return "Term{" +
                "termId=" + termId +
                ", termTitle='" + termTitle + '\'' +
                ", termStart=" + termStart +
                ", termEnd=" + termEnd +
                '}';
    }

    public int getTermId() {
        return termId;
    }

    public void setTermId(int termId) {
        this.termId = termId;
    }

    public String getTermTitle() {
        return termTitle;
    }

    public void setTermTitle(String termTitle) {
        this.termTitle = termTitle;
    }

    public Date getTermStart() {
        return termStart;
    }

    public void setTermStart(Date termStart) {
        this.termStart = termStart;
    }

    public Date getTermEnd() {
        return termEnd;
    }

    public void setTermEnd(Date termEnd) {
        this.termEnd = termEnd;
    }

    public Term(int termId, String termTitle, Date termStart, Date termEnd) {
        this.termId = termId;
        this.termTitle = termTitle;
        this.termStart = termStart;
        this.termEnd = termEnd;
    }
@Ignore
    public Term(String termTitle, Date termStart, Date termEnd) {
        this.termTitle = termTitle;
        this.termStart = termStart;
        this.termEnd = termEnd;
    }
@Ignore
    public Term() {
    }
}
