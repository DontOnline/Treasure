package com.example.administrator.treasure.net;

import com.example.administrator.treasure.user.User;
import com.example.administrator.treasure.user.UserResult;

import java.io.File;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by gqq on 2017/3/30.
 */
// 对应服务器的接口
public interface TreasureApi {
    // 构建请求
    @POST("http://www.baidu.com")
    @Headers({"context-length:1024"})
    Call<ResponseBody> getData();


    // 登录的请求
    @POST("/Handler/UserHandler.ashx?action=login")
    Call<UserResult> login(@Body User user);

    /**
     * 注解：
     * 1. 请求的方式：@GET、@POST、@PUT等
     * 2. 请求的url的处理：
     *      1. 可替换块：{id}替换的内容，@Path注解来替换。
     *      2. 查询参数：@Query("查询的参数的key")
     *                  参数比较多：@QueryMap
     * 3. 请求头信息： 添加： @Headers({}) 请求方法上面
     *                修改： @Header("x-type")String header 参数动态修改的
     * 4. 请求体：@Body String json;
     *           1. 表单：@FormUrlEncoded
     *                   @Field("username") String username
     *                   @FieldMap Map<String,String> map
     *           2. 多部分：@Multipart
     *                   @Part("name")String name
     *                   @PartMap Map<String,String> map
     */

    @POST("group/{id}/users?sort=desc")
    @Headers({"x-type:123","x-length:1024"})
    Call<ResponseBody> groupList(@Path("id") int groupId,
                                 @Query("sort") String sort,
                                 @QueryMap Map<String,String> map,
                                 @Header("x-type")String header,
                                 @Body String json);

    @POST("xxxx")
    @FormUrlEncoded
    Call<ResponseBody> getFormUrl(@Field("username") String username,
                                  @Field("password")String password,
                                  @FieldMap Map<String,String> map);

    @POST("yyyy")
    @Multipart
    Call<ResponseBody> getMultUrl(@Part("photo")File file,
                                  @Part("name")String name,
                                  @PartMap Map<String,String> map);

}

