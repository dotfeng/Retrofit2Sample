package com.example.feng.retrofit2sample;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.ResponseBody;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Converter;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class MainActivity extends AppCompatActivity {
    public static final String API_URL = "https://api.github.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn_button1 = (Button) findViewById(R.id.btn_button1);
        final TextView tv_text = (TextView) findViewById(R.id.tv_text);
        final ProgressDialog dialog = new ProgressDialog(this);

        btn_button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
                OkHttpClient client = new OkHttpClient();
                client.interceptors().add(new LoggingInterceptor());
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(API_URL)
                        .client(client)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                // Create an instance of our GitHub API interface.
                GitHub github = retrofit.create(GitHub.class);

                // Create a call instance for looking up Retrofit contributors.
                Call<List<Contributor>> call = github.contributors("square", "retrofit");

                call.enqueue(new Callback<List<Contributor>>() {

                    @Override
                    public void onResponse(Response<List<Contributor>> response, Retrofit arg1) {
                        dialog.dismiss();
                        if(response.isSuccess()) {
                            List<Contributor> contributors = response.body();
                            StringBuilder sb = new StringBuilder();
                            for (Contributor contributor : contributors) {
                               sb.append(contributor.login + " (" + contributor.contributions + ")");
                            }
                            tv_text.setText(sb.toString());
                        }else {
                            Converter<ResponseBody, Error> errorConverter =
                                    arg1.responseConverter(Error.class, new Annotation[0]);
                            // Convert the error body into our Error type.
                            Error error;
                            try {
                                error = errorConverter.convert(response.errorBody());
                                tv_text.setText("ERROR: " + error.message);
                            } catch (IOException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Throwable arg0) {
                        dialog.dismiss();
                        System.out.println("---------");
                        arg0.printStackTrace();
                    }
                });
            }
        });
    }

    public int add(int i, int j) {
        return (i + j);
    }
}
