package com.Sunilmantra.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.AsyncTask;
import android.provider.ContactsContract;

import com.Sunilmantra.database.UserDatabase;
import com.Sunilmantra.deo.UserDao;
import com.Sunilmantra.model.User;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class UserRepository {

    private UserDao mNoteDao;
    private LiveData<List<User>> mAllNotes;

    public UserRepository(Application application) {
        UserDatabase db = UserDatabase.getAppDatabase(application);
        mNoteDao = db.userDao();
        mAllNotes = mNoteDao.getAllUsers();
    }

    public LiveData<List<User>> getAllNotes() {
        return mAllNotes;
    }

    public User getNote(String noteId) throws ExecutionException, InterruptedException {
        return new getNoteAsync(mNoteDao).execute(noteId).get();
    }

    public void insertNote(User note) {
        new insertNotesAsync(mNoteDao).execute(note);
    }


    /**
     * NOTE: all write operations should be done in background thread,
     * otherwise the following error will be thrown
     * `java.lang.IllegalStateException: Cannot access database on the main thread since it may potentially lock the UI for a long period of time.`
     */

    private static class getNoteAsync extends AsyncTask<String, Void, User> {

        private UserDao mNoteDaoAsync;

        getNoteAsync(UserDao animalDao) {
            mNoteDaoAsync = animalDao;
        }

        @Override
        protected User doInBackground(String... ids) {
            return mNoteDaoAsync.getUser(ids[0]);
        }
    }

    private static class insertNotesAsync extends AsyncTask<User, Void, Long> {

        private UserDao mNoteDaoAsync;

        insertNotesAsync(UserDao noteDao) {
            mNoteDaoAsync = noteDao;
        }

        @Override
        protected Long doInBackground(User... notes) {
            long id = mNoteDaoAsync.insert(notes[0]);
            return id;
        }
    }



}
