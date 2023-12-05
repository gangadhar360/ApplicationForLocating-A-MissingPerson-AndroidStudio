package com.example.capstoneproject;
//
//import com.google.firebase.ml.vision.FirebaseVision;
//import com.google.firebase.ml.vision.common.FirebaseVisionImage;
//import com.google.firebase.ml.vision.face.FirebaseVisionFace;
//import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetector;
//import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetectorOptions;
import android.net.Uri;
import android.os.Bundle;
import java.io.IOException;

public class DetectFaces
{
//    public void detectFacesInImage(Uri imageUri) {
//        FirebaseVisionImage image;
//        try {
//            image = FirebaseVisionImage.fromFilePath(getApplicationContext(), imageUri);
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
//                    .addOnSuccessListener(faces -> {
//                        // Task successful, process the detected faces
//                        for (FirebaseVisionFace face : faces) {
//                            float smileProb = face.getSmilingProbability();
//                            // Get other face attributes and landmarks
//                        }
//                    })
//                    .addOnFailureListener(e -> {
//                        // Task failed with an exception
//                        e.printStackTrace();
//                    });
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//   }
}
