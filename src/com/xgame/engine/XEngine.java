package com.xgame.engine;

import com.xgame.graphics.ScreenDisplay;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class XEngine extends Canvas implements Runnable {

    //Constants
    public static final int WIDTH =  300;
    public static final int HEIGHT = WIDTH / 16 * 9;
    public static final int SCALE = 3;
    public static final String GAME_NAME = "XHunter";

    private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);

    //Transform buffer image to pixels
    private int[] pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();

    //Triple buffering
    private static final int BUFFER_STRATEGY = 3;

    private Thread thread;
    private JFrame mainFrame;
    private boolean running;
    private ScreenDisplay screenDisplay;

    //Constructor
    public XEngine(){
        Dimension frameSize = new Dimension(WIDTH * SCALE, HEIGHT * SCALE);
        setPreferredSize(frameSize);
        this.mainFrame = new JFrame();
        this.screenDisplay = new ScreenDisplay(WIDTH, HEIGHT);
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

    public void render(){
        BufferStrategy bufferStrategy = this.getBufferStrategy();

        if(bufferStrategy == null){
            createBufferStrategy(BUFFER_STRATEGY);
            return;
        }
        screenDisplay.clearScreen();
        screenDisplay.render();

        for(int i = 0; i<pixels.length;i++){
            pixels[i]= screenDisplay.pixels[i];
        }

        Graphics graphics = bufferStrategy.getDrawGraphics();
        graphics.drawImage(image, 0, 0, getWidth(), getHeight(), null);
        graphics.dispose();
        bufferStrategy.show();
    }

    public void update(){

    }

    @Override public void run() {
        while(running){
            this.update();
            this.render();
        }
    }

    public static void main(String[] args){
        XEngine engine = new XEngine();
        //Graphics settings
        engine.mainFrame.setResizable(false);
        engine.mainFrame.setTitle(GAME_NAME);
        engine.mainFrame.add(engine);
        engine.mainFrame.pack();
        engine.mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        engine.mainFrame.setLocationRelativeTo(null);
        engine.mainFrame.setVisible(true);

        engine.startGame();
    }
}
