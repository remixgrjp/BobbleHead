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
	SpringAnimation springAnimation;

	static class A{
		String name;
		FloatPropertyCompat floatPropertyCompat;// FloatPropertyCompat<?> floatPropertyCompat;
		public A( String name, FloatPropertyCompat f ){// FloatPropertyCompat<?> f
			this.name= name;
			this.floatPropertyCompat= f;
		}
	}
	final static A[] a= {
		new A( "TRANSLATION_X", SpringAnimation.TRANSLATION_X )
	,	new A( "TRANSLATION_Y", SpringAnimation.TRANSLATION_Y )
	,	new A( "TRANSLATION_Z", SpringAnimation.TRANSLATION_Z )
	,	new A( "SCROLL_X", SpringAnimation.SCROLL_X )
	,	new A( "SCROLL_Y", SpringAnimation.SCROLL_Y )
	,	new A( "ROTATION", SpringAnimation.ROTATION )
	,	new A( "ROTATION_X", SpringAnimation.ROTATION_X )
	,	new A( "ROTATION_Y", SpringAnimation.ROTATION_Y )
	,	new A( "X", SpringAnimation.X )
	,	new A( "Y", SpringAnimation.Y )
	,	new A( "Z", SpringAnimation.Z )
//	,	new A( "ALPHA", SpringAnimation.ALPHA )     // 0 finish : off
//	,	new A( "SCALE_X", SpringAnimation.SCALE_X ) // 0 finish : off
//	,	new A( "SCALE_Y", SpringAnimation.SCALE_Y ) // 0 finish : off
	};
	int idx= 0;//A array index

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
		springAnimation= createSpringAnimation( imageView, a[idx].floatPropertyCompat );
	}

	SpringAnimation createSpringAnimation( View v, FloatPropertyCompat f ){
		Log.d( TAG, "createSpringAnimation()" );
		SpringAnimation sa = new SpringAnimation( v, f );//最終値無し
		SpringForce sf = new SpringForce();//最終値無し
		sf.setStiffness( SpringForce.STIFFNESS_MEDIUM );//バネ定数 硬さ
		sf.setDampingRatio( SpringForce.DAMPING_RATIO_HIGH_BOUNCY / 4 );//減衰比
		sa.setSpring( sf );
		return sa;
	}

	void shakeRestart(){
		if( null != springAnimation ){
			springAnimation.removeEndListener( null );//終了リスナーを解除
			springAnimation= null;//明示的に解放
		}

		if( a.length <= ++idx ) idx= 0;
		springAnimation= createSpringAnimation( imageView, a[idx].floatPropertyCompat );
		Log.d( TAG,  a[idx].name );

		springAnimation.cancel();
		springAnimation.getSpring().setFinalPosition( 0f );
		springAnimation.setStartValue( 200f );
		springAnimation.start();
	}

	View.OnClickListener onClickListener= new View.OnClickListener(){
		@Override
		public void onClick( View v ){
			shakeRestart();
		}
	};
}