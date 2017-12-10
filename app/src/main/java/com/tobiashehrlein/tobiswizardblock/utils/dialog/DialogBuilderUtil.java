package com.tobiashehrlein.tobiswizardblock.utils.dialog;

import android.app.AlertDialog;
import android.content.Context;

import com.tobiashehrlein.tobiswizardblock.R;


/**
 * Created by Tobias Hehrlein on 17.10.2017.
 */

public class DialogBuilderUtil {

    public static AlertDialog createErrorDialog(Context context, String message) {
        return createErrorDialog(context, message, false, null);
    }

    public static AlertDialog createErrorDialog(Context context, String title, String message) {
        return createErrorDialog(context, title, message, false, null);
    }

    private static AlertDialog createErrorDialog(Context context, String title, String message, boolean cancelable, DialogBuilderListener listener) {
        return createDialog(context, title, message, cancelable, listener);
    }

    public static AlertDialog createErrorDialog(Context context, String message, DialogBuilderListener listener) {
        return createErrorDialog(context, message, false, listener);
    }

    public static AlertDialog createErrorDialog(Context context, String message, boolean cancelable, DialogBuilderListener listener) {
        return createDialog(context, "Error", message, cancelable, listener);
    }

    public static AlertDialog createDialog(Context context, String message) {
        return createDialog(context, "", message);
    }

    public static AlertDialog createDialog(Context context, String title, String message) {
        return createDialog(context, title, message, null);
    }

    public static AlertDialog createDialog(Context context, String title, String message, boolean onlyOneButton) {
        return createDialog(context, title, message, false, onlyOneButton, null);
    }

    public static AlertDialog createDialog(Context context, String title, String message, DialogBuilderListener listener) {
        return createDialog(context, title, message, false, listener);
    }

    public static AlertDialog createDialog(Context context, String title , String message, boolean cancelable, final DialogBuilderListener listener) {
        return createDialog(context, title, message, cancelable, false, listener);
    }

    public static AlertDialog createDialog(Context context, String title , String message, boolean cancelable, boolean onlyOneButton, final DialogBuilderListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setCancelable(cancelable);

        builder.setPositiveButton(android.R.string.ok, (dialog, which) -> {
            if (listener != null) {
                listener.onConfirm();
            } else {
                dialog.dismiss();
            }
        });

        if (onlyOneButton) {
            return builder.create();
        }

        builder.setNegativeButton(context.getString(R.string.cancel), (dialog, which) -> {
            if (listener != null) {
                listener.onCancel();
            } else {
                dialog.dismiss();
            }
        });

        return builder.create();
    }

}
