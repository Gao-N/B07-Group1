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

        model.isFound(email, password, new StudentLoginCallback() {
            @Override
            public void studentFound(boolean found) {
                if (found) {
                    view.openTimelineActivity();
                }
                else {
                    view.displayMessage("Incorrect Email or Password");
                }
            }
        });
    }
}
