package pt.isec.amov.petfinder;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;

/**
 * Created by mgois on 06-01-2015.
 */
public class DeleteMyPostConfirmationDialog extends DialogFragment {

    public static final String TAG = "DeleteMyPostConfirmationDialog";

    Listener listener;

    @Override
    public void onAttach(final Activity activity) {
        super.onAttach(activity);

        try {
            listener = (Listener) activity;
        } catch (final ClassCastException ex) {
            throw new ClassCastException(activity.toString() + " must implement the " + Listener.class.getSimpleName() + " interface");
        }
    }

    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        return new Builder(getActivity())
            .setMessage(R.string.my_post_delete_confirmation)
                .setPositiveButton(R.string.my_post_delete_confirmation_yes, new OnClickListener() {

                    @Override
                    public void onClick(final DialogInterface dialogInterface, final int i) {
                        listener.onDialogPostitiveClick(DeleteMyPostConfirmationDialog.this);
                    }
            })
                .setNegativeButton(R.string.my_post_delete_confirmation_no, new OnClickListener() {

                    @Override
                    public void onClick(final DialogInterface dialogInterface, final int i) {
                        listener.onDialogNegativeClick(DeleteMyPostConfirmationDialog.this);
                    }
            }).create();
    }

    public interface Listener {
        public void onDialogPostitiveClick(DialogFragment dialog);
        public void onDialogNegativeClick(DialogFragment dialog);
    }

}
