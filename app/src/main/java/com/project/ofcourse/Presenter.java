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

        if (email.isEmpty() || password.isEmpty()) {
            view.displayMessage("Incorrect Email or Password");
            return;
        }

        model.isFound(email, password, new StudentLoginCallback() {
            @Override
            public void studentFound(boolean found) {
                if (found) {
                    Student.currentUser = email;
                    view.openTimelineActivity();
                }
                else {
                    view.displayMessage("Incorrect Email or Password");
                }
            }
        });
    }

}
