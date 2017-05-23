package com.avinash.popularmoviesproject.extras;

import com.avinash.popularmoviesproject.pojo.Trailer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.avinash.popularmoviesproject.extras.Keys.EndPointBoxOffice.KEY_MOVIES;
import static com.avinash.popularmoviesproject.extras.Keys.EndPointBoxOffice.TRAILER_KEY;
import static com.avinash.popularmoviesproject.extras.Keys.EndPointBoxOffice.TRAILER_NAME;
import static com.avinash.popularmoviesproject.extras.Keys.EndPointBoxOffice.TRAILER_SITE;
import static com.avinash.popularmoviesproject.extras.Keys.EndPointBoxOffice.TRAILER_TYPE;

/**
 * Created by AVINASH on 16-05-2017.
 */

public class TrailerJsonUtils {

    public static ArrayList<Trailer> parseTrailerResponse(JSONObject response){
        ArrayList<Trailer> trailerList = new ArrayList<>();

        try {
            if (response != null && response.length() > 0) {

                JSONArray arrayTrailer = response.getJSONArray(KEY_MOVIES);
                for(int i=0;i<arrayTrailer.length();i++){

                    String key = Constants.NA;
                    String site = Constants.NA;
                    String type = Constants.NA;
                    String name = Constants.NA;

                    JSONObject trailerObject = arrayTrailer.getJSONObject(i);

                    if(trailerObject.has(TRAILER_SITE) && !trailerObject.isNull(TRAILER_SITE)){
                        site = trailerObject.getString(TRAILER_SITE);
                        if(site.equals("YouTube")){
                            if(trailerObject.has(TRAILER_KEY) && !trailerObject.isNull(TRAILER_KEY)) {
                                key = trailerObject.getString(TRAILER_KEY);
                            }
                            if(trailerObject.has(TRAILER_TYPE) && !trailerObject.isNull(TRAILER_TYPE)){
                                type = trailerObject.getString(TRAILER_TYPE);
                            }
                            if (trailerObject.has(TRAILER_NAME) && !trailerObject.isNull(TRAILER_NAME)){
                                name = trailerObject.getString(TRAILER_NAME);
                            }

                            Trailer current = new Trailer();
                            current.setKey(key);
                            current.setName(name);
                            current.setType(type);
                            current.setSite(site);
                            trailerList.add(current);

                        }
                    }

                }

                return trailerList;

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;

    }
}
