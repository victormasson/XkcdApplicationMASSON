package masson.diiage.org.xkcdapplicationmasson;

import android.os.AsyncTask;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import masson.diiage.org.xkcdapplicationmasson.models.Comic;

public class MainActivity extends AppCompatActivity {

    public static TextView textViewTitle;
    public static TextView textViewDate;
    public static ImageView imageViewImg;
    public static TextView textViewAlt;
    public static Button buttonPrev;
    public static Button buttonNext;
    public static Button buttonGo;
    public static EditText editTextNumber;
    public static ProgressBar progressBar;
    public static int number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewTitle = findViewById(R.id.textViewTitle);
        textViewDate = findViewById(R.id.textViewDate);
        imageViewImg = findViewById(R.id.imageViewImg);
        textViewAlt = findViewById(R.id.textViewAlt);
        buttonPrev = findViewById(R.id.buttonPrev);
        buttonNext = findViewById(R.id.buttonNext);
        buttonGo = findViewById(R.id.buttonGo);
        editTextNumber = findViewById(R.id.editTextNumber);
        progressBar = findViewById(R.id.progressBar);

        number = 1;

        loadApi();

        buttonPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decrementeNumber();
                loadApi();
            }
        });

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                incrementeNumber();
                loadApi();
            }
        });

        buttonGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setNumber(Integer.parseInt(editTextNumber.getText().toString()));
                loadApi();
            }
        });
    }

    private void incrementeNumber() {
        number++;
        editTextNumber.setText(String.valueOf(number));
    }

    private void decrementeNumber() {
        if (number != 1) {
            number--;
            editTextNumber.setText(String.valueOf(number));
        }
    }

    private void setNumber(int i) {
        if (i <= 0) number = 1;
        number = i;
    }

    private void visibleProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    private void invisibleProgressBar() {
        progressBar.setVisibility(View.GONE);
    }

    private void loadImageFromUrl(String url) {
        Picasso.with(this).load(url).placeholder(R.mipmap.ic_launcher) // optional for if no image
            .error(R.mipmap.ic_launcher)
            .into(imageViewImg, new com.squareup.picasso.Callback() {

                @Override
                public void onSuccess() {

                }

                @Override
                public void onError() {

                }
            });
    }

    private void loadApi() {
        String baseUrlApi = getResources().getString(R.string.base_url_api_number_a)
                + number
                + getResources().getString(R.string.base_url_api_number_z);

        URL baseUrl = null;
        try {
            baseUrl = new URL(baseUrlApi); // Création de l'URL dans les ressources
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        AsyncTask<URL, Integer, Comic> task = new AsyncTask<URL, Integer, Comic>() {
            @Override
            protected Comic doInBackground(URL... urls) {
                Comic comic = new Comic();

                try {
                    InputStream inputStream = urls[0].openStream(); // Ouverture de la connexion avec l'URL
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));  // Création d'un bufferReader pour faciliter la lecture

                    StringBuilder stringBuilder = new StringBuilder(); // Permet de concaténer plus rapidement les strings.
                    String lineBuffer = null;

                    while ((lineBuffer = bufferedReader.readLine()) != null){ // Tant qu'il y a des choses à lire.
                        stringBuilder.append(lineBuffer); // Ajout des lignes.
                    }

                    String data = stringBuilder.toString();
                    final JSONObject jsonObjectComic = new JSONObject(data);
                    comic = Comic.ComicBuilder.builder(jsonObjectComic); // Creation du comic grâce au builder Json déclaré dans la classe

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return comic;
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                visibleProgressBar();
            }

            @Override
            protected void onPostExecute(Comic comic) {
                super.onPostExecute(comic);

                invisibleProgressBar();
                try {
                    textViewDate.setText(comic.getDate().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                textViewTitle.setText(comic.getTitle());
                textViewAlt.setText(comic.getAlt());
                loadImageFromUrl(comic.getImg());
            }
        }.execute(baseUrl);
    }
}
