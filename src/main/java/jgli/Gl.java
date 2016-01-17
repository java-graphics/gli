/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jgli;

import static jgli.Format.FORMAT_COUNT;
import static jgli.Gl.ExternalFormat.*;
import static jgli.Gl.InternalFormat.*;
import static jgli.Gl.TypeFormat.*;

/**
 *
 * @author GBarbieri
 */
public class Gl {

    public Target translate(jgli.Target target) {
        if (Gl.Target.values().length != jgli.Target.TARGET_COUNT.value) {
            throw new Error("GLI error: target descriptor list doesn't match number of supported targets");
        }
        return Gl.Target.values()[target.value];
    }

    public Format translate(jgli.Format format) {
        if (!format.isValid()) {
            throw new Error("format is not valid");
        }
        return table[format.value - jgli.Format.FORMAT_FIRST.value];
    }

    public Swizzles translate(jgli.Swizzles swizzle) {

        if (Swizzle.values().length != jgli.Swizzles.Swizzle.SWIZZLE_COUNT.value) {
            throw new Error("GLI error: swizzle descriptor list doesn't match number of supported swizzles");
        }

        return new Swizzles(Swizzle.values()[swizzle.r.value], Swizzle.values()[swizzle.g.value],
                Swizzle.values()[swizzle.b.value], Swizzle.values()[swizzle.a.value]);
    }

    public static jgli.Format find(InternalFormat internalFormat, ExternalFormat externalFormat, TypeFormat type) {

        for (int formatIndex = jgli.Format.FORMAT_FIRST.value; formatIndex < FORMAT_COUNT.value; formatIndex++) {

            int index = formatIndex - jgli.Format.FORMAT_FIRST.value;

            if (table[index].internal != internalFormat) {
                continue;
            }
            if (table[index].external != externalFormat) {
                continue;
            }
            if (table[index].type != type) {
                continue;
            }
            return jgli.Format.values()[formatIndex];
        }
        return jgli.Format.FORMAT_INVALID;
    }

    /**
     * https://www.opengl.org/wiki/Image_Format.
     *
     * "": No type suffix means unsigned normalized integer format.
     *
     * "_SNORM": Signed normalized integer format. "F": Floating-point. Thus,
     * GL_RGBA32F is a floating-point format where each component is a 32-bit
     * IEEE floating-point value.
     *
     * "I": Signed integral format. Thus GL_RGBA8I gives a signed integer format
     * where each of the four components is an integer on the range [-128, 127].
     *
     * "UI": Unsigned integral format. The values go from [0, MAX_INT] for the
     * integer size.
     */
    public enum InternalFormat {

