package com.example.marikiti.testing;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface UploadReceiptService {

    @Multipart
    @POST("upload")
    Call<List<ServerResponse>> uploadReceipt(
            @Header("Cookie") String sessionIdAndRz,
            @Part MultipartBody.Part file,
            @Part("items") RequestBody items,
            @Part("isAny") RequestBody isAny
    );
}
