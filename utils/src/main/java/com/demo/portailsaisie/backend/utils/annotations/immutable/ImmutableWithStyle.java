package com.demo.portailsaisie.backend.utils.annotations.immutable;

import org.immutables.value.Value;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Value.Style(
        get = {"get*", "is*"},
        init = "with*",
        depluralize = true,
        defaults = @Value.Immutable(
                prehash = true
        ),
        optionalAcceptNullable = true,
        visibility = Value.Style.ImplementationVisibility.PUBLIC
)
public @interface ImmutableWithStyle {
}
