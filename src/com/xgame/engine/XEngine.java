package com.xgame.engine;

import javax.swing.*;
import java.awt.*;

public class XEngine extends Canvas implements Runnable {

    //Constants
    public static final int WIDTH =  300;
    public static final int HEIGHT = WIDTH / 16 * 9;
    public static final int SCALE = 3;

    private Thread thread;
    private JFrame mainFrame;
    private boolean running;

    //Constructor
    public XEngine(){
        Dimension frameSize = new Dimension(WIDTH * SCALE, HEIGHT * SCALE);
        setPreferredSize(frameSize);
        this.mainFrame = new JFrame();
    }

    /**
     * Starts the game thread.
     */
    public synchronized void startGame(){
        this.running = true;
        this.thread = new Thread(this, "Game thread");
        this.thread.start();
    }

    /**
     * Stops the game thread.
     */
    public synchronized void stopGame(){
        this.running = false;
        try {
            this.thread.join();
        }catch(InterruptedException e){
            e.printStackTrace();
        }
    }

    @Override public void run() {
        while(running){
            System.out.println("Game is running..");
        }
    }

    public static void main(String[] args){
        XEngine engine = new XEngine();
        //Graphics settings
        engine.mainFrame.setResizable(false);
        engine.mainFrame.setTitle("XGame");
        engine.mainFrame.add(engine);
        engine.mainFrame.pack();
        engine.mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        engine.mainFrame.setLocationRelativeTo(null);
        engine.mainFrame.setVisible(true);

        engine.startGame();
    }
}
