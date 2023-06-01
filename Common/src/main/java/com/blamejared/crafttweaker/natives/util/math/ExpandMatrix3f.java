package com.blamejared.crafttweaker.natives.util.math;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import org.joml.AxisAngle4d;
import org.joml.AxisAngle4f;
import org.joml.Matrix2fc;
import org.joml.Matrix3f;
import org.joml.Matrix3fc;
import org.joml.Matrix4f;
import org.joml.Matrix4fc;
import org.joml.Matrix4x3fc;
import org.joml.Quaterniond;
import org.joml.Quaterniondc;
import org.joml.Quaternionf;
import org.joml.Quaternionfc;
import org.joml.Vector3f;
import org.joml.Vector3fc;
import org.openzen.zencode.java.ZenCodeType;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.text.NumberFormat;

@ZenRegister
@Document("vanilla/api/util/math/Matrix3f")
@NativeTypeRegistration(value = Matrix3f.class, zenCodeName = "crafttweaker.api.util.math.Matrix3f")
public class ExpandMatrix3f {
    static Matrix3f internal;
    
    public static float m00() {
        
        return internal.m00();
    }
    
    public static float m01() {
        
        return internal.m01();
    }
    
    public static float m02() {
        
        return internal.m02();
    }
    
    public static float m10() {
        
        return internal.m10();
    }
    
    public static float m11() {
        
        return internal.m11();
    }
    
    public static float m12() {
        
        return internal.m12();
    }
    
    public static float m20() {
        
        return internal.m20();
    }
    
    public static float m21() {
        
        return internal.m21();
    }
    
    public static float m22() {
        
        return internal.m22();
    }
    
    public static Matrix3f m00(float m00) {
        
        return internal.m00(m00);
    }
    
    public static Matrix3f m01(float m01) {
        
        return internal.m01(m01);
    }
    
    public static Matrix3f m02(float m02) {
        
        return internal.m02(m02);
    }
    
    public static Matrix3f m10(float m10) {
        
        return internal.m10(m10);
    }
    
    public static Matrix3f m11(float m11) {
        
        return internal.m11(m11);
    }
    
    public static Matrix3f m12(float m12) {
        
        return internal.m12(m12);
    }
    
    public static Matrix3f m20(float m20) {
        
        return internal.m20(m20);
    }
    
    public static Matrix3f m21(float m21) {
        
        return internal.m21(m21);
    }
    
    public static Matrix3f m22(float m22) {
        
        return internal.m22(m22);
    }
    
    public static Matrix3f set(Matrix3fc m) {
        
        return internal.set(m);
    }
    
    public static Matrix3f setTransposed(Matrix3fc m) {
        
        return internal.setTransposed(m);
    }
    
    public static Matrix3f set(Matrix4x3fc m) {
        
        return internal.set(m);
    }
    
    public static Matrix3f set(Matrix4fc mat) {
        
        return internal.set(mat);
    }
    
    public static Matrix3f set(Matrix2fc mat) {
        
        return internal.set(mat);
    }
    
    public static Matrix3f set(AxisAngle4f axisAngle) {
        
        return internal.set(axisAngle);
    }
    
    public static Matrix3f set(AxisAngle4d axisAngle) {
        
        return internal.set(axisAngle);
    }
    
    public static Matrix3f set(Quaternionfc q) {
        
        return internal.set(q);
    }
    
    public static Matrix3f set(Quaterniondc q) {
        
        return internal.set(q);
    }
    
    public static Matrix3f mul(Matrix3fc right) {
        
        return internal.mul(right);
    }
    
    public static Matrix3f mul(Matrix3fc right, Matrix3f dest) {
        
        return internal.mul(right, dest);
    }
    
    public static Matrix3f mulLocal(Matrix3fc left) {
        
        return internal.mulLocal(left);
    }
    
    public static Matrix3f mulLocal(Matrix3fc left, Matrix3f dest) {
        
        return internal.mulLocal(left, dest);
    }
    
    public static Matrix3f set(float m00, float m01, float m02, float m10, float m11, float m12, float m20, float m21, float m22) {
        
        return internal.set(m00, m01, m02, m10, m11, m12, m20, m21, m22);
    }
    
    public static Matrix3f set(float[] m) {
        
        return internal.set(m);
    }
    
    public static Matrix3f set(Vector3fc col0, Vector3fc col1, Vector3fc col2) {
        
        return internal.set(col0, col1, col2);
    }
    
    public static float determinant() {
        
        return internal.determinant();
    }
    
