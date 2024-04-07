package info.unterrainer.mqttreader;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.ComponentActivity;

import javax.inject.Inject;

import at.ac.htl.myfirstapp.ui.layout.MainView;
import dagger.hilt.android.AndroidEntryPoint;
import info.unterrainer.mqttreader.business.Logger;
import info.unterrainer.mqttreader.model.Store;
import io.reactivex.rxjava3.disposables.Disposable;

@AndroidEntryPoint
public class MainActivity extends ComponentActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Inject
    MainView mainView;

    @Inject
    Store store;

    @Inject
    Logger logger;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        logger.subscribe();
        Log.i(TAG, "onCreate");
        mainView.compose(this);

        store.onNext(consumerModel -> consumerModel.greeting = "mit consumer");
        Disposable disposable = store.subject
                .map(model -> model.greeting)
                .map(greeting -> greeting.toUpperCase())
                .distinctUntilChanged()
                .subscribe(greeting -> Log.i(TAG, greeting));

        store.onNext(model -> {
            model.firstName = "John";
            model.lastName = "Doe";
        } );
    }
}
