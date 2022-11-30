package com.project.ofcourse;

import org.checkerframework.checker.optional.qual.Present;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Matchers;
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
        Mockito.doNothing().when(model).isFound(Matchers.eq(""), Matchers.eq(""), Matchers.anyObject());
        Presenter presenter = new Presenter(model, view);
        presenter.checkStudent();
        ArgumentCaptor<StudentLoginCallback> captor = ArgumentCaptor.forClass(StudentLoginCallback.class);
        Mockito.verify(model).isFound(Matchers.eq(""), Matchers.eq(""), captor.capture());
        captor.getValue().studentFound(false);
        Mockito.verify(view).displayMessage("Incorrect Email or Password");
    }

    @Test
    public void testPresenter_emptyEmail() {
        Mockito.when(view.getEmail()).thenReturn("");
        Mockito.when(view.getPassword()).thenReturn("abc123");
        Mockito.doNothing().when(model).isFound(Matchers.eq(""), Matchers.eq("abc123"), Matchers.anyObject());
        Presenter presenter = new Presenter(model, view);
        presenter.checkStudent();
        ArgumentCaptor<StudentLoginCallback> captor = ArgumentCaptor.forClass(StudentLoginCallback.class);
        Mockito.verify(model).isFound(Matchers.eq(""), Matchers.eq("abc123"), captor.capture());
        captor.getValue().studentFound(false);
        Mockito.verify(view).displayMessage("Incorrect Email or Password");
    }

    @Test
    public void testPresenter_emptyPassword() {
        Mockito.when(view.getEmail()).thenReturn("abc@gmail.com");
        Mockito.when(view.getPassword()).thenReturn("");
        Mockito.doNothing().when(model).isFound(Matchers.eq("abc@gmail.com"), Matchers.eq(""), Matchers.anyObject());
        Presenter presenter = new Presenter(model, view);
        presenter.checkStudent();
        ArgumentCaptor<StudentLoginCallback> captor = ArgumentCaptor.forClass(StudentLoginCallback.class);
        Mockito.verify(model).isFound(Matchers.eq("abc@gmail.com"), Matchers.eq(""), captor.capture());
        captor.getValue().studentFound(false);
        Mockito.verify(view).displayMessage("Incorrect Email or Password");
    }

    @Test
    public void testPresenter_wrongPassword() {
        Mockito.when(view.getEmail()).thenReturn("abc@gmail.com");
        Mockito.when(view.getPassword()).thenReturn("Wrong Password");
        Mockito.doNothing().when(model).isFound(Matchers.eq("abc@gmail.com"), Matchers.eq("Wrong Password"), Matchers.anyObject());
        Presenter presenter = new Presenter(model, view);
        presenter.checkStudent();
        ArgumentCaptor<StudentLoginCallback> captor = ArgumentCaptor.forClass(StudentLoginCallback.class);
        Mockito.verify(model).isFound(Matchers.eq("abc@gmail.com"), Matchers.eq("Wrong Password"), captor.capture());
        captor.getValue().studentFound(false);
        Mockito.verify(view).displayMessage("Incorrect Email or Password");
    }

    @Test
    public void testPresenter_wrongEmail() {
        Mockito.when(view.getEmail()).thenReturn("Wrong Email");
        Mockito.when(view.getPassword()).thenReturn("abc123");
        Mockito.doNothing().when(model).isFound(Matchers.eq("Wrong Email"), Matchers.eq("abc123"), Matchers.anyObject());
        Presenter presenter = new Presenter(model, view);
        presenter.checkStudent();
        ArgumentCaptor<StudentLoginCallback> captor = ArgumentCaptor.forClass(StudentLoginCallback.class);
        Mockito.verify(model).isFound(Matchers.eq("Wrong Email"), Matchers.eq("abc123"), captor.capture());
        captor.getValue().studentFound(false);
        Mockito.verify(view).displayMessage("Incorrect Email or Password");
    }

    @Test
    public void testPresenter_validCredentials() {
        Mockito.when(view.getEmail()).thenReturn("abc@gmail.com");
        Mockito.when(view.getPassword()).thenReturn("abc123");
        Mockito.doNothing().when(model).isFound(Matchers.eq("abc@gmail.com"), Matchers.eq("abc123"), Matchers.anyObject());
        Presenter presenter = new Presenter(model, view);
        presenter.checkStudent();
        ArgumentCaptor<StudentLoginCallback> captor = ArgumentCaptor.forClass(StudentLoginCallback.class);
        Mockito.verify(model).isFound(Matchers.eq("abc@gmail.com"), Matchers.eq("abc123"), captor.capture());
        captor.getValue().studentFound(true);
        Mockito.verify(view).openTimelineActivity();
    }
}