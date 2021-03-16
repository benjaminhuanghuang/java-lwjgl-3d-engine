package ben.study;

import ben.study.engine.Input;
import ben.study.engine.Window;
import org.lwjgl.glfw.GLFW;

public class Game {
    private static Window win;
    private static Input input;

    public static void init(){
        input = new Input();
        win = new Window(1280, 760, "Game");
        win.setInput(input);
    }


    public static void main(String[] args) {
        init();
        while(!win.shouldClose()){
            win.update();


            win.swapBuffers();


            if(input.isKeyDown(GLFW.GLFW_KEY_ESCAPE)){
                win.close();
            }
        }

        win.destroy();
    }

}