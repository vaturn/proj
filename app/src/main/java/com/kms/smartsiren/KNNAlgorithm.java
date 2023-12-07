package com.kms.smartsiren;

import java.util.ArrayList;
import java.util.List;

class Point {
    double latitude;
    double longitude;

    public Point(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }
}

class DataPoint {
    Point point;
    String label;

    public DataPoint(Point point, String label) {
        this.point = point;
        this.label = label;
    }

    // 맨해튼 거리 계산
    public double calculateManhattanDistance(Point otherPoint) {
        return Math.abs(this.point.latitude - otherPoint.latitude) +
                Math.abs(this.point.longitude - otherPoint.longitude);
    }
}

public class KNNAlgorithm {
    private List<DataPoint> trainingData;
    private static final double RADIUS_THRESHOLD = 5;

    public KNNAlgorithm(List<DataPoint> trainingData) {
        this.trainingData = trainingData;
    }

    // k-NN 알고리즘 예측 메서드 (k = 1)
    public String predict(Point testPoint) {
        if (trainingData.isEmpty()) {
            // 훈련 데이터가 비어있을 때
            return "NEW";
        }

        // 테스트 데이터와의 거리 계산 후 정렬
        List<DataPoint> sortedData = new ArrayList<>(trainingData);
        sortedData.sort((dp1, dp2) -> Double.compare(dp1.calculateManhattanDistance(testPoint),
                dp2.calculateManhattanDistance(testPoint)));

        // 가장 가까운 이웃 선택 (k = 1)
        DataPoint nearestNeighbor = sortedData.get(0);

        // 거리가 r = 0.5 이상이면 UUID 발급
        if (nearestNeighbor.calculateManhattanDistance(testPoint) >= RADIUS_THRESHOLD) {
            return "NEW";
        }

        // 그 외의 경우 가장 가까운 이웃의 레이블 반환
        return (nearestNeighbor.label);
    }
}
