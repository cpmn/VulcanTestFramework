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

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Objects;

/**
 * DataRegistry collects cleanup actions for data created during a scenario.
 *
 * <p>Why this exists:</p>
 * <ul>
 *   <li>Guarantees test independence (each scenario owns its data).</li>
 *   <li>Automates teardown even when scenarios fail.</li>
 *   <li>Supports API, UI, and Hybrid scenarios.</li>
 * </ul>
 *
 * <p>Cleanup order:</p>
 * <ul>
 *   <li>Cleanup runs in LIFO order (reverse creation order).</li>
 *   <li>This helps when entities depend on each other (create user → create order).</li>
 * </ul>
 *
 * Typical usage:
 * <pre>{@code
 * DataRegistry registry = ScenarioContext.getOrCreate(
 *     ScenarioKeys.DATA_REGISTRY, DataRegistry.class, DataRegistry::new
 * );
 *
 * String userId = api.createUser(...);
 * registry.registerCleanup(() -> api.deleteUser(userId));
 * }</pre>
 */
public class DataRegistry {

    /** Cleanup actions stack. Uses LIFO to delete dependencies safely. */
    private final Deque<Runnable> cleanupActions = new ArrayDeque<>();
    
    /**
     * Register a cleanup action to run after the scenario.
     *
     * @param cleanupAction action that deletes/undoes seeded test data
     */

    public void registerCleanup(Runnable cleanupAction) {
        cleanupActions.push(Objects.requireNonNull(cleanupAction, "cleanupAction cannot be null"));
    }

    /**
     * Run all cleanup actions.
     *
     * <p>Safety behavior:</p>
     * <ul>
     *   <li>If one cleanup fails, we log the error and continue with the next one.</li>
     *   <li>This prevents partial cleanup from blocking remaining cleanup steps.</li>
     * </ul>
     */
    public void cleanupAll() {
        while (!cleanupActions.isEmpty()) {
            Runnable action = cleanupActions.pop();
            try {
                action.run();
            } catch (Exception e) {
                // Log and continue with next cleanup action
                System.err.println("[DataRegistry] Cleanup action failed: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    /** @return true if no cleanup actions are registered for this scenario */
    public boolean isEmpty () {
        return cleanupActions.isEmpty();
    }
        
}
