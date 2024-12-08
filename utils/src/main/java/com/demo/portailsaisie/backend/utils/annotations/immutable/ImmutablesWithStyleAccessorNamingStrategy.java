package com.demo.portailsaisie.backend.utils.annotations.immutable;

import org.mapstruct.ap.spi.DefaultAccessorNamingStrategy;
import org.mapstruct.ap.spi.util.IntrospectorUtils;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.type.TypeKind;

public class ImmutablesWithStyleAccessorNamingStrategy extends DefaultAccessorNamingStrategy{
    public ImmutablesWithStyleAccessorNamingStrategy() {
    }

    public boolean isSetterMethod(ExecutableElement method) {
        String methodName = method.getSimpleName().toString();
        return methodName.startsWith("with") && methodName.length() > 4 && method.getReturnType().getKind() == TypeKind.VOID && !method.getParameters().isEmpty() || super.isSetterMethod(method);
    }

    public String getPropertyName(ExecutableElement getterOrSetterMethod) {
        String methodName = getterOrSetterMethod.getSimpleName().toString();
        return this.isSetterMethod(getterOrSetterMethod) && methodName.startsWith("with") ? IntrospectorUtils.decapitalize(methodName.substring(4)) : super.getPropertyName(getterOrSetterMethod);
    }
}
