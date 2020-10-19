package com.example.marikiti.app;

import android.content.Context;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.marikiti.R;
import com.example.marikiti.dialog.LoadingDialog;

import java.util.Map;

import gautam.easydevelope.utils.ViewUtils;


public class Network {
    private final String TAG = getClass().getSimpleName();
    private Context mContext;
    private String URL;
    private Map<String, String> keyValueStringMap;
    private OnResponseListener onResponseListener;
    private Network network;
    private ProgressBar progressBar;
    private SwipeRefreshLayout swipeRefreshLayout;
    private TextView tv_errorInfo;
    private Response.ErrorListener errorListener;
    private String hintString = "";

    public Response.ErrorListener getErrorListener() {
        return errorListener;
    }

    public Network setErrorListener(Response.ErrorListener errorListener) {
        this.errorListener = errorListener;
        return this;
    }

    public TextView getTv_errorInfo() {
        return tv_errorInfo;
    }

    public Network setTv_errorInfo(TextView tv_errorInfo, String hintString) {
        this.tv_errorInfo = tv_errorInfo;
        this.hintString = hintString;
        return network;
    }

    public ProgressBar getProgressBar() {
        return progressBar;
    }

    public SwipeRefreshLayout getSwipeRefreshLayout() {
        return swipeRefreshLayout;
    }

    public Network setSwipeRefreshLayout(SwipeRefreshLayout swipeRefreshLayout) {
        this.swipeRefreshLayout = swipeRefreshLayout;
        return network;
    }

    public Network setProgressBar(ProgressBar progressBar) {
        this.progressBar = progressBar;
        return network;
    }

    public Network(Context mContext, String URL, Map<String, String> keyValueStringMap, OnResponseListener onResponseListener) {
        this.mContext = mContext;
        this.URL = URL;
        this.keyValueStringMap = keyValueStringMap;
        this.onResponseListener = onResponseListener;
        network = this;
//        keyValueStringMap.put(App.KEY_TOKEN, App.TOKEN);
//        keyValueStringMap.put(App.KEY_D_TYPE, App.D_TYPE);
//        keyValueStringMap.put(App.KEY_USER_TYPE, App.USER_DRIER);
    }

    public Network getData(boolean isLoadingDialogShow) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.w(TAG, "onResponse: " + URL + " \n" + response);
                        onResponseListener.onResponse(response);
                        handleSuccess(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "onErrorResponse: ", error);
//                        errorListener.onErrorResponse(error);
                        error.printStackTrace();
                        handleError(getCurrentError(error));
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                if (keyValueStringMap != null) {
                    Log.w(TAG, "getParams: " + URL + keyValueStringMap.toString());
                    return keyValueStringMap;
                } else {
                    Log.w(TAG, "getParams: null key map " + URL);
                    return null;
                }
            }
        };
        Marikiti.getInstance().addToRequestQueue(stringRequest);
        handleStart(isLoadingDialogShow);
        return network;
    }

    public Network getBackgroundData() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        onResponseListener.onResponse(response);
                        Log.w(TAG, "onResponse: " + URL + " \n" + response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "onErrorResponse: ", error);
                        error.printStackTrace();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                if (keyValueStringMap != null) {
                    Log.w(TAG, "getParams: " + URL + keyValueStringMap.toString());
                    return keyValueStringMap;
                } else {
                    Log.w(TAG, "getParams: null key map " + URL);
                    return null;
                }
            }
        };
        Marikiti.getInstance().addToRequestQueue(stringRequest);
        return network;
    }

    public interface OnResponseListener {
        void onResponse(String response);
    }

    private void handleError(String errorString) {
        LoadingDialog.hide();

//        manage if any progress view visible
        if (getProgressBar() != null) {
            ViewUtils.hideView(getProgressBar());
        }
//      manage swipe layout
        if (getSwipeRefreshLayout() != null) {
            getSwipeRefreshLayout().setRefreshing(false);
        }
//      manage error textView
        if (getTv_errorInfo() != null){
            String htmlString = "<font color='#"+ Integer.toHexString(mContext.getResources().getColor(R.color.colorRead)).substring(2)+"'>"+ errorString + "</font>"+"\n"+hintString;
            getTv_errorInfo().setText(htmlString);
        }
    }

    // handle start
    private void handleStart(boolean isLoadingDialogShow) {
        if (getSwipeRefreshLayout() != null) {
            getSwipeRefreshLayout().setRefreshing(true);
        }
        if (isLoadingDialogShow)
            LoadingDialog.openDialog(mContext);
    }

    private void handleSuccess(String successString) {
        LoadingDialog.hide();

        if (tv_errorInfo != null) {
            ViewUtils.hideView(tv_errorInfo);
            tv_errorInfo.setText("");
        }

        if (getSwipeRefreshLayout() != null) {
            getSwipeRefreshLayout().setRefreshing(false);
        }
    }

    public String getCurrentError(VolleyError error) {
        String errorText = "";
        if (error instanceof TimeoutError) {
            errorText = "Connection time out!";
        } else if (error instanceof AuthFailureError) {
            errorText = "Authentication fail!";
        } else if (error instanceof ServerError) {
            errorText = "Some error in server , Please wait until we not solve problems!";
        } else if (error instanceof NetworkError) {
            errorText = "Network error, check your internet connection!";
        } else if (error instanceof ParseError) {
            errorText = "Some parsing error in application our team work on this error!";
        } else if (error instanceof NoConnectionError) {
            errorText = "Connection error , Check your internet connection!";
        }
        return errorText;
    }





    /*--------------------How to use---------------------*/
        /*Map<String, String> map = new HashMap<String, String>();
        new Network(mContext, "URL", map, new OnResponseListener() {
            @Override
            public void onResponse(String response) {
                // working with response
            }
        })
                .setTv_errorInfo(null, "text hint"*//*if want to show error in any text*//*)
                .setProgressBar(null*//* if any progress bar network performed *//*)
                .setSwipeRefreshLayout(null*//*if any SwipeRefreshLayout performed with network*//*)
                .getData(false*//*if want to show progress then true or false*//*);*/


}
