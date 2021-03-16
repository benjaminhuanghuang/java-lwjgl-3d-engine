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
        win.setBackgroundColor(1.0f, 2.0f, 3.0f);
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

            if(input.isButtonDown(GLFW.GLFW_MOUSE_BUTTON_LEFT)){
                System.out.println("X: " + input.getMouseX()+ ", Y:" + input.getMouseY() );
            }
        }

        win.destroy();
    }

}