package com.project.ofcourse.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


/*Tasks:
- Make Scrollable
- Add check for timeline file
- Add check for
 */
public class HomeViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("No");
    }

    public LiveData<String> getText() {
        return mText;
    }
}