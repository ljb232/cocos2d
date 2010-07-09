package org.cocos2d.nodes;

import org.cocos2d.opengl.CCTexture2D;
import org.cocos2d.types.ccBlendFunc;
import org.cocos2d.types.ccColor3B;
import org.cocos2d.types.ccMacros;
import org.cocos2d.types.CCPoint;

import javax.microedition.khronos.opengles.GL10;
import static javax.microedition.khronos.opengles.GL10.*;


public class TextureNode extends CocosNode implements CocosNode.CocosNodeRGBA, CocosNode.CocosNodeSize {

    /**
     * The texture that is rendered
     */
    private CCTexture2D texture_;

    // blend func
    private ccBlendFunc blendFunc_;

    // texture RGBA
    private int opacity_;
    private ccColor3B color_;

    boolean opacityModifyRGB_;


    public CCTexture2D getTexture() {
        return texture_;
    }

    public void setTexture(CCTexture2D texture) {
        texture_ = texture;
        setContentSize(texture.getWidth(), texture.getHeight());
//        if( ! texture.hasPremultipliedAlpha() ) {
//            blendFunc_.src = GL_SRC_ALPHA;
//            blendFunc_.dst = GL_ONE_MINUS_SRC_ALPHA;
//        }
//        opacityModifyRGB_ = texture.hasPremultipliedAlpha();

    }

    /**
     * conforms to CocosNodeOpacity and CocosNodeRGB protocol
     */
    public int getOpacity() {
        return opacity_;
    }

    public void setOpacity(int opacity) {
        opacity_ = opacity;
    }

    public void setColor(ccColor3B color) {
        color_.r = color.r;
        color_.g = color.g;
        color_.b = color.b;
    }

    public ccColor3B getColor() {
        return new ccColor3B(color_.r, color_.g, color_.b);
    }

    public TextureNode() {
        opacity_ = 255;
        color_ = new ccColor3B(255, 255, 255);
        setAnchorPoint(0.5f, 0.5f);
        blendFunc_ = new ccBlendFunc(ccMacros.CC_BLEND_SRC, ccMacros.CC_BLEND_DST);

    }

    @Override
    public void draw(GL10 gl) {
        gl.glEnableClientState(GL_VERTEX_ARRAY);
        gl.glEnableClientState(GL_TEXTURE_COORD_ARRAY);

        gl.glEnable(GL_TEXTURE_2D);

        gl.glColor4f(color_.r / 255f, color_.g / 255f, color_.b / 255f, opacity_ / 255f);

        boolean newBlend = false;
        if (blendFunc_.src != ccMacros.CC_BLEND_SRC || blendFunc_.dst != ccMacros.CC_BLEND_DST) {
            newBlend = true;
            gl.glBlendFunc(blendFunc_.src, blendFunc_.dst);
        }

        if (texture_ != null)
            texture_.drawAtPoint(gl, CCPoint.zero());

        if (newBlend)
            gl.glBlendFunc(ccMacros.CC_BLEND_SRC, ccMacros.CC_BLEND_DST);

        // is this chepear than saving/restoring color state ?
        gl.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);

        gl.glDisable(GL_TEXTURE_2D);

        gl.glDisableClientState(GL_VERTEX_ARRAY);
        gl.glDisableClientState(GL_TEXTURE_COORD_ARRAY);
    }

    @Override
    public float getWidth() {
        return texture_.getWidth();
    }

    @Override
    public float getHeight() {
        return texture_.getHeight();
    }
}