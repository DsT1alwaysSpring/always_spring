package com.example.alwaysspring.ui.slideshow;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SlideshowViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public SlideshowViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("이것은 슬라이드쇼 프래그먼트입니다.");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