    public static Matrix3f invert() {
        
        return internal.invert();
    }
    
    public static Matrix3f invert(Matrix3f dest) {
        
        return internal.invert(dest);
    }
    
    public static Matrix3f transpose() {
        
        return internal.transpose();
    }
    
    public static Matrix3f transpose(Matrix3f dest) {
        
        return internal.transpose(dest);
    }
    
    public static String toString(NumberFormat formatter) {
        
        return internal.toString(formatter);
    }
    
    public static Matrix3f get(Matrix3f dest) {
        
        return internal.get(dest);
    }
    
    public static Matrix4f get(Matrix4f dest) {
        
        return internal.get(dest);
    }
    
    public static AxisAngle4f getRotation(AxisAngle4f dest) {
        
        return internal.getRotation(dest);
    }
    
    public static Quaternionf getUnnormalizedRotation(Quaternionf dest) {
        
        return internal.getUnnormalizedRotation(dest);
    }
    
    public static Quaternionf getNormalizedRotation(Quaternionf dest) {
        
        return internal.getNormalizedRotation(dest);
    }
    
    public static Quaterniond getUnnormalizedRotation(Quaterniond dest) {
        
        return internal.getUnnormalizedRotation(dest);
    }
    
    public static Quaterniond getNormalizedRotation(Quaterniond dest) {
        
        return internal.getNormalizedRotation(dest);
    }
    
    public static FloatBuffer get(FloatBuffer buffer) {
        
        return internal.get(buffer);
    }
    
    public static FloatBuffer get(int index, FloatBuffer buffer) {
        
        return internal.get(index, buffer);
    }
    
    public static ByteBuffer get(ByteBuffer buffer) {
        
        return internal.get(buffer);
    }
    
    public static ByteBuffer get(int index, ByteBuffer buffer) {
        
        return internal.get(index, buffer);
    }
    
    public static FloatBuffer get3x4(FloatBuffer buffer) {
        
        return internal.get3x4(buffer);
    }
    
    public static FloatBuffer get3x4(int index, FloatBuffer buffer) {
        
        return internal.get3x4(index, buffer);
    }
    
    public static ByteBuffer get3x4(ByteBuffer buffer) {
        
        return internal.get3x4(buffer);
    }
    
    public static ByteBuffer get3x4(int index, ByteBuffer buffer) {
        
        return internal.get3x4(index, buffer);
    }
    
    public static FloatBuffer getTransposed(FloatBuffer buffer) {
        
        return internal.getTransposed(buffer);
    }
    
    public static FloatBuffer getTransposed(int index, FloatBuffer buffer) {
        
        return internal.getTransposed(index, buffer);
    }
    
    public static ByteBuffer getTransposed(ByteBuffer buffer) {
        
        return internal.getTransposed(buffer);
    }
    
    public static ByteBuffer getTransposed(int index, ByteBuffer buffer) {
        
        return internal.getTransposed(index, buffer);
    }
    
    public static Matrix3fc getToAddress(long address) {
        
        return internal.getToAddress(address);
    }
    
    public static float[] get(float[] arr, int offset) {
        
        return internal.get(arr, offset);
    }
    
    public static float[] get(float[] arr) {
        
        return internal.get(arr);
    }
    
    public static Matrix3f set(FloatBuffer buffer) {
        
        return internal.set(buffer);
    }
    
    public static Matrix3f set(ByteBuffer buffer) {
        
        return internal.set(buffer);
    }
    
    public static Matrix3f set(int index, FloatBuffer buffer) {
        
        return internal.set(index, buffer);
    }
    
    public static Matrix3f set(int index, ByteBuffer buffer) {
        
        return internal.set(index, buffer);
    }
    
    public static Matrix3f setFromAddress(long address) {
        
        return internal.setFromAddress(address);
    }
    
    public static Matrix3f zero() {
        
        return internal.zero();
    }
    
    public static Matrix3f identity() {
        
        return internal.identity();
    }
    
    public static Matrix3f scale(Vector3fc xyz, Matrix3f dest) {
        
        return internal.scale(xyz, dest);
    }
    
    public static Matrix3f scale(Vector3fc xyz) {
        
        return internal.scale(xyz);
    }
    
    public static Matrix3f scale(float x, float y, float z, Matrix3f dest) {
        
        return internal.scale(x, y, z, dest);
    }
    
    public static Matrix3f scale(float x, float y, float z) {
        
        return internal.scale(x, y, z);
    }
    
    public static Matrix3f scale(float xyz, Matrix3f dest) {
        
        return internal.scale(xyz, dest);
    }
    
