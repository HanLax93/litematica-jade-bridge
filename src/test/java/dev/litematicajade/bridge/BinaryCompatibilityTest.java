package dev.litematicajade.bridge;

import java.io.IOException;
import java.lang.classfile.ClassFile;
import java.lang.classfile.ClassModel;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/** Checks the shipped dependency bytecode, since Mixin method names are not checked by javac. */
class BinaryCompatibilityTest {
    private static ClassModel dependency(String name) throws IOException {
        try (var stream = BinaryCompatibilityTest.class.getClassLoader().getResourceAsStream(name.replace('.', '/') + ".class")) {
            assertNotNull(stream, "Missing runtime dependency: " + name);
            return ClassFile.of().parse(stream.readAllBytes());
        }
    }

    private static void assertMethod(ClassModel model, String name, String descriptor) {
        assertTrue(model.methods().stream().anyMatch(method -> method.methodName().equalsString(name)
                && method.methodType().equalsString(descriptor)), () -> "Missing method " + name + descriptor);
    }

    @Test void jadeTargetHookStillReturnsMinecraftHitResult() throws IOException {
        assertMethod(dependency("snownee.jade.overlay.RayTracing"), "getTarget", "()Lnet/minecraft/world/phys/HitResult;");
    }

    @Test void bothLitematicaHudInjectionPointsExistWithVoidReturn() throws IOException {
        var renderer = dependency("fi.dy.masa.litematica.render.OverlayRenderer");
        String signature = "(Lfi/dy/masa/malilib/render/GuiContext;Lfi/dy/masa/litematica/util/RayTraceUtils$RayTraceWrapper;)V";
        assertMethod(renderer, "renderBlockInfoLines", signature);
        assertMethod(renderer, "renderBlockInfoOverlay", signature);
    }

    @Test void jadeCallbackAcceptsReplacementAccessorAndOriginalContext() throws IOException {
        assertMethod(dependency("snownee.jade.api.callback.JadeRayTraceCallback"), "onRayTrace",
                "(Lnet/minecraft/world/phys/HitResult;Lsnownee/jade/api/Accessor;Lsnownee/jade/api/Accessor;)Lsnownee/jade/api/Accessor;");
    }

    @Test void litematicaMainMenuHookAndMalilibButtonsFieldExist() throws IOException {
        assertMethod(dependency("fi.dy.masa.litematica.gui.GuiMainMenu"), "initGui", "()V");
        var base = dependency("fi.dy.masa.malilib.gui.GuiBase");
        assertTrue(base.fields().stream().anyMatch(field -> field.fieldName().equalsString("buttons")
                && field.fieldType().equalsString("Ljava/util/List;")));
    }
}