        /**
         * unorm formats.
         */
        INTERNAL_R8_UNORM(0x8229), //GL_R8
        INTERNAL_RG8_UNORM(0x822B), //GL_RG8
        INTERNAL_RGB8_UNORM(0x8051), //GL_RGB8
        INTERNAL_RGBA8_UNORM(0x8058), //GL_RGBA8
        //
        INTERNAL_R16_UNORM(0x822A), //GL_R16
        INTERNAL_RG16_UNORM(0x822C), //GL_RG16
        INTERNAL_RGB16_UNORM(0x8054), //GL_RGB16
        INTERNAL_RGBA16_UNORM(0x805B), //GL_RGBA16
        //
        INTERNAL_RGB10A2_UNORM(0x8059), //GL_RGB10_A2
        INTERNAL_RGB10A2_SNORM_EXT(0xFFFC),
        /**
         * snorm formats.
         */
        INTERNAL_R8_SNORM(0x8F94), //GL_R8_SNORM
        INTERNAL_RG8_SNORM(0x8F95), //GL_RG8_SNORM
        INTERNAL_RGB8_SNORM(0x8F96), //GL_RGB8_SNORM
        INTERNAL_RGBA8_SNORM(0x8F97), //GL_RGBA8_SNORM
        //
        INTERNAL_R16_SNORM(0x8F98), //GL_R16_SNORM
        INTERNAL_RG16_SNORM(0x8F99), //GL_RG16_SNORM
        INTERNAL_RGB16_SNORM(0x8F9A), //GL_RGB16_SNORM
        INTERNAL_RGBA16_SNORM(0x8F9B), //GL_RGBA16_SNORM
        /**
         * unsigned integer formats.
         */
        INTERNAL_R8U(0x8232), //GL_R8UI
        INTERNAL_RG8U(0x8238), //GL_RG8UI
        INTERNAL_RGB8U(0x8D7D), //GL_RGB8UI
        INTERNAL_RGBA8U(0x8D7C), //GL_RGBA8UI
        //
        INTERNAL_R16U(0x8234), //GL_R16UI
        INTERNAL_RG16U(0x823A), //GL_RG16UI
        INTERNAL_RGB16U(0x8D77), //GL_RGB16UI
        INTERNAL_RGBA16U(0x8D76), //GL_RGBA16UI
        //
        INTERNAL_R32U(0x8236), //GL_R32UI
        INTERNAL_RG32U(0x823C), //GL_RG32UI
        INTERNAL_RGB32U(0x8D71), //GL_RGB32UI
        INTERNAL_RGBA32U(0x8D70), //GL_RGBA32UI
        //
        INTERNAL_RGB10A2U(0x906F), //GL_RGB10_A2UI
        INTERNAL_RGB10A2I_EXT(0xFFFB),
        /**
         * signed integer formats.
         */
        INTERNAL_R8I(0x8231), //GL_R8I
        INTERNAL_RG8I(0x8237), //GL_RG8I
        INTERNAL_RGB8I(0x8D8F), //GL_RGB8I
        INTERNAL_RGBA8I(0x8D8E), //GL_RGBA8I
        //
        INTERNAL_R16I(0x8233), //GL_R16I
        INTERNAL_RG16I(0x8239), //GL_RG16I
        INTERNAL_RGB16I(0x8D89), //GL_RGB16I
        INTERNAL_RGBA16I(0x8D88), //GL_RGBA16I
        //
        INTERNAL_R32I(0x8235), //GL_R32I
        INTERNAL_RG32I(0x823B), //GL_RG32I
        INTERNAL_RGB32I(0x8D83), //GL_RGB32I
        INTERNAL_RGBA32I(0x8D82), //GL_RGBA32I
        /**
         * Floating formats.
         */
        INTERNAL_R16F(0x822D), //GL_R16F
        INTERNAL_RG16F(0x822F), //GL_RG16F
        INTERNAL_RGB16F(0x881B), //GL_RGB16F
        INTERNAL_RGBA16F(0x881A), //GL_RGBA16F
        //
        INTERNAL_R32F(0x822E), //GL_R32F
        INTERNAL_RG32F(0x8230), //GL_RG32F
        INTERNAL_RGB32F(0x8815), //GL_RGB32F
        INTERNAL_RGBA32F(0x8814), //GL_RGBA32F
        //        
        INTERNAL_R64F_EXT(0xFFFA), //GL_R64F
        INTERNAL_RG64F_EXT(0xFFF9), //GL_RG64F
        INTERNAL_RGB64F_EXT(0xFFF8), //GL_RGB64F
        INTERNAL_RGBA64F_EXT(0xFFF7), //GL_RGBA64F
        /**
         * sRGB formats.
         */
        INTERNAL_SR8(0x8FBD), //GL_SR8_EXT
        INTERNAL_SRG8(0x8FBE), //GL_SRG8_EXT
        INTERNAL_SRGB8(0x8C41), //GL_SRGB8
        INTERNAL_SRGB8_ALPHA8(0x8C43), //GL_SRGB8_ALPHA8
        /**
         * Packed formats.
         */
        INTERNAL_RGB9E5(0x8C3D), //GL_RGB9_E5
        INTERNAL_RG11B10F(0x8C3A), //GL_R11F_G11F_B10F
        INTERNAL_RG3B2(0x2A10), //GL_R3_G3_B2
        INTERNAL_R5G6B5(0x8D62), //GL_RGB565
        INTERNAL_RGB5A1(0x8057), //GL_RGB5_A1
        INTERNAL_RGBA4(0x8056), //GL_RGBA4
        //        
        INTERNAL_RG4_EXT(0xFFFE),
        /**
         * Luminance Alpha formats.
         */
        INTERNAL_LA4(0x8043), //GL_LUMINANCE4_ALPHA4
        INTERNAL_L8(0x8040), //GL_LUMINANCE8
        INTERNAL_A8(0x803C), //GL_ALPHA8
        INTERNAL_LA8(0x8045), //GL_LUMINANCE8_ALPHA8
        INTERNAL_L16(0x8042), //GL_LUMINANCE16
        INTERNAL_A16(0x803E), //GL_ALPHA16
        INTERNAL_LA16(0x8048), //GL_LUMINANCE16_ALPHA16
        /**
         * Depth formats.
         */
        INTERNAL_D16(0x81A5), //GL_DEPTH_COMPONENT16
        INTERNAL_D24(0x81A6), //GL_DEPTH_COMPONENT24
        INTERNAL_D16S8_EXT(0xFFF6),
        INTERNAL_D24S8(0x88F0), //GL_DEPTH24_STENCIL8
        INTERNAL_D32(0x81A7), //GL_DEPTH_COMPONENT32
        INTERNAL_D32F(0x8CAC), //GL_DEPTH_COMPONENT32F
        INTERNAL_D32FS8X24(0x8CAD), //GL_DEPTH32F_STENCIL8
        INTERNAL_S8_EXT(0x8D48), //GL_STENCIL_INDEX8
        /**
         * Compressed formats.
         */
        INTERNAL_RGB_DXT1(0x83F0), //GL_COMPRESSED_RGB_S3TC_DXT1_EXT
        INTERNAL_RGBA_DXT1(0x83F1), //GL_COMPRESSED_RGBA_S3TC_DXT1_EXT
        INTERNAL_RGBA_DXT3(0x83F2), //GL_COMPRESSED_RGBA_S3TC_DXT3_EXT
        INTERNAL_RGBA_DXT5(0x83F3), //GL_COMPRESSED_RGBA_S3TC_DXT5_EXT
        INTERNAL_R_ATI1N_UNORM(0x8DBB), //GL_COMPRESSED_RED_RGTC1
        INTERNAL_R_ATI1N_SNORM(0x8DBC), //GL_COMPRESSED_SIGNED_RED_RGTC1
        INTERNAL_RG_ATI2N_UNORM(0x8DBD), //GL_COMPRESSED_RG_RGTC2
        INTERNAL_RG_ATI2N_SNORM(0x8DBE), //GL_COMPRESSED_SIGNED_RG_RGTC2
        INTERNAL_RGB_BP_UNSIGNED_FLOAT(0x8E8F), //GL_COMPRESSED_RGB_BPTC_UNSIGNED_FLOAT
        INTERNAL_RGB_BP_SIGNED_FLOAT(0x8E8E), //GL_COMPRESSED_RGB_BPTC_SIGNED_FLOAT
        INTERNAL_RGB_BP_UNORM(0x8E8C), //GL_COMPRESSED_RGBA_BPTC_UNORM
        INTERNAL_RGB_PVRTC_4BPPV1(0x8C00), //GL_COMPRESSED_RGB_PVRTC_4BPPV1_IMG
        INTERNAL_RGB_PVRTC_2BPPV1(0x8C01), //GL_COMPRESSED_RGB_PVRTC_2BPPV1_IMG
        INTERNAL_RGBA_PVRTC_4BPPV1(0x8C02), //GL_COMPRESSED_RGBA_PVRTC_4BPPV1_IMG
        INTERNAL_RGBA_PVRTC_2BPPV1(0x8C03), //GL_COMPRESSED_RGBA_PVRTC_2BPPV1_IMG
        INTERNAL_RGBA_PVRTC_4BPPV2(0x9137), //GL_COMPRESSED_RGBA_PVRTC_4BPPV2_IMG
        INTERNAL_RGBA_PVRTC_2BPPV2(0x9138), //GL_COMPRESSED_RGBA_PVRTC_2BPPV2_IMG
        INTERNAL_ATC_RGB(0x8C92), //GL_ATC_RGB_AMD
        INTERNAL_ATC_RGBA_EXPLICIT_ALPHA(0x8C93), //GL_ATC_RGBA_EXPLICIT_ALPHA_AMD
        INTERNAL_ATC_RGBA_INTERPOLATED_ALPHA(0x87EE), //GL_ATC_RGBA_INTERPOLATED_ALPHA_AMD
        //
        INTERNAL_RGB_ETC(0x8D64), //GL_COMPRESSED_RGB8_ETC1
        INTERNAL_RGB_ETC2(0x9274), //GL_COMPRESSED_RGB8_ETC2
        INTERNAL_RGBA_PUNCHTHROUGH_ETC2(0x9276), //GL_COMPRESSED_RGB8_PUNCHTHROUGH_ALPHA1_ETC2
        INTERNAL_RGBA_ETC2(0x9278), //GL_COMPRESSED_RGBA8_ETC2_EAC
        INTERNAL_R11_EAC(0x9270), //GL_COMPRESSED_R11_EAC
        INTERNAL_SIGNED_R11_EAC(0x9271), //GL_COMPRESSED_SIGNED_R11_EAC
        INTERNAL_RG11_EAC(0x9272), //GL_COMPRESSED_RG11_EAC
        INTERNAL_SIGNED_RG11_EAC(0x9273), //GL_COMPRESSED_SIGNED_RG11_EAC
        //
        INTERNAL_RGBA_ASTC_4x4(0x93B0), //GL_COMPRESSED_RGBA_ASTC_4x4_KHR
        INTERNAL_RGBA_ASTC_5x4(0x93B1), //GL_COMPRESSED_RGBA_ASTC_5x4_KHR
        INTERNAL_RGBA_ASTC_5x5(0x93B2), //GL_COMPRESSED_RGBA_ASTC_5x5_KHR
        INTERNAL_RGBA_ASTC_6x5(0x93B3), //GL_COMPRESSED_RGBA_ASTC_6x5_KHR
        INTERNAL_RGBA_ASTC_6x6(0x93B4), //GL_COMPRESSED_RGBA_ASTC_6x6_KHR
        INTERNAL_RGBA_ASTC_8x5(0x93B5), //GL_COMPRESSED_RGBA_ASTC_8x5_KHR
        INTERNAL_RGBA_ASTC_8x6(0x93B6), //GL_COMPRESSED_RGBA_ASTC_8x6_KHR
        INTERNAL_RGBA_ASTC_8x8(0x93B7), //GL_COMPRESSED_RGBA_ASTC_8x8_KHR
        INTERNAL_RGBA_ASTC_10x5(0x93B8), //GL_COMPRESSED_RGBA_ASTC_10x5_KHR
        INTERNAL_RGBA_ASTC_10x6(0x93B9), //GL_COMPRESSED_RGBA_ASTC_10x6_KHR
        INTERNAL_RGBA_ASTC_10x8(0x93BA), //GL_COMPRESSED_RGBA_ASTC_10x8_KHR
        INTERNAL_RGBA_ASTC_10x10(0x93BB), //GL_COMPRESSED_RGBA_ASTC_10x10_KHR
        INTERNAL_RGBA_ASTC_12x10(0x93BC), //GL_COMPRESSED_RGBA_ASTC_12x10_KHR
        INTERNAL_RGBA_ASTC_12x12(0x93BD), //GL_COMPRESSED_RGBA_ASTC_12x12_KHR
        /**
         * sRGB formats.
         */
        INTERNAL_SRGB_DXT1(0x8C4C), //GL_COMPRESSED_SRGB_S3TC_DXT1_EXT
        INTERNAL_SRGB_ALPHA_DXT1(0x8C4D), //GL_COMPRESSED_SRGB_ALPHA_S3TC_DXT1_EXT
        INTERNAL_SRGB_ALPHA_DXT3(0x8C4E), //GL_COMPRESSED_SRGB_ALPHA_S3TC_DXT3_EXT
        INTERNAL_SRGB_ALPHA_DXT5(0x8C4F), //GL_COMPRESSED_SRGB_ALPHA_S3TC_DXT5_EXT
        INTERNAL_SRGB_BP_UNORM(0x8E8D), //GL_COMPRESSED_SRGB_ALPHA_BPTC_UNORM
        INTERNAL_SRGB_PVRTC_2BPPV1(0x8A54), //GL_COMPRESSED_SRGB_PVRTC_2BPPV1_EXT
        INTERNAL_SRGB_PVRTC_4BPPV1(0x8A55), //GL_COMPRESSED_SRGB_PVRTC_4BPPV1_EXT
        INTERNAL_SRGB_ALPHA_PVRTC_2BPPV1(0x8A56), //GL_COMPRESSED_SRGB_ALPHA_PVRTC_2BPPV1_EXT
        INTERNAL_SRGB_ALPHA_PVRTC_4BPPV1(0x8A57), //GL_COMPRESSED_SRGB_ALPHA_PVRTC_4BPPV1_EXT
        INTERNAL_SRGB_ALPHA_PVRTC_2BPPV2(0x93F0), //COMPRESSED_SRGB_ALPHA_PVRTC_2BPPV2_IMG
        INTERNAL_SRGB_ALPHA_PVRTC_4BPPV2(0x93F1), //GL_COMPRESSED_SRGB_ALPHA_PVRTC_4BPPV2_IMG
        INTERNAL_SRGB8_ETC2(0x9275), //GL_COMPRESSED_SRGB8_ETC2
        INTERNAL_SRGB8_PUNCHTHROUGH_ALPHA1_ETC2(0x9277), //GL_COMPRESSED_SRGB8_PUNCHTHROUGH_ALPHA1_ETC2
        INTERNAL_SRGB8_ALPHA8_ETC2_EAC(0x9279), //GL_COMPRESSED_SRGB8_ALPHA8_ETC2_EAC
        INTERNAL_SRGB8_ALPHA8_ASTC_4x4(0x93D0), //GL_COMPRESSED_SRGB8_ALPHA8_ASTC_4x4_KHR
        INTERNAL_SRGB8_ALPHA8_ASTC_5x4(0x93D1), //GL_COMPRESSED_SRGB8_ALPHA8_ASTC_5x4_KHR
        INTERNAL_SRGB8_ALPHA8_ASTC_5x5(0x93D2), //GL_COMPRESSED_SRGB8_ALPHA8_ASTC_5x5_KHR
        INTERNAL_SRGB8_ALPHA8_ASTC_6x5(0x93D3), //GL_COMPRESSED_SRGB8_ALPHA8_ASTC_6x5_KHR
        INTERNAL_SRGB8_ALPHA8_ASTC_6x6(0x93D4), //GL_COMPRESSED_SRGB8_ALPHA8_ASTC_6x6_KHR
        INTERNAL_SRGB8_ALPHA8_ASTC_8x5(0x93D5), //GL_COMPRESSED_SRGB8_ALPHA8_ASTC_8x5_KHR
        INTERNAL_SRGB8_ALPHA8_ASTC_8x6(0x93D6), //GL_COMPRESSED_SRGB8_ALPHA8_ASTC_8x6_KHR
        INTERNAL_SRGB8_ALPHA8_ASTC_8x8(0x93D7), //GL_COMPRESSED_SRGB8_ALPHA8_ASTC_8x8_KHR
        INTERNAL_SRGB8_ALPHA8_ASTC_10x5(0x93D8), //GL_COMPRESSED_SRGB8_ALPHA8_ASTC_10x5_KHR
        INTERNAL_SRGB8_ALPHA8_ASTC_10x6(0x93D9), //GL_COMPRESSED_SRGB8_ALPHA8_ASTC_10x6_KHR
        INTERNAL_SRGB8_ALPHA8_ASTC_10x8(0x93DA), //GL_COMPRESSED_SRGB8_ALPHA8_ASTC_10x8_KHR
        INTERNAL_SRGB8_ALPHA8_ASTC_10x10(0x93DB), //GL_COMPRESSED_SRGB8_ALPHA8_ASTC_10x10_KHR
        INTERNAL_SRGB8_ALPHA8_ASTC_12x10(0x93DC), //GL_COMPRESSED_SRGB8_ALPHA8_ASTC_12x10_KHR
        INTERNAL_SRGB8_ALPHA8_ASTC_12x12(0x93DD);		//GL_COMPRESSED_SRGB8_ALPHA8_ASTC_12x12_KHR

