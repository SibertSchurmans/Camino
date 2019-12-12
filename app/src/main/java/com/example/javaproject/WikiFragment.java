package com.example.javaproject;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Spannable;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class WikiFragment extends Fragment {
    TextView myTextView;
    TextView titleTextView;

    private Context environment;
    private String wikiPageContent;
    private String oldSession;
    private String sessionId;

    public static String bundle2string(Bundle bundle) {
        if (bundle == null) {
            return null;
        }
        String string = "";
        for (String key : bundle.keySet()) {
            string += bundle.get(key) + ";";
        }
        //string += " }Bundle";
        return string;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wiki, container, false);

        myTextView = (TextView) view.findViewById(R.id.WikiContent);
        titleTextView = (TextView) view.findViewById(R.id.titleTextView);

        String baseUrl = "http://171.25.229.102:8229/api/wiki/name/";
        String url;


        Bundle bundle = this.getArguments();

        String bundleString = bundle2string(bundle);
        try{
            int placeNewString = bundleString.indexOf(";");
            int length = bundleString.length();

            sessionId = bundleString.substring(0,placeNewString);
            oldSession = bundleString.substring(placeNewString+1, length-1);
        }
        catch (Exception ex){
            sessionId = "hello world";
            oldSession = "";
        }

        url = baseUrl + sessionId;
        new JsonTask().execute(url);

        return view;
    }

    ProgressDialog pd;

    private class JsonTask extends AsyncTask<String, String, String> {

        protected void onPreExecute() {
            super.onPreExecute();

            pd = new ProgressDialog(getActivity());
            pd.setMessage("Please wait");
            pd.setCancelable(false);
            pd.show();
        }

        protected String doInBackground(String... params) {


            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();


                InputStream stream = connection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();
                String line = "";

                while ((line = reader.readLine()) != null) {
                    buffer.append(line+"\n");
                    Log.d("Response: ", "> " + line);   //here u ll get whole response...... :-)

                }

                return buffer.toString();


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (pd.isShowing()){
                pd.dismiss();
            }
            wikiPageContent = result;

            String titleWiki = "";
            String contentWiki = "";
            try{
                int startName = wikiPageContent.indexOf("name\":\"");
                int endName = wikiPageContent.indexOf("\",\"description\":\"");
                titleWiki = wikiPageContent.substring(startName+7, endName);

                int startContent = wikiPageContent.indexOf("description\":\"");
                int endContent = wikiPageContent.indexOf("\"}]");
                contentWiki = wikiPageContent.substring(startContent+14, endContent);
            }
            catch (Exception ex){

            }

            titleTextView.setText(titleWiki);

            String myString = "";

            if (!oldSession.equals("")){
                myString = "Go back to [" + oldSession + "] \n\n\n";
            }

            if(titleWiki.equals("") && contentWiki.equals("")){
                myString += "Nothing found";
                oldSession = "";

                titleTextView.setVisibility(View.GONE);
            }
            else{
                myString += contentWiki;
                oldSession = titleWiki;
            }

            myTextView.setMovementMethod(LinkMovementMethod.getInstance());
            String newString = myString.replace("[","");
            newString = newString.replace("]","");
            myTextView.setText(newString);
            int a = 0;
            int b = 0;
            int c = 0;
            while (myString.indexOf("[", a+1) != -1){
                int i1 = myString.indexOf("[", a);
                int i2 = myString.indexOf("]", c);
                a = i1+1;
                c = i2+1;
                final Spannable mySpannable = (Spannable)myTextView.getText();
                final String item = myString.substring(i1+1, i2);
                final String oldItem = oldSession;

                ClickableSpan myClickableSpan = new ClickableSpan() {
                    @Override
                    public void onClick(View widget) {
                        Bundle bundle = new Bundle();
                        bundle.putString("ITEM_NAME",item);
                        bundle.putString("ITEM_OLD",oldItem);

                        WikiFragment wikiFragment = new WikiFragment();
                        wikiFragment.setArguments(bundle);

                        getFragmentManager()
                                .beginTransaction()
                                .replace(R.id.fragment_container, wikiFragment)
                                .commit();
                    }
                };

                mySpannable.setSpan(myClickableSpan, i1-2*b, i2-1-2*b, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                b++;
            }
        }
    }
}
