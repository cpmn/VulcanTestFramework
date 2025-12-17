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

import org.checkerframework.checker.units.qual.C;

/*
 * ScenarioContext.java is a per-scenario "Memory" store.
 * 
 * - Data is isolated per Cucumber Scenario.
 * - Safe for parallel execution.
 * - Cleared automatically after each Scenario.
 * 
 * Typical usage:
 *  ScenarioContext.put("token", token);
 *  String token = ScenarioContext.get("token");
 */
public final class ScenarioContext {
    private ScenarioContext() {
        // Prevent instantiation
        /* utility class */
    }
    private static final ThreadLocal<Map<String, Object>> STORE = 
            ThreadLocal.withInitial(HashMap::new);

    /* Store a value for the current scenario */
    public static void put(String key, Object value) {
        STORE.get().put(key, value);
    } 

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

    /* Check if a key exist */
    public static boolean contains(String key) {
        return STORE.get().containsKey(key);
    }

    /* Clear the context for the current scenario */
    public static void remove(String key) {
        STORE.get().remove(key);
    }

    /* Clear all data for the current scenario */
    public static void clear() {
        STORE.get().clear();
        STORE.remove();
    }
    
}
