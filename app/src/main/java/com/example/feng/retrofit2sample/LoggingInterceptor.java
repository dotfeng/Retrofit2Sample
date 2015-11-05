package com.example.feng.retrofit2sample;

import java.io.IOException;

import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;


class LoggingInterceptor implements Interceptor {
  @Override public Response intercept(Chain chain) throws IOException {
    Request request = chain.request();
    Response response = chain.proceed(request);
    switch (response.code()) {
      case 200:
      case 404:
      case 500:
      default:
        System.out.println(response.code());
    }

    return response;
  }
}