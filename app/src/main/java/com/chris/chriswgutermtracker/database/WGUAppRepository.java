package com.chris.chriswgutermtracker.database;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.chris.chriswgutermtracker.utility.SampleData;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class WGUAppRepository {
    private static WGUAppDatabase mDb;
    private static WGUAppRepository instance;
    private Executor executor = Executors.newSingleThreadExecutor();

///////////////////////////////////////////////////////////////////////////////////
//objects retrieved from db
    //some might be unnecessary
    //full tables
    private LiveData<List<Term>> mTerms;
    private LiveData<List<Course>> mCourses; //unneeded?
    private LiveData<List<Assessment>> mAssessments; //unneeded?
    private LiveData<List<Note>> mNotes; //unneeded?



    //tables matching FK
    private LiveData<List<Course>> termCourses;
    private LiveData<List<Assessment>> courseAssessments;
    private LiveData<List<Note>> courseNotes;

    //
    private Term lastSelectedTerm;


////////////////////////////////////////////////////////////////////////////////////
//singleton
    private static final Object LOCK = new Object();
    public static WGUAppRepository getInstance(Context context){
        if(instance == null){
            synchronized (LOCK){
                if(instance == null){
                    instance = new WGUAppRepository(context);
                }
            }
        }
        return instance;
    }
    private WGUAppRepository(Context context){
        mDb = WGUAppDatabase.getInstance(context);
        mTerms = getAllTerms();
        mCourses = getAllCourses();
        mAssessments = getAllAssessments();
        mNotes = getAllNotes();
    }
//////////////////////////////////////////////////////////////////////////////////

//db method calls
    //get all
    public LiveData<List<Term>> getAllTerms(){
        return mDb.TermDao().getAllTerms();
    }
    public LiveData<List<Course>> getAllCourses(){
        return mDb.CourseDao().getAllCourses();
    }
    public LiveData<List<Assessment>> getAllAssessments(){
        return mDb.AssessmentDao().getAllAssessments();
    }
    public LiveData<List<Note>> getAllNotes(){
        return mDb.NoteDao().getAllNotes();
    }

    //insert
    public void insertTerm(Term term){
        executor.execute(()-> {
            mDb.TermDao().insertTerm(term);
        });
    }
    public void insertCourse(Course course){
        executor.execute(()-> {
            mDb.CourseDao().insertCourse(course);
        });
    }
    public void insertAssessment(Assessment assessment){
        executor.execute(()-> {
            mDb.AssessmentDao().insertAssessment(assessment);
        });
    }
    public void insertNote(Note note){
        executor.execute(()-> {
            mDb.NoteDao().insertNote(note);
        });
    }

    //update
    public void updateTerm(Term term){
        executor.execute(()-> {
            mDb.TermDao().updateTerm(term);
        });
    }
    public void updateCourse(Course course){
        executor.execute(()-> {
            mDb.CourseDao().updateCourse(course);
        });
    }
    public void updateAssessment(Assessment assessment){
        executor.execute(()-> {
            mDb.AssessmentDao().updateAssessment(assessment);
        });
    }
    public void updateNote(Note note){
        executor.execute(()-> {
            mDb.NoteDao().updateNote(note);
        });
    }


    //get by id
    public Term getTermById(int id){
                      return mDb.TermDao().getTermById(id);
    }
    public void setTermById(int id){
        lastSelectedTerm = getTermById(id);
    }
    public Term getSelectedTerm(){
        return lastSelectedTerm;
    }
    public Course getCourseById(int id) {
        return mDb.CourseDao().getCourseById(id);
    }



    //get all matching FK
    public LiveData<List<Course>> getCoursesWithFK(int fkId){
        return mDb.CourseDao().getCoursesWithFK(fkId);
    }
    public LiveData<List<Assessment>> getAssessmentsWithFK(int fkId){
        return mDb.AssessmentDao().getAssessmentsWithFK(fkId);
    }
    public LiveData<List<Note>> getNotesWithFK(int fkId){
        return mDb.NoteDao().getNotesWithFK(fkId);
    }
    public void setTermCourses(int fkId){
        termCourses = getCoursesWithFK(fkId);
    }
    public void setCourseAssessments(int fkId){
        courseAssessments = getAssessmentsWithFK(fkId);
    }
    public void setCourseNotes(int fkId){
        courseNotes = getNotesWithFK(fkId);
    }

    //delete by id
    public void deleteCourseById(int id){
        executor.execute(()-> {
            mDb.CourseDao().deleteCourseById(id);
        });
    }
    public void deleteTermById(int id){
        executor.execute(()-> mDb.TermDao().deleteTermById(id));
    }
    public void deleteAssessmentById(int id){
        executor.execute(()-> {
            mDb.AssessmentDao().deleteAssessmentById(id);
        });
    }
    public void deleteNoteById(int id){
        executor.execute(()-> {
            mDb.NoteDao().deleteNoteById(id);
        });
    }

    //delete by object
    public void deleteTerm(Term term){
        executor.execute(()-> {
            mDb.TermDao().deleteTerm(term);
        });
    }
    public void deleteCourse(Course course){
        executor.execute(()-> {
            mDb.CourseDao().deleteCourse(course);
        });
    }
    public void deleteAssessment(Assessment assessment){
        executor.execute(()-> {
            mDb.AssessmentDao().deleteAssessment(assessment);
        });
    }
    public void deleteNote(Note note){
        executor.execute(()-> {
            mDb.NoteDao().deleteNote(note);
        });
    }

    //delete all matching FK
    public void deleteCoursesInTerm(int id){
        executor.execute(()-> {
            mDb.CourseDao().deleteCoursesMatchingFK(id);
        });
    }
    public void deleteAssessmentsInCourse(int id){
        executor.execute(()-> {
            mDb.AssessmentDao().deleteAssessmentsMatchingFK(id);
        });
    }
    public void deleteNotesInCourse(int id){
        executor.execute(()-> {
            mDb.NoteDao().deleteNotesMatchingFK(id);
        });
    }


    //delete all
    public void deleteAllNotes(){
        executor.execute(()->{
            mDb.NoteDao().deleteAllNotes();
        });
    }
    public void deleteAllAssessments(){
        executor.execute(()-> {
            mDb.AssessmentDao().deleteAllAssessments();
        });
    }
    public void deleteAllCourses(){
        executor.execute(()-> {
            mDb.CourseDao().deleteAllCourses();
        });
    }
    public void deleteAllTerms(){
        executor.execute(()-> {
            mDb.TermDao().deleteAllTerms();
        });
    }

    public Integer getCourseCountWithFK(int id){
        return mDb.CourseDao().getCourseCountWithFK(id);
    }
///////////////////////////////////////////////////////////////////////////////////

//add sample data
    public void clearDBAndAddSampleData(){
        executor.execute(()-> {
            mDb.TermDao().deleteAllTerms();
            mDb.TermDao().insertTerm(SampleData.term1);
            mDb.TermDao().insertTerm(SampleData.term2);
            mDb.TermDao().insertTerm(SampleData.term3);
            mDb.CourseDao().insertCourse(SampleData.course1);
            mDb.AssessmentDao().insertAssessment(SampleData.assessment1);
            mDb.NoteDao().insertNote(SampleData.note1);

        });
        System.out.println("reset db");
    }

    public LiveData<List<Term>> getMTerms() {
        return mTerms;
    }
    public LiveData<List<Course>> getTermCourses() {
        return termCourses;
    }


}
