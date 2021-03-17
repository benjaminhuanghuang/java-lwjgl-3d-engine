package ben.study;

import ben.study.engine.Input;
import ben.study.engine.Window;
import ben.study.engine.maths.Vector3f;
import ben.study.engine.maths.Vector2f;
import ben.study.engine.object.Camera;
import ben.study.engine.object.GameObject;
import ben.study.graphics.*;
import org.lwjgl.glfw.GLFW;

public class Game {
    private static Window win;
    private static Input input;
    public static Renderer renderer;
    public static Shader shader;

    public static Mesh mesh;
    public static GameObject obj;
    public static Camera camera = new Camera(new Vector3f(0, 0, 1), new Vector3f(0, 0, 0));


    public static void init() {
        input = new Input();
        win = new Window(1280, 760, "Game");
        win.setBackgroundColor(2.0f, 2.0f, 3.0f);
        win.setInput(input);
//        shader = new Shader("shaders/texture.vert", "shaders/texture.frag");
//        shader = new Shader("shaders/uniform.vert", "shaders/uniform.frag");
        shader = new Shader("shaders/obj.vert", "shaders/obj.frag");
        shader.create();
        renderer = new Renderer(win, shader);

        mesh = Modules.Cube;

        obj = new GameObject(new Vector3f(0, 0, 0), new Vector3f(0, 0, 0), new Vector3f(1, 1, 1), mesh);

        mesh.create();
    }

    public static void main(String[] args) {
        init();
        while (!win.shouldClose() && !input.isKeyDown(GLFW.GLFW_KEY_ESCAPE)) {
            win.update();
            //obj.update();
            camera.update();

            renderer.renderMesh(obj, camera);

            win.swapBuffers();


            if (input.isKeyDown(GLFW.GLFW_KEY_ESCAPE)) {
                win.close();
            }
            if (input.isKeyDown(GLFW.GLFW_KEY_0)) {
                win.setFullscreen(!win.isFullscreen());
            }
            if (input.isButtonDown(GLFW.GLFW_MOUSE_BUTTON_LEFT)) {
                System.out.println("X: " + input.getMouseX() + ", Y:" + input.getMouseY());
            }
        }

        win.destroy();
        mesh.destroy();
        shader.destroy();
    }

}