    public static Matrix3f scale(float xyz) {
        
        return internal.scale(xyz);
    }
    
    public static Matrix3f scaleLocal(float x, float y, float z, Matrix3f dest) {
        
        return internal.scaleLocal(x, y, z, dest);
    }
    
    public static Matrix3f scaleLocal(float x, float y, float z) {
        
        return internal.scaleLocal(x, y, z);
    }
    
    public static Matrix3f scaling(float factor) {
        
        return internal.scaling(factor);
    }
    
    public static Matrix3f scaling(float x, float y, float z) {
        
        return internal.scaling(x, y, z);
    }
    
    public static Matrix3f scaling(Vector3fc xyz) {
        
        return internal.scaling(xyz);
    }
    
    public static Matrix3f rotation(float angle, Vector3fc axis) {
        
        return internal.rotation(angle, axis);
    }
    
    public static Matrix3f rotation(AxisAngle4f axisAngle) {
        
        return internal.rotation(axisAngle);
    }
    
    public static Matrix3f rotation(float angle, float x, float y, float z) {
        
        return internal.rotation(angle, x, y, z);
    }
    
    public static Matrix3f rotationX(float ang) {
        
        return internal.rotationX(ang);
    }
    
    public static Matrix3f rotationY(float ang) {
        
        return internal.rotationY(ang);
    }
    
    public static Matrix3f rotationZ(float ang) {
        
        return internal.rotationZ(ang);
    }
    
    public static Matrix3f rotationXYZ(float angleX, float angleY, float angleZ) {
        
        return internal.rotationXYZ(angleX, angleY, angleZ);
    }
    
    public static Matrix3f rotationZYX(float angleZ, float angleY, float angleX) {
        
        return internal.rotationZYX(angleZ, angleY, angleX);
    }
    
    public static Matrix3f rotationYXZ(float angleY, float angleX, float angleZ) {
        
        return internal.rotationYXZ(angleY, angleX, angleZ);
    }
    
    public static Matrix3f rotation(Quaternionfc quat) {
        
        return internal.rotation(quat);
    }
    
    public static Vector3f transform(Vector3f v) {
        
        return internal.transform(v);
    }
    
    public static Vector3f transform(Vector3fc v, Vector3f dest) {
        
        return internal.transform(v, dest);
    }
    
    public static Vector3f transform(float x, float y, float z, Vector3f dest) {
        
        return internal.transform(x, y, z, dest);
    }
    
    public static Vector3f transformTranspose(Vector3f v) {
        
        return internal.transformTranspose(v);
    }
    
    public static Vector3f transformTranspose(Vector3fc v, Vector3f dest) {
        
        return internal.transformTranspose(v, dest);
    }
    
    public static Vector3f transformTranspose(float x, float y, float z, Vector3f dest) {
        
        return internal.transformTranspose(x, y, z, dest);
    }
    
    public static void writeExternal(ObjectOutput out) throws IOException {
        
        internal.writeExternal(out);
    }
    
    public static void readExternal(ObjectInput in) throws IOException {
        
        internal.readExternal(in);
    }
    
    public static Matrix3f rotateX(float ang, Matrix3f dest) {
        
        return internal.rotateX(ang, dest);
    }
    
    public static Matrix3f rotateX(float ang) {
        
        return internal.rotateX(ang);
    }
    
    public static Matrix3f rotateY(float ang, Matrix3f dest) {
        
        return internal.rotateY(ang, dest);
    }
    
    public static Matrix3f rotateY(float ang) {
        
        return internal.rotateY(ang);
    }
    
    public static Matrix3f rotateZ(float ang, Matrix3f dest) {
        
        return internal.rotateZ(ang, dest);
    }
    
    public static Matrix3f rotateZ(float ang) {
        
        return internal.rotateZ(ang);
    }
    
    public static Matrix3f rotateXYZ(Vector3f angles) {
        
        return internal.rotateXYZ(angles);
    }
    
    public static Matrix3f rotateXYZ(float angleX, float angleY, float angleZ) {
        
        return internal.rotateXYZ(angleX, angleY, angleZ);
    }
    
    public static Matrix3f rotateXYZ(float angleX, float angleY, float angleZ, Matrix3f dest) {
        
        return internal.rotateXYZ(angleX, angleY, angleZ, dest);
    }
    
    public static Matrix3f rotateZYX(Vector3f angles) {
        
        return internal.rotateZYX(angles);
    }
    
