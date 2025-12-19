/*
 * Copyright (c) 2025 cpmn.tech
 *
 * Licensed under the MIT License.
 * You may obtain a copy of the License at
 * https://opensource.org/licenses/MIT
 *
 * This file is part of the VulcanTestFramework project.
 * A QA Automation Project by Claudia Paola Muñoz (cpmn.tech)
 */

package com.vulcan.framework.shared.context;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * ApiClientRegistry manages API client instances for a single Cucumber scenario.
 *
 * <p>Key properties:</p>
 * <ul>
 *   <li><b>Per-scenario</b>: stored inside ScenarioContext</li>
 *   <li><b>Lazy</b>: creates clients only when requested</li>
 *   <li><b>Reuse</b>: returns the same instance for the remainder of the scenario</li>
 *   <li><b>Parallel-safe</b>: no static/global shared client instances</li>
 * </ul>
 *
 * <p>Usage:</p>
 * <pre>{@code
 * ApiClientRegistry registry = ScenarioContext.getOrCreate(
 *     ScenarioKeys.API_CLIENT_REGISTRY,
 *     ApiClientRegistry.class,
 *     ApiClientRegistry::new
 * );
 *
 * UserApiClient client = registry.get(UserApiClient.class, UserApiClient::new);
 * }</pre>
 */
public class ApiClientRegistry {

    private final Map<Class<?>, Object> clients = new HashMap<>();

    /**
     * Returns an existing client for this scenario, or creates it if missing.
     *
     * @param type the class of the client (used as the map key)
     * @param supplier how to create the client if it doesn't exist yet
     * @return the scenario-scoped client instance
     */
    public <T> T get(Class<T> type, Supplier<T> supplier) {
        Objects.requireNonNull(type, "type cannot be null");
        Objects.requireNonNull(supplier, "supplier cannot be null");

        Object existing = clients.get(type);
        if (existing == null) {
            T created = supplier.get();
            clients.put(type, created);
            return created;
        }

        if (!type.isInstance(existing)) {
            throw new IllegalStateException(
                "ApiClientRegistry entry for " + type.getName() + " is not of the expected type."
            );
        }

        return type.cast(existing);
    }

    /**
     * Clears all scenario-scoped API clients.
     * Currently clients don't require explicit closing, but this keeps lifecycle explicit.
     */
    public void clear() {
        clients.clear();
    }

    /** For debug/visibility. */
    public int size() {
        return clients.size();
    }
}