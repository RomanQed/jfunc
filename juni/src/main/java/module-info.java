/**
 * The {@code com.github.romanqed.juni} module provides unified functional interfaces
 * that combine both synchronous and asynchronous execution models.
 * <p>
 * These interfaces extend both sync (from {@code jfunc}) and async (from {@code jfut}) contracts,
 * and expose introspection methods ({@code isSync()}, {@code isAsync()}, {@code isUni()}) to reflect
 * the *original nature* of the implementation — whether it was designed as synchronous,
 * asynchronous, or both.
 * <p>
 * This abstraction is useful for frameworks and pipelines that want to optimize dispatch logic
 * without sacrificing portability or backward compatibility across Java versions.
 */
module com.github.romanqed.juni {
    // Imports
    requires com.github.romanqed.jfunc;
    requires com.github.romanqed.jsync;
    // Exports
    exports com.github.romanqed.juni;
}