    public static Matrix3f rotateZYX(float angleZ, float angleY, float angleX) {
        
        return internal.rotateZYX(angleZ, angleY, angleX);
    }
    
    public static Matrix3f rotateZYX(float angleZ, float angleY, float angleX, Matrix3f dest) {
        
        return internal.rotateZYX(angleZ, angleY, angleX, dest);
    }
    
    public static Matrix3f rotateYXZ(Vector3f angles) {
        
        return internal.rotateYXZ(angles);
    }
    
    public static Matrix3f rotateYXZ(float angleY, float angleX, float angleZ) {
        
        return internal.rotateYXZ(angleY, angleX, angleZ);
    }
    
    public static Matrix3f rotateYXZ(float angleY, float angleX, float angleZ, Matrix3f dest) {
        
        return internal.rotateYXZ(angleY, angleX, angleZ, dest);
    }
    
    public static Matrix3f rotate(float ang, float x, float y, float z) {
        
        return internal.rotate(ang, x, y, z);
    }
    
    public static Matrix3f rotate(float ang, float x, float y, float z, Matrix3f dest) {
        
        return internal.rotate(ang, x, y, z, dest);
    }
    
    public static Matrix3f rotateLocal(float ang, float x, float y, float z, Matrix3f dest) {
        
        return internal.rotateLocal(ang, x, y, z, dest);
    }
    
    public static Matrix3f rotateLocal(float ang, float x, float y, float z) {
        
        return internal.rotateLocal(ang, x, y, z);
    }
    
    public static Matrix3f rotateLocalX(float ang, Matrix3f dest) {
        
        return internal.rotateLocalX(ang, dest);
    }
    
    public static Matrix3f rotateLocalX(float ang) {
        
        return internal.rotateLocalX(ang);
    }
    
    public static Matrix3f rotateLocalY(float ang, Matrix3f dest) {
        
        return internal.rotateLocalY(ang, dest);
    }
    
    public static Matrix3f rotateLocalY(float ang) {
        
        return internal.rotateLocalY(ang);
    }
    
    public static Matrix3f rotateLocalZ(float ang, Matrix3f dest) {
        
        return internal.rotateLocalZ(ang, dest);
    }
    
    public static Matrix3f rotateLocalZ(float ang) {
        
        return internal.rotateLocalZ(ang);
    }
    
    public static Matrix3f rotate(Quaternionfc quat) {
        
        return internal.rotate(quat);
    }
    
    public static Matrix3f rotate(Quaternionfc quat, Matrix3f dest) {
        
        return internal.rotate(quat, dest);
    }
    
    public static Matrix3f rotateLocal(Quaternionfc quat, Matrix3f dest) {
        
        return internal.rotateLocal(quat, dest);
    }
    
    public static Matrix3f rotateLocal(Quaternionfc quat) {
        
        return internal.rotateLocal(quat);
    }
    
    public static Matrix3f rotate(AxisAngle4f axisAngle) {
        
        return internal.rotate(axisAngle);
    }
    
    public static Matrix3f rotate(AxisAngle4f axisAngle, Matrix3f dest) {
        
        return internal.rotate(axisAngle, dest);
    }
    
    public static Matrix3f rotate(float angle, Vector3fc axis) {
        
        return internal.rotate(angle, axis);
    }
    
    public static Matrix3f rotate(float angle, Vector3fc axis, Matrix3f dest) {
        
        return internal.rotate(angle, axis, dest);
    }
    
    public static Matrix3f lookAlong(Vector3fc dir, Vector3fc up) {
        
        return internal.lookAlong(dir, up);
    }
    
    public static Matrix3f lookAlong(Vector3fc dir, Vector3fc up, Matrix3f dest) {
        
        return internal.lookAlong(dir, up, dest);
    }
    
    public static Matrix3f lookAlong(float dirX, float dirY, float dirZ, float upX, float upY, float upZ, Matrix3f dest) {
        
        return internal.lookAlong(dirX, dirY, dirZ, upX, upY, upZ, dest);
    }
    
    public static Matrix3f lookAlong(float dirX, float dirY, float dirZ, float upX, float upY, float upZ) {
        
        return internal.lookAlong(dirX, dirY, dirZ, upX, upY, upZ);
    }
    
    public static Matrix3f setLookAlong(Vector3fc dir, Vector3fc up) {
        
        return internal.setLookAlong(dir, up);
    }
    
    public static Matrix3f setLookAlong(float dirX, float dirY, float dirZ, float upX, float upY, float upZ) {
        
        return internal.setLookAlong(dirX, dirY, dirZ, upX, upY, upZ);
    }
    
