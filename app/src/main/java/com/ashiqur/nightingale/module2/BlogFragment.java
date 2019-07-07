package com.ashiqur.nightingale.module2;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import java.util.EmptyStackException;
import java.util.Stack;

import com.ashiqur.nightingale.R;
/**
 * A simple {@link Fragment} subclass.
 */
public class BlogFragment extends Fragment {

    //java
    private Context context;
    private View view;
    private String urlHome="https://www.kidshealth.org";
    private String urlNow= "https://www.kidshealth.org";
    private ProgressBar progressBar;
    private WebView webView;
    private static String TAG="Blog-FRAGMENT";
    private Stack<String> urlsbrowsed;
    @Override
    public void onAttach(Context c) {
        super.onAttach(c);

        Activity a;

        context=c;


    }

    public BlogFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (this.getArguments() != null) {
            urlHome = "https://"+this.getArguments().getString("url home");
            urlNow=urlHome;
            Log.wtf(TAG , urlHome+"");
        }
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_blog, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeXmlVariables(view);
        initializeJavaVariables();

    }

    private void initializeXmlVariables(View view) {
        this.view=view;

        BottomNavigationView bottomNavigationView=((MainActivity)context).getBottomNavigationView();
       // bottomNavigationView.getMenu().clear();
        //bottomNavigationView.setOnNavigationItemSelectedListener(this);
        //bottomNavigationView.inflateMenu(R.menu.menu_browser_blog);
        bottomNavigationView.setVisibility(View.VISIBLE);
        progressBar =view.findViewById(R.id.progressbar_frag_blog);
        Log.wtf(TAG,"Initialized XML Vars");
    }

    private void initializeJavaVariables() {
        initializeWebView();
        urlsbrowsed=new Stack<>();
        Log.wtf(TAG,"Initialized Java Vars");
    }
    private void initializeWebView()
    {
        webView =view.findViewById(R.id.webview_frag_blog);

        webView.loadUrl(urlNow);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);

                urlsbrowsed.push(url);
                urlNow=url;
                progressBar.setVisibility(View.VISIBLE);
                progressBar.setProgress(200);
                Log.wtf(TAG,"Started Loading "+url);

            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

                progressBar.setProgress(500);
                progressBar.setVisibility(View.INVISIBLE);
                Log.wtf(TAG,"Finished Loading "+url);
            }

        });

    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_browser_blog, menu);
        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.item_bottomnav_blog_home_frag_blog:
                webView.loadUrl(urlHome);
                return true;
            case R.id.item_bottomnav_refreshblog_frag_blog:
                Log.wtf(TAG,"Refreshing "+urlNow);
                webView.loadUrl(urlNow);
                return true;
            case R.id.item_bottomnav_back_frag_blog:
                String urlbefore="";
                try {
                    urlsbrowsed.pop();
                    urlbefore = urlsbrowsed.pop();
                }catch (EmptyStackException e){}
                Log.wtf(TAG,"Loading Previous Page "+urlbefore);
                if( urlbefore!=null && !urlbefore.equals("") )webView.loadUrl(urlbefore);
                return true;

        }


        return false;
    }


}
