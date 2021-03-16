package ben.study;

import ben.study.engine.Window;

public class Game {
    private static Window win;


    public static void init(){
        win = new Window(1280, 760, "Game");
    }


    public static void main(String[] args) {
        init();
        while(!win.shouldClose()){
            win.update();


            win.swapBuffers();
        }
    }

}