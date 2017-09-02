package com.king.practices.app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.net.ParseException;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonParseException;
import com.jess.arms.base.App;
import com.jess.arms.base.delegate.AppLifecycles;
import com.jess.arms.di.module.AppModule;
import com.jess.arms.di.module.ClientModule;
import com.jess.arms.di.module.GlobalConfigModule;
import com.jess.arms.http.GlobalHttpHandler;
import com.jess.arms.http.RequestInterceptor;
import com.jess.arms.integration.ConfigModule;
import com.jess.arms.utils.ArmsUtils;
import com.king.practices.BuildConfig;
import com.king.practices.R;
import com.king.practices.mvp.model.api.Api;
import com.king.practices.utils.UiUtils;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.ButterKnife;
import io.rx_cache2.internal.RxCache;
import me.jessyan.progressmanager.ProgressManager;
import me.jessyan.rxerrorhandler.handler.listener.ResponseErrorListener;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.HttpException;
import retrofit2.Retrofit;
import timber.log.Timber;

/**
 * Created by admin on 2017/9/2.
 */

public class GlobalConfiguration implements ConfigModule {
    @Override
    public void applyOptions(Context context, GlobalConfigModule.Builder builder) {
        builder.baseurl(Api.APP_DOMAIN)
                .globalHttpHandler(new GlobalHttpHandler() {
                    //用来处理http响应结果
                    //这里可以提供一个全局处理Http请求和响应结果的处理类
                    //比客户端提前一步拿到服务器返回的结果,可以做一些操作,比如token超时,重新获取
                    @Override
                    public Response onHttpResultResponse(String httpResult, Interceptor.Chain chain, Response response) {
                        /*
                           这里可以先客户端一步拿到每一次http请求的结果,可以解析成json,做一些操作,如检测到token过期后
                           重新请求token,并重新执行请求
                           */
                        try {
                            if (!TextUtils.isEmpty(httpResult) && RequestInterceptor.isJson(response.body().contentType())) {
                                JSONArray array = new JSONArray(httpResult);
                                JSONObject object = (JSONObject) array.get(0);
                                String login = object.getString("login");
                                String avatar_url = object.getString("avatar_url");
                                Timber.w("Result ------> " + login + "    ||   Avatar_url------> " + avatar_url);
                            }

                        } catch (JSONException e) {
                            //                            e.printStackTrace();
                            Timber.w("onHttpResultResponse: Value cannot be converted to JSONArray");
                            return response;
                        }
                        return null;
                    }

                    // 这里可以在请求服务器之前可以拿到request,做一些操作
                    // 比如给request统一添加token或者header以及参数加密等操作
                    @Override
                    public Request onHttpRequestBefore(Interceptor.Chain chain, Request request) {
                         /*
                         如果需要再请求服务器之前做一些操作,则重新返回一个做过操作的的requeat如增加header,
                         不做操作则直接返回request参数
                           return chain.request().newBuilder().header("token", tokenId)
                                  .build();
                        */
                        return request;
                    }
                })
                .responseErrorListener(new ResponseErrorListener() {////处理所有Rxjava的onError逻辑
                    @Override
                    public void handleResponseError(Context context, Throwable t) {
                        /* 用来提供处理所有错误的监听
                       rxjava必要要使用ErrorHandleSubscriber(默认实现Subscriber的onError方法),此监听才生效 */
                        Timber.tag("Catch-Error").w(t.getMessage());
                        //这里不光是只能打印错误,还可以根据不同的错误作出不同的逻辑处理
                        String msg = "未知错误";
                        if (t instanceof UnknownHostException) {
                            msg = "网络不可用";
                        } else if (t instanceof SocketTimeoutException) {
                            msg = "请求网络超时";
                        } else if (t instanceof HttpException) {
                            HttpException httpException = (HttpException) t;
                            msg = convertStatusCode(httpException);
                        } else if (t instanceof JsonParseException || t instanceof ParseException || t instanceof JSONException || t instanceof JsonIOException) {
                            msg = "数据解析错误";
                        }
                        UiUtils.snackbarText(msg);
                    }
                })
                .gsonConfiguration(new AppModule.GsonConfiguration() {//自定义配置Gson的参数
                    @Override
                    public void configGson(Context context, GsonBuilder builder) {
                        builder.serializeNulls()////支持序列化null的参数
                                //支持将序列化key为object的map,默认只能序列化key为string的map
                                .enableComplexMapKeySerialization();
                    }
                })
                .retrofitConfiguration(new ClientModule.RetrofitConfiguration() {
                    @Override
                    public void configRetrofit(Context context, Retrofit.Builder builder) {
                        //这里可以自己自定义配置Retrofit的参数,甚至你可以替换系统配置好的okhttp对象
                        //retrofitBuilder.addConverterFactory(FastJsonConverterFactory.create());
                        //比如使用fastjson替代gson
                    }
                })
                .okhttpConfiguration(new ClientModule.OkhttpConfiguration() {
                    @Override
                    public void configOkhttp(Context context, OkHttpClient.Builder builder) {
                        //自定义配置Okhttp的参数
                        builder.writeTimeout(10, TimeUnit.SECONDS);
                        //开启使用一行代码监听 Retrofit／Okhttp 上传下载进度监听,以及 Glide 加载进度监听
                        //详细使用方法查看 https://github.com/JessYanCoding/ProgressManager
                        ProgressManager.getInstance().with(builder);
                    }
                })
                .rxCacheConfiguration(new ClientModule.RxCacheConfiguration() {
                    @Override
                    public void configRxCache(Context context, RxCache.Builder builder) {
                        //自定义配置RxCache的参数
                        builder.useExpiredDataIfLoaderNotAvailable(true);
                    }
                });
    }

