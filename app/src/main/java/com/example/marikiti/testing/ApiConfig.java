package com.example.marikiti.testing;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

import static com.example.marikiti.utilities.APP_URL.HOSTNAME;

public interface ApiConfig {





    @POST("modal.php")
    Call<ServerResponse> upload(
            @Header("Authorization") String authorization,
            @PartMap Map<String, RequestBody> map

    );


    @Multipart
    @POST("upload_multiple_file.php")
    Call<ResponseBody> uploadMultiple(
            @Part("description") RequestBody description,
            @Part("size") RequestBody size,
            @Part List<MultipartBody.Part> files
   );


    Call<ResponseBody> Upload(@Part MultipartBody.Part files,@Part("file") RequestBody name);


    @Multipart
    @POST("upload_multiple_file.php")
    Call uploadFile(@Part MultipartBody.Part files);

    ApiConfig getSimple(@Part MultipartBody.Part files);

    @Multipart
    @POST("upload_multiple_file.php")
    Call<ServerResponse> inside_image_upload(

            @PartMap Map<String, RequestBody> map
    );

    @Multipart
    @POST("add_id.php")
    Call<ServerResponse> nationalid_upload(
            @PartMap Map<String, RequestBody> map

    );

    @Multipart
    @POST("stories_upload_videos.php")
    Call<ServerResponse>status_videoUpload(@Header("Authorization") String authorization, @PartMap Map<String, RequestBody> map);


}