        public final int value;

        private InternalFormat(int value) {
            this.value = value;
        }

        public static InternalFormat get(int value) {
            for (InternalFormat internalFormat : values()) {
                if (internalFormat.value == value) {
                    return internalFormat;
                }
            }
            // First as default
            return INTERNAL_R8_UNORM;
        }
    }

    public enum ExternalFormat {

        EXTERNAL_NONE(0), //GL_NONE
        EXTERNAL_RED(0x1903), //GL_RED
        EXTERNAL_RG(0x8227), //GL_RG
        EXTERNAL_RGB(0x1907), //GL_RGB
        EXTERNAL_BGR(0x80E0), //GL_BGR
        EXTERNAL_RGBA(0x1908), //GL_RGBA
        EXTERNAL_BGRA(0x80E1), //GL_BGRA
        EXTERNAL_RED_INTEGER(0x8D94), //GL_RED_INTEGER
        EXTERNAL_RG_INTEGER(0x8228), //GL_RG_INTEGER
        EXTERNAL_RGB_INTEGER(0x8D98), //GL_RGB_INTEGER
        EXTERNAL_BGR_INTEGER(0x8D9A), //GL_BGR_INTEGER
        EXTERNAL_RGBA_INTEGER(0x8D99), //GL_RGBA_INTEGER
        EXTERNAL_BGRA_INTEGER(0x8D9B), //GL_BGRA_INTEGER
        EXTERNAL_DEPTH(0x1902), //GL_DEPTH_COMPONENT
        EXTERNAL_DEPTH_STENCIL(0x84F9), //GL_DEPTH_STENCIL
        EXTERNAL_STENCIL(0x1901), //GL_STENCIL_INDEX
        //
        EXTERNAL_LUMINANCE(0x1909), //GL_LUMINANCE
        EXTERNAL_ALPHA(0x1906), //GL_ALPHA
        EXTERNAL_LUMINANCE_ALPHA(0x190A);//GL_LUMINANCE_ALPHA

        public final int value;

        private ExternalFormat(int value) {
            this.value = value;
        }

        public static ExternalFormat get(int value) {
            for (ExternalFormat externalFormat : values()) {
                if (externalFormat.value == value) {
                    return externalFormat;
                }
            }
            return EXTERNAL_NONE;
        }
    }

    public enum TypeFormat {

