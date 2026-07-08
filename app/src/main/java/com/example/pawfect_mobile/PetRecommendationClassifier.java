package com.example.pawfect_mobile;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.util.Log;

import org.tensorflow.lite.InterpreterApi;
import org.tensorflow.lite.InterpreterFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class PetRecommendationClassifier {

    private static final String TAG = "PetClassifier";
    private static final String MODEL_FILE = "pet_recommendation_model.tflite";

    private InterpreterApi interpreter;
    private boolean usingTfliteModel = false;

    private final String[] labels = {"Dog", "Cat", "Rabbit", "Bird", "Fish"};

    public PetRecommendationClassifier(Context context) {
        try {
            MappedByteBuffer modelBuffer = loadModelFile(context);
            interpreter = new InterpreterFactory().create(
                    modelBuffer,
                    new InterpreterApi.Options()
            );
            usingTfliteModel = true;
        } catch (Exception e) {
            usingTfliteModel = false;
            Log.e(TAG, "TFLite model not loaded. Using rule-based fallback.", e);
        }
    }

    private MappedByteBuffer loadModelFile(Context context) throws IOException {
        AssetFileDescriptor fileDescriptor = context.getAssets().openFd(MODEL_FILE);

        FileInputStream inputStream = new FileInputStream(fileDescriptor.getFileDescriptor());
        FileChannel fileChannel = inputStream.getChannel();

        long startOffset = fileDescriptor.getStartOffset();
        long declaredLength = fileDescriptor.getDeclaredLength();

        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength);
    }

    public PredictionResult predict(float[] features) {
        float[][] input = new float[1][10];
        float[][] output = new float[1][5];

        for (int i = 0; i < 10; i++) {
            input[0][i] = features[i];
        }

        if (interpreter != null) {
            interpreter.run(input, output);
        } else {
            output[0] = ruleBasedPrediction(features);
        }

        int bestIndex = getMaxIndex(output[0]);
        float confidence = output[0][bestIndex] * 100f;

        return new PredictionResult(
                labels[bestIndex],
                confidence,
                usingTfliteModel,
                output[0]
        );
    }

    private float[] ruleBasedPrediction(float[] f) {
        float livingSpace = f[0];
        float activity = f[1];
        float time = f[2];
        float allergies = f[3];
        float children = f[4];
        float budget = f[5];
        float grooming = f[6];
        float noise = f[7];
        float experience = f[8];
        float size = f[9];

        float dog = 0.25f
                + livingSpace * 0.20f
                + activity * 0.25f
                + time * 0.20f
                + budget * 0.15f
                + children * 0.10f
                + experience * 0.10f
                + size * 0.10f
                - allergies * 0.10f;

        float cat = 0.25f
                + (1f - livingSpace) * 0.15f
                + mediumScore(time) * 0.20f
                + (1f - allergies) * 0.15f
                + mediumScore(budget) * 0.15f
                + (1f - noise) * 0.10f
                + (1f - size) * 0.10f;

        float rabbit = 0.25f
                + (1f - livingSpace) * 0.15f
                + (1f - activity) * 0.15f
                + mediumScore(time) * 0.20f
                + (1f - noise) * 0.15f
                + mediumScore(budget) * 0.10f
                + (1f - size) * 0.10f;

        float bird = 0.25f
                + mediumScore(livingSpace) * 0.10f
                + mediumScore(activity) * 0.10f
                + mediumScore(time) * 0.15f
                + noise * 0.20f
                + mediumScore(budget) * 0.10f
                + experience * 0.10f
                + (1f - size) * 0.10f;

        float fish = 0.25f
                + (1f - livingSpace) * 0.15f
                + (1f - activity) * 0.20f
                + (1f - time) * 0.20f
                + allergies * 0.20f
                + (1f - budget) * 0.10f
                + (1f - grooming) * 0.10f
                + (1f - noise) * 0.10f
                + (1f - size) * 0.10f;

        float[] scores = {dog, cat, rabbit, bird, fish};
        return normalize(scores);
    }

    private float mediumScore(float value) {
        return 1f - Math.abs(value - 0.5f) * 2f;
    }

    private float[] normalize(float[] scores) {
        float sum = 0f;

        for (float score : scores) {
            if (score < 0f) {
                score = 0f;
            }
            sum += score;
        }

        if (sum == 0f) {
            return new float[]{0.2f, 0.2f, 0.2f, 0.2f, 0.2f};
        }

        float[] normalized = new float[scores.length];

        for (int i = 0; i < scores.length; i++) {
            normalized[i] = scores[i] / sum;
        }

        return normalized;
    }

    private int getMaxIndex(float[] values) {
        int maxIndex = 0;
        float maxValue = values[0];

        for (int i = 1; i < values.length; i++) {
            if (values[i] > maxValue) {
                maxValue = values[i];
                maxIndex = i;
            }
        }

        return maxIndex;
    }

    public void close() {
        if (interpreter != null) {
            interpreter.close();
            interpreter = null;
        }
    }

    public static class PredictionResult {
        public final String predictedCategory;
        public final float confidence;
        public final boolean usedTfliteModel;
        public final float[] allScores;

        public PredictionResult(
                String predictedCategory,
                float confidence,
                boolean usedTfliteModel,
                float[] allScores
        ) {
            this.predictedCategory = predictedCategory;
            this.confidence = confidence;
            this.usedTfliteModel = usedTfliteModel;
            this.allScores = allScores;
        }
    }
}