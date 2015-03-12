class ArmorSetBuilderDecorationsDialogFragment extends DialogFragment {
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) { // TODO: This is the dialog that will show when the user wishes to delete a decoration
		LayoutInflater inflater = getActivity().getLayoutInflater();
		
		View addView = inflater.inflate(R.layout.dialog_armor_set_builder_decoration_list, null);
		
		TextView decoration1 = (TextView) addView.findViewById(R.id.decoration_1);
		TextView decoration2 = (TextView) addView.findViewById(R.id.decoration_2);
		TextView decoration3 = (TextView) addView.findViewById(R.id.decoration_3);
		
		return new AlertDialog.Builder(getActivity())
			.setTitle(R.string.option_wishlist_add)
			.setView(addView)
			.setNegativeButton(android.R.string.cancel, null)
			.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {

			})
			.create();
	}
}