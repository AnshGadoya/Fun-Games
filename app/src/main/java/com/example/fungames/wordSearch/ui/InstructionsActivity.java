package com.example.fungames.wordSearch.ui;


import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

import com.example.fungames.R;


/**
 * The activity that explains how to play the game.
 *
 * @author Andrew Smith
 */
public class InstructionsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.ws_activity_instructions);
	}

}
