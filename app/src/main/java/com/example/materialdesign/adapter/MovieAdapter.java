package com.example.materialdesign.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.materialdesign.R;
import com.example.materialdesign.interfaces.MovieListener;
import com.example.materialdesign.model.MovieItem;
import com.example.roundimageview.RoundImageView;

import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieHolder> {

    private List<MovieItem> movies_list;
    private MovieListener movieListener;

    public MovieAdapter(List<MovieItem> movies_list, MovieListener movieListener) {
        this.movies_list = movies_list;
        this.movieListener = movieListener;
    }

    @NonNull
    @Override
    public MovieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new MovieHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MovieHolder holder, int position) {
        holder.bindMovie(movies_list.get(position));
    }


    @Override
    public int getItemCount() {
        return movies_list.size();
    }

    public List<MovieItem> getSelectedMovies() {

        List<MovieItem> selected_movies = new ArrayList<>();

        for (MovieItem movieItem : movies_list) {
            if (movieItem.isSelected) {

                selected_movies.add(movieItem);
            }
        }
        return selected_movies;
    }

    public class MovieHolder extends RecyclerView.ViewHolder {

        public ConstraintLayout l_movie_parent;
        public View view_background;
        public RoundImageView movie_image;
        public TextView movie_name, movie_creator, movie_about;
        public RatingBar movie_rating;
        public ImageView img_selected;

        public MovieHolder(@NonNull View itemView) {
            super(itemView);

            l_movie_parent = itemView.findViewById(R.id.l_movie_parent);
            view_background = itemView.findViewById(R.id.view_background);

            movie_image = itemView.findViewById(R.id.movie_image);
            movie_name = itemView.findViewById(R.id.movie_name);
            movie_creator = itemView.findViewById(R.id.movie_creator);
            movie_about = itemView.findViewById(R.id.movie_about);
            movie_rating = itemView.findViewById(R.id.movie_rating);

            img_selected = itemView.findViewById(R.id.img_selected);
        }

        void bindMovie(final MovieItem movieItem) {
            movie_image.setImageResource(movieItem.image);
            movie_name.setText(movieItem.name);
            movie_creator.setText(movieItem.creator);
            movie_about.setText(movieItem.about);
            movie_rating.setRating(movieItem.rating);

            if (movieItem.isSelected) {
                view_background.setBackgroundResource(R.drawable.movie_selected_background);
                img_selected.setVisibility(View.VISIBLE);

            } else {
                view_background.setBackgroundResource(R.drawable.movie_background);
                img_selected.setVisibility(View.GONE);
            }

            l_movie_parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (movieItem.isSelected){ // movie item is visible make it gone
                        view_background.setBackgroundResource(R.drawable.movie_background);
                        img_selected.setVisibility(View.GONE);

                        movieItem.isSelected = false;

                        if (getSelectedMovies().size() == 0){
                            movieListener.onMovieAction(false);
                        }

                    } else { // movie item is gone make it visible
                        view_background.setBackgroundResource(R.drawable.movie_selected_background);
                        img_selected.setVisibility(View.VISIBLE);
                        movieItem.isSelected = true;
                        movieListener.onMovieAction(true);

                    }
                }
            });
        }
    }
}