    public static Vector3f getRow(int row, Vector3f dest) throws IndexOutOfBoundsException {
        
        return internal.getRow(row, dest);
    }
    
    public static Matrix3f setRow(int row, Vector3fc src) throws IndexOutOfBoundsException {
        
        return internal.setRow(row, src);
    }
    
    public static Matrix3f setRow(int row, float x, float y, float z) throws IndexOutOfBoundsException {
        
        return internal.setRow(row, x, y, z);
    }
    
    public static Vector3f getColumn(int column, Vector3f dest) throws IndexOutOfBoundsException {
        
        return internal.getColumn(column, dest);
    }
    
    public static Matrix3f setColumn(int column, Vector3fc src) throws IndexOutOfBoundsException {
        
        return internal.setColumn(column, src);
    }
    
    public static Matrix3f setColumn(int column, float x, float y, float z) throws IndexOutOfBoundsException {
        
        return internal.setColumn(column, x, y, z);
    }
    
    public static float get(int column, int row) {
        
        return internal.get(column, row);
    }
    
    public static Matrix3f set(int column, int row, float value) {
        
        return internal.set(column, row, value);
    }
    
    public static float getRowColumn(int row, int column) {
        
        return internal.getRowColumn(row, column);
    }
    
    public static Matrix3f setRowColumn(int row, int column, float value) {
        
        return internal.setRowColumn(row, column, value);
    }
    
    public static Matrix3f normal() {
        
        return internal.normal();
    }
    
    public static Matrix3f normal(Matrix3f dest) {
        
        return internal.normal(dest);
    }
    
    public static Matrix3f cofactor() {
        
        return internal.cofactor();
    }
    
    public static Matrix3f cofactor(Matrix3f dest) {
        
        return internal.cofactor(dest);
    }
    
    public static Vector3f getScale(Vector3f dest) {
        
        return internal.getScale(dest);
    }
    
    public static Vector3f positiveZ(Vector3f dir) {
        
        return internal.positiveZ(dir);
    }
    
    public static Vector3f normalizedPositiveZ(Vector3f dir) {
        
        return internal.normalizedPositiveZ(dir);
    }
    
    public static Vector3f positiveX(Vector3f dir) {
        
        return internal.positiveX(dir);
    }
    
    public static Vector3f normalizedPositiveX(Vector3f dir) {
        
        return internal.normalizedPositiveX(dir);
    }
    
    public static Vector3f positiveY(Vector3f dir) {
        
        return internal.positiveY(dir);
    }
    
    public static Vector3f normalizedPositiveY(Vector3f dir) {
        
        return internal.normalizedPositiveY(dir);
    }
    
    public static boolean equals(Matrix3fc m, float delta) {
        
        return internal.equals(m, delta);
    }
    
    public static Matrix3f swap(Matrix3f other) {
        
        return internal.swap(other);
    }
    
    public static Matrix3f add(Matrix3fc other) {
        
        return internal.add(other);
    }
    
    public static Matrix3f add(Matrix3fc other, Matrix3f dest) {
        
        return internal.add(other, dest);
    }
    
    public static Matrix3f sub(Matrix3fc subtrahend) {
        
        return internal.sub(subtrahend);
    }
    
    public static Matrix3f sub(Matrix3fc subtrahend, Matrix3f dest) {
        
        return internal.sub(subtrahend, dest);
    }
    
    public static Matrix3f mulComponentWise(Matrix3fc other) {
        
        return internal.mulComponentWise(other);
    }
    
    public static Matrix3f mulComponentWise(Matrix3fc other, Matrix3f dest) {
        
        return internal.mulComponentWise(other, dest);
    }
    
    public static Matrix3f setSkewSymmetric(float a, float b, float c) {
        
        return internal.setSkewSymmetric(a, b, c);
    }
    
    public static Matrix3f lerp(Matrix3fc other, float t) {
        
        return internal.lerp(other, t);
    }
    
    public static Matrix3f lerp(Matrix3fc other, float t, Matrix3f dest) {
        
        return internal.lerp(other, t, dest);
    }
    
    public static Matrix3f rotateTowards(Vector3fc direction, Vector3fc up, Matrix3f dest) {
        
        return internal.rotateTowards(direction, up, dest);
    }
    
    public static Matrix3f rotateTowards(Vector3fc direction, Vector3fc up) {
        
        return internal.rotateTowards(direction, up);
    }
    
