package com.example.mybin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.mybin.R.drawable.trashbin;

public class MainActivity extends AppCompatActivity {

    // 현재시간을 msec 으로 구한다.
    long now = System.currentTimeMillis();
    // 현재시간을 date 변수에 저장한다.
    Date date = new Date(now);
    // 시간을 나타냇 포맷을 정한다 ( yyyy/MM/dd 같은 형태로 변형 가능 )
    SimpleDateFormat sdfNow = new SimpleDateFormat("yyyy/MM/dd");
    // nowDate 변수에 값을 저장한다.
    String formatDate = sdfNow.format(date);

    TextView dateNow;
    TextView main_glass_percent;
    TextView main_can_percent;
    TextView main_plastic_percent;

    ImageView main_glass_img;
    ImageView main_can_img;
    ImageView main_plastic_img;
    ImageView main_reset;

    UploadService uploadService;
    RequestBody trash;
    Call<JsonObject> call;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dateNow = findViewById(R.id.date_now);
        dateNow.setText(formatDate);    // TextView 에 현재 시간 문자열 할당
        main_glass_percent=findViewById(R.id.main_glass_percent);
        main_can_percent=findViewById(R.id.main_can_percent);
        main_plastic_percent=findViewById(R.id.main_plastic_percent);

        main_can_img=findViewById(R.id.main_can_img);
        main_glass_img=findViewById(R.id.main_glass_img);
        main_plastic_img=findViewById(R.id.main_plastic_img);
        main_reset=findViewById(R.id.main_reset);

