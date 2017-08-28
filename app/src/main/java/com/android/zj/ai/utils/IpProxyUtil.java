package com.android.zj.ai.utils;

import android.util.Log;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;

/**
 *
 * Created by zijian.cheng on 2017/8/28.
 */

public class IpProxyUtil {

    public static void getIpProxy() {
        getInt(0).subscribe(getSubscriber());
    }

    public static Observable<Integer> getInt(int i) {
        return Observable.just(i).concatMap(new Function<Integer, ObservableSource<? extends Integer>>() {
            @Override
            public ObservableSource<? extends Integer> apply(@NonNull Integer integer) throws Exception {
                if (integer == 10) {  //判断条件
                    return Observable.just(10); //发射数据
                } else {
                    return Observable
                            .just(integer)
                            .concatWith(getInt(integer + 1));
                    //连接发射
                }
            }
        });
    }

    private static Observer<Object> getSubscriber() {
        return new Observer<Object>() {

            @Override
            public void onSubscribe(Disposable d) {
                Log.d("IpProxyUtil", "Disposable ");
            }

            @Override
            public void onNext(Object o) {
                Log.d("IpProxyUtil", "onNext : " + o);
            }

            @Override
            public void onError(Throwable e) {
                Log.d("IpProxyUtil", "onError : " + e.toString());
            }

            @Override
            public void onComplete() {
                Log.d("IpProxyUtil", "onCompleted");
            }
        };
    }

}
