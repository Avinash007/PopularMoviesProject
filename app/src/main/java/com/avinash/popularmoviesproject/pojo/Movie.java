package com.avinash.popularmoviesproject.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by AVINASH on 12-04-2017.
 */

public class Movie implements Parcelable {
    private Long id;

    private String title;
    private Date releaseDate;
    private String synopsis;
    private Double rating;
    private String urlImage;
    private String urlCast;
    private String urlReviews;

    public Movie() {

    }

    public Movie(Parcel input){
        id=input.readLong();
        title = input.readString();
        releaseDate = new Date(input.readLong());
        synopsis = input.readString();
        rating = input.readDouble();
        urlImage = input.readString();
    }

    public Movie(Long id, String title, Date releaseDate, String synopsis, Double rating, String urlImage, String urlCast, String urlReviews) {
        this.id = id;
        this.title = title;
        this.releaseDate = releaseDate;
        this.synopsis = synopsis;
        this.rating = rating;
        this.urlImage = urlImage;
        this.urlCast = urlCast;
        this.urlReviews = urlReviews;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public Double getRating() {
        return rating;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public String getUrlCast() {
        return urlCast;
    }

    public String getUrlReviews() {
        return urlReviews;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public void setUrlCast(String urlCast) {
        this.urlCast = urlCast;
    }

    public void setUrlReviews(String urlReviews) {
        this.urlReviews = urlReviews;
    }

    @Override
    public String toString() {
        return "ID: "+id+
                " Title: "+title+
                " Release Date: "+releaseDate+
                " Ratings: "+rating+
                " Synopsis: "+synopsis+"\n";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(id);
        parcel.writeString(title);
        parcel.writeLong(releaseDate.getTime());
        parcel.writeString(synopsis);
        parcel.writeDouble(rating);
        parcel.writeString(urlImage);

    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>(){
        public Movie createFromParcel(Parcel in){
            return new Movie(in);
        }
        public Movie[] newArray(int size){
            return new Movie[size];
        }
    };


}
