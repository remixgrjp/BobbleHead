package asia.remix.bobbleheadbasic;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity{
	final static String TAG= "MainActivity";

	@Override
	public void onStart(){//onCreate → onStart → onResume ／ onRestart → onStart → onResume
		super.onStart();
		Log.d( TAG, "onStart()" );
	}

	@Override
	public void onResume(){
		super.onResume();
		Log.d( TAG, "onResume()" );
	}

	@Override
	public void onPause(){//→ onResume()
		super.onPause();
		Log.d( TAG, "onPause()" );
	}

	@Override
	public void onStop(){//→ onRestart() → onStart()
		super.onStop();
		Log.d( TAG, "onStop()" );
	}

	@Override
	public void onDestroy(){//onPause() → onStop() → onDestroy()
		super.onDestroy();
		Log.d( TAG, "onDestroy()" );
	}

	@Override
	public void onWindowFocusChanged( boolean hasFocus ){
		Log.d( TAG, String.format( "onWindowFocusChanged( %s )", hasFocus ) );
	}

	@Override
	protected void onCreate( Bundle savedInstanceState ){
		super.onCreate( savedInstanceState );
		Log.d( TAG, String.format( "onCreate( %s )", savedInstanceState ) );
		EdgeToEdge.enable( this );
		setContentView( R.layout.activity_main );
		ViewCompat.setOnApplyWindowInsetsListener( findViewById( R.id.main ), ( v, insets )->{
			Insets systemBars = insets.getInsets( WindowInsetsCompat.Type.systemBars() );
			v.setPadding( systemBars.left, systemBars.top, systemBars.right, systemBars.bottom );
			return insets;
		} );
	}
}