    @Override
    public void injectAppLifecycle(Context context, List<AppLifecycles> lifecycles) {
        lifecycles.add(new AppLifecycles() {
            @Override
            public void attachBaseContext(Context base) {

            }

            @Override
            public void onCreate(Application application) {
                if (BuildConfig.LOG_DEBUG) {
                    Timber.plant(new Timber.DebugTree());
                    ButterKnife.setDebug(true);
                }
                // AppDelegate.Lifecycle 的所有方法都会在基类Application对应的生命周期中被调用,所以在对应的方法中可以扩展一些自己需要的逻辑
                //leakCanary内存泄露检查
                ArmsUtils.obtainAppComponentFromContext(application).extras().put(RefWatcher.class.getName(), BuildConfig.USE_CANARY ? LeakCanary.install(application) : RefWatcher.DISABLED);
                //扩展 AppManager 的远程遥控功能

//                ArmsUtils.obtainAppComponentFromContext(application).appManager().setCurrentActivity();
            }

            @Override
            public void onTerminate(Application application) {

            }
        });
    }

    @Override
    public void injectActivityLifecycle(Context context, List<Application.ActivityLifecycleCallbacks> lifecycles) {
        lifecycles.add(new Application.ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(final Activity activity, Bundle bundle) {
                Timber.w(activity + "-onActivityCreate");
                //支持toolbar
                if (activity.findViewById(R.id.toolbar) != null) {
                    if (activity instanceof AppCompatActivity) {
                        ((AppCompatActivity) activity).setSupportActionBar((Toolbar) activity.findViewById(R.id.toolbar));
                        ((AppCompatActivity) activity).getSupportActionBar().setDisplayShowTitleEnabled(false);
                    } else {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            activity.setActionBar((android.widget.Toolbar) activity.findViewById(R.id.toolbar));
                            activity.getActionBar().setDisplayShowTitleEnabled(false);
                        }
                    }
                }
                //设置toolbar的title 直接在在 AndroidManifest.xml 中设置 Label即可
                if (activity.findViewById(R.id.toolbar_title) != null) {
                    ((TextView) activity.findViewById(R.id.toolbar_title)).setText(activity.getTitle());
                }
                //设置toolbar的返回关闭
                if (activity.findViewById(R.id.toolbar_back) != null) {
                    activity.findViewById(R.id.toolbar_back).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            activity.onBackPressed();
                        }
                    });
                }

            }

            @Override
            public void onActivityStarted(Activity activity) {
                Timber.w(activity + " - onActivityStart ");
            }

            @Override
            public void onActivityResumed(Activity activity) {
                Timber.w(activity + " - onActivityResumed");

            }

            @Override
            public void onActivityPaused(Activity activity) {
                Timber.w(activity + " - onActivityPaused");
            }

            @Override
            public void onActivityStopped(Activity activity) {
                Timber.w(activity + " - onActivityStopped");
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
                Timber.w(activity + " - onActivitySaveInstanceState");
            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                Timber.w(activity + " - onActivityDestroyed");
            }
        });
    }

    @Override
    public void injectFragmentLifecycle(Context context, List<FragmentManager.FragmentLifecycleCallbacks> lifecycles) {
        lifecycles.add(new FragmentManager.FragmentLifecycleCallbacks() {

            @Override
            public void onFragmentCreated(FragmentManager fm, Fragment f, Bundle savedInstanceState) {
                f.setRetainInstance(true);
            }

            @Override
            public void onFragmentDestroyed(FragmentManager fm, Fragment f) {
                ((RefWatcher) ((App) f.getActivity().getApplication())
                        .getAppComponent()
                        .extras()
                        .get(RefWatcher.class.getName())
                )
                        .watch(f);
            }
        });
    }

    private String convertStatusCode(HttpException httpException) {
        String msg;
        if (httpException.code() == 500) {
            msg = "服务器发生错误";
        } else if (httpException.code() == 404) {
            msg = "请求地址不存在";
        } else if (httpException.code() == 403) {
            msg = "请求被服务器拒绝";
        } else if (httpException.code() == 307) {
            msg = "请求被重定向到其他页面";
        } else {
            msg = httpException.message();
        }
        return msg;
    }
}
