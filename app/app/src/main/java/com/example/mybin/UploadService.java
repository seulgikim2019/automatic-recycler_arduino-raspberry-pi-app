package com.example.mybin;

import com.google.gson.JsonObject;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface UploadService {

    @Multipart
    @POST("/trash.php")
    Call<JsonObject> trashDb(
            @Part("trash") RequestBody description);


}
