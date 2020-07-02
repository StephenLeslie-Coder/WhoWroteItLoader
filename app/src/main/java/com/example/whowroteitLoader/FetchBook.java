package com.example.whowroteitLoader;

import android.os.AsyncTask;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.ref.WeakReference;

public class FetchBook extends AsyncTask<String,Void,String> {
    WeakReference<TextView> mTitleText;
    WeakReference<TextView> mAuthorText;
    @Override
    protected String doInBackground(String... strings) {
        return NetworkUtils.getBookInfo(strings[0]);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        try{
            JSONObject jsonObject= new JSONObject(s);
            JSONArray itemsArray=jsonObject.getJSONArray("items");
            int i=0;
            String title= null;
            String authors=null;
            while (i<itemsArray.length() && (authors==null && title==null)){
                JSONObject book= itemsArray.getJSONObject(i);
                JSONObject volumeInfo= book.getJSONObject("volumeInfo");
                try{
                    title=volumeInfo.getString("title");
                    authors=volumeInfo.getString("authors");
                }catch (Exception e ){
                    e.printStackTrace();
                }
                i++;

            }
            if(title!=null && authors!=null){
                mTitleText.get().setText(title);
                mAuthorText.get().setText(authors);
            }else{
                mTitleText.get().setText("No results");
                mAuthorText.get().setText("");
            }

        }catch (Exception e){

            mTitleText.get().setText("No results");
            mAuthorText.get().setText("");

        }
    }
    FetchBook (TextView tT, TextView aT){
        this.mAuthorText= new WeakReference<>(aT);
        this.mTitleText= new WeakReference<>(tT);
    }
}