        TYPE_NONE(0), //GL_NONE
        TYPE_I8(0x1400), //GL_BYTE
        TYPE_U8(0x1401), //GL_UNSIGNED_BYTE
        TYPE_I16(0x1402), //GL_SHORT
        TYPE_U16(0x1403), //GL_UNSIGNED_SHORT
        TYPE_I32(0x1404), //GL_INT
        TYPE_U32(0x1405), //GL_UNSIGNED_INT
        TYPE_I64 (0x140E),					//GL_INT64_ARB
        TYPE_U64 (0x140F),					//GL_UNSIGNED_INT64_ARB
        TYPE_F16(0x140B), //GL_HALF_FLOAT
        TYPE_F32(0x1406), //GL_FLOAT
        TYPE_F64 (0x140A),					//GL_DOUBLE
        TYPE_UINT32_RGB9_E5_REV(0x8C3E), //GL_UNSIGNED_INT_5_9_9_9_REV
        TYPE_UINT32_RG11B10F_REV(0x8C3B), //GL_UNSIGNED_INT_10F_11F_11F_REV
        TYPE_UINT8_RG3B2(0x8032), //GL_UNSIGNED_BYTE_3_3_2
        TYPE_UINT8_RG3B2_REV(0x8362), //GL_UNSIGNED_BYTE_2_3_3_REV
        TYPE_UINT16_RGB5A1(0x8034), //GL_UNSIGNED_SHORT_5_5_5_1
        TYPE_UINT16_RGB5A1_REV(0x8366), //GL_UNSIGNED_SHORT_1_5_5_5_REV
        TYPE_UINT16_R5G6B5(0x8363), //GL_UNSIGNED_SHORT_5_6_5
        TYPE_UINT16_R5G6B5_REV(0x8364), //GL_UNSIGNED_SHORT_5_6_5_REV
        TYPE_UINT16_RGBA4(0x8033), //GL_UNSIGNED_SHORT_4_4_4_4
        TYPE_UINT16_RGBA4_REV(0x8365), //GL_UNSIGNED_SHORT_4_4_4_4_REV
        TYPE_UINT32_RGB10A2(0x8036), //GL_UNSIGNED_INT_10_10_10_2
        TYPE_UINT32_RGB10A2_REV(0x8368), //GL_UNSIGNED_INT_2_10_10_10_REV
        //        
        TYPE_UINT8_RG4_REV_GTC(0xFFFD);

        public final int value;

        private TypeFormat(int value) {
            this.value = value;
        }

        public static TypeFormat get(int value) {
            for (TypeFormat typeFormat : values()) {
                if (typeFormat.value == value) {
                    return typeFormat;
                }
            }
            return TYPE_NONE;
        }
    }

    public enum Target {

        TARGET_1D(0x0DE0),
        TARGET_1D_ARRAY(0x8C18),
        TARGET_2D(0x0DE1),
        TARGET_2D_ARRAY(0x8C1A),
        TARGET_3D(0x806F),
        TARGET_RECT(0x84F5),
        TARGET_RECT_ARRAY(0x84F5), // Not supported by OpenGL
        TARGET_CUBE(0x8513),
        TARGET_CUBE_ARRAY(0x9009);

        public final int value;

        private Target(int value) {
            this.value = value;
        }
    }

    public enum Swizzle {

        /**
         * Reordered because of:
         *
         * public Swizzles translate(jgli.Format.Swizzles swizzle).
         */
        SWIZZLE_RED(0x1903), //GL_RED
        SWIZZLE_GREEN(0x1904), //GL_GREEN
        SWIZZLE_BLUE(0x1905), //GL_BLUE
        SWIZZLE_ALPHA(0x1906), //GL_ALPHA
        SWIZZLE_ZERO(0x0000), //GL_ZERO
        SWIZZLE_ONE(0x0001); //GL_ONE

        public final int value;

        private Swizzle(int value) {
            this.value = value;
        }
    }

    public class Swizzles {

        public Swizzle r;
        public Swizzle g;
        public Swizzle b;
        public Swizzle a;

        public Swizzles(Swizzle r, Swizzle g, Swizzle b, Swizzle a) {
            this.r = r;
            this.g = g;
            this.b = b;
            this.a = a;
        }

        public int[] toArray() {
            return new int[]{r.value, g.value, b.value, a.value};
        }
    }

    public static class Format {

        public InternalFormat internal;
        public ExternalFormat external;
        public TypeFormat type;
        public Swizzles swizzles;

        public Format(InternalFormat internal, ExternalFormat external, TypeFormat type) {
            this.internal = internal;
            this.external = external;
            this.type = type;
        }
    }

