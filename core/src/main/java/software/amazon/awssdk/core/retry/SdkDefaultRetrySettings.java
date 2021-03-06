/*
 * Copyright 2010-2018 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License").
 * You may not use this file except in compliance with the License.
 * A copy of the License is located at
 *
 *  http://aws.amazon.com/apache2.0
 *
 * or in the "license" file accompanying this file. This file is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package software.amazon.awssdk.core.retry;

import static java.util.Collections.unmodifiableSet;

import java.io.IOException;
import java.time.Duration;
import java.util.HashSet;
import java.util.Set;
import software.amazon.awssdk.annotations.SdkInternalApi;
import software.amazon.awssdk.core.exception.RetryableException;
import software.amazon.awssdk.http.HttpStatusCodes;

@SdkInternalApi
public final class SdkDefaultRetrySettings {

    /**
     * When throttled retries are enabled, each retry attempt will consume this much capacity.
     * Successful retry attempts will release this capacity back to the pool while failed retries
     * will not.  Successful initial (non-retry) requests will always release 1 capacity unit to the
     * pool.
     */
    public static final int RETRY_THROTTLING_COST = 5;

    /**
     * When throttled retries are enabled, this is the total number of subsequent failed retries
     * that may be attempted before retry capacity is fully drained.
     */
    public static final int THROTTLED_RETRIES = 100;

    public static final Duration BASE_DELAY = Duration.ofMillis(100);

    public static final Duration MAX_BACKOFF = Duration.ofMillis(20_000);

    public static final Integer DEFAULT_MAX_RETRIES = 3;

    public static final Set<String> RETRYABLE_ERROR_CODES;
    public static final Set<Integer> RETRYABLE_STATUS_CODES;
    public static final Set<Class<? extends Exception>> RETRYABLE_EXCEPTIONS;

    static {
        Set<String> retryableErrorCodes = new HashSet<>();
        retryableErrorCodes.add("PriorRequestNotComplete");
        RETRYABLE_ERROR_CODES = unmodifiableSet(retryableErrorCodes);

        Set<Integer> retryableStatusCodes = new HashSet<>();
        retryableStatusCodes.add(HttpStatusCodes.INTERNAL_SERVER_ERROR);
        retryableStatusCodes.add(HttpStatusCodes.BAD_GATEWAY);
        retryableStatusCodes.add(HttpStatusCodes.SERVICE_UNAVAILABLE);
        retryableStatusCodes.add(HttpStatusCodes.GATEWAY_TIMEOUT);
        RETRYABLE_STATUS_CODES = unmodifiableSet(retryableStatusCodes);

        Set<Class<? extends Exception>> retryableExceptions = new HashSet<>();
        retryableExceptions.add(RetryableException.class);
        retryableExceptions.add(IOException.class);
        RETRYABLE_EXCEPTIONS = unmodifiableSet(retryableExceptions);
    }

    private SdkDefaultRetrySettings() {}
}