    public static Matrix3f rotateTowards(float dirX, float dirY, float dirZ, float upX, float upY, float upZ) {
        
        return internal.rotateTowards(dirX, dirY, dirZ, upX, upY, upZ);
    }
    
    public static Matrix3f rotateTowards(float dirX, float dirY, float dirZ, float upX, float upY, float upZ, Matrix3f dest) {
        
        return internal.rotateTowards(dirX, dirY, dirZ, upX, upY, upZ, dest);
    }
    
    public static Matrix3f rotationTowards(Vector3fc dir, Vector3fc up) {
        
        return internal.rotationTowards(dir, up);
    }
    
    public static Matrix3f rotationTowards(float dirX, float dirY, float dirZ, float upX, float upY, float upZ) {
        
        return internal.rotationTowards(dirX, dirY, dirZ, upX, upY, upZ);
    }
    
    public static Vector3f getEulerAnglesZYX(Vector3f dest) {
        
        return internal.getEulerAnglesZYX(dest);
    }
    
    public static Vector3f getEulerAnglesXYZ(Vector3f dest) {
        
        return internal.getEulerAnglesXYZ(dest);
    }
    
    public static Matrix3f obliqueZ(float a, float b) {
        
        return internal.obliqueZ(a, b);
    }
    
    public static Matrix3f obliqueZ(float a, float b, Matrix3f dest) {
        
        return internal.obliqueZ(a, b, dest);
    }
    
    public static Matrix3f reflect(float nx, float ny, float nz, Matrix3f dest) {
        
        return internal.reflect(nx, ny, nz, dest);
    }
    
    public static Matrix3f reflect(float nx, float ny, float nz) {
        
        return internal.reflect(nx, ny, nz);
    }
    
    public static Matrix3f reflect(Vector3fc normal) {
        
        return internal.reflect(normal);
    }
    
    public static Matrix3f reflect(Quaternionfc orientation) {
        
        return internal.reflect(orientation);
    }
    
    public static Matrix3f reflect(Quaternionfc orientation, Matrix3f dest) {
        
        return internal.reflect(orientation, dest);
    }
    
    public static Matrix3f reflect(Vector3fc normal, Matrix3f dest) {
        
        return internal.reflect(normal, dest);
    }
    
    public static Matrix3f reflection(float nx, float ny, float nz) {
        
        return internal.reflection(nx, ny, nz);
    }
    
    public static Matrix3f reflection(Vector3fc normal) {
        
        return internal.reflection(normal);
    }
    
    public static Matrix3f reflection(Quaternionfc orientation) {
        
        return internal.reflection(orientation);
    }
    
    public static boolean isFinite() {
        
        return internal.isFinite();
    }
    
    public static float quadraticFormProduct(float x, float y, float z) {
        
        return internal.quadraticFormProduct(x, y, z);
    }
    
    public static float quadraticFormProduct(Vector3fc v) {
        
        return internal.quadraticFormProduct(v);
    }
    
    public static Matrix3f mapXZY() {
        
        return internal.mapXZY();
    }
    
    public static Matrix3f mapXZY(Matrix3f dest) {
        
        return internal.mapXZY(dest);
    }
    
    public static Matrix3f mapXZnY() {
        
        return internal.mapXZnY();
    }
    
    public static Matrix3f mapXZnY(Matrix3f dest) {
        
        return internal.mapXZnY(dest);
    }
    
    public static Matrix3f mapXnYnZ() {
        
        return internal.mapXnYnZ();
    }
    
    public static Matrix3f mapXnYnZ(Matrix3f dest) {
        
        return internal.mapXnYnZ(dest);
    }
    
    public static Matrix3f mapXnZY() {
        
        return internal.mapXnZY();
    }
    
    public static Matrix3f mapXnZY(Matrix3f dest) {
        
        return internal.mapXnZY(dest);
    }
    
    public static Matrix3f mapXnZnY() {
        
        return internal.mapXnZnY();
    }
    
    public static Matrix3f mapXnZnY(Matrix3f dest) {
        
        return internal.mapXnZnY(dest);
    }
    
    public static Matrix3f mapYXZ() {
        
        return internal.mapYXZ();
    }
    
    public static Matrix3f mapYXZ(Matrix3f dest) {
        
        return internal.mapYXZ(dest);
    }
    
    public static Matrix3f mapYXnZ() {
        
        return internal.mapYXnZ();
    }
    
    public static Matrix3f mapYXnZ(Matrix3f dest) {
        
        return internal.mapYXnZ(dest);
    }
    
    public static Matrix3f mapYZX() {
        
        return internal.mapYZX();
    }
    
