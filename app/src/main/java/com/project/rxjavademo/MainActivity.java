package com.project.rxjavademo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    CompositeDisposable mCompositeDisposable;
    private Subscription mSubscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                btn();
//                mSubscription.request(1);
            }
        });
        Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull FlowableEmitter<Integer> emitter) throws Exception {
              /*  for (int i = 0;; i++) {
                    Log.d(TAG, "emit " + i);
                    emitter.onNext(i);
                }*/
                Log.d(TAG, "emit " + emitter.requested());
                emitter.onNext(1);
                Log.d(TAG, "emit " + emitter.requested());
                emitter.onNext(2);
                Log.d(TAG, "emit " + emitter.requested());
                emitter.onNext(3);
                Log.d(TAG, "emit " + emitter.requested());
            }
        }, BackpressureStrategy.ERROR)
             /*   .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())*/
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        Log.d(TAG, "onSubscribe");
//                        s.request(Long.MAX_VALUE); //注意这句代码 }
                        mSubscription = s;
//                        mSubscription.request(1);
                        mSubscription.request(10);
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.d(TAG, "onNext: " + integer);

                    }

                    @Override
                    public void onError(Throwable t) {
                        Log.w(TAG, "onError: ", t);
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete");
                    }
                });
       /* Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> e) throws Exception {
                for (int i = 0; ; i++) {
                    e.onNext(i);
                    Thread.sleep(2000);
                }

            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
//                        Thread.sleep(2000);
                        Log.d(TAG, "value:" + integer);
                    }
                });*/
        /*   .sample(2, TimeUnit.SECONDS)  //sample取样
         .filter(new Predicate<Integer>() {
                    @Override
                    public boolean test(@NonNull Integer integer) throws Exception {
                        return integer % 10 == 0;
                    }
                })*/
//        Log.d(TAG, "当前线程:" + Thread.currentThread().getName());
       /* Observable<Integer> observable1 = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> e) throws Exception {
                for (int i = 0; ; i++) {
                    e.onNext(i);
                }
            }
        })
                .subscribeOn(Schedulers.io());
        Observable<String> observable2 = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> e) throws Exception {

                e.onNext("a");

            }
        })
                .subscribeOn(Schedulers.io());
        Observable.zip(observable1, observable2, new BiFunction<Integer, String, String>() {
            @Override
            public String apply(@NonNull Integer integer, @NonNull String s) throws Exception {
                return integer + s;
            }
        })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Thread.sleep(2000);
                        Log.d(TAG, s);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.w(TAG, throwable);
                    }
                });*/
         /*       .concatMap(new Function<Integer, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(@NonNull Integer integer) throws Exception {
                ArrayList<String> strs = new ArrayList<>();
                for (int i = 0; i < 3; i++) {
                    strs.add("I am value"+integer);
                }
                return Observable.fromIterable(strs).delay(10, TimeUnit.MILLISECONDS);
            }
        })
                .flatMap(new Function<Integer, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(@NonNull Integer integer) throws Exception {
                ArrayList<String> strs = new ArrayList<>();
                for (int i = 0; i < 3; i++) {
                    strs.add("I am value"+integer);
                }
                return Observable.fromIterable(strs).delay(10, TimeUnit.MILLISECONDS);
            }
        })
                .subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.d(TAG, s);
            }
        });*/

    }

    public void btn() {
        RetrofitClient.create().create(Api.class)
                .getCityList()
                .subscribeOn(Schedulers.io())//在io线程请求网络
                .observeOn(AndroidSchedulers.mainThread())//回到主线程处理请求结果
                .subscribe(new Observer<CityBean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull CityBean cityBean) {
                        Log.d(TAG, cityBean.result.data.get(0).toString());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        mCompositeDisposable.clear();
    }
}
/*
//创建上游
        Observable.create(new ObservableOnSubscribe<Integer>() {
@Override
public void subscribe(@NonNull ObservableEmitter<Integer> e) throws Exception {
        Log.d(TAG, "onSubscribe1");
        e.onNext(1);
        Log.d(TAG, "onSubscribe2");
        e.onNext(2);
        Log.d(TAG, "onSubscribe3");
        e.onNext(3);
//                e.onComplete();
        Log.d(TAG, "onSubscribe4");
        e.onNext(4);
//                e.onError(new Throwable());

               *//* e.onComplete();
                e.onComplete();*//*
        }

        }).subscribe(new Observer<Integer>() {
        Disposable mDisposable;
@Override
public void onSubscribe(@NonNull Disposable d) {
        Log.d(TAG, "onSubscribe");
        mDisposable = d;
        }

@Override
public void onNext(@NonNull Integer integer) {
        Log.d(TAG, "onNext:" + integer);
        if(integer == 3){
        mDisposable.dispose();
        }

        }

@Override
public void onError(@NonNull Throwable e) {
        Log.d(TAG, "onError");
        }

@Override
public void onComplete() {
        Log.d(TAG, "onComplete");
        }
        });*/
