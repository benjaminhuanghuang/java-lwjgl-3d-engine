package ben.study.engine;

import org.lwjgl.glfw.*;

public class Input {
    private static boolean[] keys = new boolean[GLFW.GLFW_KEY_LAST];
    private static boolean[] buttons = new boolean[GLFW.GLFW_MOUSE_BUTTON_LAST];
    private double mouseX, mouseY;
    private double scrollX, scrollY;

    private GLFWKeyCallback keyboardCallback;
    private GLFWCursorPosCallback mouseMoveCallback;
    private GLFWMouseButtonCallback mouseClickCallback;
    private GLFWScrollCallback mouseScrollCallback;

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

        mouseScrollCallback = new GLFWScrollCallback() {
            public void invoke(long window, double offsetx, double offsety) {
                scrollX += offsetx;
                scrollY += offsety;
            }
        };
    }

    public double getMouseX() {
        return mouseX;
    }

    public double getMouseY() {
        return mouseY;
    }

    public double getScrollX() {
        return scrollX;
    }

    public double getScrollY() {
        return scrollY;
    }

    public static boolean isKeyDown(int key) {
        return keys[key];
    }

    public static boolean isButtonDown(int key) {
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

    public GLFWScrollCallback getMouseScrollCallback() {
        return mouseScrollCallback;
    }

    public void destroy() {
        keyboardCallback.free();
        mouseClickCallback.free();
        mouseMoveCallback.free();
        mouseScrollCallback.free();
    }
}
