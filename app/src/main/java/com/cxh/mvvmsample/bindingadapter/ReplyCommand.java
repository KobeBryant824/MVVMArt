package com.cxh.mvvmsample.bindingadapter;

import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/**
 * Created by kelin on 15-8-4.
 */
public class ReplyCommand<T> {

    private Action execute0;
    private Consumer<T> execute1;

    public ReplyCommand(Action execute) {
        this.execute0 = execute;
    }

    public ReplyCommand(Consumer<T> execute) {
        this.execute1 = execute;
    }

    public void execute() {
        try {
            if (execute0 != null) {
                execute0.run();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void execute(T parameter) {
        try {
            if (execute1 != null) {
                execute1.accept(parameter);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
