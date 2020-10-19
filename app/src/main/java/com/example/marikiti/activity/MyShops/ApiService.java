package com.example.marikiti.activity.MyShops;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import static com.example.marikiti.utilities.APP_URL.HOSTNAME;

public interface ApiService {
    String BASE_URL = HOSTNAME;

    @Multipart
    @POST("upload_multiple_file.php")
    Call<ResponseBody> uploadMultiple(
            @Part("description") RequestBody description,
            @Part("size") RequestBody size,
            @Part List<MultipartBody.Part> files);


    @Multipart
    @POST("upload_multiple_file.php")
    Call<ResponseBody> Upload(@Part MultipartBody.Part files);
}
