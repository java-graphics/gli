/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jgli;

/**
 *
 * @author GBarbieri
 */
public class Util {

    public static int levels(int[] dimensions) {
        return (int) (Math.log(Glm.compMax(dimensions)) / Math.log(2) + 1);
    }

//    public static void main(String[] args) {
//        System.out.println("" + levels(new int[]{64}));
//    }
}