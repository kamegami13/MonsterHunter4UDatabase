package com.daviancorp.android.ui.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.util.Log;
import android.widget.TextView;

import com.daviancorp.android.monsterhunter3udatabase.R;

public class AboutDialogFragment extends DialogFragment {
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		Log.d("helpme", "oncreatedialog");
		final TextView message = new TextView(getActivity());

		final SpannableString s = new SpannableString(getActivity().getText(
				R.string.about_message));
		Linkify.addLinks(s, Linkify.WEB_URLS);
	
		message.setText(s);
		message.setMovementMethod(LinkMovementMethod.getInstance());
		;
		message.setPadding(20, 10, 20, 10);
		message.setTextSize(18);

		return new AlertDialog.Builder(getActivity())
				.setTitle(R.string.about)
				.setPositiveButton(R.string.alert_rate,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
						        Intent intent = new Intent();
						        intent.setAction(Intent.ACTION_VIEW);
						        intent.addCategory(Intent.CATEGORY_BROWSABLE);
						        intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.daviancorp.android.monsterhunter3udatabase"));
						        startActivity(intent);
								dialog.dismiss();
							}
						})
				.setNegativeButton(R.string.alert_button,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								dialog.dismiss();
							}
						})
				.setView(message).create();

	}
}
