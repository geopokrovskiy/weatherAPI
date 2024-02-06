package com.geopokrovskiy.utils;

import com.geopokrovskiy.entity.CloudOctant;
import com.geopokrovskiy.entity.CloudType;
import com.geopokrovskiy.entity.ForecastEntity;
import com.geopokrovskiy.entity.Precipitations;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Random;

@Component
public class RandomForecastGenerator {
    public ForecastEntity generateRandomForecast() {
        Random random = new Random();
        double temperature;
        Precipitations precipitations;
        int windSpeed;
        double windAngle;
        CloudType cloudType;
        CloudOctant cloudOctant;

        double nextDouble;

        nextDouble = random.nextDouble();
        temperature = (nextDouble - 0.5) * 60;

        nextDouble = random.nextDouble();
        if (nextDouble < 0.1 && temperature < 0) {
            precipitations = Precipitations.SNOW;
        } else if (nextDouble > 0.1 && nextDouble < 0.4 && temperature > 0) {
            precipitations = Precipitations.RAIN;
        } else {
            precipitations = Precipitations.NONE;
        }

        nextDouble = random.nextDouble();
        windSpeed = (int) (nextDouble * 100);

        nextDouble = random.nextDouble();
        windAngle = nextDouble * 360;

        nextDouble = random.nextDouble();
        int numberOctant = (int) (nextDouble * CloudOctant.values().length);
        cloudOctant = CloudOctant.values()[numberOctant];

        nextDouble = random.nextDouble();
        int numberCloudType = (int) (nextDouble * CloudType.values().length);
        cloudType = CloudType.values()[numberCloudType];

        ForecastEntity forecast = new ForecastEntity();
        forecast.setTime(LocalDateTime.now());
        forecast.setPrecipitations(precipitations);
        forecast.setTemperature(temperature);
        forecast.setWindSpeed(windSpeed);
        forecast.setWindAngle(windAngle);
        forecast.setCloudOctant(cloudOctant);
        forecast.setCloudType(cloudType);

        return forecast;
    }
}
