/**
 * The {@code com.github.romanqed.jfut} module provides a unified abstraction layer
 * over synchronous and asynchronous functional interfaces.
 * <p>
 * It defines a family of {@code Uni*} interfaces that combine both sync and async
 * behaviors into a single contract. These interfaces are especially useful for designing
 * generic execution pipelines, middleware chains, or API layers that can operate
 * transparently on both blocking and non-blocking logic.
 *
 * <p>Features:
 * <ul>
 *     <li>Zero-argument and multi-argument function and runnable types</li>
 *     <li>Support for {@link java.util.concurrent.CompletableFuture}</li>
 *     <li>Runtime introspection of execution mode via {@code isNativeAsync()}</li>
 *     <li>Simple wrappers around {@code jfunc} types</li>
 * </ul>
 *
 * <p>This module depends on {@code com.github.romanqed.jfunc}, which defines
 * the base functional interfaces.
 */
module com.github.romanqed.jfut {
    // Imports
    requires com.github.romanqed.jfunc;
    // Exports
    exports com.github.romanqed.jfut;
}
