package com.example.mvvmpattern.repository;


import android.util.Log;

import com.example.mvvmpattern.response_model.ArticleResponse;
import com.example.mvvmpattern.service.ApiRequest;
import com.example.mvvmpattern.service.RetrofitRequest;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ArticleRepository {
    private static final String TAG = ArticleRepository.class.getSimpleName();
    private ApiRequest apiRequest;

    public ArticleRepository() {
        apiRequest = RetrofitRequest.getRetrofitInstance().create(ApiRequest.class);
    }

    public LiveData<ArticleResponse> getMovieArticles(String query, String key) {
        final MutableLiveData<ArticleResponse> data = new MutableLiveData<>();
        apiRequest.getMovieArticles(query, key)
                .enqueue(new Callback<ArticleResponse>() {


                    @Override
                    public void onResponse(Call<ArticleResponse> call, Response<ArticleResponse> response) {
                        Log.d(TAG, "onResponse response:: " + response);



                        if (response.body() != null) {
                            data.setValue(response.body());

                            Log.d(TAG, "articles total result:: " + response.body().getTotalResults());
                            Log.d(TAG, "articles size:: " + response.body().getArticles().size());
                            Log.d(TAG, "articles title pos 0:: " + response.body().getArticles().get(0).getTitle());
                        }
                    }

                    @Override
                    public void onFailure(Call<ArticleResponse> call, Throwable t) {
                        data.setValue(null);
                    }
                });
        return data;
    }
}