    private static Format[] table = new Format[]{
        new Format(INTERNAL_RG4_EXT, EXTERNAL_RGBA, TYPE_UINT8_RG4_REV_GTC), //FORMAT_R4G4_UNORM,
        new Format(INTERNAL_RGBA4, EXTERNAL_RGBA, TYPE_UINT16_RGBA4_REV), //FORMAT_RGBA4_UNORM,
        new Format(INTERNAL_RGBA4, EXTERNAL_RGBA, TYPE_UINT16_RGBA4), //FORMAT_BGRA4_UNORM,
        new Format(INTERNAL_R5G6B5, EXTERNAL_RGB, TYPE_UINT16_R5G6B5_REV), //FORMAT_R5G6B5_UNORM,
        new Format(INTERNAL_R5G6B5, EXTERNAL_RGB, TYPE_UINT16_R5G6B5), //FORMAT_B5G6R5_UNORM,
        new Format(INTERNAL_RGB5A1, EXTERNAL_RGBA, TYPE_UINT16_RGB5A1_REV), //FORMAT_RGB5A1_UNORM,
        new Format(INTERNAL_RGB5A1, EXTERNAL_RGBA, TYPE_UINT16_RGB5A1), //FORMAT_BGR5A1_UNORM,
        new Format(INTERNAL_RGB5A1, EXTERNAL_RGBA, TYPE_UINT16_RGB5A1), //FORMAT_A1RGB5_UNORM,

        new Format(INTERNAL_R8_UNORM, EXTERNAL_RED, TYPE_U8), //FORMAT_R8_UNORM,
        new Format(INTERNAL_R8_SNORM, EXTERNAL_RED, TYPE_I8), //FORMAT_R8_SNORM,
        new Format(INTERNAL_R8_UNORM, EXTERNAL_RED, TYPE_U8), //FORMAT_R8_USCALED,
        new Format(INTERNAL_R8_SNORM, EXTERNAL_RED, TYPE_I8), //FORMAT_R8_SSCALED,
        new Format(INTERNAL_R8U, EXTERNAL_RED_INTEGER, TYPE_U8), //FORMAT_R8_UINT,
        new Format(INTERNAL_R8I, EXTERNAL_RED_INTEGER, TYPE_I8), //FORMAT_R8_SINT,
        new Format(INTERNAL_SR8, EXTERNAL_RED, TYPE_U8), //FORMAT_R8_SRGB,

        new Format(INTERNAL_RG8_UNORM, EXTERNAL_RG, TYPE_U8), //FORMAT_RG8_UNORM,
        new Format(INTERNAL_RG8_SNORM, EXTERNAL_RG, TYPE_I8), //FORMAT_RG8_SNORM,
        new Format(INTERNAL_RG8_UNORM, EXTERNAL_RG, TYPE_U8), //FORMAT_RG8_USCALED,
        new Format(INTERNAL_RG8_SNORM, EXTERNAL_RG, TYPE_I8), //FORMAT_RG8_SSCALED,
        new Format(INTERNAL_RG8U, EXTERNAL_RG_INTEGER, TYPE_U8), //FORMAT_RG8_UINT,
        new Format(INTERNAL_RG8I, EXTERNAL_RG_INTEGER, TYPE_I8), //FORMAT_RG8_SINT,
        new Format(INTERNAL_SRG8, EXTERNAL_RG, TYPE_U8), //FORMAT_RG8_SRGB,

        new Format(INTERNAL_RGB8_UNORM, EXTERNAL_RGB, TYPE_U8), //FORMAT_RGB8_UNORM,
        new Format(INTERNAL_RGB8_SNORM, EXTERNAL_RGB, TYPE_I8), //FORMAT_RGB8_SNORM,
        new Format(INTERNAL_RGB8_UNORM, EXTERNAL_RGB, TYPE_U8), //FORMAT_RGB8_USCALED,
        new Format(INTERNAL_RGB8_SNORM, EXTERNAL_RGB, TYPE_I8), //FORMAT_RGB8_SSCALED,
        new Format(INTERNAL_RGB8U, EXTERNAL_RGB_INTEGER, TYPE_U8), //FORMAT_RGB8_UINT,
        new Format(INTERNAL_RGB8I, EXTERNAL_RGB_INTEGER, TYPE_I8), //FORMAT_RGB8_SINT,
        new Format(INTERNAL_SRGB8, EXTERNAL_RGB, TYPE_U8), //FORMAT_RGB8_SRGB,

        new Format(INTERNAL_RGB8_UNORM, EXTERNAL_BGR, TYPE_U8), //FORMAT_BGR8_UNORM,
        new Format(INTERNAL_RGB8_SNORM, EXTERNAL_BGR, TYPE_I8), //FORMAT_BGR8_SNORM,
        new Format(INTERNAL_RGB8_UNORM, EXTERNAL_BGR, TYPE_U8), //FORMAT_BGR8_USCALED,
        new Format(INTERNAL_RGB8_SNORM, EXTERNAL_BGR, TYPE_I8), //FORMAT_BGR8_SSCALED,
        new Format(INTERNAL_RGB8U, EXTERNAL_BGR_INTEGER, TYPE_U8), //FORMAT_BGR8_UINT,
        new Format(INTERNAL_RGB8I, EXTERNAL_BGR_INTEGER, TYPE_I8), //FORMAT_BGR8_SINT,
        new Format(INTERNAL_SRGB8, EXTERNAL_BGR, TYPE_U8), //FORMAT_BGR8_SRGB,

        new Format(INTERNAL_RGBA8_UNORM, EXTERNAL_RGBA, TYPE_U8), //FORMAT_RGBA8_UNORM,
        new Format(INTERNAL_RGBA8_SNORM, EXTERNAL_RGBA, TYPE_I8), //FORMAT_RGBA8_SNORM,
        new Format(INTERNAL_RGBA8_UNORM, EXTERNAL_RGBA, TYPE_U8), //FORMAT_RGBA8_USCALED,
        new Format(INTERNAL_RGBA8_SNORM, EXTERNAL_RGBA, TYPE_I8), //FORMAT_RGBA8_SSCALED,
        new Format(INTERNAL_RGBA8U, EXTERNAL_RGBA_INTEGER, TYPE_U8), //FORMAT_RGBA8_UINT,
        new Format(INTERNAL_RGBA8I, EXTERNAL_RGBA_INTEGER, TYPE_I8), //FORMAT_RGBA8_SINT,
        new Format(INTERNAL_SRGB8_ALPHA8, EXTERNAL_RGBA, TYPE_U8), //FORMAT_RGBA8_SRGB,

        new Format(INTERNAL_RGBA8_UNORM, EXTERNAL_BGRA, TYPE_U8), //FORMAT_BGRA8_UNORM,
        new Format(INTERNAL_RGBA8_SNORM, EXTERNAL_BGRA, TYPE_I8), //FORMAT_BGRA8_SNORM,
        new Format(INTERNAL_RGBA8_UNORM, EXTERNAL_BGRA, TYPE_U8), //FORMAT_BGRA8_USCALED,
        new Format(INTERNAL_RGBA8_SNORM, EXTERNAL_BGRA, TYPE_I8), //FORMAT_BGRA8_SSCALED,
        new Format(INTERNAL_RGBA8U, EXTERNAL_BGRA_INTEGER, TYPE_U8), //FORMAT_BGRA8_UINT,
        new Format(INTERNAL_RGBA8I, EXTERNAL_BGRA_INTEGER, TYPE_I8), //FORMAT_BGRA8_SINT,
        new Format(INTERNAL_SRGB8_ALPHA8, EXTERNAL_BGRA, TYPE_U8), //FORMAT_BGRA8_SRGB,

        new Format(INTERNAL_RGBA8_UNORM, EXTERNAL_BGRA, TYPE_U8), //FORMAT_ABGR8_UNORM,
        new Format(INTERNAL_RGBA8_SNORM, EXTERNAL_BGRA, TYPE_I8), //FORMAT_ABGR8_SNORM,
        new Format(INTERNAL_RGBA8_UNORM, EXTERNAL_BGRA, TYPE_U8), //FORMAT_ABGR8_USCALED,
        new Format(INTERNAL_RGBA8_SNORM, EXTERNAL_BGRA, TYPE_I8), //FORMAT_ABGR8_SSCALED,
        new Format(INTERNAL_RGBA8U, EXTERNAL_BGRA_INTEGER, TYPE_U8), //FORMAT_ABGR8_UINT,
        new Format(INTERNAL_RGBA8I, EXTERNAL_BGRA_INTEGER, TYPE_I8), //FORMAT_ABGR8_SINT,
        new Format(INTERNAL_SRGB8_ALPHA8, EXTERNAL_BGRA, TYPE_U8), //FORMAT_ABGR8_SRGB,

        new Format(INTERNAL_RGB10A2_UNORM, EXTERNAL_RGBA, TYPE_UINT32_RGB10A2_REV), //FORMAT_RGB10A2_UNORM,
        new Format(INTERNAL_RGB10A2_SNORM_EXT, EXTERNAL_RGBA, TYPE_UINT32_RGB10A2_REV), //FORMAT_RGB10A2_SNORM,
        new Format(INTERNAL_RGB10A2_UNORM, EXTERNAL_RGBA, TYPE_UINT32_RGB10A2_REV), //FORMAT_RGB10A2_USCALE,
        new Format(INTERNAL_RGB10A2_SNORM_EXT, EXTERNAL_RGBA, TYPE_UINT32_RGB10A2_REV), //FORMAT_RGB10A2_SSCALE,
        new Format(INTERNAL_RGB10A2U, EXTERNAL_RGBA_INTEGER, TYPE_UINT32_RGB10A2_REV), //FORMAT_RGB10A2_UINT,
        new Format(INTERNAL_RGB10A2I_EXT, EXTERNAL_RGBA_INTEGER, TYPE_UINT32_RGB10A2_REV), //FORMAT_RGB10A2_SINT,

        new Format(INTERNAL_RGB10A2_UNORM, EXTERNAL_RGBA, TYPE_UINT32_RGB10A2), //FORMAT_BGR10A2_UNORM,
        new Format(INTERNAL_RGB10A2_SNORM_EXT, EXTERNAL_RGBA, TYPE_UINT32_RGB10A2), //FORMAT_BGR10A2_SNORM,
        new Format(INTERNAL_RGB10A2_UNORM, EXTERNAL_RGBA, TYPE_UINT32_RGB10A2), //FORMAT_BGR10A2_USCALE,
        new Format(INTERNAL_RGB10A2_SNORM_EXT, EXTERNAL_RGBA, TYPE_UINT32_RGB10A2), //FORMAT_BGR10A2_SSCALE,
        new Format(INTERNAL_RGB10A2U, EXTERNAL_RGBA_INTEGER, TYPE_UINT32_RGB10A2), //FORMAT_BGR10A2_UINT,
        new Format(INTERNAL_RGB10A2I_EXT, EXTERNAL_RGBA_INTEGER, TYPE_UINT32_RGB10A2), //FORMAT_BGR10A2_SINT,

        new Format(INTERNAL_R16_UNORM, EXTERNAL_RED, TYPE_U16), //FORMAT_R16_UNORM_PACK16,
        new Format(INTERNAL_R16_SNORM, EXTERNAL_RED, TYPE_I16), //FORMAT_R16_SNORM_PACK16,
        new Format(INTERNAL_R16_UNORM, EXTERNAL_RED, TYPE_U16), //FORMAT_R16_UNORM_PACK16,
        new Format(INTERNAL_R16_SNORM, EXTERNAL_RED, TYPE_I16), //FORMAT_R16_SNORM_PACK16,
        new Format(INTERNAL_R16U, EXTERNAL_RED_INTEGER, TYPE_U16), //FORMAT_R16_UINT_PACK16,
        new Format(INTERNAL_R16I, EXTERNAL_RED_INTEGER, TYPE_I16), //FORMAT_R16_SINT_PACK16,
        new Format(INTERNAL_R16F, EXTERNAL_RED, TYPE_F16), //FORMAT_R16_SFLOAT_PACK16,

        new Format(INTERNAL_RG16_UNORM, EXTERNAL_RG, TYPE_U16), //FORMAT_RG16_UNORM_PACK16,
        new Format(INTERNAL_RG16_SNORM, EXTERNAL_RG, TYPE_I16), //FORMAT_RG16_SNORM_PACK16,
        new Format(INTERNAL_RG16_UNORM, EXTERNAL_RG, TYPE_U16), //FORMAT_RG16_USCALE,
        new Format(INTERNAL_RG16_SNORM, EXTERNAL_RG, TYPE_I16), //FORMAT_RG16_SSCALE,
        new Format(INTERNAL_RG16U, EXTERNAL_RG_INTEGER, TYPE_U16), //FORMAT_RG16_UINT_PACK16,
        new Format(INTERNAL_RG16I, EXTERNAL_RG_INTEGER, TYPE_I16), //FORMAT_RG16_SINT_PACK16,
        new Format(INTERNAL_RG16F, EXTERNAL_RG, TYPE_F16), //FORMAT_RG16_SFLOAT_PACK16,

        new Format(INTERNAL_RGB16_UNORM, EXTERNAL_RGB, TYPE_U16), //FORMAT_RGB16_UNORM_PACK16,
        new Format(INTERNAL_RGB16_SNORM, EXTERNAL_RGB, TYPE_I16), //FORMAT_RGB16_SNORM_PACK16,
        new Format(INTERNAL_RGB16_UNORM, EXTERNAL_RGB, TYPE_U16), //FORMAT_RGB16_USCALE,
        new Format(INTERNAL_RGB16_SNORM, EXTERNAL_RGB, TYPE_I16), //FORMAT_RGB16_USCALE,
        new Format(INTERNAL_RGB16U, EXTERNAL_RGB_INTEGER, TYPE_U16), //FORMAT_RGB16_UINT_PACK16,
        new Format(INTERNAL_RGB16I, EXTERNAL_RGB_INTEGER, TYPE_I16), //FORMAT_RGB16_SINT_PACK16,
        new Format(INTERNAL_RGB16F, EXTERNAL_RGB, TYPE_F16), //FORMAT_RGB16_SFLOAT_PACK16,

        new Format(INTERNAL_RGBA16_UNORM, EXTERNAL_RGBA, TYPE_U16), //FORMAT_RGBA16_UNORM_PACK16,
        new Format(INTERNAL_RGBA16_SNORM, EXTERNAL_RGBA, TYPE_I16), //FORMAT_RGBA16_SNORM_PACK16,
        new Format(INTERNAL_RGBA16_UNORM, EXTERNAL_RGBA, TYPE_U16), //FORMAT_RGBA16_USCALE,
        new Format(INTERNAL_RGBA16_SNORM, EXTERNAL_RGBA, TYPE_I16), //FORMAT_RGBA16_SSCALE,
        new Format(INTERNAL_RGBA16U, EXTERNAL_RGBA_INTEGER, TYPE_U16), //FORMAT_RGBA16_UINT_PACK16,
        new Format(INTERNAL_RGBA16I, EXTERNAL_RGBA_INTEGER, TYPE_I16), //FORMAT_RGBA16_SINT_PACK16,
        new Format(INTERNAL_RGBA16F, EXTERNAL_RGBA, TYPE_F16), //FORMAT_RGBA16_SFLOAT_PACK16,

        new Format(INTERNAL_R32U, EXTERNAL_RED_INTEGER, TYPE_U32), //FORMAT_R32_UINT_PACK32,
        new Format(INTERNAL_R32I, EXTERNAL_RED_INTEGER, TYPE_I32), //FORMAT_R32_SINT_PACK32,
        new Format(INTERNAL_R32F, EXTERNAL_RED, TYPE_F32), //FORMAT_R32_SFLOAT_PACK32,

        new Format(INTERNAL_RG32U, EXTERNAL_RG_INTEGER, TYPE_U32), //FORMAT_RG32_UINT_PACK32,
        new Format(INTERNAL_RG32I, EXTERNAL_RG_INTEGER, TYPE_I32), //FORMAT_RG32_SINT_PACK32,
        new Format(INTERNAL_RG32F, EXTERNAL_RG, TYPE_F32), //FORMAT_RG32_SFLOAT_PACK32,

        new Format(INTERNAL_RGB32U, EXTERNAL_RGB_INTEGER, TYPE_U32), //FORMAT_RGB32_UINT_PACK32,
        new Format(INTERNAL_RGB32I, EXTERNAL_RGB_INTEGER, TYPE_I32), //FORMAT_RGB32_SINT_PACK32,
        new Format(INTERNAL_RGB32F, EXTERNAL_RGB, TYPE_F32), //FORMAT_RGB32_SFLOAT_PACK32,

        new Format(INTERNAL_RGBA32U, EXTERNAL_RGBA_INTEGER, TYPE_U32), //FORMAT_RGBA32_UINT_PACK32,
        new Format(INTERNAL_RGBA32I, EXTERNAL_RGBA_INTEGER, TYPE_I32), //FORMAT_RGBA32_SINT_PACK32,
        new Format(INTERNAL_RGBA32F, EXTERNAL_RGBA, TYPE_F32), //FORMAT_RGBA32_SFLOAT_PACK32,

        new Format(INTERNAL_R64F_EXT, EXTERNAL_RED, TYPE_U64), //FORMAT_R64_UINT_PACK64,
        new Format(INTERNAL_R64F_EXT, EXTERNAL_RED, TYPE_I64), //FORMAT_R64_SINT_PACK64,
        new Format(INTERNAL_R64F_EXT, EXTERNAL_RED, TYPE_F64), //FORMAT_R64_SFLOAT_PACK64,

        new Format(INTERNAL_RG64F_EXT, EXTERNAL_RG, TYPE_U64), //FORMAT_RG64_UINT_PACK64,
        new Format(INTERNAL_RG64F_EXT, EXTERNAL_RG, TYPE_I64), //FORMAT_RG64_SINT_PACK64,
        new Format(INTERNAL_RG64F_EXT, EXTERNAL_RG, TYPE_F64), //FORMAT_RG64_SFLOAT_PACK64,

        new Format(INTERNAL_RGB64F_EXT, EXTERNAL_RGB, TYPE_U64), //FORMAT_RGB64_UINT_PACK64,
        new Format(INTERNAL_RGB64F_EXT, EXTERNAL_RGB, TYPE_I64), //FORMAT_RGB64_SINT_PACK64,
        new Format(INTERNAL_RGB64F_EXT, EXTERNAL_RGB, TYPE_F64), //FORMAT_RGB64_SFLOAT_PACK64,

        new Format(INTERNAL_RGBA64F_EXT, EXTERNAL_RGBA, TYPE_U64), //FORMAT_RGBA64_UINT_PACK64,
        new Format(INTERNAL_RGBA64F_EXT, EXTERNAL_RGBA, TYPE_I64), //FORMAT_RGBA64_SINT_PACK64,
        new Format(INTERNAL_RGBA64F_EXT, EXTERNAL_RGBA, TYPE_F64), //FORMAT_RGBA64_SFLOAT_PACK64,

        new Format(INTERNAL_RG11B10F, EXTERNAL_RGB, TYPE_UINT32_RG11B10F_REV), //FORMAT_RG11B10_UFLOAT_PACK32,
        new Format(INTERNAL_RGB9E5, EXTERNAL_RGB, TYPE_UINT32_RGB9_E5_REV), //FORMAT_RGB9E5_UFLOAT_PACK32,

        new Format(INTERNAL_D16, EXTERNAL_DEPTH, TYPE_NONE), //FORMAT_D16_UNORM_PACK16,
        new Format(INTERNAL_D24, EXTERNAL_DEPTH, TYPE_NONE), //FORMAT_D24_UNORM,
        new Format(INTERNAL_D32F, EXTERNAL_DEPTH, TYPE_NONE), //FORMAT_D32_UFLOAT,
        new Format(INTERNAL_S8_EXT, EXTERNAL_STENCIL, TYPE_NONE), //FORMAT_S8_UNORM,
        new Format(INTERNAL_D16S8_EXT, EXTERNAL_DEPTH, TYPE_NONE), //FORMAT_D16_UNORM_S8_UINT_PACK32,
        new Format(INTERNAL_D24S8, EXTERNAL_DEPTH_STENCIL, TYPE_NONE), //FORMAT_D24_UNORM_S8_UINT_PACK32,
        new Format(INTERNAL_D32FS8X24, EXTERNAL_DEPTH_STENCIL, TYPE_NONE), //FORMAT_D32_SFLOAT_S8_UINT_PACK64,

        new Format(INTERNAL_RGB_DXT1, EXTERNAL_NONE, TYPE_NONE), //FORMAT_RGB_DXT1_UNORM_BLOCK8,
        new Format(INTERNAL_SRGB_DXT1, EXTERNAL_NONE, TYPE_NONE), //FORMAT_RGB_DXT1_SRGB_BLOCK8,
        new Format(INTERNAL_RGBA_DXT1, EXTERNAL_NONE, TYPE_NONE), //FORMAT_RGBA_DXT1_UNORM_BLOCK8,
        new Format(INTERNAL_SRGB_ALPHA_DXT1, EXTERNAL_NONE, TYPE_NONE), //FORMAT_RGBA_DXT1_SRGB_BLOCK8,
        new Format(INTERNAL_RGBA_DXT3, EXTERNAL_NONE, TYPE_NONE), //FORMAT_RGBA_DXT3_UNORM_BLOCK16,
        new Format(INTERNAL_SRGB_ALPHA_DXT3, EXTERNAL_NONE, TYPE_NONE), //FORMAT_RGBA_DXT3_SRGB_BLOCK16,
        new Format(INTERNAL_RGBA_DXT5, EXTERNAL_NONE, TYPE_NONE), //FORMAT_RGBA_DXT5_UNORM_BLOCK16,
        new Format(INTERNAL_SRGB_ALPHA_DXT5, EXTERNAL_NONE, TYPE_NONE), //FORMAT_RGBA_DXT5_SRGB_BLOCK16,
        new Format(INTERNAL_R_ATI1N_UNORM, EXTERNAL_NONE, TYPE_NONE), //FORMAT_R_ATI1N_UNORM_BLOCK8,
        new Format(INTERNAL_R_ATI1N_SNORM, EXTERNAL_NONE, TYPE_NONE), //FORMAT_R_ATI1N_SNORM_BLOCK8,
        new Format(INTERNAL_RG_ATI2N_UNORM, EXTERNAL_NONE, TYPE_NONE), //FORMAT_RG_ATI2N_UNORM_BLOCK16,
        new Format(INTERNAL_RG_ATI2N_SNORM, EXTERNAL_NONE, TYPE_NONE), //FORMAT_RG_ATI2N_SNORM_BLOCK16,
        new Format(INTERNAL_RGB_BP_UNSIGNED_FLOAT, EXTERNAL_NONE, TYPE_NONE), //FORMAT_RGB_BP_UFLOAT_BLOCK16,
        new Format(INTERNAL_RGB_BP_SIGNED_FLOAT, EXTERNAL_NONE, TYPE_NONE), //FORMAT_RGB_BP_SFLOAT_BLOCK16,
        new Format(INTERNAL_RGB_BP_UNORM, EXTERNAL_NONE, TYPE_NONE), //FORMAT_RGB_BP_UNORM,
        new Format(INTERNAL_SRGB_BP_UNORM, EXTERNAL_NONE, TYPE_NONE), //FORMAT_RGB_BP_SRGB,

        new Format(INTERNAL_RGB_ETC2, EXTERNAL_NONE, TYPE_NONE), //FORMAT_RGB_ETC2_UNORM_BLOCK8,
        new Format(INTERNAL_SRGB8_ETC2, EXTERNAL_NONE, TYPE_NONE), //FORMAT_RGB_ETC2_SRGB_BLOCK8,
        new Format(INTERNAL_RGBA_PUNCHTHROUGH_ETC2, EXTERNAL_NONE, TYPE_NONE), //FORMAT_RGBA_ETC2_PUNCHTHROUGH_UNORM,
        new Format(INTERNAL_SRGB8_PUNCHTHROUGH_ALPHA1_ETC2, EXTERNAL_NONE, TYPE_NONE), //FORMAT_RGBA_ETC2_PUNCHTHROUGH_SRGB,
        new Format(INTERNAL_RGBA_ETC2, EXTERNAL_NONE, TYPE_NONE), //FORMAT_RGBA_ETC2_UNORM_BLOCK16,
        new Format(INTERNAL_SRGB8_ALPHA8_ETC2_EAC, EXTERNAL_NONE, TYPE_NONE), //FORMAT_RGBA_ETC2_SRGB_BLOCK16,
        new Format(INTERNAL_R11_EAC, EXTERNAL_NONE, TYPE_NONE), //FORMAT_R11_EAC_UNORM,
        new Format(INTERNAL_SIGNED_R11_EAC, EXTERNAL_NONE, TYPE_NONE), //FORMAT_R11_EAC_SNORM,
        new Format(INTERNAL_RG11_EAC, EXTERNAL_NONE, TYPE_NONE), //FORMAT_RG11_EAC_UNORM,
        new Format(INTERNAL_SIGNED_RG11_EAC, EXTERNAL_NONE, TYPE_NONE), //FORMAT_RG11_EAC_SNORM,

        new Format(INTERNAL_RGBA_ASTC_4x4, EXTERNAL_NONE, TYPE_NONE), //FORMAT_RGBA_ASTC4X4_UNORM,
        new Format(INTERNAL_SRGB8_ALPHA8_ASTC_4x4, EXTERNAL_NONE, TYPE_NONE), //FORMAT_RGBA_ASTC4X4_SRGB,
        new Format(INTERNAL_RGBA_ASTC_5x4, EXTERNAL_NONE, TYPE_NONE), //FORMAT_RGBA_ASTC5X4_UNORM,
        new Format(INTERNAL_SRGB8_ALPHA8_ASTC_5x4, EXTERNAL_NONE, TYPE_NONE), //FORMAT_RGBA_ASTC5X4_SRGB,
        new Format(INTERNAL_RGBA_ASTC_5x5, EXTERNAL_NONE, TYPE_NONE), //FORMAT_RGBA_ASTC5X5_UNORM,
        new Format(INTERNAL_SRGB8_ALPHA8_ASTC_5x5, EXTERNAL_NONE, TYPE_NONE), //FORMAT_RGBA_ASTC5X5_SRGB,
        new Format(INTERNAL_RGBA_ASTC_6x5, EXTERNAL_NONE, TYPE_NONE), //FORMAT_RGBA_ASTC6X5_UNORM,
        new Format(INTERNAL_SRGB8_ALPHA8_ASTC_6x5, EXTERNAL_NONE, TYPE_NONE), //FORMAT_RGBA_ASTC6X5_SRGB,
        new Format(INTERNAL_RGBA_ASTC_6x6, EXTERNAL_NONE, TYPE_NONE), //FORMAT_RGBA_ASTC6X6_UNORM,
        new Format(INTERNAL_SRGB8_ALPHA8_ASTC_6x6, EXTERNAL_NONE, TYPE_NONE), //FORMAT_RGBA_ASTC6X6_SRGB,
        new Format(INTERNAL_RGBA_ASTC_8x5, EXTERNAL_NONE, TYPE_NONE), //FORMAT_RGBA_ASTC8X5_UNORM,
        new Format(INTERNAL_SRGB8_ALPHA8_ASTC_8x5, EXTERNAL_NONE, TYPE_NONE), //FORMAT_RGBA_ASTC8X5_SRGB,
        new Format(INTERNAL_RGBA_ASTC_8x6, EXTERNAL_NONE, TYPE_NONE), //FORMAT_RGBA_ASTC8X6_UNORM,
        new Format(INTERNAL_SRGB8_ALPHA8_ASTC_8x6, EXTERNAL_NONE, TYPE_NONE), //FORMAT_RGBA_ASTC8X6_SRGB,
        new Format(INTERNAL_RGBA_ASTC_8x8, EXTERNAL_NONE, TYPE_NONE), //FORMAT_RGBA_ASTC8X8_UNORM,
        new Format(INTERNAL_SRGB8_ALPHA8_ASTC_8x8, EXTERNAL_NONE, TYPE_NONE), //FORMAT_RGBA_ASTC8X8_SRGB,
        new Format(INTERNAL_RGBA_ASTC_10x5, EXTERNAL_NONE, TYPE_NONE), //FORMAT_RGBA_ASTC10X5_UNORM,
        new Format(INTERNAL_SRGB8_ALPHA8_ASTC_10x5, EXTERNAL_NONE, TYPE_NONE), //FORMAT_RGBA_ASTC10X5_SRGB,
        new Format(INTERNAL_RGBA_ASTC_10x6, EXTERNAL_NONE, TYPE_NONE), //FORMAT_RGBA_ASTC10X6_UNORM,
        new Format(INTERNAL_SRGB8_ALPHA8_ASTC_10x6, EXTERNAL_NONE, TYPE_NONE), //FORMAT_RGBA_ASTC10X6_SRGB,
        new Format(INTERNAL_RGBA_ASTC_10x8, EXTERNAL_NONE, TYPE_NONE), //FORMAT_RGBA_ASTC10X8_UNORM,
        new Format(INTERNAL_SRGB8_ALPHA8_ASTC_10x8, EXTERNAL_NONE, TYPE_NONE), //FORMAT_RGBA_ASTC10X8_SRGB,
        new Format(INTERNAL_RGBA_ASTC_10x10, EXTERNAL_NONE, TYPE_NONE), //FORMAT_RGBA_ASTC10X10_UNORM,
        new Format(INTERNAL_SRGB8_ALPHA8_ASTC_10x10, EXTERNAL_NONE, TYPE_NONE), //FORMAT_RGBA_ASTC10X10_SRGB,
        new Format(INTERNAL_RGBA_ASTC_12x10, EXTERNAL_NONE, TYPE_NONE), //FORMAT_RGBA_ASTC12X10_UNORM,
        new Format(INTERNAL_SRGB8_ALPHA8_ASTC_12x10, EXTERNAL_NONE, TYPE_NONE), //FORMAT_RGBA_ASTC12X10_SRGB,
        new Format(INTERNAL_RGBA_ASTC_12x12, EXTERNAL_NONE, TYPE_NONE), //FORMAT_RGBA_ASTC12X12_UNORM,
        new Format(INTERNAL_SRGB8_ALPHA8_ASTC_12x12, EXTERNAL_NONE, TYPE_NONE), //FORMAT_RGBA_ASTC12X12_SRGB,

        new Format(INTERNAL_RGB_PVRTC_4BPPV1, EXTERNAL_NONE, TYPE_NONE), //FORMAT_RGB_PVRTC1_8X8_UNORM_BLOCK32,
        new Format(INTERNAL_SRGB_PVRTC_2BPPV1, EXTERNAL_NONE, TYPE_NONE), //FORMAT_RGB_PVRTC1_8X8_SRGB_BLOCK32,
        new Format(INTERNAL_RGB_PVRTC_2BPPV1, EXTERNAL_NONE, TYPE_NONE), //FORMAT_RGB_PVRTC1_16X8_UNORM_BLOCK32,
        new Format(INTERNAL_SRGB_PVRTC_4BPPV1, EXTERNAL_NONE, TYPE_NONE), //FORMAT_RGB_PVRTC1_16X8_SRGB_BLOCK32,
        new Format(INTERNAL_RGBA_PVRTC_4BPPV1, EXTERNAL_NONE, TYPE_NONE), //FORMAT_RGBA_PVRTC1_8X8_UNORM_BLOCK32,
        new Format(INTERNAL_SRGB_ALPHA_PVRTC_2BPPV1, EXTERNAL_NONE, TYPE_NONE), //FORMAT_RGBA_PVRTC1_8X8_SRGB_BLOCK32,
        new Format(INTERNAL_RGBA_PVRTC_2BPPV1, EXTERNAL_NONE, TYPE_NONE), //FORMAT_RGBA_PVRTC1_16X8_UNORM_BLOCK32,
        new Format(INTERNAL_SRGB_ALPHA_PVRTC_4BPPV1, EXTERNAL_NONE, TYPE_NONE), //FORMAT_RGBA_PVRTC1_16X8_SRGB_BLOCK32,
        new Format(INTERNAL_RGBA_PVRTC_4BPPV2, EXTERNAL_NONE, TYPE_NONE), //FORMAT_RGBA_PVRTC2_4X4_UNORM_BLOCK8,
        new Format(INTERNAL_SRGB_ALPHA_PVRTC_4BPPV2, EXTERNAL_NONE, TYPE_NONE), //FORMAT_RGBA_PVRTC2_4X4_SRGB_BLOCK8,
        new Format(INTERNAL_RGBA_PVRTC_2BPPV2, EXTERNAL_NONE, TYPE_NONE), //FORMAT_RGBA_PVRTC2_8X4_UNORM_BLOCK8,
        new Format(INTERNAL_SRGB_ALPHA_PVRTC_2BPPV2, EXTERNAL_NONE, TYPE_NONE), //FORMAT_RGBA_PVRTC2_8X4_SRGB_BLOCK8,

        new Format(INTERNAL_RGB_ETC, EXTERNAL_NONE, TYPE_NONE), //FORMAT_RGB_ETC_UNORM_BLOCK8,
        new Format(INTERNAL_ATC_RGB, EXTERNAL_NONE, TYPE_NONE), //FORMAT_RGB_ATC_UNORM_BLOCK8,
        new Format(INTERNAL_ATC_RGBA_EXPLICIT_ALPHA, EXTERNAL_NONE, TYPE_NONE), //FORMAT_RGBA_ATC_EXPLICIT_UNORM_BLOCK16,
        new Format(INTERNAL_ATC_RGBA_INTERPOLATED_ALPHA, EXTERNAL_NONE, TYPE_NONE), //FORMAT_RGBA_ATC_INTERPOLATED_UNORM_BLOCK16,

        new Format(INTERNAL_R8_UNORM, EXTERNAL_RED, TYPE_U8), //FORMAT_L8_UNORM_BLOCK8,
        new Format(INTERNAL_R8_UNORM, EXTERNAL_RED, TYPE_U8), //FORMAT_A8_UNORM_BLOCK8,
        new Format(INTERNAL_RG8_UNORM, EXTERNAL_RG, TYPE_U8), //FORMAT_LA8_UNORM_BLOCK8,
        new Format(INTERNAL_R16_UNORM, EXTERNAL_RED, TYPE_U16), //FORMAT_L16_UNORM_BLOCK16,
        new Format(INTERNAL_R16_UNORM, EXTERNAL_RED, TYPE_U16), //FORMAT_A16_UNORM_BLOCK16,
        new Format(INTERNAL_RG16_UNORM, EXTERNAL_RG, TYPE_U16), //FORMAT_LA16_UNORM_BLOCK16,

        new Format(INTERNAL_RGB8_UNORM, EXTERNAL_BGRA, TYPE_U8), //FORMAT_BGRX8_UNORM,
        new Format(INTERNAL_SRGB8, EXTERNAL_BGRA, TYPE_U8), //FORMAT_BGRX8_SRGB,

        new Format(INTERNAL_RG3B2, EXTERNAL_RGB, TYPE_UINT8_RG3B2_REV) //FORMAT_RG3B2_UNORM,
    };

    public static final Gl instance = new Gl();

    public Gl() {
        if (table.length != FORMAT_COUNT.value) {
            throw new Error("GLI error: format descriptor list doesn't match number of supported formats");
        }
    }
}
