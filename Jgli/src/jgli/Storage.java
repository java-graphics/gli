/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jgli;

import java.nio.ByteBuffer;

/**
 *
 * @author elect
 */
public class Storage {

    private int layers;
    private int faces;
    private int levels;
    private int blockSize;
    private int[] blockCount;
    private int[] dimensions;
    private ByteBuffer data;

    public Storage(jgli.Format format, int[] dimensions, int layers, int faces, int levels) {

        this.layers = layers;
        this.faces = faces;
        this.levels = levels;
        blockSize = format.blockSize();
        blockCount = Glm.max(Glm.divide(dimensions, format.blockDimensions()), new int[]{1, 1, 1});
        this.dimensions = dimensions;

        if (layers <= 0) {
            throw new Error("layers <= 0!");
        }
        if (faces <= 0) {
            throw new Error("faces <= 0!");
        }
        if (levels <= 0) {
            throw new Error("levels <= 0!");
        }
        if (!Glm.all(Glm.greaterThan(dimensions, new byte[]{0, 0, 0}))) {
            throw new Error("Not all dimensions > 0!");
        }
    }

    public int layerSize(int baseFace, int maxFace, int baseLevel, int maxLevel) {

        if (maxFace >= faces) {
            throw new Error("maxFace >= faces!");
        }
        if (maxLevel >= levels) {
            throw new Error("maxLevel >= levels!");
        }
        /**
         * The size of a layer is the sum of the size of each face. All the
         * faces have the same size.
         */
        return faceSize(baseLevel, maxLevel) * (maxFace - baseFace + 1);
    }

    private int faceSize(int baseLevel, int maxLevel) {

        if (maxLevel >= levels) {
            throw new Error("maxLevel >= levels!");
        }

        int faceSize = 0;

        // The size of a face is the sum of the size of each level.
        for (int level = baseLevel; level <= maxLevel; level++) {
//            System.out.println("level_size(" + level + ") " + levelSize(level));
            faceSize += levelSize(level);
        }
        return faceSize;
    }

    public int levelSize(int level) {

        if (level >= levels) {
            throw new Error("level >= levels!");
        }

        return blockSize * Glm.compMul(blockCount(level));
    }

    private int[] blockCount(int level) {

        if (!(level < levels)) {
            throw new Error("!(level < levels)");
        }

        return Glm.max(Glm.shiftRight(blockCount, level), new int[]{1, 1, 1});
    }

    public boolean empty() {
        return data == null;
    }

    public void setData(ByteBuffer data) {
        this.data = data;
        System.out.println("capacity " + data.capacity());
    }

    public int[] dimensions(int level) {
        return Glm.max(Glm.shiftRight(dimensions, level), new int[]{1, 1, 1});
    }

    public ByteBuffer data(int layer, int face, int level) {
        int offset = offset(layer, face, level);
        int levelSize = levelSize(level);
        data.position(offset);
        data.limit(offset + levelSize);
        ByteBuffer result = data.slice();
        data.position(0);
        data.limit(data.capacity());
        return result;
    }

    public int offset(int layer, int face, int level) {
        if (layer >= layers) {
            throw new Error("layer >= layers");
        }
        if (face >= faces) {
            throw new Error("face >= faces");
        }
        if (level >= levels) {
            throw new Error("level >= levels");
        }
        int layerSize = layerSize(0, faces - 1, 0, levels - 1);
        int faceSize = faceSize(0, levels - 1);
        int baseOffset = layerSize * layer + faceSize * face;

        for (int levelIndex = 0, levelCount = level; levelIndex < levelCount; levelIndex++) {
            baseOffset += levelSize(levelIndex);
        }
        return baseOffset;
    }
}
