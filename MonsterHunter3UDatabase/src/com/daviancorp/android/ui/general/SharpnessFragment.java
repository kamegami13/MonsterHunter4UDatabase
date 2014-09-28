package com.daviancorp.android.ui.general;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



public class SharpnessFragment extends Fragment {
	DrawSharpness mDrawSharpness;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent,
	Bundle savedInstanceState) {
//	View v = inflater.inflate(R.layout.fragment_drag_and_draw, parent, false);
		
		mDrawSharpness = new DrawSharpness(getActivity(), 80,70,110,90,50,50,30);
		mDrawSharpness.setBackgroundColor(Color.TRANSPARENT);
		return mDrawSharpness;
	}

}
