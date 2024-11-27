package com.example.textstream;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class LogoutDialogFragment extends DialogFragment {

    public interface LogoutDialogListener {
        void onLogoutConfirmed();
    }

    private LogoutDialogListener listener;

    public static LogoutDialogFragment newInstance() {
        return new LogoutDialogFragment();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof LogoutDialogListener) {
            listener = (LogoutDialogListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement LogoutDialogListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Are you sure you want to log out?")
                .setPositiveButton("Yes", (dialog, id) -> {
                    if (listener != null) {
                        listener.onLogoutConfirmed();
                    }
                })
                .setNegativeButton("No", (dialog, id) -> dialog.dismiss());
        return builder.create();
    }
}