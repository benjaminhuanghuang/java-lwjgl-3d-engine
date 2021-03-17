package ben.study.graphics;

import ben.study.engine.maths.Matrix4f;
import ben.study.engine.object.GameObject;
import org.lwjgl.opengl.*;

public class Renderer {
    private Shader shader;

    public Renderer(Shader shader) {
        this.shader = shader;
    }

    public void renderMesh(GameObject object) {
        GL30.glBindVertexArray(object.getMesh().getVAO());
        GL30.glEnableVertexAttribArray(0);
        GL30.glEnableVertexAttribArray(1);
        GL30.glEnableVertexAttribArray(2);
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, object.getMesh().getIBO());
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL13.glBindTexture(GL11.GL_TEXTURE_2D, object.getMesh().getTexture().getTextureID());
        shader.bind();
        shader.setUniform("model", Matrix4f.transform(object.getPosition(), object.getRotation(), object.getScale()));
        GL11.glDrawElements(GL11.GL_TRIANGLES, object.getMesh().getIndices().length, GL11.GL_UNSIGNED_INT, 0);
        shader.unbind();
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
        GL30.glDisableVertexAttribArray(0);
        GL30.glDisableVertexAttribArray(1);
        GL30.glDisableVertexAttribArray(2);
        GL30.glBindVertexArray(0);
    }
}