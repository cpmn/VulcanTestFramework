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

/**
 * ScenarioContext is a per-scenario "memory" store.
 *
 * <p>It enables steps, hooks, and helpers to share data within the same scenario
 * without using static global state.</p>
 *
 * <h3>Key properties</h3>
 * <ul>
 *   <li><b>Scenario-scoped</b>: data is isolated per scenario execution.</li>
 *   <li><b>Parallel-safe</b>: uses ThreadLocal to avoid cross-thread contamination.</li>
 *   <li><b>Lifecycle-managed</b>: cleared in Hooks {@code @After} to prevent leakage.</li>
 * </ul>
 *
 * <h3>Typical usage</h3>
 * <pre>{@code
 * ScenarioContext.put(ScenarioKeys.AUTH_TOKEN, token);
 * String token = ScenarioContext.get(ScenarioKeys.AUTH_TOKEN, String.class);
 * }</pre>
 */
public final class ScenarioContext {
    private ScenarioContext() {
        // Utility class: prevent instantiation
    }
    
    /**
     * ThreadLocal store for the current scenario/thread.
     * Each scenario execution gets its own map instance.
     */
    private static final ThreadLocal<Map<String, Object>> STORE = 
            ThreadLocal.withInitial(HashMap::new);

    /** Store a value for the current scenario. */
    public static void put(String key, Object value) {
        STORE.get().put(key, value);
    } 

    /**
     * Get a typed value for the current scenario.
     *
     * @throws IllegalStateException if the key is missing or the type does not match
     */
    public static <T> T get(String key,Class<T> type) {
        Object value = STORE.get().get(key);
        if (value == null) {
            throw new IllegalStateException("ScenarioContext key not found '" + key + "'");
        }
        if(!type.isInstance(value)) {
            throw new IllegalStateException("ScenarioContext Key '" + key + "' is not of type " + type.getSimpleName());
        }
        return type.cast(value);
    }

    /* Check if a key exist for the current scenario. */
    public static boolean contains(String key) {
        return STORE.get().containsKey(key);
    }

    /** Remove a single key from the current scenario context. */
    public static void remove(String key) {
        STORE.get().remove(key);
    }

    /**
     * Clear all scenario data and remove the ThreadLocal reference.
     * Must be called in Hooks after each scenario to prevent leakage.
     */
    public static void clear() {
        STORE.get().clear();
        STORE.remove();
    }

    /**
     * Get a typed value if present; returns null if missing.
     *
     * @throws IllegalStateException if the key exists but the type does not match
     */
    public static <T> T getOptional(String key, Class<T> type) {
        Object value = STORE.get().get(key);
        if (value == null) return null;
        if (!type.isInstance(value)) {
            throw new IllegalStateException(
                "ScenarioContext key '" + key + "' is not of type " + type.getSimpleName()
            );
        }
        return type.cast(value);
    }

    
    /**
     * Get a typed value if present; otherwise create it, store it, and return it.
     *
     * Typical usage:
     * <pre>{@code
     * DataRegistry registry = ScenarioContext.getOrCreate(
     *     ScenarioKeys.DATA_REGISTRY,
     *     DataRegistry.class,
     *     DataRegistry::new
     * );
     * }</pre>
     *
     * @throws IllegalStateException if the key exists but the type does not match
     */
    public static <T> T getOrCreate(String key, Class<T> type, java.util.function.Supplier<T> supplier) {
        Object value = STORE.get().get(key);
        if (value == null) {
            T created = supplier.get();
            STORE.get().put(key, created);
            return created;           
        }
        if (!type.isInstance(value)) {
            throw new IllegalStateException(
                "ScenarioContext key '" + key + "' is not of type " + type.getSimpleName()
            );
        }
        return type.cast(value);
    }    
}
