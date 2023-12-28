package com.blamejared.crafttweaker.api.action.base;

/**
 * Represents an action that should be executed on every game reload, instead of only once.
 *
 * <p>Refer to the documentation of {@link IAction} for more information.</p>
 *
 * <p>If the action requires some additional code to be run to correctly rollback some changes, refer to
 * {@link IUndoableAction}.</p>
 *
 * @since 9.1.0
 */
public interface IRuntimeAction extends IAction {}
