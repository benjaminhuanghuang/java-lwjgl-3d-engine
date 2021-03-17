package ben.study;

import ben.study.engine.Input;
import ben.study.engine.Window;
import ben.study.engine.maths.Vector3f;
import ben.study.graphics.Mesh;
import ben.study.graphics.Renderer;
import ben.study.graphics.Shader;
import ben.study.graphics.Vertex;
import org.lwjgl.glfw.GLFW;

public class Game {
    private static Window win;
    private static Input input;
    public static Renderer renderer;
    public static Shader shader;

    public static Mesh mesh = new Mesh(new Vertex[]{
            new Vertex(new Vector3f(-0.5f,  0.5f, 0.0f), new Vector3f(1.0f, 0.0f, 0.0f)),
            new Vertex(new Vector3f(-0.5f, -0.5f, 0.0f), new Vector3f(0.0f, 1.0f, 0.0f)),
            new Vertex(new Vector3f( 0.5f, -0.5f, 0.0f), new Vector3f(0.0f, 0.0f, 1.0f)),
            new Vertex(new Vector3f( 0.5f,  0.5f, 0.0f), new Vector3f(1.0f, 1.0f, 0.0f))
    }, new int[]{
            0, 1, 2,
            0, 3, 2
    });

    public static void init() {
        input = new Input();
        win = new Window(1280, 760, "Game");
        win.setBackgroundColor(2.0f, 2.0f, 3.0f);
        win.setInput(input);
        mesh.create();
        shader = new Shader("shaders/main.vert", "shaders/main.frag");
        shader.create();
        renderer = new Renderer(shader);
    }

    public static void main(String[] args) {
        init();
        while (!win.shouldClose() && !input.isKeyDown(GLFW.GLFW_KEY_ESCAPE)) {
            win.update();

            renderer.renderMesh(mesh);

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