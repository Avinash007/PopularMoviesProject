package com.avinash.popularmoviesproject.extras;

import com.avinash.popularmoviesproject.pojo.Review;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import static com.avinash.popularmoviesproject.extras.Keys.EndPointBoxOffice.*;

/**
 * Created by AVINASH on 09-05-2017.
 */

public class ReviewJsonUtils {

    public static ArrayList<Review> parseReviewResponse(JSONObject response){
        ArrayList<Review> reviewsList = new ArrayList<>();

        try{
            if(response!= null || response.length()>0){
                JSONArray arrayReview = response.getJSONArray(KEY_MOVIES);

                for(int i=0;i<arrayReview.length();i++){

                    String author = Constants.NA;
                    String content = Constants.NA;

                    JSONObject reviewObject = arrayReview.getJSONObject(i);
                    if(reviewObject.has(KEY_AUTHOR) && !reviewObject.isNull(KEY_AUTHOR)){
                        author = reviewObject.getString(KEY_AUTHOR);
                    }
                    if(reviewObject.has(KEY_CONTENT) && !reviewObject.isNull(KEY_CONTENT)){
                        content = reviewObject.getString(KEY_CONTENT);
                    }

                    Review review = new Review();
                    review.setAuthor(author);
                    review.setContent(content);

                    reviewsList.add(review);
                }

                return reviewsList;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;

    }
}
