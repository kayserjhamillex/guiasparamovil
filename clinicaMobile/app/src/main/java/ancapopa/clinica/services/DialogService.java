package ancapopa.clinica.services;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.widget.DatePicker;

import java.util.Calendar;

import ancapopa.clinica.R;


/**
 * Created by vlad on 27/07/2017.
 */

public class DialogService {
    public static AlertDialog buildLogoutDialog(Context context, DialogInterface.OnClickListener onConfirm) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setMessage(R.string.dialog_logout_content)
                .setTitle(R.string.dialog_logout_title)
                .setCancelable(true)
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton(R.string.yes,onConfirm);

        return builder.create();
    }

    public static void showTimeChooserDialog(AppCompatActivity activity, TimePickerDialog.OnTimeSetListener listener, int hour, int minute) {
        TimePickerFragment newFragment = new TimePickerFragment();
        newFragment.setListener(listener);
        newFragment.setHour(hour);
        newFragment.setMinute(minute);
        newFragment.show(activity.getSupportFragmentManager(), "timePicker");
    }


    public static void showTimeChooserDialog(AppCompatActivity activity, TimePickerDialog.OnTimeSetListener listener) {
        showTimeChooserDialog(activity,listener,-1,-1);
    }

    public static void showDateChooserDialog(AppCompatActivity activity, DatePickerDialog.OnDateSetListener listener, int hour, int minute) {
        DatePickerFragment newFragment = new DatePickerFragment();
        newFragment.setListener(listener);
        newFragment.show(activity.getSupportFragmentManager(), "datePicker");
    }


    public static void showDateChooserDialog(AppCompatActivity activity, DatePickerDialog.OnDateSetListener listener) {
        showDateChooserDialog(activity,listener,-1,-1);
    }

    public static class TimePickerFragment extends DialogFragment {
        public TimePickerDialog.OnTimeSetListener getListener() {
            return listener;
        }

        public void setListener(TimePickerDialog.OnTimeSetListener listener) {
            this.listener = listener;
        }

        private TimePickerDialog.OnTimeSetListener listener;

        public int getHour() {
            return hour;
        }

        public void setHour(int hour) {
            this.hour = hour;
        }

        private int hour = -1;

        public void setMinute(int minute) {
            this.minute = minute;
        }

        public int getMinute() {
            return minute;
        }

        private int minute = -1;

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
// Use the current time as the default values for the picker
            if (hour == -1 || minute == -1) {

                final Calendar c = Calendar.getInstance();
                hour = c.get(Calendar.HOUR_OF_DAY);
                minute = c.get(Calendar.MINUTE);
            }

// Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), listener, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }
    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        public DatePickerDialog.OnDateSetListener getListener() {
            return listener;
        }

        public void setListener(DatePickerDialog.OnDateSetListener listener) {
            this.listener = listener;
        }

        private DatePickerDialog.OnDateSetListener listener;

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user
        }
    }
}
