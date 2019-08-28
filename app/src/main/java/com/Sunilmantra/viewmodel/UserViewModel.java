package com.Sunilmantra.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.Sunilmantra.model.User;
import com.Sunilmantra.repository.UserRepository;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class UserViewModel extends AndroidViewModel {

    private UserRepository mRepository;
    private LiveData<List<User>> notes;

    public UserViewModel(@NonNull Application application) {
        super(application);

        mRepository = new UserRepository(application);
    }

    public LiveData<List<User>> getNotes() {
        if (notes == null) {
            notes = mRepository.getAllNotes();
        }

        return notes;
    }

    public User getNote(String username) throws ExecutionException, InterruptedException {
        return mRepository.getNote(username);
    }

    public void insertNote(User note) {
        mRepository.insertNote(note);
    }

}
