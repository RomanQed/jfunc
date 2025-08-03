/**
 * Provides functional asynchronous interfaces (arity-0 to arity-2) based on
 * {@link java.util.concurrent.CompletableFuture}, designed to mirror and complement
 * the synchronous interfaces from {@code jfunc}.
 *
 * <p>Includes utility classes for async execution and integration helpers.
 * Intended as a lightweight base for portable and composable async logic,
 * with support for future migration to Project Loom.
 */
module com.github.romanqed.jsync {
    // Imports
    requires com.github.romanqed.jfunc;
    // Exports
    exports com.github.romanqed.jsync;
}
