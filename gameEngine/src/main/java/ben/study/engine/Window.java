package ben.study.engine;

import ben.study.engine.maths.Vector3f;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.glfw.GLFWWindowSizeCallback;
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

    public Input input;
    private int frames;
    private long time;

    private Vector3f background = new Vector3f(0,0,0);
    private boolean isResized;
    private boolean isFullscreen;
    private int[] windowPosX = new int[1], windowPosY = new int[1];


    public Window(int w, int h, String t) {
        this.width = w;
        this.height = h;
        this.title = t;
        input = new Input();
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
        glfwWindow = glfwCreateWindow(width, height, title, isFullscreen ? glfwGetPrimaryMonitor() : 0, NULL);
        if (glfwWindow == NULL) {
            System.err.println("Could not create GLFW window!");
            return;
        }

        GLFWVidMode videoMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        windowPosX[0] = (videoMode.width() - width) / 2;
        windowPosY[0] = (videoMode.height() - height) / 2;
        glfwSetWindowPos(glfwWindow, windowPosX[0], windowPosY[0]);


        // Make the OpenGL context current
        glfwMakeContextCurrent(glfwWindow);
        // Enable v-sync
        glfwSwapInterval(1);

        glfwSetWindowSizeCallback(glfwWindow, new GLFWWindowSizeCallback(){
            @Override
            public void invoke(long window, int w, int h) {
                width = w;
                height = h;
                isResized = true;
            }
        });

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

    public void setInput(Input input) {
        // Set input callbacks
        glfwSetKeyCallback(glfwWindow, input.getKeyboardCallback());
        glfwSetCursorPosCallback(glfwWindow, input.getMouseMoveCallback());
        glfwSetMouseButtonCallback(glfwWindow, input.getMouseClickCallback());
        glfwSetScrollCallback(glfwWindow, input.getMouseScrollCallback());
    }

    public void close() {
        glfwSetWindowShouldClose(glfwWindow, true);
    }

    public void update() {
        if (isResized) {
            glViewport(0, 0, width, height);
            isResized = false;
        }

        // clear window
        glClearColor(background.getX(), background.getY(), background.getZ(), 1.0f);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        glfwPollEvents();

        frames++;
        // how many frames per second
        if (System.currentTimeMillis() > time + 1000) {
            glfwSetWindowTitle(glfwWindow, title + " | FPS: " + frames);
            time = System.currentTimeMillis();
            frames = 0;
        }
    }

    public void swapBuffers() {
        glfwSwapBuffers(glfwWindow);
    }

    public boolean shouldClose() {
        return glfwWindowShouldClose(glfwWindow);
    }

    public void setBackgroundColor(float r, float g, float b){
        this.background.set(r, g, b);
    }
    public boolean isFullscreen() {
        return isFullscreen;
    }

    public void setFullscreen(boolean isFullscreen) {
        this.isFullscreen = isFullscreen;
        isResized = true;
        if (isFullscreen) {
            glfwGetWindowPos(glfwWindow, windowPosX, windowPosY);
            glfwSetWindowMonitor(glfwWindow, glfwGetPrimaryMonitor(), 0, 0, width, height, 0);
        } else {
            glfwSetWindowMonitor(glfwWindow, 0, windowPosX[0], windowPosY[0], width, height, 0);
        }
    }

    public void destroy() {
        input.destroy();
        glfwWindowShouldClose(glfwWindow);
        glfwDestroyWindow(glfwWindow);
        glfwTerminate();
    }
}
