package com.project.ofcourse;

public class Presenter {
    private Model model;
    private StudentLogin view;

    public Presenter(Model model, StudentLogin view) {
        this.model = model;
        this.view = view;
    }

    public void checkStudent() {
        Student student = new Student(view.getEmail(), view.getPassword());

        if (model.isFound(student)) {
            view.openTimelineActivity();
        }
        else {
            view.displayMessage("Incorrect Email or Password");
        }

    }

}
