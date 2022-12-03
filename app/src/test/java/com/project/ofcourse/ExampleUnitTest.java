package com.project.ofcourse;

import org.checkerframework.checker.optional.qual.Present;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;
import org.mockito.Mockito;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(MockitoJUnitRunner.class)
public class ExampleUnitTest {

    @Mock
    StudentLogin view;

    @Mock
    Model model;

    @Test
    public void testPresenter_emptyEmailAndPassword() {
        Mockito.when(view.getEmail()).thenReturn("");
        Mockito.when(view.getPassword()).thenReturn("");
        Mockito.when(model.isFound("", "")).thenReturn(false);
        Presenter presenter = new Presenter(model, view);
        presenter.checkStudent();
        Mockito.verify(view).displayMessage("Incorrect Email or Password");
    }

    @Test
    public void testPresenter_emptyEmail() {
        Mockito.when(view.getEmail()).thenReturn("");
        Mockito.when(view.getPassword()).thenReturn("abc123");
        Mockito.when(model.isFound("", "abc123")).thenReturn(false);
        Presenter presenter = new Presenter(model, view);
        presenter.checkStudent();
        Mockito.verify(view).displayMessage("Incorrect Email or Password");
    }

    @Test
    public void testPresenter_emptyPassword() {
        Mockito.when(view.getEmail()).thenReturn("abc@gmail.com");
        Mockito.when(view.getPassword()).thenReturn("");
        Mockito.when(model.isFound("abc@gmail.com", "")).thenReturn(false);
        Presenter presenter = new Presenter(model, view);
        presenter.checkStudent();
        Mockito.verify(view).displayMessage("Incorrect Email or Password");
    }

    @Test
    public void testPresenter_wrongPassword() {
        Mockito.when(view.getEmail()).thenReturn("abc@gmail.com");
        Mockito.when(view.getPassword()).thenReturn("Wrong Password");
        Mockito.when(model.isFound("abc@gmail.com", "Wrong Password")).thenReturn(false);
        Presenter presenter = new Presenter(model, view);
        presenter.checkStudent();
        Mockito.verify(view).displayMessage("Incorrect Email or Password");
    }

    @Test
    public void testPresenter_wrongEmail() {
        Mockito.when(view.getEmail()).thenReturn("Wrong Email");
        Mockito.when(view.getPassword()).thenReturn("abc123");
        Mockito.when(model.isFound("Wrong Email", "abc123")).thenReturn(false);
        Presenter presenter = new Presenter(model, view);
        presenter.checkStudent();
        Mockito.verify(view).displayMessage("Incorrect Email or Password");
    }

    @Test
    public void testPresenter_validCredentials() {
        Mockito.when(view.getEmail()).thenReturn("abc@gmail.com");
        Mockito.when(view.getPassword()).thenReturn("abc123");
        Mockito.when(model.isFound("abc@gmail.com", "abc123")).thenReturn(true);
        Presenter presenter = new Presenter(model, view);
        presenter.checkStudent();
        Mockito.verify(view).openTimelineActivity();
    }
}