    public static Matrix3f mapYZX(Matrix3f dest) {
        
        return internal.mapYZX(dest);
    }
    
    public static Matrix3f mapYZnX() {
        
        return internal.mapYZnX();
    }
    
    public static Matrix3f mapYZnX(Matrix3f dest) {
        
        return internal.mapYZnX(dest);
    }
    
    public static Matrix3f mapYnXZ() {
        
        return internal.mapYnXZ();
    }
    
    public static Matrix3f mapYnXZ(Matrix3f dest) {
        
        return internal.mapYnXZ(dest);
    }
    
    public static Matrix3f mapYnXnZ() {
        
        return internal.mapYnXnZ();
    }
    
    public static Matrix3f mapYnXnZ(Matrix3f dest) {
        
        return internal.mapYnXnZ(dest);
    }
    
    public static Matrix3f mapYnZX() {
        
        return internal.mapYnZX();
    }
    
    public static Matrix3f mapYnZX(Matrix3f dest) {
        
        return internal.mapYnZX(dest);
    }
    
    public static Matrix3f mapYnZnX() {
        
        return internal.mapYnZnX();
    }
    
    public static Matrix3f mapYnZnX(Matrix3f dest) {
        
        return internal.mapYnZnX(dest);
    }
    
    public static Matrix3f mapZXY() {
        
        return internal.mapZXY();
    }
    
    public static Matrix3f mapZXY(Matrix3f dest) {
        
        return internal.mapZXY(dest);
    }
    
    public static Matrix3f mapZXnY() {
        
        return internal.mapZXnY();
    }
    
    public static Matrix3f mapZXnY(Matrix3f dest) {
        
        return internal.mapZXnY(dest);
    }
    
    public static Matrix3f mapZYX() {
        
        return internal.mapZYX();
    }
    
    public static Matrix3f mapZYX(Matrix3f dest) {
        
        return internal.mapZYX(dest);
    }
    
    public static Matrix3f mapZYnX() {
        
        return internal.mapZYnX();
    }
    
    public static Matrix3f mapZYnX(Matrix3f dest) {
        
        return internal.mapZYnX(dest);
    }
    
    public static Matrix3f mapZnXY() {
        
        return internal.mapZnXY();
    }
    
    public static Matrix3f mapZnXY(Matrix3f dest) {
        
        return internal.mapZnXY(dest);
    }
    
    public static Matrix3f mapZnXnY() {
        
        return internal.mapZnXnY();
    }
    
    public static Matrix3f mapZnXnY(Matrix3f dest) {
        
        return internal.mapZnXnY(dest);
    }
    
    public static Matrix3f mapZnYX() {
        
        return internal.mapZnYX();
    }
    
    public static Matrix3f mapZnYX(Matrix3f dest) {
        
        return internal.mapZnYX(dest);
    }
    
    public static Matrix3f mapZnYnX() {
        
        return internal.mapZnYnX();
    }
    
    public static Matrix3f mapZnYnX(Matrix3f dest) {
        
        return internal.mapZnYnX(dest);
    }
    
    public static Matrix3f mapnXYnZ() {
        
        return internal.mapnXYnZ();
    }
    
    public static Matrix3f mapnXYnZ(Matrix3f dest) {
        
        return internal.mapnXYnZ(dest);
    }
    
    public static Matrix3f mapnXZY() {
        
        return internal.mapnXZY();
    }
    
    public static Matrix3f mapnXZY(Matrix3f dest) {
        
        return internal.mapnXZY(dest);
    }
    
    public static Matrix3f mapnXZnY() {
        
        return internal.mapnXZnY();
    }
    
    public static Matrix3f mapnXZnY(Matrix3f dest) {
        
        return internal.mapnXZnY(dest);
    }
    
    public static Matrix3f mapnXnYZ() {
        
        return internal.mapnXnYZ();
    }
    
    public static Matrix3f mapnXnYZ(Matrix3f dest) {
        
        return internal.mapnXnYZ(dest);
    }
    
    public static Matrix3f mapnXnYnZ() {
        
        return internal.mapnXnYnZ();
    }
    
    public static Matrix3f mapnXnYnZ(Matrix3f dest) {
        
        return internal.mapnXnYnZ(dest);
    }
    
    public static Matrix3f mapnXnZY() {
        
        return internal.mapnXnZY();
    }
    
    public static Matrix3f mapnXnZY(Matrix3f dest) {
        
        return internal.mapnXnZY(dest);
    }
    
    public static Matrix3f mapnXnZnY() {
        
        return internal.mapnXnZnY();
    }
    
