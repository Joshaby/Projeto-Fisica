package br.edu.ifpb;

import javax.swing.*;

public class StopWatch extends Thread {
    private Integer seconds;
    private JLabel label;

    public StopWatch(JLabel label, Integer seconds) {
        this.label = label;
        this.seconds = seconds;
    }

    public void setSeconds(Integer seconds) { this.seconds = seconds; }
    public Integer getSeconds() { return seconds; }

    @Override
    public void run() {
        while (true) {
            int seconds = this.seconds % 60;
            int hour = this.seconds / 60;
            int minutes = hour % 60;
            hour = hour / 60;
            label.setText("Tempo restante: " + minutes + ":" + (seconds < 10 ? "0" + seconds : seconds));
            try {
                Thread.currentThread().sleep(1000);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.seconds --;
        }
    }
}
