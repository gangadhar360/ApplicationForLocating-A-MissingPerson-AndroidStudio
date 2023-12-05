package com.example.capstoneproject;
//
//import android.content.Intent;
//import android.net.Uri;
//import android.os.Bundle;
//import android.widget.Toast;
//import androidx.appcompat.app.AppCompatActivity;
////import com.google.android.gms.tasks.OnFailureListener;
////import com.google.android.gms.tasks.OnSuccessListener;
////import com.google.firebase.ml.vision.FirebaseVision;
////import com.google.firebase.ml.vision.common.FirebaseVisionImage;
////import com.google.firebase.ml.vision.common.FirebaseVisionPoint;
////import com.google.firebase.ml.vision.face.FirebaseVisionFace;
////import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetector;
////import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetectorOptions;
////import com.google.firebase.ml.vision.face.FirebaseVisionFaceLandmark;
//
//import com.google.android.gms.tasks.OnFailureListener;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//

import androidx.appcompat.app.AppCompatActivity;
public class FaceDetection extends AppCompatActivity {
//
//    private static final float SIMILARITY_THRESHOLD = 0.6f; // Set your similarity threshold
//
//    @Override
//    protected void onCreate( Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        // Set up your layout, views, permissions, etc.
//        // ...
//
//        Intent intent = getIntent();
//        if (intent != null) {
//            String imageUriString = intent.getStringExtra("IMAGE_URI");
//            if (imageUriString != null) {
//                Uri imageUri = Uri.parse(imageUriString);
//                detectFacesAndMatch(imageUri);
//            }
//        }
//    }
//
//    private void detectFacesAndMatch(Uri imageUri)
//    {
//        FirebaseVisionImage image = null;
//        try {
//            image = FirebaseVisionImage.fromFilePath(getApplicationContext(), imageUri);
//
//            FirebaseVisionFaceDetectorOptions options =
//                    new FirebaseVisionFaceDetectorOptions.Builder()
//                            .setPerformanceMode(FirebaseVisionFaceDetectorOptions.FAST)
//                            .setLandmarkMode(FirebaseVisionFaceDetectorOptions.ALL_LANDMARKS)
//                            .setClassificationMode(FirebaseVisionFaceDetectorOptions.ALL_CLASSIFICATIONS)
//                            .setMinFaceSize(0.15f)
//                            .build();
//
//            FirebaseVisionFaceDetector detector = FirebaseVision.getInstance()
//                    .getVisionFaceDetector(options);
//
//            detector.detectInImage(image)
//                    .addOnSuccessListener(new OnSuccessListener<List<FirebaseVisionFace>>() {
//                        @Override
//                        public void onSuccess(List<FirebaseVisionFace> faces) {
//                            // Process the detected faces
//                            // Here, you'd likely iterate through the faces and perform matching with the dataset
//                            for (FirebaseVisionFace face : faces) {
//                                // Calculate facial embeddings for detected face
//                                float[] detectedFaceEmbedding = calculateFaceEmbedding(face);
//
//                                // Compare embeddings with dataset
//                                boolean isMatched = compareWithDataset(detectedFaceEmbedding);
//
//                                // Display result using Toast
//                                Toast.makeText(FaceDetection.this,
//                                        isMatched ? "Match found!" : "No match found!",
//                                        Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    })
//                    .addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(Exception e) {
//                            // Task failed with an exception
//                            e.printStackTrace();
//                        }
//                    });
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//
//
////    private float[] calculateFaceEmbedding(FirebaseVisionFace face) {
////        // Calculate facial embeddings for the detected face using Firebase ML Kit
////        // Convert face information into embedding
////        //return new float[]{}; // Return calculated embeddings
////            float[] embedding = new float[128]; // Placeholder for embeddings (assuming length 128)
////
////            // Get face landmarks
////            //List<FirebaseVisionFaceLandmark> landmarks = face.getLandmarks();//face.getLandmarks();
////            List<FirebaseVisionFaceLandmark> landmarks = face.getLandmarks();
////
////            // Example: Using landmarks to calculate embeddings
////            // You'd need to extract relevant information from landmarks and process it to get embeddings
////            // Example:
////            // Loop through landmarks to gather relevant information
////            for (FirebaseVisionFaceLandmark landmark : landmarks) {
////                FirebaseVisionPoint point = landmark.getPosition(); // Get landmark position
////                // Process landmark information to create embeddings
////                // Example: Aggregate landmark positions, angles, or distances to form embeddings
////                // You might use a specialized library or algorithm to perform this task
////                // Add processed information to the embedding array
////                // For demonstration purposes, filling it with dummy values
////                // Replace this with your actual embedding calculation logic
////                for (int i = 0; i < 128; i++) {
////                    embedding[i] = i * 0.1f; // Assigning dummy values
////                }
////            }
////            return embedding;
////    }
//
//    private float[] calculateFaceEmbedding(FirebaseVisionFace face) {
//        // Calculate facial embeddings for the detected face using Firebase ML Kit
//        float[] embedding = new float[128]; // Placeholder for embeddings (assuming length 128)
//
//        // Get face landmarks
//        FirebaseVisionFaceLandmark leftEye = face.getLandmark(FirebaseVisionFaceLandmark.LEFT_EYE);
//        FirebaseVisionFaceLandmark rightEye = face.getLandmark(FirebaseVisionFaceLandmark.RIGHT_EYE);
//        // Add other landmarks as needed
//
//        // Example: Using landmarks to calculate embeddings
//        // You'd need to extract relevant information from landmarks and process it to get embeddings
//        // Example: Aggregate landmark positions, angles, or distances to form embeddings
//        // You might use a specialized library or algorithm to perform this task
//        // Add processed information to the embedding array
//        // For demonstration purposes, filling it with dummy values
//        // Replace this with your actual embedding calculation logic
//        for (int i = 0; i < 128; i++) {
//            embedding[i] = i * 0.1f; // Assigning dummy values
//        }
//
//        return embedding;
//    }
//
//
//    private boolean compareWithDataset(float[] detectedFaceEmbedding) {
//        // Compare detected face embeddings with the dataset
//        // Load dataset embeddings and perform comparison
//        // Return true if a match is found based on your similarity threshold
//        // ...
//        //return false; // Return the result of the comparison
//
//            // Load dataset embeddings and perform comparison
//            // Compare the detected face embeddings with the dataset embeddings
//            // Return true if a match is found based on your similarity threshold
//
//            // Example: Assuming you have dataset embeddings loaded in a list
//            List<float[]> datasetEmbeddings = loadDatasetEmbeddings(); // Load dataset embeddings
//
//            // Compare the detected face embedding with each dataset embedding
//            for (float[] datasetEmbedding : datasetEmbeddings) {
//                float similarityScore = calculateSimilarity(detectedFaceEmbedding, datasetEmbedding);
//
//                if (similarityScore > SIMILARITY_THRESHOLD) {
//                    return true; // Match found
//                }
//            }
//
//            return false; // No match found
//
//    }
//
//    private float calculateSimilarity(float[] embedding1, float[] embedding2) {
//        // Check if the arrays are of the same length
//        if (embedding1.length != embedding2.length) {
//            throw new IllegalArgumentException("Embeddings must have the same length");
//        }
//
//        float dotProduct = 0;
//        float norm1 = 0;
//        float norm2 = 0;
//
//        for (int i = 0; i < embedding1.length; i++)
//        {
//            dotProduct += embedding1[i] * embedding2[i];
//            norm1 += embedding1[i] * embedding1[i];
//            norm2 += embedding2[i] * embedding2[i];
//        }
//
//        // Calculate the cosine similarity
//        return dotProduct / (float)(Math.sqrt(norm1) * Math.sqrt(norm2));
//    }
//
//
//    private List<float[]> loadDatasetEmbeddings() {
//        // Load your dataset embeddings here
//        // Replace this with your actual logic for loading embeddings from your dataset
//        List<float[]> datasetEmbeddings = new ArrayList<>();
//
//        // Add example embeddings (replace with your actual embeddings)
//        float[] exampleEmbedding1 = { /* embedding values */ };
//        float[] exampleEmbedding2 = { /* embedding values */ };
//
//        datasetEmbeddings.add(exampleEmbedding1);
//        datasetEmbeddings.add(exampleEmbedding2);
//
//        return datasetEmbeddings;
//    }
//
}
