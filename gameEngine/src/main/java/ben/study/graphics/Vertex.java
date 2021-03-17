package ben.study.graphics;

import ben.study.engine.maths.Vector3f;
import ben.study.engine.maths.Vector2f;

public class Vertex {
    private Vector3f position, color;
    private Vector2f textureCoord;

    public Vertex(Vector3f position, Vector3f color, Vector2f textureCoord) {
        this.position = position;
        this.color = color;
        this.textureCoord = textureCoord;
    }

    public Vector3f getPosition() {
        return position;
    }

    public Vector3f getColor() {
        return color;
    }

    public Vector2f getTextureCoord() {
        return textureCoord;
    }
}