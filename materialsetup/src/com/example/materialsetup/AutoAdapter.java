package com.example.materialsetup;

import java.util.List;

import android.content.Context;
import android.widget.ArrayAdapter;

public class AutoAdapter extends ArrayAdapter<String>{

	public AutoAdapter(Context context, int resource, List<String> objects) {
		super(context, resource, objects);
	}

}
