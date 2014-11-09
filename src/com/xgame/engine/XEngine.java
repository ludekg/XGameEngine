package com.xgame.engine;

public class XEngine implements  Runnable {

    //Constants
    public static final int WIDTH =  300;
    public static final int HEIGHT = WIDTH / 16 * 9;
    public static final int SCALE = 3;

    private Thread thread;
    private boolean running;

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

        }
    }
}
