package com.rhl.imagewiki;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

/**
 * Image search activity
 */
public class ImageSearchActivity extends AppCompatActivity {
    private static final String TAG = "ImageSearchActivity";
    private RecyclerView mRecyclerView;
    private List<Page> mPageList;
    private NetworkManager mNetworkManager;
    private ProgressBar mProgressBar;
    private TextView mErrorText;
    private String mPreviousString = "";
    private EditText mSearchEditText;

    // response listener
    private Response.Listener<JSONObject> mResponseListener = new Response.Listener<JSONObject>() {
        @Override
        public void onResponse(JSONObject response) {
            Log.d(TAG, "RESPONSE: " + response.toString());
            mPageList = ResponseParser.parse(response);
            updateResults(mPageList, R.string.result_unavailable);
        }
    };

    //error listener
    private Response.ErrorListener mErrorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.e(TAG, "ERROR in network::" + error.getMessage());
            showError(R.string.unable_to_connect);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_search);

        initializeViews();

        //initialize list
        StaggeredGridLayoutManager staggeredGridLayoutManager
                = new StaggeredGridLayoutManager(3, RecyclerView.VERTICAL);// staggered grid with 3 columns
        mRecyclerView.setLayoutManager(staggeredGridLayoutManager);
        ViewAdapter viewAdapter = new ViewAdapter(ImageSearchActivity.this, mPageList);
        mRecyclerView.setAdapter(viewAdapter);

        mNetworkManager = NetworkManager.getInstance(this);
        mSearchEditText.addTextChangedListener(new SearchTextWatcher());
    }

    private void initializeViews() {
        mErrorText = (TextView) findViewById(R.id.errorText);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mSearchEditText = (EditText) findViewById(R.id.editText);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        stopProgress();
    }

    private void startProgress() {
        mProgressBar.setIndeterminate(true);
    }

    private void stopProgress() {
        mProgressBar.setIndeterminate(false);
        mProgressBar.setProgress(100);// set progress as max to display it as a line
    }

    private JsonObjectRequest getJsonObjectRequest(String url) {
        return new JsonObjectRequest(Request.Method.GET, url, mResponseListener, mErrorListener);
    }

    private void showError(int errorMessage) {
        mErrorText.setText(errorMessage);
        mErrorText.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.GONE);
        stopProgress();
    }

    private void updateResults(List<Page> pages, int errorMessage) {
        if (pages == null || pages.size() == 0) {// show error if there are no results
            showError(errorMessage);
        } else {
            ViewAdapter viewAdapter = new ViewAdapter(ImageSearchActivity.this, pages);
            mRecyclerView.swapAdapter(viewAdapter, false);//swap new adapter with old one
            mRecyclerView.setVisibility(View.VISIBLE);
            mErrorText.setVisibility(View.GONE);
            stopProgress();
        }
    }

    //text watcher for type to search
    private class SearchTextWatcher implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            //do nothing
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            mNetworkManager.cancelPending();
            if (s.toString().equals("")) {// don't search for empty text
                showError(R.string.enter_text);
                return;
            }
            if (mPreviousString.equals(s.toString().trim())) {// don't search for previous query again
                return;
            }
            mPreviousString = s.toString();
            startProgress();

            //create url
            String url = null;
            try {
                url = NetworkManager.BASE_URL + URLEncoder.encode(s.toString(), "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            Log.d(TAG, "url:" + url);

            // make network call
            if (NetworkManager.getInstance(ImageSearchActivity.this).isOnline()) {
                mNetworkManager.addToRequestQueue(getJsonObjectRequest(url));
            } else {//show error network not available
                showError(R.string.network_unavailable);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
            //do nothing
        }
    }
}