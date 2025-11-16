package it.unibo.mvc.view;

import it.unibo.mvc.api.DrawNumberController;
import it.unibo.mvc.api.DrawNumberView;
import it.unibo.mvc.api.DrawResult;

public class DrawNumberStandardOutputView implements DrawNumberView {

    private DrawNumberController controller;

    public DrawNumberStandardOutputView() {

    }

    @Override
    public void setController(final DrawNumberController observer) {
        this.controller = observer;
    }

    @Override
    public void start() {
        System.out.println("StandardOutput view started. Ready to display results");
    }

    @Override
    public void result(DrawResult res) {
        System.out.println(res.getDescription());
    }

}