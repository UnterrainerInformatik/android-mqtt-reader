package info.unterrainer.mqttreader.model;

import java.util.function.Consumer;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.rxjava3.subjects.BehaviorSubject;

@Singleton
public class Store {

    public final BehaviorSubject<Model> subject;
    public final ModelMapper<Model> mapper;

    @Inject
    public Store() {
        subject = BehaviorSubject.createDefault(new Model());
        mapper = new ModelMapper<>(Model.class);
    }

    public void onNext(Consumer<Model> recipe) {
        Model model = mapper.clone(subject.getValue());
        recipe.accept(model);
        subject.onNext(model);
    }
}
