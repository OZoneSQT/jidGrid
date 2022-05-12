package dk.seahawk.jidgrid.util;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

public class MyExceptionHandler extends AppCompatActivity {

    public MyExceptionHandler() {
    }

    public void pushExceptionMessage(String message) {
        // System.out.println(message);

        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
        Log.e("Error: ", message);
    }

}
