package com.project.ofcourse;

public class Presenter {
    private Model model;
    private StudentLogin view;

    public Presenter(Model model, StudentLogin view) {
        this.model = model;
        this.view = view;
    }

    public void checkStudent() {
        String email = view.getEmail();
        String password = view.getPassword();

        if (model.isFound(email, password)) {
            view.openTimelineActivity();
        }
        else {
            view.displayMessage("Incorrect Email or Password");
        }
    }
}
