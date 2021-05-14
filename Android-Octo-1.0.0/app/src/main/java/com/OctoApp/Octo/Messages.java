package com.OctoApp.Octo;

import android.content.Context;
import android.widget.Toast;

public class Messages {
    int ToastDuration = Toast.LENGTH_SHORT;
    public void OutputErrorMessage(int MessageIndex, Context ApplicationContext) {
        final CharSequence[] ToastText = {"Please input an email", "Please input a password", "Please make a password with more than 8 characters", "Please input a username", "Username is taken", "User does not exist. Please try again"};

        Toast toast = Toast.makeText(ApplicationContext, ToastText[MessageIndex], ToastDuration);
        toast.show();
    }

    public void OutputCompletionMessage(int MessageIndex, Context ApplicationContext) {
        final CharSequence[] ToastText = {"Sign up successful", "Login successful"};

        Toast toast = Toast.makeText(ApplicationContext, ToastText[MessageIndex], ToastDuration);
        toast.show();
    }

    public void OutputCompletionMessage(String Message, Context ApplicationContext) {
        Toast toast = Toast.makeText(ApplicationContext, Message, ToastDuration);
        toast.show();
    }
}
