package ben.study.engine;

import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_VERSION;
import static org.lwjgl.opengl.GL13.GL_TEXTURE1;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {
    private int width, height;

    private long glfwWindow;

    private String title;

    private int frames;
    private long time;

    public Window(int w, int h, String t) {
        this.width = w;
        this.height = h;
        this.title = t;

        init();

        time = System.currentTimeMillis();
    }

    public void init() {
        if (!glfwInit()) {
            System.err.println("Could not initialize GLFW!");
            return;
        }

        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);

        // Create window
        glfwWindow = glfwCreateWindow(width, height, "Flappy", NULL, NULL);
        if (glfwWindow == NULL) {
            System.err.println("Could not create GLFW window!");
            return;
        }

        GLFWVidMode videomode = glfwGetVideoMode(glfwGetPrimaryMonitor());

        glfwSetWindowPos(
                glfwWindow,
                (videomode.width() - width) / 2,
                (videomode.height() - height) / 2
        );


        // Make the OpenGL context current
        glfwMakeContextCurrent(glfwWindow);
        // Enable v-sync
        glfwSwapInterval(1);

        glfwShowWindow(glfwWindow);

        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the GLCapabilities instance and makes the OpenGL
        // bindings available for use.
        GL.createCapabilities();

        glEnable(GL_DEPTH_TEST);
        glActiveTexture(GL_TEXTURE1);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        System.out.println("OpenGL: " + glGetString(GL_VERSION));
    }

    public void update(){
        glfwPollEvents();

        frames++;
        // how many frames per second
        if(System.currentTimeMillis() > time + 1000){
            glfwSetWindowTitle(glfwWindow, title + " | FPS: " + frames);
            time = System.currentTimeMillis();
            frames = 0;
        }
    }

    public void swapBuffers()
    {
        glfwSwapBuffers(glfwWindow);
    }

    public boolean shouldClose(){
        return glfwWindowShouldClose(glfwWindow);
    }

}
