package com.example.materialdesign.activity.list;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Movie;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.materialdesign.R;
import com.example.materialdesign.activity.InitialActivity;
import com.example.materialdesign.activity.MainActivity;
import com.example.materialdesign.adapter.MovieAdapter;
import com.example.materialdesign.adapter.SongDragAdapter;
import com.example.materialdesign.interfaces.MovieListener;
import com.example.materialdesign.model.MovieItem;
import com.example.materialdesign.utility.VariousTools;

import java.util.ArrayList;
import java.util.List;


public class MovieList extends AppCompatActivity implements MovieListener {

    private RecyclerView rv_movies;
    private MovieAdapter movieAdapter;
    private List<MovieItem> movies;
    private AppCompatButton btn_addToWatchList;

    private ImageView btn_previous_activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);

        VariousTools.setSystemBarColor(this, R.color.colorPrimaryDark);
        initComponents();
    }

    private void initComponents() {

        rv_movies = findViewById(R.id.moviesRecylcerView);
        btn_addToWatchList = findViewById(R.id.btn_addToWatchList);
        btn_previous_activity = findViewById(R.id.btn_previous_activity);


        btn_previous_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MovieList.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                startActivity(intent);
            }
        });

        populateList();

        btn_addToWatchList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<MovieItem> selectedMovies = movieAdapter.getSelectedMovies();

                StringBuilder movieNames = new StringBuilder();

                for (int i  = 0; i < selectedMovies.size(); i++){
                    if (i==0){
                        movieNames.append(selectedMovies.get(i).name);
                    } else {
                        movieNames.append("\n").append(selectedMovies.get(i).name);
                    }
                }
                Toast.makeText(MovieList.this, movieNames.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void populateList() {

        movies = new ArrayList<>();

        MovieItem first = new MovieItem();
        first.image = R.drawable.image_1;
        first.name = "First Movie";
        first.rating = 5f;
        first.creator = "First Creator";
        first.about = "This is a story about the first movie. Made in this year. Its this kind of a movie";
        movies.add(first);

        MovieItem second = new MovieItem();
        second.image = R.drawable.image_2;
        second.name = "Second Movie";
        second.rating = 3.7f;
        second.creator = "Second Creator";
        second.about = "This is a story about the second movie. Made in this year. Its this kind of a movie";
        movies.add(second);

        MovieItem third = new MovieItem();
        third.image = R.drawable.image_3;
        third.name = "Third Movie";
        third.rating = 1f;
        third.creator = "Third Creator";
        third.about = "This is a story about the third movie. Made in this year. Its this kind of a movie";
        movies.add(third);

        MovieItem fourth = new MovieItem();
        fourth.image = R.drawable.image_4;
        fourth.name = "Fourth Movie";
        fourth.rating = 5f;
        fourth.creator = "Fourth Creator";
        fourth.about = "This is a story about the fourth movie. Made in this year. Its this kind of a movie";
        movies.add(fourth);

        MovieItem fifth = new MovieItem();
        fifth.image = R.drawable.image_5;
        fifth.name = "Fifth Movie";
        fifth.rating = 2.2f;
        fifth.creator = "Fifth Creator";
        fifth.about = "This is a story about the fifth movie. Made in this year. Its this kind of a movie";
        movies.add(fifth);

        MovieItem sixth = new MovieItem();
        sixth.image = R.drawable.image_6;
        sixth.name = "Sixth Movie";
        sixth.rating = 5f;
        sixth.creator = "Sixth Creator";
        sixth.about = "This is a story about the sixth movie. Made in this year. Its this kind of a movie";
        movies.add(sixth);

        MovieItem seventh = new MovieItem();
        seventh.image = R.drawable.image_7;
        seventh.name = "Seventh Movie";
        seventh.rating = 5f;
        seventh.creator = "Seventh Creator";
        seventh.about = "This is a story about the first movie. Made in this year. Its this kind of a movie";
        movies.add(seventh);

        MovieItem eight = new MovieItem();
        eight.image = R.drawable.image_8;
        eight.name = "Eight Movie";
        eight.rating = 5f;
        eight.creator = "Eight Creator";
        eight.about = "This is a story about the eight movie. Made in this year. Its this kind of a movie";
        movies.add(eight);

        MovieItem ninth = new MovieItem();
        ninth.image = R.drawable.image_9;
        ninth.name = "Ninth Movie";
        ninth.rating = 5f;
        ninth.creator = "Ninth Creator";
        ninth.about = "This is a story about the ninth movie. Made in this year. Its this kind of a movie";
        movies.add(ninth);

        MovieItem tenth = new MovieItem();
        tenth.image = R.drawable.image_10;
        tenth.name = "Tenth Movie";
        tenth.rating = 5f;
        tenth.creator = "Tenth Creator";
        tenth.about = "This is a story about the tenth movie. Made in this year. Its this kind of a movie";
        movies.add(tenth);

        MovieItem eleventh = new MovieItem();
        eleventh.image = R.drawable.image_1;
        eleventh.name = "Eleventh Movie";
        eleventh.rating = 5f;
        eleventh.creator = "Eleventh Creator";
        eleventh.about = "This is a story about the eleventh movie. Made in this year. Its this kind of a movie";
        movies.add(eleventh);

        movieAdapter = new MovieAdapter(movies, this);
        rv_movies.setAdapter(movieAdapter);
    }

    @Override
    public void onMovieAction(Boolean isSelected) {
        if (isSelected){
            btn_addToWatchList.setVisibility(View.VISIBLE);
        }else{
            btn_addToWatchList.setVisibility(View.GONE);
        }
    }
}