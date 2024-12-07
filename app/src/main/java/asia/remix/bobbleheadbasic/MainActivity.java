package asia.remix.bobbleheadbasic;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.dynamicanimation.animation.FloatPropertyCompat;//Float No cast "SpringAnimation.TRANSLATION_Y"
import androidx.dynamicanimation.animation.SpringAnimation;
import androidx.dynamicanimation.animation.SpringForce;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity{
	final static String TAG= "MainActivity";
	ImageView imageView;
	SpringAnimation springAnimationX;
	SpringAnimation springAnimationY;

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

		imageView= findViewById( R.id.imageView );
		imageView.setOnClickListener( onClickListener );
		springAnimationX= createSpringAnimationB( imageView, SpringAnimation.TRANSLATION_X );
		springAnimationY= createSpringAnimationA( imageView, SpringAnimation.TRANSLATION_Y );
	}

	SpringAnimation createSpringAnimationA( View v, FloatPropertyCompat f ){
		Log.d( TAG, "createSpringAnimation()" );
		SpringAnimation sa = new SpringAnimation( v, f );//最終値無し
		SpringForce sf = new SpringForce();//最終値無し
		sf.setStiffness( SpringForce.STIFFNESS_MEDIUM );//バネ定数 硬さ
		sf.setDampingRatio( SpringForce.DAMPING_RATIO_HIGH_BOUNCY / 4 );//減衰比
		sa.setSpring( sf );
		return sa;
	}

	SpringAnimation createSpringAnimationB( View v, FloatPropertyCompat f ){
		Log.d( TAG, "createSpringAnimation()" );
		SpringAnimation sa = new SpringAnimation( v, f );//最終値無し
		SpringForce sf = new SpringForce();//最終値無し
		sf.setStiffness( SpringForce.STIFFNESS_LOW );//バネ定数 硬さ
		sf.setDampingRatio( SpringForce.DAMPING_RATIO_HIGH_BOUNCY / 3 );//減衰比
		sa.setSpring( sf );
		return sa;
	}

	void shakeRestart(){
		springAnimationX.getSpring().setFinalPosition( 0f );
		springAnimationX.setStartValue( 200f );
		springAnimationX.start();

		springAnimationY.getSpring().setFinalPosition( 0f );
		springAnimationY.setStartValue( 200f );
		springAnimationY.start();
	}

	View.OnClickListener onClickListener= new View.OnClickListener(){
		@Override
		public void onClick( View v ){
			shakeRestart();
		}
	};
}