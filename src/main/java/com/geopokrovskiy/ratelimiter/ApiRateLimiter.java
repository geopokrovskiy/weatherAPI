package com.geopokrovskiy.ratelimiter;

import com.geopokrovskiy.entity.Status;
import com.geopokrovskiy.exception.ApiKeyException;
import com.geopokrovskiy.exception.ErrorCodes;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.ConsumptionProbe;
import io.github.bucket4j.local.LocalBucketBuilder;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Data
@Component
@AllArgsConstructor
public class ApiRateLimiter {
    private final Map<String, Bucket> buckets = new ConcurrentHashMap<>();

    private Bucket createBucket(Status status) {
        LocalBucketBuilder builder = new LocalBucketBuilder();
        Bandwidth bandwidth;
        if (status.equals(Status.BASIC)) {
            bandwidth = Bandwidth.simple(1, Duration.ofMinutes(1));
            builder.addLimit(bandwidth);
        } else if (status.equals(Status.SILVER)) {
            bandwidth = Bandwidth.simple(10, Duration.ofMinutes(1));
            builder.addLimit(bandwidth);
        } else if (status.equals(Status.GOLD)) {
            bandwidth = Bandwidth.simple(30, Duration.ofMinutes(1));
            builder.addLimit(bandwidth);
        }
        return builder.build();
    }

    public boolean tryConsume(String apiKey, Status status) {
        Bucket bucket = buckets.computeIfAbsent(apiKey, k -> this.createBucket(status));
        if (bucket == null) throw new ApiKeyException("Incorrect API Key", ErrorCodes.INCORRECT_API_KEY);

        ConsumptionProbe probe = bucket.tryConsumeAndReturnRemaining(1);
        return probe.isConsumed();
    }
}
