package com.xgame.graphics;

public class ScreenDisplay {

    private int width;
    private int height;

    public int[] pixels;

    public ScreenDisplay(int width, int height){
        this.width = width;
        this.height = height;
        this.pixels = new int[width * height];
    }

    /**
     *
     */
    public void render(){
        for(int y = 0; y < height; y++){
            for(int x = 0; x < width; x++){
                pixels[x+y*width] = 0xff00ff;
            }
        }
    }
}
