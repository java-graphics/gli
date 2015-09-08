/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jgli;

import java.nio.ByteBuffer;
import static jgli.Target.*;

/**
 *
 * @author gbarbieri
 */
public class Texture {

    public ByteBuffer data;
    public int format;
    public int target;
    public int baseLayer;
    public int maxLayer;
    public int baseFace;
    public int maxFace;
    public int baseLevel;
    public int maxLevel;
    private Storage storage;
    public int size;

    public Texture() {

    }

//    public Texture(ByteBuffer data, int format, int target, int baseLayer, int maxLayer, int baseFace, int maxFace, int baseLevel, int maxLevel) {
//
//        this(target, format, dimensions, maxLayer, target, maxLevel);
//        this.data = data;
//
//        this
//    }
    /**
     * Create an empty texture instance
     */
    public Texture(int target, int format, int[] dimensions, int layers, int faces, int levels) {

        this.format = format;
        this.target = target;
        this.baseLayer = 0;
        this.maxLayer = layers - 1;
        this.baseFace = 0;
        this.maxFace = faces - 1;
        this.baseLevel = 0;
        this.maxLevel = levels - 1;

        if (!(target != TARGET_CUBE || (target == TARGET_CUBE && dimensions[0] == dimensions[1]))) {
            throw new Error("!(target != TARGET_CUBE || (target == TARGET_CUBE && dimensions[0] == dimensions[1]))");
        }
        if (!(target != TARGET_CUBE_ARRAY || (target == TARGET_CUBE_ARRAY && dimensions[0] == dimensions[1]))) {
            throw new Error("!(target != TARGET_CUBE_ARRAY || (target == TARGET_CUBE_ARRAY && dimensions[0] == dimensions[1]))");
        }
        
        storage = new Storage(format, dimensions, layers, faces, levels);
        
        size = storage.layer_size(baseFace, maxFace, baseLevel, maxLevel) * layers;
    }
}
