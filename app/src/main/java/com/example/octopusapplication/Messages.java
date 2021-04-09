package com.example.octopusapplication;

import android.content.Context;
import android.widget.Toast;

public class Messages {
    public void OutputErrorMessage(int MessageIndex, Context ApplicationContext) {
        final CharSequence[] ToastText = {"Please input an email", "Please input a password", "Please make a password with more than 8 characters", "Please input a username", "Username is taken"};
        int ToastDuration = Toast.LENGTH_LONG;

        Toast toast = Toast.makeText(ApplicationContext, ToastText[MessageIndex], ToastDuration);
        toast.show();
    }
    public void OutputCompletionMessage(int MessageIndex, Context ApplicationContext) {
        final CharSequence[] ToastText = {"Sign up successful", "Login successful"};
        int ToastDuration = Toast.LENGTH_LONG;

        Toast toast = Toast.makeText(ApplicationContext, ToastText[MessageIndex], ToastDuration);
        toast.show();
    }
}