    public static Matrix3f mapnXnZnY(Matrix3f dest) {
        
        return internal.mapnXnZnY(dest);
    }
    
    public static Matrix3f mapnYXZ() {
        
        return internal.mapnYXZ();
    }
    
    public static Matrix3f mapnYXZ(Matrix3f dest) {
        
        return internal.mapnYXZ(dest);
    }
    
    public static Matrix3f mapnYXnZ() {
        
        return internal.mapnYXnZ();
    }
    
    public static Matrix3f mapnYXnZ(Matrix3f dest) {
        
        return internal.mapnYXnZ(dest);
    }
    
    public static Matrix3f mapnYZX() {
        
        return internal.mapnYZX();
    }
    
    public static Matrix3f mapnYZX(Matrix3f dest) {
        
        return internal.mapnYZX(dest);
    }
    
    public static Matrix3f mapnYZnX() {
        
        return internal.mapnYZnX();
    }
    
    public static Matrix3f mapnYZnX(Matrix3f dest) {
        
        return internal.mapnYZnX(dest);
    }
    
    public static Matrix3f mapnYnXZ() {
        
        return internal.mapnYnXZ();
    }
    
    public static Matrix3f mapnYnXZ(Matrix3f dest) {
        
        return internal.mapnYnXZ(dest);
    }
    
    public static Matrix3f mapnYnXnZ() {
        
        return internal.mapnYnXnZ();
    }
    
    public static Matrix3f mapnYnXnZ(Matrix3f dest) {
        
        return internal.mapnYnXnZ(dest);
    }
    
    public static Matrix3f mapnYnZX() {
        
        return internal.mapnYnZX();
    }
    
    public static Matrix3f mapnYnZX(Matrix3f dest) {
        
        return internal.mapnYnZX(dest);
    }
    
    public static Matrix3f mapnYnZnX() {
        
        return internal.mapnYnZnX();
    }
    
    public static Matrix3f mapnYnZnX(Matrix3f dest) {
        
        return internal.mapnYnZnX(dest);
    }
    
    public static Matrix3f mapnZXY() {
        
        return internal.mapnZXY();
    }
    
    public static Matrix3f mapnZXY(Matrix3f dest) {
        
        return internal.mapnZXY(dest);
    }
    
    public static Matrix3f mapnZXnY() {
        
        return internal.mapnZXnY();
    }
    
    public static Matrix3f mapnZXnY(Matrix3f dest) {
        
        return internal.mapnZXnY(dest);
    }
    
    public static Matrix3f mapnZYX() {
        
        return internal.mapnZYX();
    }
    
    public static Matrix3f mapnZYX(Matrix3f dest) {
        
        return internal.mapnZYX(dest);
    }
    
    public static Matrix3f mapnZYnX() {
        
        return internal.mapnZYnX();
    }
    
    public static Matrix3f mapnZYnX(Matrix3f dest) {
        
        return internal.mapnZYnX(dest);
    }
    
    public static Matrix3f mapnZnXY() {
        
        return internal.mapnZnXY();
    }
    
    public static Matrix3f mapnZnXY(Matrix3f dest) {
        
        return internal.mapnZnXY(dest);
    }
    
    public static Matrix3f mapnZnXnY() {
        
        return internal.mapnZnXnY();
    }
    
    public static Matrix3f mapnZnXnY(Matrix3f dest) {
        
        return internal.mapnZnXnY(dest);
    }
    
    public static Matrix3f mapnZnYX() {
        
        return internal.mapnZnYX();
    }
    
    public static Matrix3f mapnZnYX(Matrix3f dest) {
        
        return internal.mapnZnYX(dest);
    }
    
    public static Matrix3f mapnZnYnX() {
        
        return internal.mapnZnYnX();
    }
    
    public static Matrix3f mapnZnYnX(Matrix3f dest) {
        
        return internal.mapnZnYnX(dest);
    }
    
    public static Matrix3f negateX() {
        
        return internal.negateX();
    }
    
    public static Matrix3f negateX(Matrix3f dest) {
        
        return internal.negateX(dest);
    }
    
    public static Matrix3f negateY() {
        
        return internal.negateY();
    }
    
    public static Matrix3f negateY(Matrix3f dest) {
        
        return internal.negateY(dest);
    }
    
    public static Matrix3f negateZ() {
        
        return internal.negateZ();
    }
    
    public static Matrix3f negateZ(Matrix3f dest) {
        
        return internal.negateZ(dest);
    }
    
}
