package com.lzy.yrouter.compiler;

import com.google.auto.service.AutoService;
import com.lzy.yrouter.annotation.Module;
import com.lzy.yrouter.annotation.Route;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author Lizhengyu
 * @description
 * @date 2019/4/11 14:29
 **/
@AutoService(Processor.class)
public class RouterProcessor extends AbstractProcessor {

    private Types mTypeUtils;
    private Elements mElementUtils;
    private Filer mFiler;
    private Messager mMessager;

    public static final String PREFIX = "ModuleRoute_";

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);

        //初始化我们需要的基础工具
        mTypeUtils = processingEnv.getTypeUtils();
        mElementUtils = processingEnv.getElementUtils();
        mFiler = processingEnv.getFiler();
        mMessager = processingEnv.getMessager();
    }

    /**
     * Annotation type
     *
     * @return
     */
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> set = new LinkedHashSet<>();
        set.add(Route.class.getCanonicalName());
        return set;
    }

    /**
     * java version
     * SourceVersion.latestSupported()
     * or SourceVersion.RELEASE_7 if you have to use
     *
     * @return
     */
    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        String moduleName;
        Set<? extends Element> moduleList = roundEnvironment.getElementsAnnotatedWith(Module.class);
        if (moduleList != null && moduleList.size() == 1) {
            Module annotation = moduleList.iterator().next().getAnnotation(Module.class);
            moduleName = PREFIX + annotation.value();
        } else if (moduleList != null && moduleList.size() > 1) {
            mMessager.printMessage(Diagnostic.Kind.NOTE, "Annotation Module has more than one ");
            return false;
        } else {
            mMessager.printMessage(Diagnostic.Kind.NOTE, "Can not find annotation Module");
            return false;
        }

        Set<? extends Element> routeElements = roundEnvironment.getElementsAnnotatedWith(Route.class);

        MethodSpec.Builder initMethod = MethodSpec.methodBuilder("load")
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL, Modifier.STATIC);

        for (Element element : routeElements) {
            loadRoute(initMethod, element);
        }

        TypeSpec moduleRoute = TypeSpec.classBuilder(moduleName)
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addMethod(initMethod.build())
                .build();
        try {
            JavaFile.builder("com.lzy.yrouter.moduleroute", moduleRoute)
                    .build()
                    .writeTo(mFiler);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }


    private void loadRoute(MethodSpec.Builder initMethod, Element element) {
        Route route = element.getAnnotation(Route.class);
        ClassName className;

        if (element.getKind() == ElementKind.CLASS) {
            className = ClassName.get((TypeElement) element);
        } else {
            throw new IllegalArgumentException("Route unknow type");
        }
        for (String format : route.value()) {
            String low = format.toLowerCase();
            if (low.startsWith("http://") || low.startsWith("https://")) {
            } else if (!low.contains("/")) {
                //throw new IllegalArgumentException("Route value must be XXX/XX in:"+className);
            }

            initMethod.addStatement("com.lzy.yrouter.api.RouterCenter.map($S, $T.class)", format, className);
        }
    }
}