        //retrofit
        uploadService=MyRetrofit.getmRetrofit().create(UploadService.class);
        trash=RequestBody.create(MediaType.parse("multipart/form-data"),"trash");

    }



    @Override
    protected void onStart() {
        super.onStart();
        Log.i("main","start");

        //retrofit 요청
        call=uploadService.trashDb(trash);

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Log.i("db show","reponse okay");

                //값 가지고 와서 -> db percent 따라서 사진 변경.
                try {
                    JSONObject jsonObject=new JSONObject(new Gson().toJson(response.body()));

                 //   Log.i("jsonobject",jsonObject.getString("can_percent"));


                    int can_percent=Integer.parseInt(jsonObject.getString("can_percent"));
                    int gla_percent=Integer.parseInt(jsonObject.getString("gla_percent"));
                    int pla_percent=Integer.parseInt(jsonObject.getString("pla_percent"));

                    int can_img = trashbin;
                    String can_string ="거의없음";
                    switch (can_percent/10){
                        case 0: //0~9%
                        case 1:
                        case 2:
                            can_img= trashbin;
                            break;
                        case 3:
                        case 4:
                            can_string ="적음";
                            can_img= R.drawable.trashbin1;
                            break;
                        case 5:
                        case 6:
                            can_string ="적당";
                            can_img= R.drawable.trashbin2;
                            break;
                        case 7: //70~80%
                        case 8:
                            can_string ="많음";
                            can_img= R.drawable.trashbin3;
                            break;
                        case 9: //90~100%
                        case 10:
                            can_string ="가득";
                            can_img= R.drawable.trashbin4;
                            break;
                        default:
                            can_img= trashbin;
                            break;
                    }


                    main_can_img.setImageDrawable(getResources().getDrawable(can_img));
                    main_can_percent.setText(can_string);
                    String pla_string ="거의없음";
                    int pla_img = trashbin;
                    switch (pla_percent/10){
                        case 0: //0~9%
                        case 1:
                        case 2:
                            pla_img= trashbin;
                            break;
                        case 3:
                        case 4:
                            pla_string ="적음";
                            pla_img= R.drawable.trashbin1_pla;
                            break;
                        case 5:
                        case 6:
                            pla_string ="적당";
                            pla_img= R.drawable.trashbin2_pla;
                            break;
                        case 7: //70~80%
                        case 8:
                            pla_string ="많음";
                            pla_img= R.drawable.trashbin3_pla;
                            break;
                        case 9: //90~100%
                        case 10:
                            pla_string ="가득";
                            pla_img= R.drawable.trashbin4_pla;
                            break;
                        default:
                            pla_img= trashbin;
                            break;
                    }


                    main_plastic_img.setImageDrawable(getResources().getDrawable(pla_img));
                    main_plastic_percent.setText(pla_string);
                    String gla_string="거의없음";
                    int gla_img = trashbin;
                    switch (gla_percent/10){
                        case 0: //0~9%
                        case 1:
                        case 2:
                            gla_img= trashbin;
                            break;
                        case 3:
                        case 4:
                            gla_string="적음";
                            gla_img= R.drawable.trashbin1;
                            break;
                        case 5:
                        case 6:
                            gla_string="적당";
                            gla_img= R.drawable.trashbin2;
                            break;
                        case 7: //70~80%
                        case 8:
                            gla_string="많음";
                            gla_img= R.drawable.trashbin3;
                            break;
                        case 9: //90~100%
                        case 10:
                            gla_string="가득";
                            gla_img= R.drawable.trashbin4;
                            break;
                        default:
                            gla_img= trashbin;
                            break;
                    }


                    main_glass_img.setImageDrawable(getResources().getDrawable(gla_img));
                    main_glass_percent.setText(gla_string);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.i("db show","error");
            }
        });





    }


    @Override
    protected void onResume() {
        super.onResume();

        //reset버튼을 눌렀을 때 -> 다시 재 통신 요청.

        main_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("click","reset click");
                //retrofit 요청
                call=uploadService.trashDb(trash);

                call.enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        Log.i("db show","reponse okay");

                        //값 가지고 와서 -> db percent 따라서 사진 변경.
                        try {
                            JSONObject jsonObject=new JSONObject(new Gson().toJson(response.body()));

                            //   Log.i("jsonobject",jsonObject.getString("can_percent"));

                            int can_percent=Integer.parseInt(jsonObject.getString("can_percent"));
                            int gla_percent=Integer.parseInt(jsonObject.getString("gla_percent"));
                            int pla_percent=Integer.parseInt(jsonObject.getString("pla_percent"));

                            int can_img = trashbin;
                            String can_string ="거의없음";
                            switch (can_percent/10){
                                case 0: //0~9%
                                case 1:
                                case 2:
                                    can_img= trashbin;
                                    break;
                                case 3:
                                case 4:
                                    can_string ="적음";
                                    can_img= R.drawable.trashbin1;
                                    break;
                                case 5:
                                case 6:
                                    can_string ="적당";
                                    can_img= R.drawable.trashbin2;
                                    break;
                                case 7: //70~80%
                                case 8:
                                    can_string ="많음";
                                    can_img= R.drawable.trashbin3;
                                    break;
                                case 9: //90~100%
                                case 10:
                                    can_string ="가득";
                                    can_img= R.drawable.trashbin4;
                                    break;
                                default:
                                    can_img= trashbin;
                                    break;
                            }


                            main_can_img.setImageDrawable(getResources().getDrawable(can_img));
                            main_can_percent.setText(can_string);
                            String pla_string ="거의없음";
                            int pla_img = trashbin;
                            switch (pla_percent/10){
                                case 0: //0~9%
                                case 1:
                                case 2:
                                    pla_img= trashbin;
                                    break;
                                case 3:
                                case 4:
                                    pla_string ="적음";
                                    pla_img= R.drawable.trashbin1_pla;
                                    break;
                                case 5:
                                case 6:
                                    pla_string ="적당";
                                    pla_img= R.drawable.trashbin2_pla;
                                    break;
                                case 7: //70~80%
                                case 8:
                                    pla_string ="많음";
                                    pla_img= R.drawable.trashbin3_pla;
                                    break;
                                case 9: //90~100%
                                case 10:
                                    pla_string ="가득";
                                    pla_img= R.drawable.trashbin4_pla;
                                    break;
                                default:
                                    pla_img= trashbin;
                                    break;
                            }


                            main_plastic_img.setImageDrawable(getResources().getDrawable(pla_img));
                            main_plastic_percent.setText(pla_string);
                            String gla_string="거의없음";
                            int gla_img = trashbin;
                            switch (gla_percent/10){
                                case 0: //0~9%
                                case 1:
                                case 2:
                                    gla_img= trashbin;
                                    break;
                                case 3:
                                case 4:
                                    gla_string="적음";
                                    gla_img= R.drawable.trashbin1;
                                    break;
                                case 5:
                                case 6:
                                    gla_string="적당";
                                    gla_img= R.drawable.trashbin2;
                                    break;
                                case 7: //70~80%
                                case 8:
                                    gla_string="많음";
                                    gla_img= R.drawable.trashbin3;
                                    break;
                                case 9: //90~100%
                                case 10:
                                    gla_string="가득";
                                    gla_img= R.drawable.trashbin4;
                                    break;
                                default:
                                    gla_img= trashbin;
                                    break;
                            }


                            main_glass_img.setImageDrawable(getResources().getDrawable(gla_img));
                            main_glass_percent.setText(gla_string);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        Log.i("db show","error");
                    }
                });
            }
        });


    }
}
