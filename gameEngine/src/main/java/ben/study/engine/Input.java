package ben.study.engine;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;

public class Input {
    private boolean[] keys = new boolean[GLFW.GLFW_KEY_LAST];
    private boolean[] buttons = new boolean[GLFW.GLFW_MOUSE_BUTTON_LAST];
    private double mouseX, mouseY;

    private GLFWKeyCallback keyboardCallback;
    private GLFWCursorPosCallback mouseMoveCallback;
    private GLFWMouseButtonCallback mouseClickCallback;

    public Input() {
        keyboardCallback = new GLFWKeyCallback() {
            @Override
            public void invoke(long window, int key, int scancode, int action, int mods) {
                keys[key] = action != GLFW.GLFW_RELEASE;
            }
        };

        mouseMoveCallback = new GLFWCursorPosCallback() {
            @Override
            public void invoke(long window, double xpos, double ypos) {
                mouseX = xpos;
                mouseY = ypos;
            }
        };
        mouseClickCallback = new GLFWMouseButtonCallback() {
            @Override
            public void invoke(long window, int button, int action, int mods) {
                buttons[button] = action != GLFW.GLFW_RELEASE;
            }
        };
    }

    public double getMouseX() {
        return mouseX;
    }

    public double getMouseY() {
        return mouseY;
    }

    public boolean isKeyDown(int key) {
        return keys[key];
    }

    public boolean isButtonDown(int key) {
        return buttons[key];
    }

    public GLFWKeyCallback getKeyboardCallback() {
        return keyboardCallback;
    }

    public GLFWCursorPosCallback getMouseMoveCallback() {
        return mouseMoveCallback;
    }

    public GLFWMouseButtonCallback getMouseClickCallback() {
        return mouseClickCallback;
    }

    public void destroy() {
        keyboardCallback.free();
        mouseClickCallback.free();
        mouseMoveCallback.free();
    }
}
