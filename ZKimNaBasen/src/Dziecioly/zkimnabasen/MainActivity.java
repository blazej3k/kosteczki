package Dziecioly.zkimnabasen;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;


public class MainActivity extends ActionBarActivity {

	Context context;
	Button przycisk;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = getApplicationContext();
        
        przycisk = (Button) findViewById(R.id.dupa);
        
<<<<<<< HEAD
        System.out.println("Karolka");
             
=======
        System.out.println("DUPA");
        System.out.println("Dwie æwiartki kurczaka.");
        System.out.println("Dwie æwiartki kurczakadddd.");
        
        
        //ugigguygiuguigu
        System.out.println("zagajnik");
        
        
        System.out.println("JAKI PIEKNY WALCZYK");
>>>>>>> branch 'master' of https://github.com/blazej3k/kosteczki.git
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
