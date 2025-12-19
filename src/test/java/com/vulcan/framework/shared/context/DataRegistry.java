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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * DataRegistry collects cleanup actions for data created during a scenario.
 *
 * Enhancements:
 * - Supports named cleanup actions (e.g., "deleteUser:123")
 * - Runs cleanup in LIFO order (reverse creation order)
 * - Logs each cleanup action start/success/failure
 */
public class DataRegistry {

    private static final Logger logger = LogManager.getLogger(DataRegistry.class);

    /**
     * A cleanup action with a human-readable name.
     * Example: name="deleteUser:123"
     */
    private static final class NamedCleanupAction {
        private final String name;
        private final Runnable action;

        private NamedCleanupAction(String name, Runnable action) {
            this.name = Objects.requireNonNull(name, "name cannot be null").trim();
            if (this.name.isEmpty()) {
                throw new IllegalArgumentException("name cannot be blank");
            }
            this.action = Objects.requireNonNull(action, "action cannot be null");
        }
    }

    /** Cleanup actions stack. Uses LIFO to delete dependencies safely. */
    private final Deque<NamedCleanupAction> cleanupActions = new ArrayDeque<>();

    /**
     * Register a cleanup action using a descriptive name.
     *
     * Example:
     * registry.registerCleanup("deleteUser:" + userId, () -> api.deleteUser(userId));
     *
     * @param name a human-readable action name
     * @param cleanupAction code that deletes/undoes seeded test data
     */
    public void registerCleanup(String name, Runnable cleanupAction) {
        cleanupActions.push(new NamedCleanupAction(name, cleanupAction));
        logger.debug("Registered cleanup action '{}' | totalActions={}", name, cleanupActions.size());
    }

    /**
     * Convenience overload when you don't care about naming.
     * (Still logs something useful.)
     */
    public void registerCleanup(Runnable cleanupAction) {
        registerCleanup("cleanupAction#" + (cleanupActions.size() + 1), cleanupAction);
    }

    /**
     * Run all cleanup actions.
     *
     * Safety behavior:
     * - If one cleanup fails, we log and continue.
     * - We do not rethrow exceptions; teardown is best-effort.
     */
    public void cleanupAll() {
        int total = cleanupActions.size();
        if (total == 0) {
            logger.info("No cleanup actions to execute.");
            return;
        }

        logger.info("Executing {} cleanup action(s) (LIFO order).", total);

        int executed = 0;
        int failed = 0;

        while (!cleanupActions.isEmpty()) {
            NamedCleanupAction next = cleanupActions.pop();

            logger.info("Cleanup START | name='{}'", next.name);
            try {
                next.action.run();
                executed++;
                logger.info("Cleanup OK    | name='{}'", next.name);
            } catch (Exception e) {
                failed++;
                logger.error("Cleanup FAIL  | name='{}' | error={}", next.name, e.getMessage(), e);
            }
        }

        logger.info("Cleanup complete | total={} | executed={} | failed={}", total, executed, failed);
    }

    /** @return true if no cleanup actions are registered for this scenario */
    public boolean isEmpty() {
        return cleanupActions.isEmpty();
    }

    /** @return how many cleanup actions are currently registered */
    public int size() {
        return cleanupActions.size();
    }
}