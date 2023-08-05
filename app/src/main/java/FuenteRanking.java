import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.costa.frotiloop.R;

/**
 * Created by costa on 14/08/2022.
 */

public class FuenteRanking extends AppCompatActivity {

    TextView tv_fuente;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_item_ranking);

        tv_fuente=(TextView)findViewById(R.id.textView_texto);

        Typeface fuente = Typeface.createFromAsset(getAssets(), "fuentes/boxi_bold.otf");
        tv_fuente.setTypeface(fuente);
    }
}
